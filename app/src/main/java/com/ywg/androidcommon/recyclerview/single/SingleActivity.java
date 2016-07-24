package com.ywg.androidcommon.recyclerview.single;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.ywg.androidcommon.R;
import com.ywg.androidcommon.recyclerview.DividerDecoration;
import com.ywg.androidcommon.recyclerview.ItemClickSupport;
import com.ywg.androidcommon.recyclerview.MockService;

public class SingleActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private LinearLayoutManager mLinearLayoutManager;

    private SingleAdapter mSingleAdapter;

    private MockService mockService;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);

        mockService = new MockService();
        mSingleAdapter = new SingleAdapter(this);
        mSingleAdapter.fillList(mockService.getPersonList());

        mRecyclerView = (RecyclerView) findViewById(R.id.single_rv);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        DividerDecoration decoration = new DividerDecoration(this, DividerDecoration.VERTICAL_LIST);
        Drawable drawable = getResources().getDrawable(R.drawable.divider_single);
        decoration.setDivider(drawable);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setAdapter(mSingleAdapter);

        View view = LayoutInflater.from(this).inflate(R.layout.item_single_header, null, false);
        mSingleAdapter.addHeaderView(view);

        ItemClickSupport.addTo(mRecyclerView)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                      //  DevLog.d("click position:" + position);
                    }
                })
                .setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                       // DevLog.d("long click position:" + position);
                        return false;
                    }
                });


    }


}
