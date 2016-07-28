package com.ywg.androidcommon.listview.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 通用Adapter抽象类
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

    private Context mContext;

    private LayoutInflater mInflater;

    //为丰富程序功能，提供了两种常见的数据类型
    private List<T> mDataList;
   // private T[] mDataArray = null;//数据源T[]

    private final int mItemLayoutId;

    public CommonAdapter(Context context, List<T> dataList, int layoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mDataList = dataList;
        this.mItemLayoutId = layoutId;
    }

   /**
     * 构造方法(与上一个只有数据源不同)
     */
   /* public CommonAdapter(Context context, T[] dataArray, int layoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mDataArray = dataArray;
        this.mItemLayoutId = layoutId;
    }*/

    @Override
    public int getCount() {
       /* if (mDataList != null) {
            return mDataList.size();
        } else {
            return mDataArray.length;
        }*/
        return mDataList.size();
    }

    @Override
    public T getItem(int position) {
        /*if (mDataList != null) {
            return mDataList.get(position);
        } else {
            return mDataArray[position];
        }*/
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 根据View Type返回布局资源
     *
     * @param type
     * @return
     */
    protected int getItemLayout(int type) {
        return mItemLayoutId;
    }

    /**
     * 封装getView方法
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //得到一个ViewHolder
        CommonViewHolder viewHolder = CommonViewHolder.get(mContext, convertView, parent, mItemLayoutId, position);

        //设置控件内容
        convert(viewHolder, (T) getItem(position));

        //返回复用的View
        return viewHolder.getConvertView();
    }

    /**
     * 提供抽象方法，来设置控件内容
     *
     * @param viewHolder 一个ViewHolder
     * @param t          一个数据集
     */
    public abstract void convert(CommonViewHolder viewHolder, T t);

    private CommonViewHolder getViewHolder(int position, View convertView,
                                           ViewGroup parent) {
        return CommonViewHolder.get(mContext, convertView, parent, mItemLayoutId,
                position);
    }

    /**
     * @param item
     */
    public void addItem(T item) {
        mDataList.add(item);
        notifyDataSetChanged();
    }

    /**
     * @param items
     */
    public void addItems(List<T> items) {
        mDataList.addAll(items);
        notifyDataSetChanged();
    }

    /**
     * @param item
     */
    public void addItemToHead(T item) {
        mDataList.add(0, item);
        notifyDataSetChanged();
    }

    /**
     * @param items
     */
    public void addItemsToHead(List<T> items) {
        mDataList.addAll(0, items);
        notifyDataSetChanged();
    }

    /**
     * @param position
     */
    public void remove(int position) {
        mDataList.remove(position);
        notifyDataSetChanged();
    }

    /**
     * @param item
     */
    public void remove(T item) {
        mDataList.remove(item);
        notifyDataSetChanged();
    }

    /**
     * clear all data
     */
    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    /***
     * 设置数据
     */
  /*  public abstract void setData(List<T> dataList);*/

    /**
     * 设置数据
     */
    /*public abstract void setData(T[] dataArray);*/
}