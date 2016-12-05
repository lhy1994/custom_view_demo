package com.example.liuhaoyuan.customviewdemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by liuhaoyuan on 2016/11/21.
 */

public class ToggleButton extends View {

    private Bitmap mSlideBackgroundBitmap;
    private Bitmap mSwitchBackgroundBitmap;
    private Paint mPaint;
    private boolean mState=false;
    private int mLeftOffset;
    private boolean mTouchMode=false;
    private int mCurrentX;
    private int mMaxLeftOffset;

    public ToggleButton(Context context) {
        super(context);
        init();
    }

    public ToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        int switch_background = attrs.getAttributeResourceValue("http://schemas.android.com/apk/res-auto", "switch_background", -1);
        int slide_background = attrs.getAttributeResourceValue("http://schemas.android.com/apk/res-auto", "slide_background", -1);
        boolean state = attrs.getAttributeBooleanValue("http://schemas.android.com/apk/res-auto", "state", false);
        setSwitchBackgroundResourse(switch_background);
        setSlideBackgroundResourse(slide_background);
        setSwitchState(state);
        init();

    }

    public ToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        mPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mSwitchBackgroundBitmap.getWidth(),mSwitchBackgroundBitmap.getHeight());
    }


    @Override
    protected void onDraw(Canvas canvas) {
//        先画背景
        canvas.drawBitmap(mSwitchBackgroundBitmap,0,0,mPaint);
//        再画开关

        if (mTouchMode){
            mLeftOffset=mCurrentX-mSlideBackgroundBitmap.getWidth()/2;
            mMaxLeftOffset=mSwitchBackgroundBitmap.getWidth()-mSlideBackgroundBitmap.getWidth();
            if (mLeftOffset<0){
                mLeftOffset=0;
            }else if (mLeftOffset>mMaxLeftOffset){
                mLeftOffset=mMaxLeftOffset;
            }
            canvas.drawBitmap(mSlideBackgroundBitmap,mLeftOffset,0,mPaint);
        }else {
            if(mState){
                mLeftOffset = mSwitchBackgroundBitmap.getWidth()-mSlideBackgroundBitmap.getWidth();
                canvas.drawBitmap(mSlideBackgroundBitmap, mLeftOffset,0,mPaint);
            }else {
                canvas.drawBitmap(mSlideBackgroundBitmap,0,0,mPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mTouchMode=true;
                mCurrentX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                mCurrentX= (int) event.getX();
                break;
            case MotionEvent.ACTION_UP:
                mTouchMode=false;
                mCurrentX= (int) event.getX();

                int center = mSwitchBackgroundBitmap.getWidth() / 2;
                mState=mCurrentX>center?true:false;
                break;
        }
        invalidate();
        return true;
    }

    public void setSlideBackgroundResourse(int resID){
        mSlideBackgroundBitmap = BitmapFactory.decodeResource(getResources(), resID);
    }

    public void setSwitchBackgroundResourse(int resID){
        mSwitchBackgroundBitmap = BitmapFactory.decodeResource(getResources(), resID);
    }

    public void setSwitchState(boolean state){
        this.mState=state;
    }
}
