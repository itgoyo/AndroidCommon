package com.ywg.androidcommon.sample.zoomimageview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.ywg.androidcommon.R;
import com.ywg.androidcommon.widget.ZoomImageView;


/**
 * 单张图片Activity
 */
public class SingleImageActivity extends AppCompatActivity implements View.OnClickListener {

    private ZoomImageView mZoomImageView;
    private int width;
    private int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_image);

        findViewById(R.id.btn_normal_image).setOnClickListener(this);
        findViewById(R.id.btn_long_image).setOnClickListener(this);
        findViewById(R.id.btn_gif_image).setOnClickListener(this);

        mZoomImageView = (ZoomImageView) findViewById(R.id.zoom_imageview);
        mZoomImageView.post(new Runnable() {
            @Override
            public void run() {
                Log.i("test", mZoomImageView.getWidth() + "," + mZoomImageView.getHeight());
                width = mZoomImageView.getWidth();
                height = mZoomImageView.getHeight();
            }
        });
        mZoomImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_normal_image:
                mZoomImageView.reSetState();
                Glide.with(this)
                       /* .load("http://qximg.lightplan.cc/2016/02/20/1455947314173671.jpg?imageView2/2/w/"
                                + width + "/h/" + height)*/
                        .load("http://qximg.lightplan.cc/2016/02/20/1455947314173671.jpg")
                        .into(mZoomImageView);
                break;
            case R.id.btn_long_image:
                mZoomImageView.reSetState();
                Glide.with(this)
                        /*.load("http://qximg.lightplan.cc/2016/03/1/1456833663958973.jpg?imageView2/2/w/"
                                + width + "/h/" + height)*/
                        .load("http://qximg.lightplan.cc/2016/03/1/1456833663958973.jpg")
                        .into(mZoomImageView);
                break;
            case R.id.btn_gif_image:
                mZoomImageView.reSetState();
                Glide.with(this)
                    /*    .load("http://qximg.lightplan.cc/2016/05/10/1462863397324242.gif?imageView2/2/w/"
                                + width + "/h/" + height)*/
                        .load("http://qximg.lightplan.cc/2016/05/10/1462863397324242.gif")
                        .into(mZoomImageView);
                break;
        }
    }
}
