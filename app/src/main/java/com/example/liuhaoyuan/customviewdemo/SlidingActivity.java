package com.example.liuhaoyuan.customviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.liuhaoyuan.customviewdemo.view.SlidingMenu;

public class SlidingActivity extends AppCompatActivity {

    private SlidingMenu slidingMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding);
        slidingMenu = (SlidingMenu) findViewById(R.id.sm);
    }

    public void switchMenu(View view){
        slidingMenu.swtichState();
    }
}
