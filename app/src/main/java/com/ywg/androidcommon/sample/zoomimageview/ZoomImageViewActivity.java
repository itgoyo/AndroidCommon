package com.ywg.androidcommon.sample.ZoomImageView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ywg.androidcommon.R;

public class ZoomImageViewActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnSingleImage;

    private Button mBtnMultiImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_image_view);

        mBtnSingleImage = (Button) findViewById(R.id.btn_single_image);
        mBtnMultiImage = (Button) findViewById(R.id.btn_multi_image);

        mBtnSingleImage.setOnClickListener(this);
        mBtnMultiImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_single_image:
                startActivity(new Intent(this,SingleImageActivity.class));
                break;
            case R.id.btn_multi_image:
                startActivity(new Intent(this,MultiImageActivity.class));
                break;
        }
    }
}
