package com.ywg.androidtools.utils.bitmapcache;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.ywg.androidtools.R;

/**
 * 自定义的BitmapUtils,实现三级缓存
 */
public class BitmapCacheUtil {

    private NetCacheUtil mNetCacheUtils;
    private LocalCacheUtil mLocalCacheUtils;
    private MemoryCacheUtil mMemoryCacheUtils;

    public BitmapCacheUtil() {
        mMemoryCacheUtils = new MemoryCacheUtil();
        mLocalCacheUtils = new LocalCacheUtil();
        mNetCacheUtils = new NetCacheUtil(mLocalCacheUtils, mMemoryCacheUtils);
    }

    public void disPlayImage(ImageView imageView, String url) {
        imageView.setImageResource(R.mipmap.ic_launcher);
        Bitmap bitmap;
        //1.内存缓存
        bitmap = mMemoryCacheUtils.getBitmapFromMemory(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            System.out.println("从内存缓存中加载图片");
            return;
        }

        //2.本地缓存
        bitmap = mLocalCacheUtils.getBitmapFromLocal(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            System.out.println("从本地SD卡加载的图片");
            //从本地获取图片后,保存至内存中
            mMemoryCacheUtils.setBitmapToMemory(url, bitmap);
            return;
        }
        //3.网络缓存
        mNetCacheUtils.getBitmapFromNet(imageView, url);
    }
}
