package com.ywg.androidcommon.sample.TabView;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.ywg.androidcommon.R;

/**
 * 作者：yaochangliang on 2016/8/14 15:50
 * 邮箱：yaochangliang159@sina.com
 */
public class UseInFragment extends FragmentActivity {
    FragmentSample fragmentSample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.use_in_fragment);
        fragmentSample = new FragmentSample();
        getSupportFragmentManager().beginTransaction().add(R.id.frame, fragmentSample).show(fragmentSample).commit();
    }
}