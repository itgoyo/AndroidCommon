package com.ywg.androidtools.adapter.recyclerview;

/**
 * Created by zhy on 16/4/9.
 */
public interface SectionSupport<T>
{
    public int sectionHeaderLayoutId();

    public int sectionTitleTextViewId();

    public String getTitle(T t);
}