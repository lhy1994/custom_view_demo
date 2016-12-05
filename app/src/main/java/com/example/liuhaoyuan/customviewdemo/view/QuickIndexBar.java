package com.example.liuhaoyuan.customviewdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by liuhaoyuan on 2016/11/24.
 */

public class QuickIndexBar extends View {

    private Paint paint;
    private String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
            "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private int width;
    private float cellHeight;
    private float x;
    private float y;
    private float downY;
    private int lastIndex = -1;
    private int index;
    private OnLetterTouchListener mListener;

    public QuickIndexBar(Context context) {
        super(context);
        init();
    }

    public QuickIndexBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public QuickIndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setTextSize(18);
        //设置文字的绘制起点在文字底边框中点
        paint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = getMeasuredWidth();
        cellHeight = getMeasuredHeight() / letters.length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < letters.length; i++) {
            x = width / 2;
            y = i * cellHeight + cellHeight / 2 + getTextHeight(letters[i]) / 2;
            paint.setColor(i == lastIndex ? Color.BLACK : Color.WHITE);
            canvas.drawText(letters[i], x, y, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

            case MotionEvent.ACTION_MOVE:
                downY = event.getY();
                index = (int) (downY / cellHeight);
                if (lastIndex != index) {
//                    Log.e("test", index + "");
                    if (mListener != null) {
                        mListener.onLetterTouch(letters[index]);
                    }
                    lastIndex = index;
                }
                break;
            case MotionEvent.ACTION_UP:
                lastIndex = -1;
                break;
        }
        invalidate();
        return true;
    }

    private int getTextHeight(String text) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect.height();
    }

    public interface OnLetterTouchListener {
        public void onLetterTouch(String letter);
    }

    public void setOnLetterTouchListener(OnLetterTouchListener listener) {
        this.mListener = listener;
    }
}
