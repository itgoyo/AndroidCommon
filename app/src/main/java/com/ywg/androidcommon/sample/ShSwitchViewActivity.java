package com.ywg.androidcommon.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ywg.androidcommon.R;
import com.ywg.androidcommon.widget.ShSwitchView;

public class ShSwitchViewActivity extends AppCompatActivity {
    ShSwitchView switchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sh_switch_view);
        switchView = (ShSwitchView) findViewById(R.id.switch_view);
        switchView.setOn(true);
    }

}
