package com.example.liuhaoyuan.customviewdemo;

import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.liuhaoyuan.customviewdemo.view.QuickIndexBar;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Collections;


public class QuickIndexActivity extends AppCompatActivity {

    private QuickIndexBar quickIndexBar;
    private ArrayList<Friend> friends;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private ListView listView;
    private TextView wordToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_index);
        init();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void init() {
        wordToast = (TextView) findViewById(R.id.tv_current_word);
        ViewCompat.setScaleX(wordToast, 0);
        ViewCompat.setScaleY(wordToast, 0);

        quickIndexBar = (QuickIndexBar) findViewById(R.id.quick_index_bar);
        quickIndexBar.setOnLetterTouchListener(new QuickIndexBar.OnLetterTouchListener() {
            @Override
            public void onLetterTouch(String letter) {
//                Toast.makeText(QuickIndexActivity.this,letter,Toast.LENGTH_SHORT).show();
                for (int i = 0; i < friends.size(); i++) {
                    String s = friends.get(i).getPinyin().charAt(0) + "";
                    if (s.equals(letter)) {
                        listView.setSelection(i);
                        break;
                    }
                }
                showCurrentWord(letter);
            }
        });
        listView = (ListView) findViewById(R.id.lv_index);

        friends = new ArrayList<>();
        fillList();
        Collections.sort(friends);

        listView.setAdapter(new QuickIndexAdapter(this, friends));
    }

    private Handler handler = new Handler();
    private boolean isScale = false;

    private void showCurrentWord(final String lettter) {
//        wordToast.setVisibility(View.VISIBLE);
        wordToast.setText(lettter);
        if (!isScale) {
            wordToast.animate().setDuration(450).scaleX(1).scaleY(1).start();
            isScale = true;
        }

        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//              wordToast.setVisibility(View.GONE);
                wordToast.animate().setDuration(450).scaleY(0).scaleX(0).start();
                isScale = false;
            }
        }, 1000);
    }

    private void fillList() {
        // 虚拟数据
        friends.add(new Friend("李伟"));
        friends.add(new Friend("张三"));
        friends.add(new Friend("阿三"));
        friends.add(new Friend("阿四"));
        friends.add(new Friend("段誉"));
        friends.add(new Friend("段正淳"));
        friends.add(new Friend("张三丰"));
        friends.add(new Friend("陈坤"));
        friends.add(new Friend("林俊杰1"));
        friends.add(new Friend("陈坤2"));
        friends.add(new Friend("王二a"));
        friends.add(new Friend("林俊杰a"));
        friends.add(new Friend("张四"));
        friends.add(new Friend("林俊杰"));
        friends.add(new Friend("王二"));
        friends.add(new Friend("王二b"));
        friends.add(new Friend("赵四"));
        friends.add(new Friend("杨坤"));
        friends.add(new Friend("赵子龙"));
        friends.add(new Friend("杨坤1"));
        friends.add(new Friend("李伟1"));
        friends.add(new Friend("宋江"));
        friends.add(new Friend("宋江1"));
        friends.add(new Friend("李伟3"));
    }


}
