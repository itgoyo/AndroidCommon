package com.ywg.androidcommon.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ywg.androidcommon.R;
import com.ywg.androidcommon.widget.SwitchView;

public class SwitchViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_view);
        final SwitchView viewSwitch = (SwitchView) findViewById(R.id.switch_view);
        // 设置初始状态。true为开;false为关[默认]。set up original status. true for open and false for close[default]
        viewSwitch.setOpened(true);
        viewSwitch.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(View view) {
                // 原本为关闭的状态，被点击后 originally present close status after clicking

                // 执行一些耗时的业务逻辑操作 implement some time-consuming logic operation
                viewSwitch.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewSwitch.toggleSwitch(true); //以动画效果切换到打开的状态 through changing animation effect to open status
                    }
                }, 1000);
            }

            @Override
            public void toggleToOff(View view) {
                // 原本为打开的状态，被点击后 originally present the status of open after clicking
                viewSwitch.toggleSwitch(false);
            }
        });

    }
}