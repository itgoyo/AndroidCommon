package com.ywg.androidcommon.sample.CircleIndicator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ywg.androidcommon.R;
import com.ywg.androidcommon.widget.CircleIndicator;

public class DynamicAdapterFragment extends Fragment implements View.OnClickListener {

    private SamplePagerAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sample_dynamic_adapter, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        view.findViewById(R.id.add).setOnClickListener(this);
        view.findViewById(R.id.remove).setOnClickListener(this);

        mAdapter = new SamplePagerAdapter(1) {
            @Override
            public int getItemPosition(Object object) {
                return POSITION_NONE;
            }
        };

        ViewPager viewpager = (ViewPager) view.findViewById(R.id.viewpager);
        CircleIndicator indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        viewpager.setAdapter(mAdapter);
        indicator.setViewPager(viewpager);
        mAdapter.registerDataSetObserver(indicator.getDataSetObserver());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                mAdapter.addItem();
                break;
            case R.id.remove:
                mAdapter.removeItem();
                break;
        }
    }
}
