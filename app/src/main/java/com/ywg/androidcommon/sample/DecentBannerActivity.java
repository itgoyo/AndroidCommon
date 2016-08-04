package com.ywg.androidcommon.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ywg.androidcommon.R;
import com.ywg.androidcommon.banner.DecentBanner;

import java.util.ArrayList;
import java.util.List;

public class DecentBannerActivity extends AppCompatActivity {

    private DecentBanner decentBanner;
    private List<View> views;
    private List<String> titles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decent_banner);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }

        decentBanner = (DecentBanner) findViewById(R.id.decent_banner);
        View view1 = getLayoutInflater().inflate(R.layout.popular_layout, null);
        View view2 = getLayoutInflater().inflate(R.layout.daily_layout, null);
        View view3 = getLayoutInflater().inflate(R.layout.recommend_layout, null);
        views = new ArrayList<>();
        views.add(view1);
        views.add(view2);
        views.add(view3);

        titles = new ArrayList<>();
        titles.add("POPULAR");
        titles.add("IMAGE");
        titles.add("RECOMMEND");
        decentBanner.start(views, titles, 5, 500, R.drawable.logo);
    }
}
