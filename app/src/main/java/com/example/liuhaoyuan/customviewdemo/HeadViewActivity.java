package com.example.liuhaoyuan.customviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.liuhaoyuan.customviewdemo.view.HeadViewList;

import java.util.ArrayList;
import java.util.List;

public class HeadViewActivity extends AppCompatActivity {


    private HeadViewList headViewList;
    private ArrayList<String> list;
    private View headerView;
    private ImageView headImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_view);
        init();
    }

    private void init() {
        list = new ArrayList<>();
        for (int i=0;i<15;i++){
            list.add("item" +i);
        }

        headViewList = (HeadViewList) findViewById(R.id.lv_head_view);
        headerView = View.inflate(this, R.layout.header_view, null);
        headImage = (ImageView) headerView.findViewById(R.id.iv_header);

        headViewList.addHeaderView(headerView);
        headViewList.setAdapter(new ArrayAdapter<String>(this,R.layout.item_drag,list));
        headViewList.setImageView(headImage);
    }
}
