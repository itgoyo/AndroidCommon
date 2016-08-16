package com.ywg.androidcommon.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.widget.SeekBar;

import com.ywg.androidcommon.R;
import com.ywg.androidcommon.widget.BezierLoadingView;

public class BezierLoadingViewActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private BezierLoadingView mLoadingView;
    private AppCompatSeekBar mSbExternalR, mSbInternalR, mSbRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier_loading_view);

        mLoadingView = (BezierLoadingView) this.findViewById(R.id.loading_view);
        mSbExternalR = (AppCompatSeekBar) this.findViewById(R.id.sb_external_r);
        mSbInternalR = (AppCompatSeekBar) this.findViewById(R.id.sb_internal_r);
        mSbRate = (AppCompatSeekBar) this.findViewById(R.id.sb_rate);
        mSbExternalR.setOnSeekBarChangeListener(this);
        mSbInternalR.setOnSeekBarChangeListener(this);
        mSbRate.setOnSeekBarChangeListener(this);

        mLoadingView.start();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar == mSbExternalR) {
            mLoadingView.setExternalRadius(progress);
        } else if (seekBar == mSbInternalR) {
            mLoadingView.setInternalRadius(progress);
        } else if (seekBar == mSbRate) {
            mLoadingView.setDuration(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoadingView.stop();
    }
}
