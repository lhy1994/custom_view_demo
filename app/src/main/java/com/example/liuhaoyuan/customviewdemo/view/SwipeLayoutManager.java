package com.example.liuhaoyuan.customviewdemo.view;

/**
 * Created by liuhaoyuan on 2016/11/27.
 */

public class SwipeLayoutManager {
    private static SwipeLayoutManager manager = new SwipeLayoutManager();

    private SwipeLayoutManager() {
    }

    public static SwipeLayoutManager getInstance() {
        return manager;
    }

    private SwipeLayout currentLayout;

    public void setCurrentSwipeLayout(SwipeLayout currentLayout) {
        this.currentLayout = currentLayout;
    }

    public SwipeLayout getCurrentSwipeLayout() {
        return this.currentLayout;
    }

    public boolean isShouldSwipe(SwipeLayout swipeLayout) {
        if (currentLayout == null) {
            return true;
        } else {
            return currentLayout == swipeLayout;
        }
    }

    public void closeCurrentLayout() {
        if (this.currentLayout != null) {
            currentLayout.close();
            clearCurrentLayout();
        }
    }

    public void clearCurrentLayout() {
        this.currentLayout = null;
    }
}
