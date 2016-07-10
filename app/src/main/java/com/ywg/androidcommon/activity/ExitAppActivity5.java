package com.ywg.androidcommon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * 退出程序方法5：第四种方法的改版式:
 *  1、设置MainActivity的加载模式为singleTask
    2、重写MainActivity中的onNewIntent方法
    3、需要退出时在Intent中添加退出的tag
 在需要退出的时候添加如下代码：
 Intent intent = new Intent(this,MainActivity.class);
 intent.putExtra(MainActivity.TAG_EXIT, true);
 startActivity(intent);
 *
 */
public class ExitAppActivity5 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private static final String TAG_EXIT = "exit";

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            boolean isExit = intent.getBooleanExtra(TAG_EXIT, false);
            if (isExit) {
                this.finish();
            }
        }
    }



}