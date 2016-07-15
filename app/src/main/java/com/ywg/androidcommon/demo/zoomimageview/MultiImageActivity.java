package com.ywg.androidcommon.demo.zoomimageview;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.ywg.androidcommon.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 多张图片Activity
 */
public class MultiImageActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    private MultiImageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_image);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mAdapter = new MultiImageAdapter(this, getData());
        mViewPager.setAdapter(mAdapter);
        mAdapter.setOnCickedListener(new MultiImageAdapter.OnCickedListener() {
            @Override
            public void onClicked() {
                finish();
            }
        });

    }

    /*?imageView2/2/w/900/h/1600*/
    private List<String> getData() {
        List<String> stringList = new ArrayList<>();
        stringList.add("http://qximg.lightplan.cc/2016/02/20/1455947314173671.jpg");
        stringList.add("http://qximg.lightplan.cc/2016/03/1/1456833663958973.jpg");
        stringList.add("http://qximg.lightplan.cc/2016/05/10/1462863397324242.gif");
        return stringList;
    }
}
