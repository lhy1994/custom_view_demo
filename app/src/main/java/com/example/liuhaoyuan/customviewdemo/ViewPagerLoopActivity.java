package com.example.liuhaoyuan.customviewdemo;


import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/*
* 无限循环viewpager
*
* */

public class ViewPagerLoopActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ArrayList<Integer> imageList;
    private MyPagerAdapter myPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_loop);

        initView();
        initData();
    }

    private void initData() {
        imageList = new ArrayList<>();
        //额外添加最后一页到position 0
        imageList.add(R.drawable.e);

        imageList.add(R.drawable.a);
        imageList.add(R.drawable.b);
        imageList.add(R.drawable.c);
        imageList.add(R.drawable.d);
        imageList.add(R.drawable.e);
//       额外添加第一页到最后位置
        imageList.add(R.drawable.a);

        myPagerAdapter = new MyPagerAdapter();
        viewPager.setAdapter(myPagerAdapter);
        //为viewpager 设置监听，实现页面跳转
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset==0){
                    if (position==0){
                        viewPager.setCurrentItem(imageList.size()-2,false);
                    }else if (position==imageList.size()-1){
                        viewPager.setCurrentItem(1,false);
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        设置初始位置为1
        viewPager.setCurrentItem(1);

//        伪无限循环，设置初始位置
//        int initPosition=(Integer.MAX_VALUE/2)%imageList.size();
//        viewPager.setCurrentItem(Integer.MAX_VALUE/2-initPosition);
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.vp_loop);
    }

    public class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageList.size();

//            伪无限循环，设置最大值
//            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(ViewPagerLoopActivity.this, R.layout.pager_loop, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_loop);
            int current = position % imageList.size();
            imageView.setImageResource(imageList.get(current));
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }
}
