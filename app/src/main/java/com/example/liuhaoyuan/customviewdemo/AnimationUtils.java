package com.example.liuhaoyuan.customviewdemo;

import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;

/**
 * Created by liuhaoyuan on 2016/11/16.
 */

public class AnimationUtils {
    public static void close(RelativeLayout relativeLayout,long startOffset) {
        for (int i = 0; i < relativeLayout.getChildCount(); i++) {
            relativeLayout.getChildAt(i).setEnabled(false);
        }

        RotateAnimation rotateAnimation = new RotateAnimation(0, -180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 1);
        rotateAnimation.setDuration(500);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setStartOffset(startOffset);
        relativeLayout.startAnimation(rotateAnimation);
    }

    public static void show(RelativeLayout relativeLayout,long startOffset) {
        for (int i = 0; i < relativeLayout.getChildCount(); i++) {
            relativeLayout.getChildAt(i).setEnabled(true);
        }

        RotateAnimation rotateAnimation = new RotateAnimation(-180, 0,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 1);
        rotateAnimation.setDuration(500);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setStartOffset(startOffset);
        relativeLayout.startAnimation(rotateAnimation);
    }
}
