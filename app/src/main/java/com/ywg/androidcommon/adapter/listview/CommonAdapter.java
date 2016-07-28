package com.ywg.androidcommon.adapter.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.ywg.androidcommon.adapter.CommonViewHolder;

import java.util.List;

public abstract class CommonAdapter<T> extends BaseAdapter {

    protected Context mContext;

    protected List<T> mDatas;

    protected LayoutInflater mInflater;

    private int layoutId;

    public CommonAdapter(Context context, int layoutId, List<T> datas) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = datas;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonViewHolder holder = CommonViewHolder.get(mContext, convertView, parent,
                layoutId, position);
        convert(holder, getItem(position));
        return holder.getConvertView();
    }

    public abstract void convert(CommonViewHolder holder, T t);

    /**
     * @param item
     */
    public void addItem(T item) {
        mDatas.add(item);
        notifyDataSetChanged();
    }

    /**
     * @param items
     */
    public void addItems(List<T> items) {
        mDatas.addAll(items);
        notifyDataSetChanged();
    }

    /**
     * @param item
     */
    public void addItemToHead(T item) {
        mDatas.add(0, item);
        notifyDataSetChanged();
    }

    /**
     * @param items
     */
    public void addItemsToHead(List<T> items) {
        mDatas.addAll(0, items);
        notifyDataSetChanged();
    }

    /**
     * @param position
     */
    public void remove(int position) {
        mDatas.remove(position);
        notifyDataSetChanged();
    }

    /**
     * @param item
     */
    public void remove(T item) {
        mDatas.remove(item);
        notifyDataSetChanged();
    }

    /**
     * clear all data
     */
    public void clear() {
        mDatas.clear();
        notifyDataSetChanged();
    }

}