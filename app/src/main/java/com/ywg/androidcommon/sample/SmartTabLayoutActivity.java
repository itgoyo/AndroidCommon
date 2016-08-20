package com.ywg.androidcommon.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.ywg.androidcommon.R;
import com.ywg.androidcommon.widget.SmartTabLayout.SmartTabLayout;

import java.util.ArrayList;
import java.util.List;

public class SmartTabLayoutActivity extends AppCompatActivity {

    private List<String> mTabList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_tab_layout);

        setData();

        SmartTabLayoutAdapter adapter = new SmartTabLayoutAdapter(getSupportFragmentManager(), mTabList);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.smart_tab_layout);
        viewPagerTab.setViewPager(viewPager);
    }

    private void setData() {
        String title = null;
        mTabList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            title = "Tab" + (i + 1);
            mTabList.add(title);
        }
    }

    public class SmartTabLayoutAdapter extends FragmentPagerAdapter {

        private List<String> mTabList;

        public SmartTabLayoutAdapter(FragmentManager fm, List<String> tabList) {
            super(fm);
            this.mTabList = tabList;
        }

        @Override
        public Fragment getItem(int position) {
            return SmartTabLayoutFragment.newInstance(mTabList.get(position));
        }

        @Override
        public int getCount() {
            return mTabList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabList.get(position);
        }
    }
}
