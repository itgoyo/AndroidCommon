package com.ywg.androidcommon.sample.touchimageview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ywg.androidcommon.R;

public class TouchImageViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_image_view);

        findViewById(R.id.single_touchimageview_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(TouchImageViewActivity.this, SingleTouchImageViewActivity.class));
            }
        });
        findViewById(R.id.viewpager_example_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(TouchImageViewActivity.this, ViewPagerExampleActivity.class));
            }
        });
        findViewById(R.id.mirror_touchimageview_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(TouchImageViewActivity.this, MirroringExampleActivity.class));
            }
        });
        findViewById(R.id.switch_image_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(TouchImageViewActivity.this, SwitchImageExampleActivity.class));
            }
        });
        findViewById(R.id.switch_scaletype_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(TouchImageViewActivity.this, SwitchScaleTypeExampleActivity.class));
            }
        });
    }
}
