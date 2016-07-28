package com.ywg.androidcommon.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;

import com.ywg.androidcommon.R;

public class SwitchButton1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_button1);

        ((CompoundButton) findViewById(R.id.switch_main_1)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                findViewById(R.id.switch_main_2).setEnabled(isChecked);
                findViewById(R.id.switch_main_3).setEnabled(isChecked);
                findViewById(R.id.switch_main_4).setEnabled(isChecked);
                findViewById(R.id.switch_main_5).setEnabled(isChecked);
                findViewById(R.id.switch_main_6).setEnabled(isChecked);
                findViewById(R.id.switch_main_7).setEnabled(isChecked);
                findViewById(R.id.switch_main_8).setEnabled(isChecked);
            }
        });
    }
}
