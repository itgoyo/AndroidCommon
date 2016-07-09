package com.ywg.androidtools;

import android.app.Application;
import android.content.Context;

import com.ywg.androidtools.utils.CrashHandler;

public class AppApplication extends Application {


    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mContext = this;
        CrashHandler.getInstance().init(this);//初始化全局异常管理
    }

    public static Context getContext(){
        return mContext;
    }
}