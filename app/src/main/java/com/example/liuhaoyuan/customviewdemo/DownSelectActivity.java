package com.example.liuhaoyuan.customviewdemo;

import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DownSelectActivity extends AppCompatActivity {

    private ImageView imageView;
    private ArrayList<String> data;
    private ListView listView;
    private MyAdapter myAdapter;
    private EditText editText;
    private PopupWindow popupWindow;

    private boolean showList=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_select);

        initView();
        initData();
    }

    private void initData() {
        data = new ArrayList<>();
        for (int i=0;i<15;i++){
            data.add(9000+i+"");
        }

        myAdapter = new MyAdapter();
        listView.setAdapter(myAdapter);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showList){
                    popupWindow.dismiss();
                }else {
                    showList();
                }
                showList=!showList;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(DownSelectActivity.this,position+"",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        editText = (EditText) findViewById(R.id.et_down);
        imageView = (ImageView) findViewById(R.id.iv_down);
        listView = new ListView(this);
    }

    private void showList(){
        if (popupWindow==null){
            popupWindow = new PopupWindow(listView,editText.getWidth(),300);

            //popupWindow防止内部view无法获得焦点，要添加以下三个属性
//            popupWindow.setFocusable(true);
//            popupWindow.setBackgroundDrawable(new BitmapDrawable());
//            popupWindow.setOutsideTouchable(true);
        }
        popupWindow.showAsDropDown(editText,0,10);
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final View  view=View.inflate(DownSelectActivity.this,R.layout.item_selecter,null);
            TextView textView= (TextView) view.findViewById(R.id.tv_select);
            textView.setText(data.get(position));
            ImageButton imageButton= (ImageButton) view.findViewById(R.id.btn_close);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    data.remove(position);
                    notifyDataSetChanged();

                    int listviewheight=data.size()*view.getHeight();
                    popupWindow.update(editText.getWidth(),listviewheight<300? listviewheight:300);
                    if (data.size()==0){
                        popupWindow.dismiss();
                    }
                }
            });
            return view;
        }
    }
}
