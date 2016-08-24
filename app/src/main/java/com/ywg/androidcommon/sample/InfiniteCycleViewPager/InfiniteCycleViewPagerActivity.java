package com.ywg.androidcommon.sample.InfiniteCycleViewPager;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.ywg.androidcommon.R;
import com.ywg.androidcommon.widget.NavigationTabStrip;

public class InfiniteCycleViewPagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infinite_cycle_view_pager);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.vp_main);
        viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(2);

        final NavigationTabStrip navigationTabStrip = (NavigationTabStrip) findViewById(R.id.nts);
        navigationTabStrip.setTitles("HORIZONTAL", "VERTICAL", "TWO-WAY");
        navigationTabStrip.setViewPager(viewPager);
    }
}
