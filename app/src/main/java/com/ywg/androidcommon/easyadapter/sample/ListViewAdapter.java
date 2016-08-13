package com.ywg.androidcommon.easyadapter.sample;

import android.content.Context;

import com.ywg.androidcommon.R;
import com.ywg.androidcommon.easyadapter.abslistview.EasyLVAdapter;
import com.ywg.androidcommon.easyadapter.abslistview.EasyLVHolder;


import java.util.List;


public class ListViewAdapter extends EasyLVAdapter<Bean> {

    public ListViewAdapter(Context context, List<Bean> list, int... layoutIds) {
        super(context, list, layoutIds);
    }

    @Override
    public void convert(EasyLVHolder holder, int position, final Bean bean) {
        holder.setText(R.id.tv, bean.name);
    }

    @Override
    public int getLayoutIndex(int position, Bean item) {
        if (position % 2 == 0)
            return 1;
        else return 0;
    }
}