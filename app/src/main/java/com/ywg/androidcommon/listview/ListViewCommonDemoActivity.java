package com.ywg.androidcommon.listview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.ywg.androidcommon.R;
import com.ywg.androidcommon.listview.common.CommonAdapter;
import com.ywg.androidcommon.listview.common.CommonViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ListViewCommonDemoActivity extends AppCompatActivity {
    private ListView mListview;
    private List<Bean> mDatas = new ArrayList<Bean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_common_demo);
        initViews();
        initDatas();
        CommonAdapter commonAdapter = new CommonAdapter<Bean>(
                ListViewCommonDemoActivity.this, mDatas, R.layout.item_list) {
            @Override
            public void convert(CommonViewHolder helper, Bean item) {
                helper.setText(R.id.tv_title, item.getTitle());
                helper.setText(R.id.tv_describe, item.getDesc());
                helper.setText(R.id.tv_phone, item.getPhone());
                helper.setText(R.id.tv_time, item.getTime());
            }
        };
        // 设置适配器
        mListview.setAdapter(commonAdapter);
    }

    private void initViews() {
        mListview = (ListView) findViewById(R.id.list_view);
    }


    private void initDatas() {
        Bean bean = null;
        bean = new Bean("美女一只", "周三早上捡到妹子一只，在食堂二楼", "10086", "20130240122");
        mDatas.add(bean);
        bean = new Bean("美女一捆", "周三早上捡到妹子一捆，在食堂三楼", "10086", "20130240122");
        mDatas.add(bean);
        bean = new Bean("比卡丘一个", "周三早上捡到比卡丘一个，在食堂一楼", "10086", "20130240122");
        mDatas.add(bean);
        bean = new Bean("汉子一火车", "周三早上捡到xxxxxxxxxx，在xxx", "10086",
                "20130240122");
        mDatas.add(bean);
    }
}
