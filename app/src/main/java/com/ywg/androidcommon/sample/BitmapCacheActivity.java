package com.ywg.androidcommon.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ywg.androidcommon.utils.bitmapcache.BitmapCacheUtil;
import com.ywg.androidcommon.R;

public class BitmapCacheActivity extends AppCompatActivity {

    private ImageView mImageView;

    private Button mLoadBtn;

    private BitmapCacheUtil mBitmapCacheUtils;

    private String image_url = "http://c.hiphotos.baidu.com/zhidao/pic/item/a6efce1b9d16fdfa95c7a1cab78f8c5494ee7b6e.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_cache);

        mImageView = (ImageView) findViewById(R.id.image_view);
        mLoadBtn = (Button) findViewById(R.id.btn_load);
        mBitmapCacheUtils = new BitmapCacheUtil();

        mLoadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mBitmapCacheUtils.disPlayImage(mImageView,image_url);
            }
        });

    }

}
