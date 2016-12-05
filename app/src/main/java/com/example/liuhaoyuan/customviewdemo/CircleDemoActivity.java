package com.example.liuhaoyuan.customviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class CircleDemoActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView home;
    boolean isShowLevel2=true;
    boolean isShowLevel3 =true;
    private RelativeLayout level3;
    private RelativeLayout level2;
    private ImageView menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_demo);
        initView();
        initListener();
    }

    private void initView() {
        home = (ImageView)findViewById(R.id.iv_home);
        menu = (ImageView) findViewById(R.id.iv_menu);
        level2 = (RelativeLayout) findViewById(R.id.rl_level2);
        level3 = (RelativeLayout) findViewById(R.id.rl_level3);
    }

    private void initListener() {
        home.setOnClickListener(this);
        menu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.iv_home:
                if(isShowLevel2){
                    if(isShowLevel3){
                        AnimationUtils.close(level3,100);
                        isShowLevel3 =false;
                    }
                    AnimationUtils.close(level2,0);
                    isShowLevel2=false;
                }else{
                    AnimationUtils.show(level2,0);
                    isShowLevel2=true;
                }
                break;

            case R.id.iv_menu:
                if (isShowLevel3){
                    AnimationUtils.close(level3,0);
                }else {
                    AnimationUtils.show(level3,0);
                }
                isShowLevel3=!isShowLevel3;
                break;
        }
    }
}
