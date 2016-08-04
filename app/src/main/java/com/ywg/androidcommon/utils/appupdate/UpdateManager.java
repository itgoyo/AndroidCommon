package com.ywg.androidcommon.utils.appupdate;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/**
 * Created by zs on 2016/7/7.
 * 在需要的地方加上 new UpdateManager(this).checkUpdate(false);
 * 在AndroidManifest.xml加上
 * <!-- 网络 -->
 <uses-permission android:name="android.permission.INTERNET"/>
 <!-- sd卡存储 -->
 <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
 <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>

 <service android:name=".DownLoadService"/>
 */
public class UpdateManager {

    private Context mContext;

    public UpdateManager(Context context) {
        this.mContext = context;
    }

    /**
     * 检测软件更新
     */
    public void checkUpdate(final boolean isToast) {
        /**
         * 在这里请求后台接口，获取更新的内容和最新的版本号
         */
        // 版本的更新信息
        String version_info = "更新内容\n" + "    1. 车位分享异常处理\n" + "    2. 发布车位折扣格式统一\n" + "    ";
        int mVersion_code =getVersionCode(mContext);// 当前的版本号
        int nVersion_code = 2;
        if (mVersion_code < nVersion_code) {
            // 显示提示对话
            showNoticeDialog(version_info);
        } else {
            if (isToast) {
                Toast.makeText(mContext, "已经是最新版本", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 显示更新对话框
     *
     * @param version_info
     */
    private void showNoticeDialog(String version_info) {
        // 构造对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("更新提示");
        builder.setMessage(version_info);
        // 更新
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mContext.startService(new Intent(mContext, DownLoadService.class));
            }
        });
        // 稍后更新
        builder.setNegativeButton("以后更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog noticeDialog = builder.create();
        noticeDialog.show();
    }

    /*
    * 获取应用名
    */
    public static String getVersionName(Context context){
        String versionName = null;
        try {
            //获取包管理者
            PackageManager pm = context.getPackageManager();
            //获取packageInfo
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            //获取versionName
            versionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return versionName;
    }
    /*
     * 获取应用版本
     */
    public static int getVersionCode(Context context){

        int versionCode = 0;
        try {
            //获取包管理者
            PackageManager pm = context.getPackageManager();
            //获取packageInfo
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            //获取versionCode
            versionCode = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return versionCode;
    }
}