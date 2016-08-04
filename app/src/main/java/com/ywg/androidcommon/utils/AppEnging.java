package com.ywg.androidcommon.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.ywg.androidcommon.model.AppInfo;

import java.util.ArrayList;
import java.util.List;

/**
* 获取App信息的一个封装类(包名、版本号、应用信息、图标、名称等)
*/
public class AppEnging {
    public static List<AppInfo> getAppInfos(Context context) {
        List<AppInfo> list = new ArrayList<AppInfo>();
        //获取应用程序信息
        //包的管理者
        PackageManager pm = context.getPackageManager();
        //获取系统中安装到所有软件信息
        List<PackageInfo> installedPackages = pm.getInstalledPackages(0);
        for (PackageInfo packageInfo : installedPackages) {
            //获取包名
            String packageName = packageInfo.packageName;
            //获取版本号
            String versionName = packageInfo.versionName;
            //获取application
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            int uid = applicationInfo.uid;
            //获取应用程序的图标
            Drawable icon = applicationInfo.loadIcon(pm);
            //获取应用程序的名称
            String name = applicationInfo.loadLabel(pm).toString();
            //是否是用户程序
            //获取应用程序中相关信息,是否是系统程序和是否安装到SD卡
            boolean isUser;
            int flags = applicationInfo.flags;
            if ((applicationInfo.FLAG_SYSTEM & flags) == applicationInfo.FLAG_SYSTEM) {
                //系统程序
                isUser = false;
            } else {
                //用户程序
                isUser = true;
            }
            //是否安装到SD卡
            boolean isSD;
            if ((applicationInfo.FLAG_EXTERNAL_STORAGE & flags) == applicationInfo.FLAG_EXTERNAL_STORAGE) {
                //安装到了SD卡
                isSD = true;
            } else {
                //安装到手机中
                isSD = false;
            }
            //添加到bean中
            AppInfo appInfo = new AppInfo(name, icon, packageName, versionName, isSD, isUser);
            //将bean存放到list集合
            list.add(appInfo);
        }
        return list;
    }
}