package com.ywg.androidcommon.recyclerview.common;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * $desc
 *
 * @author zxb
 * @date 16/7/16 下午11:31
 */
public class CommonViewHolder extends RecyclerView.ViewHolder {

    private ViewBinder mViewBinder;

    public CommonViewHolder(View itemView) {
        super(itemView);
        mViewBinder = ViewBinder.create(itemView);
    }

    public CommonViewHolder(View itemView, ViewBinder viewBinder){
        super(itemView);
        mViewBinder = viewBinder;
    }

    public CommonViewHolder imageLoader(AdapterImageLoader imageLoader){
        mViewBinder.setImageLoader(imageLoader);
        return this;
    }

    public ViewBinder viewBinder(){
        return mViewBinder;
    }
}