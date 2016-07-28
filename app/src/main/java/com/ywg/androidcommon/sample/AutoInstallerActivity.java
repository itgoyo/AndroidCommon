package com.ywg.androidcommon.sample;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.ywg.androidcommon.R;
import com.ywg.androidcommon.utils.autoinstaller.AutoInstaller;

import java.io.File;

public class AutoInstallerActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String APK_FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Download" + File.separator + "test.apk";
    public static final String CACHE_FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Download";
    public static final String APK_URL = "http://55g.pc6.com/44300404751/4760001/43900265701";
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_installer);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("正在下载");

        findViewById(R.id.btn_install).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
         /* 方案一: 默认安装器 */
       /* AutoInstaller installer = AutoInstaller.getDefault(AutoInstallerActivity.this);
//        installer.install(APK_FILE_PATH);
        installer.installFromUrl(APK_URL);
        installer.setOnStateChangedListener(new AutoInstaller.OnStateChangedListener() {
            @Override
            public void onStart() {
                mProgressDialog.show();
            }

            @Override
            public void onComplete() {
                mProgressDialog.dismiss();
            }

            @Override
            public void onNeed2OpenService() {
                Toast.makeText(AutoInstallerActivity.this, "请打开辅助功能服务", Toast.LENGTH_SHORT).show();
            }
        });*/


        /* 方案二: 构造器 */
        AutoInstaller installer = new AutoInstaller.Builder(AutoInstallerActivity.this)
                //.setMode(AutoInstaller.MODE.AUTO_ONLY)
                .setCacheDirectory(CACHE_FILE_PATH)
                .build();
//        installer.install(APK_FILE_PATH);
        installer.installFromUrl(APK_URL);
        installer.setOnStateChangedListener(new AutoInstaller.OnStateChangedListener() {
            @Override
            public void onStart() {
                mProgressDialog.show();
            }

            @Override
            public void onComplete() {
                mProgressDialog.dismiss();
            }

            @Override
            public void onNeed2OpenService() {
                Toast.makeText(AutoInstallerActivity.this, "请打开辅助功能服务", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
