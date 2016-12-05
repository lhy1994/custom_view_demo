package com.example.liuhaoyuan.customviewdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by liuhaoyuan on 2016/11/22.
 */

public class SlidingMenu extends ViewGroup {

    private View leftMenu;
    private View content;
    private int startX;
    private int endX;
    private int scrollOffset;
    private int targetPosition;
    private int currentState=CONTENT_STATE;
    private static final int CONTENT_STATE=0;
    private static final int MENU_STATE=1;
    private Scroller scroller;
    private int startY;

    public SlidingMenu(Context context) {
        super(context);
        init(context);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        scroller = new Scroller(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        leftMenu = getChildAt(0);
//        leftMenu.measure(leftMenu.getLayoutParams().width,heightMeasureSpec);
//
        content = getChildAt(1);
//        content.measure(widthMeasureSpec,heightMeasureSpec);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChild(leftMenu,widthMeasureSpec,heightMeasureSpec);
        measureChild(content,widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        leftMenu.layout(-leftMenu.getMeasuredWidth(),0,0,b);

        content.layout(l,t,r,b);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                //计算此次滚动的距离
                endX = (int) event.getX();
                scrollOffset = startX-endX;
                //目标滚动位置：之前滚动的距离+将要滚动的距离
                targetPosition = scrollOffset+getScrollX();
                //限定边界
                if (targetPosition <-leftMenu.getMeasuredWidth()){
                    scrollTo(-leftMenu.getMeasuredWidth(),0);
                }else if(targetPosition >0){
                    scrollTo(0,0);
                }else{
                    scrollBy(scrollOffset,0);
                }
                //更新起始点
                startX=endX;
                break;
            case MotionEvent.ACTION_UP:

                int center=-leftMenu.getMeasuredWidth()/2;
                if (getScrollX()<center){
                    currentState=MENU_STATE;
                }else {
                    currentState=CONTENT_STATE;
                }
                updateView();
                break;
        }
        return true;
    }

    public void open(){
        currentState=MENU_STATE;
        updateView();
    }

    public void close(){
        currentState=CONTENT_STATE;
        updateView();
    }

    public void swtichState(){
        if (currentState==CONTENT_STATE){
            open();
        }else {
            close();
        }
    }

    /*
    *平滑滚动到制定位置
    *
    * */
    private void updateView() {
        int dx=0;
        if (currentState==CONTENT_STATE){
//            scrollTo(0,0);
            dx=0-getScrollX();
        }else{
//            scrollTo(-leftMenu.getMeasuredWidth(),0);
            dx=-leftMenu.getMeasuredWidth()-getScrollX();
        }

        int duration=Math.abs(dx*3);
//        开始模拟数据
        scroller.startScroll(getScrollX(),0,dx,0,duration);
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        //计算新位置并判断动画是否结束
        if (scroller.computeScrollOffset()){
//            移动到模拟的位置，刷新界面
            scrollTo(scroller.getCurrX(),0);
            invalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepet=false;
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                //此处必须用和onTouchEvent（）中相同的变量，否则可能坐标错乱
                startX = (int) ev.getX();
                startY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int xOffset= (int) Math.abs(ev.getX()- startX);
                int yOffset= (int) Math.abs(ev.getY()- startY);

                if (xOffset>yOffset && xOffset>5){
                    intercepet=true;
                }
                else {
                    intercepet=false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercepet=false;
                break;
        }
        return intercepet;
    }
}
