package com.example.liuhaoyuan.customviewdemo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.liuhaoyuan.customviewdemo.utils.PinYinUtil;

import java.util.ArrayList;

/**
 * Created by liuhaoyuan on 2016/11/24.
 */

public class QuickIndexAdapter extends BaseAdapter {
    private ArrayList<Friend> list;
    private Context context;

    public QuickIndexAdapter(Context context, ArrayList<Friend> list) {
        this.context = context;
        this.list = list;
    }

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
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_index_list, null);
        }

        ViewHolder holder=ViewHolder.getHolder(convertView);

        //注意这里setText不能设置参数为char，否则将调用setText（int res）方法
        String currenWord=list.get(position).getPinyin().charAt(0)+"";
        if (position>0){
            String lastWord=list.get(position-1).getPinyin().charAt(0)+"";
            if (lastWord.equals(currenWord)){
                holder.head.setVisibility(View.GONE);
            }else {
                //这里再次设置可见性是因为listview本身的复用机制
                holder.head.setVisibility(View.VISIBLE);
                holder.head.setText(currenWord);
            }
        }else {
            //这里再次设置可见性是因为listview本身的复用机制
            holder.head.setVisibility(View.VISIBLE);
            holder.head.setText(currenWord);
        }

        holder.body.setText(list.get(position).getName());

        return convertView;
    }

    static class ViewHolder {
        TextView head;
        TextView body;

        public ViewHolder(View convertView) {
            head = (TextView) convertView.findViewById(R.id.tv_item_head);
            body = (TextView) convertView.findViewById(R.id.tv_item_body);
        }

        public static ViewHolder getHolder(View convertView) {
            ViewHolder holder= (ViewHolder) convertView.getTag();
            if (holder == null) {
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            return holder;
        }
    }
}
