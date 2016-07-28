package com.ywg.androidcommon.listview.common;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Spanned;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ywg.androidcommon.listview.ImageLoader;

/**
 * 通用ViewHolder
 */
public class CommonViewHolder {

    /**
     * the object of the TAG
     */
    private String TAG = getClass().getSimpleName();
    //所有控件的集合  使用SparseArray效率高一些
    private SparseArray<View> mViews;
    //记录位置 可能会用到
    private int mPosition;
    //复用的View
    private View mConvertView;

    private Object mTag;

    /**
     * 构造函数
     *
     * @param context  上下文对象
     * @param parent   父类容器
     * @param layoutId 布局的ID
     * @param position item的位置
     */
    public CommonViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.mPosition = position;
        this.mViews = new SparseArray<View>();
        //加载布局
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        //将ViewHolderM赋值给View的Tag
        mConvertView.setTag(this);
    }

    /**
     * 得到一个ViewHolder
     *
     * @param context     上下文对象
     * @param convertView 复用的View
     * @param parent      父类容器
     * @param layoutId    布局的ID
     * @param position    item的位置
     * @return
     */
    public static CommonViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        //如果为空  直接新建一个ViewHolder
        if (convertView == null) {
            //如果convertView为空，则实例化VCommonViewHolder
            return new CommonViewHolder(context, parent, layoutId, position);
        } else {
            //否则从convertView的Tag中取出CommonViewHolder，避免重复创建
            CommonViewHolder viewHolder = (CommonViewHolder) convertView.getTag();
            //记得更新条目位置
            viewHolder.mPosition = position;
            return viewHolder;
        }
    }

    /**
     * @return 返回复用的View
     */
    public View getConvertView() {
        return mConvertView;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        this.mPosition = position;
    }

    public Object getTag() {
        return mTag;
    }

    public void setTag(Object tag) {
        this.mTag = tag;
    }

    /**
     * 通过ViewId获取控件 如果没有则加入views
     *
     * @param viewId View的Id
     * @param <T>    View的子类
     * @return 返回View
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**------------------------------------华丽的分割线------------------------------------*/
    /**以下方法为额外封装的方法，只是简单几个，以后可以慢慢完善*/
    /**
     * 为文本设置text
     *
     * @param viewId view的Id
     * @param text   文本
     * @return 返回ViewHolder
     */
    public CommonViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public CommonViewHolder setText(int viewId, int stringId) {
        TextView tv = getView(viewId);
        tv.setText(stringId);
        return this;
    }


    public CommonViewHolder setTextColor(int viewId, int color) {
        TextView tv = getView(viewId);
        tv.setTextColor(color);
        return this;
    }

    /**
     * 设置TextView的内容
     *
     * @param viewId
     * @param text，Spanned类型，可设置部分字体变色
     * @return
     */
    public CommonViewHolder setText(int viewId, Spanned text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public CommonViewHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);

        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param bm
     * @return
     */
    public CommonViewHolder setImageBitmap(int viewId, Bitmap bm) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }

    public CommonViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }


    public CommonViewHolder setImageDrawable(int viewId, Uri uri) {
        ImageView view = getView(viewId);
        view.setImageURI(uri);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param url
     * @return
     */
    public CommonViewHolder setImageByUrl(int viewId, String url) {
        ImageLoader.getInstance(3, ImageLoader.Type.LIFO).loadImage(url,
                (ImageView) getView(viewId));
        return this;
    }


    /**
     * 设置图片的可见性
     *
     * @param viewId
     * @param visible
     * @return
     */
    public CommonViewHolder setImageViewVisible(int viewId, Boolean visible) {
        ImageView iv = getView(viewId);
        if (visible) {
            iv.setVisibility(View.VISIBLE);
        } else {
            iv.setVisibility(View.GONE);
        }
        return this;
    }

    /**
     * @param viewId
     * @param color
     */
    public CommonViewHolder setBackgroundColor(int viewId, int color) {
        View target = getView(viewId);
        target.setBackgroundColor(color);
        return this;
    }


    /**
     * @param viewId
     * @param resId
     */
    public CommonViewHolder setBackgroundResource(int viewId, int resId) {
        View target = getView(viewId);
        target.setBackgroundResource(resId);
        return this;
    }


    /**
     * @param viewId
     * @param drawable
     */
    public CommonViewHolder setBackgroundDrawable(int viewId, Drawable drawable) {
        View target = getView(viewId);
        target.setBackgroundDrawable(drawable);
        return this;
    }

    /**
     * @param viewId
     * @param drawable
     */
    @TargetApi(16)
    public CommonViewHolder setBackground(int viewId, Drawable drawable) {
        View target = getView(viewId);
        target.setBackground(drawable);
        return this;
    }


    @TargetApi(16)
    public CommonViewHolder setImageAlpha(int viewId, int alpha) {
        ImageView target = getView(viewId);
        target.setImageAlpha(alpha);
        return this;
    }

    public CommonViewHolder setChecked(int viewId, boolean checked) {
        Checkable checkable = getView(viewId);
        checkable.setChecked(checked);
        return this;
    }


    public CommonViewHolder setProgress(int viewId, int progress) {
        ProgressBar view = getView(viewId);
        view.setProgress(progress);
        return this;
    }

    public CommonViewHolder setProgress(int viewId, int progress, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    public CommonViewHolder setMax(int viewId, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        return this;
    }

    public CommonViewHolder setRating(int viewId, float rating) {
        RatingBar view = getView(viewId);
        view.setRating(rating);
        return this;
    }

    public CommonViewHolder setVisibility(int viewId, int visible) {
        View view = getView(viewId);
        view.setVisibility(visible);
        return this;
    }


    public CommonViewHolder setRating(int viewId, float rating, int max) {
        RatingBar view = getView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    public CommonViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public CommonViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }


    public CommonViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

    public CommonViewHolder setOnItemClickListener(int viewId, AdapterView.OnItemClickListener listener) {
        AdapterView view = getView(viewId);
        view.setOnItemClickListener(listener);
        return this;
    }


    public CommonViewHolder setOnItemLongClickListener(int viewId, AdapterView.OnItemLongClickListener listener) {
        AdapterView view = getView(viewId);
        view.setOnItemLongClickListener(listener);
        return this;
    }


    public CommonViewHolder setOnItemSelectedClickListener(int viewId, AdapterView.OnItemSelectedListener listener) {
        AdapterView view = getView(viewId);
        view.setOnItemSelectedListener(listener);
        return this;
    }
}
