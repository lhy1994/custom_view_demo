package com.example.liuhaoyuan.customviewdemo.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Created by liuhaoyuan on 2016/11/25.
 */

public class HeadViewList extends ListView {

    private int ImageViewHeight;
    private int ImageHeight;
    private int maxHeight;

    public HeadViewList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeadViewList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HeadViewList(Context context) {
        super(context);
    }

    private ImageView imageView;

    public void setImageView(final ImageView imageView) {
        this.imageView = imageView;

        imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ImageViewHeight = imageView.getMeasuredHeight();
                ImageHeight = imageView.getDrawable().getIntrinsicHeight();
                maxHeight = ImageViewHeight>ImageHeight?ImageViewHeight*2: ImageHeight;

                imageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        if (isTouchEvent && deltaY<0){

            if (imageView!=null){
                int height = imageView.getHeight()-deltaY;
                if (height>maxHeight){
                    height=maxHeight;
                }
                imageView.getLayoutParams().height=height;
                imageView.requestLayout();
            }
        }
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction()==MotionEvent.ACTION_UP){
            ValueAnimator animator=ValueAnimator.ofInt(imageView.getHeight(),ImageViewHeight);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (int) animation.getAnimatedValue();
                    imageView.getLayoutParams().height=value;
                    imageView.requestLayout();
                }
            });
            animator.setInterpolator(new OvershootInterpolator());
            animator.setDuration(350);
            animator.start();
        }
        return super.onTouchEvent(ev);
    }
}
