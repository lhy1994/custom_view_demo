package com.example.liuhaoyuan.customviewdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button circle= (Button) findViewById(R.id.btn_circle);
    }

    public void circle(View view){
        Intent intent=new Intent(this,CircleDemoActivity.class);
        startActivity(intent);
    }

    public void loop(View view){
        Intent intent=new Intent(this,ViewPagerLoopActivity.class);
        startActivity(intent);
    }

    public void down(View view){
        Intent intent=new Intent(this,DownSelectActivity.class);
        startActivity(intent);
    }

    public void slidingMenu(View view){
        Intent intent=new Intent(this,SlidingActivity.class);
        startActivity(intent);
    }

    public void toggle(View view){
        Intent intent=new Intent(this,ToggleButtonActivity.class);
        startActivity(intent);
    }

    public void drag(View view){
        Intent intent=new Intent(this,DragActivity.class);
        startActivity(intent);
    }

    public void quickIndex(View view){
        Intent intent=new Intent(this,QuickIndexActivity.class);
        startActivity(intent);
    }

    public void headView(View view){
        Intent intent=new Intent(this,HeadViewActivity.class);
        startActivity(intent);
    }

    public void swipeLayout(View view){
        Intent intent=new Intent(this,SwipeDeleteActivity.class);
        startActivity(intent);
    }

    public void stickView(View view){
        Intent intent=new Intent(this,StickViewActivity.class);
        startActivity(intent);
    }

    public void refreshList(View view){
        Intent intent=new Intent(this,RefreshListActivity.class);
        startActivity(intent);
    }


}
