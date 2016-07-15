package com.ywg.androidcommon.utils.bitmapcache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * 三级缓存之内存缓存
 * Lru算法
    Lru: Least Recently Used 近期最少使用算法。
    Android提供了LruCache类来实现这个缓存算法。
 */
public class MemoryCacheUtil {

    // private HashMap<String,Bitmap> mMemoryCache=new HashMap<>();//1.因为强引用,容易造成内存溢出，所以考虑使用下面弱引用的方法
    // private HashMap<String, SoftReference<Bitmap>> mMemoryCache = new HashMap<>();//2.因为在Android2.3+后,系统会优先考虑回收弱引用对象,官方提出使用LruCache
    private LruCache<String, Bitmap> mMemoryCache;

    public MemoryCacheUtil() {
        // lruCache最大允许内存一般为Android系统分给每个应用程序内存大小（默认Android系统给每个应用程序分配16兆内存）的八分之一（推荐）
        // 获得当前应用程序运行的最大可用内存大小
        long maxMemory = Runtime.getRuntime().maxMemory();
        //得到手机最大允许内存的1/8,即超过指定内存,则开始回收
        int cacheSize = (int) (maxMemory / 8);
        //需要传入允许的内存最大值,虚拟机默认内存16M,真机不一定相同
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            //用于计算每个条目的大小
            @Override
            protected int sizeOf(String key, Bitmap value) {
                // 获取每张图片所占内存的大小
                // 计算方法是：图片显示的宽度的像素点乘以高度的像素点
                int byteCount = value.getRowBytes() * value.getHeight();// 获取图片占用内存大小
                return byteCount;
            }
        };

    }

    /**
     * 从内存中读取Bitmap
     *
     * @param url
     */
    public Bitmap getBitmapFromMemory(String url) {
        //Bitmap bitmap = mMemoryCache.get(url);//1.强引用方法
        /*2.弱引用方法
        SoftReference<Bitmap> bitmapSoftReference = mMemoryCache.get(url);
        if (bitmapSoftReference != null) {
            Bitmap bitmap = bitmapSoftReference.get();
            return bitmap;
        }
        */
        // LRU--least recently use
        // 最近最少使用,将内存控制在一定的大小内，超过这个内存大小，就会优先释放最近最少使用的那些东东
        Bitmap bitmap = mMemoryCache.get(url);
        return bitmap;

    }

    /**
     * 将图片保存到内存中
     *
     * @param url
     * @param bitmap
     */
    public void addBitmapToMemory(String url, Bitmap bitmap) {
        // 向内存中设置，key,value的形式，首先想到HashMap
        //mMemoryCache.put(url, bitmap);//1.强引用方法
        /*2.弱引用方法
        mMemoryCache.put(url, new SoftReference<>(bitmap));
        */
        if (getBitmapFromMemory(url) == null) {
            mMemoryCache.put(url, bitmap);
        }
    }
}