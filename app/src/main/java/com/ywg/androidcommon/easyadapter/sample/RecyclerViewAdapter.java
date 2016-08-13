package com.ywg.androidcommon.easyadapter.sample;

import android.content.Context;

import com.ywg.androidcommon.R;
import com.ywg.androidcommon.easyadapter.recyclerview.EasyRVAdapter;
import com.ywg.androidcommon.easyadapter.recyclerview.EasyRVHolder;


import java.util.List;


public class RecyclerViewAdapter extends EasyRVAdapter<Bean> {
    public RecyclerViewAdapter(Context context, List<Bean> list, int... layoutIds) {
        super(context, list, layoutIds);
    }

    @Override
    protected void onBindData(EasyRVHolder viewHolder, int position, Bean item) {
        viewHolder.setText(R.id.tv, item.name);
    }

    @Override
    public int getLayoutIndex(int position, Bean item) {
        if (position % 2 == 0)
            return 0;
        else return 1;
    }
}