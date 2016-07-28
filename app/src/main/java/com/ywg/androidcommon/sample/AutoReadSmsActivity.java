package com.ywg.androidcommon.sample;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.ywg.androidcommon.R;
import com.ywg.androidcommon.receiver.AutoReadSmsObserver;

/**
 * 第一种,实现ContentObserver,把我们自己的Observer注册到短信服务,短信应用收到新的短信的时候会发送给我我们自己的Observer,然后我们在自己的Observer中.通过代码发送给我们的需要填充的界面就行了.这种方式是利用了观察者模式
 */
public class AutoReadSmsActivity extends AppCompatActivity {

    public static final int MSG_RECEIVED_CODE = 1;

    private EditText mEtCode;

    private Button mBtnGetCode;

    private AutoReadSmsObserver mSmsObserver;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_RECEIVED_CODE:
                    String code = (String) msg.obj;
                    mEtCode.setText(code);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_read_sms);

        mEtCode = (EditText) findViewById(R.id.et_code);
        mBtnGetCode = (Button) findViewById(R.id.btn_get_code);
        // 验证码长度为6
        mSmsObserver = new AutoReadSmsObserver(this, mHandler, 6);
        Uri uri = Uri.parse("content://sms");
        //注册内容观察者
        getContentResolver().registerContentObserver(uri, true, mSmsObserver);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getContentResolver().unregisterContentObserver(mSmsObserver);
    }
}
