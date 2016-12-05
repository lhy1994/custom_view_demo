package com.example.liuhaoyuan.customviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SwipeDeleteActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_delete);
        init();
    }

    private void init() {
        listView = (ListView) findViewById(R.id.lv_swipe);
        list = new ArrayList<>();
        for (int i=0;i<20;i++){
            list.add("content "+i);
        }

        listView.setAdapter(new MySwipeAdapter());
    }

    class MySwipeAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView==null){
                convertView=View.inflate(SwipeDeleteActivity.this,R.layout.item_swipe,null);
            }

            ViewHolder holder=ViewHolder.getViewHolder(convertView);
            holder.content.setText(list.get(position));
            return convertView;
        }


    }

    static class ViewHolder{
        TextView content,call,delete;
        public ViewHolder (View convertView){
            content= (TextView) convertView.findViewById(R.id.tv_content);
            call= (TextView) convertView.findViewById(R.id.tv_call);
            delete= (TextView) convertView.findViewById(R.id.tv_delete);
        }

        public static ViewHolder getViewHolder(View convertView){
            ViewHolder holder= (ViewHolder) convertView.getTag();
            if(holder==null){
                holder=new ViewHolder(convertView);
                convertView.setTag(holder);
            }

            return holder;
        }
    }
}
