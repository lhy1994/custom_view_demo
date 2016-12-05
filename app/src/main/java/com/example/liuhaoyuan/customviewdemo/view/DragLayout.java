package com.example.liuhaoyuan.customviewdemo.view;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.FloatEvaluator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by liuhaoyuan on 2016/11/23.
 */

public class DragLayout extends FrameLayout {

    public ViewDragHelper viewDragHelper;
    private View menu;
    private View content;
    private int width;
    private int dragRange;
    private FloatEvaluator floatEvaluator;
    private ArgbEvaluator argbEvaluator;
    private final int STATE_OPEN=1;
    private final int STATE_CLOSE=2;
    private int state=STATE_CLOSE;
    private GestureDetector gestureDetector;

    public DragLayout(Context context) {
        super(context);
        init(context);
    }

    public DragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        viewDragHelper = ViewDragHelper.create(this, new MyCallback());

        floatEvaluator = new FloatEvaluator();
        argbEvaluator=new ArgbEvaluator();

        gestureDetector = new GestureDetector(context, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
//                在菜单打开状态下，单击content部分直接关闭菜单
                if (state==STATE_OPEN && e.getX()>dragRange){
                    close();
                    state=STATE_CLOSE;
                }
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        menu = getChildAt(0);
        content = getChildAt(1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = getMeasuredWidth();
        dragRange = (int) (width * 0.6);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //在菜单打开情况下，屏蔽content部分的子view响应
        if (state==STATE_OPEN){
            return true;
        }
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        gestureDetector.onTouchEvent(event);
        return true;
    }



    class MyCallback extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == menu || child == content;
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            if (changedView == menu) {
                menu.layout(0, 0, menu.getMeasuredWidth(), menu.getMeasuredHeight());

                int newleft = content.getLeft() + dx;
                if (newleft < 0) {
                    newleft = 0;
                } else if (newleft > dragRange) {
                    newleft = dragRange;
                }
                content.layout(newleft, content.getTop() + dy, newleft + content.getMeasuredWidth(), content.getTop() + dy + content.getMeasuredHeight());
            }
//            根据位移百分比执行动画效果
            float percent = content.getLeft()*1f / dragRange;
            executeAnim(percent);
        }


        @Override
        public int getViewHorizontalDragRange(View child) {
            return dragRange;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (child == content) {
                if (left < 0) {
                    left = 0;
                } else if (left > dragRange) {
                    left = dragRange;
                }
            }
            return left;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if (content.getLeft() < dragRange / 2) {
               close();
                state=STATE_CLOSE;
            } else {
                open();
                state=STATE_OPEN;
            }
//            判断手指如果快速滑动则打开或关闭菜单
            if (state==STATE_CLOSE && xvel>200){
                open();
                state=STATE_OPEN;
            }else if (state==STATE_OPEN && xvel<-200){
                close();
                state=STATE_CLOSE;
            }
        }

    }

    @Override
    public void computeScroll() {
        if (viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(DragLayout.this);
        }
    }

    public void executeAnim(float percent) {
        //内容面板动画
        content.setScaleX(floatEvaluator.evaluate(percent, 1, 0.8));
        content.setScaleY(floatEvaluator.evaluate(percent, 1, 0.8));
//        菜单面板动画
        menu.setTranslationX(floatEvaluator.evaluate(percent,-menu.getMeasuredWidth()/2,0));
        menu.setScaleX(floatEvaluator.evaluate(percent,0.5,1));
        menu.setScaleY(floatEvaluator.evaluate(percent,0.5,1));
        menu.setAlpha(floatEvaluator.evaluate(percent,0.3,1));
        getBackground().setColorFilter((Integer) argbEvaluator.evaluate(percent, Color.BLACK,Color.TRANSPARENT), PorterDuff.Mode.SRC_OVER);
    }

    public void close(){
        viewDragHelper.smoothSlideViewTo(content, 0, content.getTop());
        ViewCompat.postInvalidateOnAnimation(DragLayout.this);
    }

    public void open(){
        viewDragHelper.smoothSlideViewTo(content, dragRange, content.getTop());
        ViewCompat.postInvalidateOnAnimation(DragLayout.this);
    }
}
