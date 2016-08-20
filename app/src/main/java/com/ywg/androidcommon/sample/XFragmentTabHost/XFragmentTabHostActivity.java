package com.ywg.androidcommon.sample.XFragmentTabHost;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ywg.androidcommon.R;

public class XFragmentTabHostActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnNormal;
    private Button mBtnMove;
    private Button mBtnRipple;
    private Button mBtnClip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xfragment_tab_host);
        assignViews();
    }

    private void assignViews() {
        mBtnNormal = (Button) findViewById(R.id.btn_normal);
        mBtnMove = (Button) findViewById(R.id.btn_move);
        mBtnRipple = (Button) findViewById(R.id.btn_ripple);
        mBtnClip = (Button) findViewById(R.id.btn_clip);
        mBtnNormal.setOnClickListener(this);
        mBtnMove.setOnClickListener(this);
        mBtnRipple.setOnClickListener(this);
        mBtnClip.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_normal:
                startActivity(new Intent(XFragmentTabHostActivity.this, NormalActivity.class));
                break;
            case R.id.btn_clip:
                startActivity(new Intent(XFragmentTabHostActivity.this, ClipActivity.class));
                break;
            case R.id.btn_ripple:
                startActivity(new Intent(XFragmentTabHostActivity.this, QihooActivity.class));
                break;
            case R.id.btn_move:
                startActivity(new Intent(XFragmentTabHostActivity.this, MoveActivity.class));
                break;
        }
    }
}
