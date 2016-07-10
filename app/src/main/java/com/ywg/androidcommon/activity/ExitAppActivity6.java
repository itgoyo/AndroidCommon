package com.ywg.androidcommon.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * 退出程序方法6：进程式：
 * 通过直接杀死当前应用的进程来结束应用，简单粗暴，而且有（wu）效！

 android.os.Process.killProcess(android.os.Process.myPid());
 System.exit(0);

 ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
 manager.killBackgroundProcesses(getPackageName());

 这三种都能达到同样的效果，但是在模拟器上都会弹出 Unfortunately , XXX has stopped 消息提示框，但确实能退出应用。
 部分真机直接失效，只能finish当前Activity（比如我手上这台小米note，国产的几款ROM fw层改动太多，使用这种方式需慎重） 。
 *
 */
public class ExitAppActivity6 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}


