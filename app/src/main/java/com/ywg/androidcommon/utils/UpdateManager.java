package com.ywg.androidcommon.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by zhulei on 16/6/13.
 */
public class UpdateManager {

    private Context mContext;
    private MaterialDialog mProgressDialog;
    private ProgressBar mProgressBar;

    private String apkUrl;
    private String savePath = "/sdcard/store/";
    private String saveFileName = savePath + "storeReleaseNew.apk";

    public static final int DOWN_UPDATE = 1;
    public static final int DOWN_OVER = 2;

    private boolean mInterceptFlag;
    private int mProgress;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_UPDATE:
                    mProgressBar.setProgress(mProgress);
                    break;
                case DOWN_OVER:
                    installApk();
                    break;
            }
        }
    };

    public UpdateManager(Context context) {
        this.mContext = context;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/store/";
            saveFileName = savePath + "storeReleaseNew.apk";
        }
    }

    public void checkUpdateInfo() {
        //向服务端请求最新安装包的相关信息
      /*  Api.call(Api.METHOD_GET, Api.VERSION_UPDATE, null, null, new Api.Callback<Map>() {
            @Override
            public void onSuccess(Map result, String link) {
                if (result != null) {
                    //获取到的参数
                    //1.最新版本名称;2.最新版本号;3.更新内容;4.下载链接;5.是否强制更新
                    //ParseUtil是我们项目里封装解析Map的一个工具类,请自行解析
                    String versionName = ParseUtil.parseString(result, "versionName", "");
                    int versionCode = ParseUtil.parseInt(result, "versionCode");
                    String desc = ParseUtil.parseString(result, "desc", "");
                    apkUrl = ParseUtil.parseString(result, "url");
                    boolean isForce = ParseUtil.parseBoolean(result, "force");
                    if (versionCode > BuildConfig.VERSION_CODE) {
                        showNoticeDialog("版本更新:" + versionName, desc, isForce);
                    }
                }
            }

            @Override
            public void onFail(String message) {
            }

            @Override
            public void onTokenOverdue() {
            }
        });*/
    }

    private void showNoticeDialog(String title, String message, boolean isForce) {
        //此处我使用了MaterialDialog的库,dialog的样式请自行处理
        final MaterialDialog dialog = new MaterialDialog(mContext);
        dialog.setTitle(title)
                .setMessage(message)
                .setCanceledOnTouchOutside(false)
                .setPositiveButton("开始更新", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        showDownloadDialog();
                    }
                });
        if (!isForce) {
            dialog.setNegativeButton("暂不更新", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
        dialog.show();
    }

    private void showDownloadDialog() {
        mProgressDialog = new MaterialDialog(mContext);
       /* View customView = LayoutInflater.from(mContext).inflate(R.layout.progress, null);
        mProgressBar = (ProgressBar) customView.findViewById(R.id.progress);
        mProgressDialog.setTitle("正在更新...")
                .setContentView(customView)
                .setCanceledOnTouchOutside(false)
                .setPositiveButton("取消下载", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mProgressDialog.dismiss();
                        mInterceptFlag = true;
                    }
                })
                .show();
        downLoadApk();*/
    }

    /**
     * 开线程下载
     */
    private void downLoadApk() {
        Thread downLoadThread = new Thread(mDownLoadRunnable);
        downLoadThread.start();
    }

    private Runnable mDownLoadRunnable = new Runnable() {
        @Override
        public void run() {
            if (apkUrl == null) {
                return;
            }
            FileOutputStream fos = null;
            InputStream is = null;
            try {
                URL url = new URL(apkUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                int length = conn.getContentLength();
                is = conn.getInputStream();

                File file = new File(savePath);
                if (!file.exists()) {
                    file.mkdir();
                }
                File apkFile = new File(saveFileName);
                fos = new FileOutputStream(apkFile);
                int count = 0;
                byte buf[] = new byte[1024];
                do {
                    int len = is.read(buf);
                    if (len != -1) {
                        count += len;
                        mProgress = (int) (((float) count / length) * 100);
                        mHandler.sendEmptyMessage(DOWN_UPDATE);
                    } else {
                        mHandler.sendEmptyMessage(DOWN_OVER);
                        break;
                    }
                    fos.write(buf, 0, len);
                } while (!mInterceptFlag);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                mProgressDialog.dismiss();
                if (null != is) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (null != fos) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    /**
     * 安装apk
     */
    private void installApk() {
        File apkFile = new File(saveFileName);
        if (!apkFile.exists()) {
            return;
        }
        //开启安装界面
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkFile.toString()), "application/vnd.android.package-archive");
        mContext.startActivity(i);
        //如果不杀进程不会跳到安装后打开的页面
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}