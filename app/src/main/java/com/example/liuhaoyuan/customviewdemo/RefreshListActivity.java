package com.example.liuhaoyuan.customviewdemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.example.liuhaoyuan.customviewdemo.view.RefreshListView;

import java.util.ArrayList;

public class RefreshListActivity extends AppCompatActivity {

    private RefreshListView refreshListView;
    private ArrayList<String> data;
    Handler handler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_list);
        init();
    }

    private void init() {
        refreshListView = (RefreshListView) findViewById(R.id.lv_refresh);
        data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add("item "+i);
        }

        refreshListView.setAdapter(new ArrayAdapter<>(this,R.layout.item_drag,data));
        refreshListView.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromServer();
                Toast.makeText(RefreshListActivity.this,"on refreshing",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoadMore() {
                getDataFromServer();
                Toast.makeText(RefreshListActivity.this,"on loading more",Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void getDataFromServer() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshListView.onRefreshComplete(true);
            }
        },3000);
    }

}
