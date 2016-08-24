package com.ywg.androidcommon.sample;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.widget.SeekBar;

import com.ywg.androidcommon.R;
import com.ywg.androidcommon.widget.CircleLoadingView;

public class CircleLoadingViewActivity extends AppCompatActivity {

    private CircleLoadingView mCircleLoadingView;

    private AppCompatSeekBar mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_loading_view);

        mCircleLoadingView = (CircleLoadingView) findViewById(R.id.circle_loading_view);
        mCircleLoadingView.setImageBitmap(
                BitmapFactory.decodeResource(getResources(), R.drawable.maron5));

        mSeekBar = (AppCompatSeekBar) findViewById(R.id.seek_bar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mCircleLoadingView.setPercent(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
