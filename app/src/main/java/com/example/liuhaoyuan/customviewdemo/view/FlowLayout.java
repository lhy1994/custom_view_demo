package com.example.liuhaoyuan.customviewdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by liuhaoyuan on 2017/1/1.
 */

public class FlowLayout extends ViewGroup {
    private int mUsedWidth=0;
    private int mHorizontalSpacing=10;
    private int mVerticalSpacing=5;
    private Line mLine;
    private ArrayList<Line> lines=new ArrayList<>();
    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width=MeasureSpec.getSize(widthMeasureSpec)-getPaddingLeft()-getPaddingRight();
        int height=MeasureSpec.getSize(heightMeasureSpec)-getPaddingBottom()-getTop();

        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int childCount = getChildCount();
        for (int i=0;i<childCount;i++){
            View view = getChildAt(i);
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width, widthMode==MeasureSpec.EXACTLY?MeasureSpec.AT_MOST:widthMode);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, heightMode==MeasureSpec.EXACTLY?MeasureSpec.AT_MOST:heightMode);

            view.measure(childWidthMeasureSpec,childHeightMeasureSpec);
            if (mLine==null){
                mLine=new Line();
            }
            int childWidth=view.getMeasuredWidth();
            mUsedWidth+=childWidth;
            if (mUsedWidth<width){
                mLine.addView(view);

                mUsedWidth+=mHorizontalSpacing;
                if (mUsedWidth>width){
                    newLine();
                }
            }else {
                if (mLine.getViewCountOfLine()==0){
                    mLine.addView(view);
                    newLine();
                }else {
                    newLine();
                    mLine.addView(view);
                    mUsedWidth+=childWidth+mHorizontalSpacing;
                }
            }
        }
        if (mLine!=null && mLine.getViewCountOfLine()!=0 && !lines.contains(mLine)){
            lines.add(mLine);
        }
        int totalWidth=MeasureSpec.getSize(widthMeasureSpec);
        int totalHeight=0;
        for (int i=0;i<lines.size();i++){
            totalHeight+=lines.get(i).maxHeight;
        }
        totalHeight+=(lines.size()-1)*mVerticalSpacing+getPaddingBottom()+getPaddingTop();
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(totalWidth,totalHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left=l+getPaddingLeft();
        int top=t+getPaddingTop();
        for (int i=0;i<lines.size();i++){
            Line line = lines.get(i);
            line.layout(left,top);
            top+=line.maxHeight+mVerticalSpacing;
        }
    }

    private void newLine(){
        lines.add(mLine);
        mUsedWidth=0;
        mLine=new Line();
    }

    class Line {
        private ArrayList<View> childs=new ArrayList<>();
        private int maxHeight;
        private int totalWidth;
        public void addView(View view){
            childs.add(view);
            totalWidth +=view.getMeasuredWidth();
            int height = view.getMeasuredHeight();
            if (height>maxHeight){
                maxHeight=height;
            }
        }
        public int getViewCountOfLine(){
            return childs.size();
        }

        public void layout(int left,int top){
            int screenWidth=getMeasuredWidth()-getPaddingRight()-getPaddingLeft();
            int totalSpace=screenWidth-totalWidth-(childs.size()-1)*mHorizontalSpacing;
            if (totalSpace>0){
                int space=totalSpace/childs.size();
                for (int i=0;i<childs.size();i++){
                    View view = childs.get(i);
                    int measuredWidth = view.getMeasuredWidth();
                    int measuredHeight = view.getMeasuredHeight();

                    measuredWidth+=space;
                    int widthMeasureSpec = MeasureSpec.makeMeasureSpec(measuredWidth, MeasureSpec.EXACTLY);
                    int heightMeasureSpec = MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.EXACTLY);
                    view.measure(widthMeasureSpec,heightMeasureSpec);

                    int topOffset=maxHeight-view.getMeasuredWidth()/2;
                    view.layout(left,top+topOffset,left+measuredWidth,top+measuredHeight);
                    left+=measuredWidth+mHorizontalSpacing;
                }
            }else {
                View view = childs.get(0);
                view.layout(left,top,left+view.getMeasuredWidth(),top+view.getMeasuredHeight());
            }
        }
    }
}
