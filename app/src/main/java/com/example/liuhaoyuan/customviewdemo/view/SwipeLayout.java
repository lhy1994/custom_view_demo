package com.example.liuhaoyuan.customviewdemo.view;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by liuhaoyuan on 2016/11/26.
 */

public class SwipeLayout extends FrameLayout {

    private View content;
    private View delete;
    private int deleteHeight;
    private int deleteWidth;
    private int contentWidth;
    private ViewDragHelper viewDragHelper;
    private int downY;
    private int downX;

    private final int STATE_CLOSE = 1;
    private final int STATE_OPEN = 2;
    private int currentState = STATE_CLOSE;

    public SwipeLayout(Context context) {
        super(context);
        init();
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        viewDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == content || child == delete;
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                if (changedView == content) {
                    delete.layout(delete.getLeft() + dx, delete.getTop() + dy, delete.getRight() + dx, delete.getBottom() + dy);
                } else if (changedView == delete) {
                    content.layout(content.getLeft() + dx, content.getTop() + dy, content.getRight() + dx, content.getBottom() + dy);
                }

                if (currentState != STATE_CLOSE && content.getLeft() == 0) {
                    currentState = STATE_CLOSE;
                    SwipeLayoutManager.getInstance().clearCurrentLayout();
                } else if (currentState != STATE_OPEN && content.getLeft() == -deleteWidth) {
                    currentState = STATE_OPEN;
                    SwipeLayoutManager.getInstance().setCurrentSwipeLayout(SwipeLayout.this);
                }
            }

            @Override
            public void onViewCaptured(View capturedChild, int activePointerId) {
                super.onViewCaptured(capturedChild, activePointerId);
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                if (content.getLeft() < -deleteWidth / 2) {
                    open();
                } else {
                    close();
                }
            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                return deleteWidth;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                if (child == content) {
                    if (left > 0) {
                        left = 0;
                    } else if (left < -deleteWidth) {
                        left = -deleteWidth;
                    }
                } else if (child == delete) {
                    if (left < contentWidth - deleteWidth) {
                        left = contentWidth - deleteWidth;
                    } else if (left > contentWidth) {
                        left = contentWidth;
                    }
                }

                return left;
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        content = getChildAt(0);
        delete = getChildAt(1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        deleteHeight = delete.getMeasuredHeight();
        deleteWidth = delete.getMeasuredWidth();
        contentWidth = content.getMeasuredWidth();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        content.layout(0, 0, contentWidth, deleteHeight);
        delete.layout(content.getRight(), 0, content.getRight() + deleteWidth, deleteHeight);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!SwipeLayoutManager.getInstance().isShouldSwipe(SwipeLayout.this)) {
            return true;
        }
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (SwipeLayoutManager.getInstance().getCurrentSwipeLayout() != null) {
            if (!SwipeLayoutManager.getInstance().isShouldSwipe(this)) {
                SwipeLayoutManager.getInstance().closeCurrentLayout();
                requestDisallowInterceptTouchEvent(true);
                return true;
            }else {
                requestDisallowInterceptTouchEvent(true);
            }
        }
        viewDragHelper.processTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getRawX();
                downY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getRawX();
                int moveY = (int) event.getRawY();

                int dx = Math.abs(moveX - downX);
                int dy = Math.abs(moveY - downY);
                if (dx > dy) {
                    requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }


    public void close() {
        viewDragHelper.smoothSlideViewTo(content, 0, content.getTop());
        ViewCompat.postInvalidateOnAnimation(this);
    }

    public void open() {
        viewDragHelper.smoothSlideViewTo(content, -deleteWidth, content.getTop());
        ViewCompat.postInvalidateOnAnimation(this);
    }

    @Override
    public void computeScroll() {
        if (viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
        super.computeScroll();
    }
}
