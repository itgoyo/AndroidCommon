package com.ywg.androidcommon.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ywg.androidcommon.utils.AppManager;

/**
 * 退出程序方法1：建立一个全局容器，在BaseActivity中把所有的Activity存储起来，退出时循环遍历finish所有Activity
 * 这种方法比较简单， 但是可以看到activityStack持有这Activity的强引用，也就是说当某个Activity异常退出时，
 * activityStack没有即使释放掉引用，就会导致内存问题，接下来我们看一种类似的方式，但是会稍微优雅一点点
 */
public class ExitAppActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 添加Activity到堆栈
        AppManager.getInstance().addActivity(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 结束Activity&从栈中移除该Activity
        AppManager.getInstance().removeActivity(this);
    }
}