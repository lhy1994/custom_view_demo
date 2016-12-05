package com.example.liuhaoyuan.customviewdemo.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.example.liuhaoyuan.customviewdemo.utils.GeometryUtil;
import com.example.liuhaoyuan.customviewdemo.utils.Util;

/**
 * Created by liuhaoyuan on 2016/11/27.
 */

public class StickView extends View {

    private Paint paint;
    private Path path;
    private float lineK;

    private boolean isDragOutOfRange = false;
    private float xOffset;
    private float yOffset;

    public StickView(Context context) {
        super(context);
        init();
    }


    public StickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StickView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
    }

    private float dragRadius = 12f;
    private float stickyRadius = 12f;
    private PointF dragCenter = new PointF(100f, 120f);
    private PointF stickyCenter = new PointF(180f, 120f);

    private PointF[] stickyPoints = new PointF[]{new PointF(180f, 108f), new PointF(180f, 132f)};
    private PointF[] dragPoints = new PointF[]{new PointF(100f, 108f), new PointF(100f, 132f)};

    private PointF controlPoint = new PointF(140f, 120f);

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(0, -Util.getStatusBarHeight(getResources()));

        xOffset = dragCenter.x - stickyCenter.x;
        yOffset = dragCenter.y - stickyCenter.y;
        stickyRadius = getStickyRadius();
        if (xOffset != 0) {
            lineK = yOffset / xOffset;
        }
        dragPoints = GeometryUtil.getIntersectionPoints(dragCenter, dragRadius, (double) lineK);
        stickyPoints = GeometryUtil.getIntersectionPoints(stickyCenter, stickyRadius, (double) lineK);

        controlPoint = GeometryUtil.getPointByPercent(dragCenter, stickyCenter, 0.618f);

        canvas.drawCircle(dragCenter.x, dragCenter.y, dragRadius, paint);

        if (!isDragOutOfRange) {
            canvas.drawCircle(stickyCenter.x, stickyCenter.y, stickyRadius, paint);

            path = new Path();
            path.moveTo(stickyPoints[0].x, stickyPoints[0].y);
            path.quadTo(controlPoint.x, controlPoint.y, dragPoints[0].x, dragPoints[0].y);
            path.lineTo(dragPoints[1].x, dragPoints[1].y);
            path.quadTo(controlPoint.x, controlPoint.y, stickyPoints[1].x, stickyPoints[1].y);

            canvas.drawPath(path, paint);
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dragCenter.set(event.getRawX(), event.getRawY());
                break;
            case MotionEvent.ACTION_MOVE:
                dragCenter.set(event.getRawX(), event.getRawY());
                if (GeometryUtil.getDistanceBetween2Points(dragCenter, stickyCenter) > maxDistance) {
                    isDragOutOfRange = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isDragOutOfRange) {
                    dragCenter.set(stickyCenter.x, stickyCenter.y);
                    isDragOutOfRange = false;
                } else {
                        ValueAnimator animator=ValueAnimator.ofFloat(1);
                        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float animatedFraction = animation.getAnimatedFraction();
                                Log.e("test",animatedFraction+"");
                                PointF pointF = GeometryUtil.getPointByPercent(dragCenter, stickyCenter, animatedFraction);
                                dragCenter.set(pointF);
                                invalidate();
                            }
                        });
//                        animator.setDuration(2000);
                        animator.setInterpolator(new OvershootInterpolator(10));
                        animator.start();
                }
                break;

        }
        invalidate();
        return true;
    }

    private float maxDistance = 180;

    public float getStickyRadius() {
        float radius;
        float centerDistance = GeometryUtil.getDistanceBetween2Points(dragCenter, stickyCenter);
        float fraction = centerDistance / maxDistance;
        radius = GeometryUtil.evaluateValue(fraction, 12, 4);
        return radius;

    }
}
