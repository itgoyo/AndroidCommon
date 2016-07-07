package com.ywg.androidtools.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * 退出程序方法4：RS优雅式：即Receiver+singleTask
 * 我们只需两步操作即可优雅的实现app的退出：
    1、在MainActivity注册一个退出广播，和第二个广播式一样，但是这里只需要在MainActivity一个页面注册即可。
     2、设置MainActivity的启动模式为singleTask。
 当我们需要退出的时候只需要startActivity(this,MainActivity.class)， 再发送一个退出广播。
 上面代码首先会把栈中MainActivity之上的所有Activity移除出栈，然后接到广播finish自己。一切OK !
 没有弹框，不用考虑机型Rom适配。不会有内存问题，就是那么的优雅，简单！
 */
public class ExitAppActivity4 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}