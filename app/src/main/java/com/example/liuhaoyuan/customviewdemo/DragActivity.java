package com.example.liuhaoyuan.customviewdemo;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DragActivity extends AppCompatActivity {

    private ListView contentList;
    private ListView menuList;
    private List<String> menuData;
    private List<String> contentData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag);
        init();
    }

    private void init() {
        contentList = (ListView) findViewById(R.id.lv_drag);
        menuList = (ListView) findViewById(R.id.lv_drag_menu);

        contentData = new ArrayList<>();
        menuData = new ArrayList<>();
        for (int i=1;i<21;i++){
            contentData.add("content item "+i);
            menuData.add("menu item "+i);
        }

        contentList.setAdapter(new ArrayAdapter<String>(this,R.layout.item_drag,contentData));
        menuList.setAdapter(new ArrayAdapter<String>(this,R.layout.item_drag,menuData){
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView= (TextView) super.getView(position, convertView, parent);
                textView.setTextColor(Color.WHITE);
                return textView;
            }
        });
    }
}
