package com.ywg.androidcommon.sample.tabview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.ywg.androidcommon.R;

public class TabViewActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_view);
    }

    public void quick_start(View view) {
        startActivity(new Intent(this, QuickStartActivity.class));
    }

    public void custom_in_xml(View view) {
        startActivity(new Intent(this, CustomInXmlActivity.class));
    }

    public void custom_in_java(View view) {
        startActivity(new Intent(this, CustomInJavaActivity.class));
    }

    public void use_in_fragment(View view) {
        startActivity(new Intent(this, UseInFragment.class));
    }
}
