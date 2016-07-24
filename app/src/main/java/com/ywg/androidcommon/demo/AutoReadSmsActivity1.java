package com.ywg.androidcommon.demo;

import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.ywg.androidcommon.R;
import com.ywg.androidcommon.receiver.AutoReadSmsCodeReceiver;

/**
 * 第二种,我们自己的应用创建一个广播接收器,接受短信变化的广播,然后在收到广播的时候,再把验证码提取出来发送给我们的需要填充验证码的地方就行了
 */
public class AutoReadSmsActivity1 extends AppCompatActivity {

    public static final int BC_SMS_RECEIVE = 1;

    private EditText mEtCode;

    private Button mBtnGetCode;

    private AutoReadSmsCodeReceiver mSmsCodeReceiver;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BC_SMS_RECEIVE:
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
        mSmsCodeReceiver = new AutoReadSmsCodeReceiver(AutoReadSmsActivity1.this, mHandler, 6);
        IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(mSmsCodeReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mSmsCodeReceiver);
    }
}
