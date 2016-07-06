package com.ywg.androidtools.widget.zoomimageview;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

public class MultiImageAdapter extends PagerAdapter {

    private List<String> mStringList;

    private Context mContext;

    public MultiImageAdapter(Context context, List<String> stringList) {
        this.mContext = context;
        this.mStringList = stringList;
    }

    @Override
    public int getCount() {
        return mStringList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ZoomImageView ziv = new ZoomImageView(mContext);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        ziv.reSetState();
        Glide.with(mContext)
                .load(mStringList.get(position))
                .into(ziv);
        //ziv.setImageForUrl(imgs[position]);
        container.addView(ziv, layoutParams);
        return ziv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}