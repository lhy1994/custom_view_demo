package com.example.liuhaoyuan.customviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.liuhaoyuan.customviewdemo.view.ToggleButton;

public class ToggleButtonActivity extends AppCompatActivity {

    private ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toggle_button);
        init();
    }

    private void init() {
        toggleButton = (ToggleButton) findViewById(R.id.toggle);
//        toggleButton.setSwitchBackgroundResourse(R.drawable.switch_background);
//        toggleButton.setSlideBackgroundResourse(R.drawable.slide_button);
//        toggleButton.setSwitchState(false);
    }

}
