package com.ywg.androidcommon.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

import com.ywg.androidcommon.demo.AutoReadSmsActivity1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutoReadSmsCodeReceiver extends BroadcastReceiver {
    private static final String TAG = "AutoReadSmsCodeReceiver";
    private static final String SMS_RECEIVED_ACTION = Telephony.Sms.Intents.SMS_RECEIVED_ACTION;// 接收到短信时的action

    private Context mContext;
    private Handler mHandler;
    private int codeLength = 0;

    public AutoReadSmsCodeReceiver(Context context, Handler handler, int codeLength) {
        // TODO Auto-generated constructor stub
        this.mContext = context;
        this.mHandler = handler;
        this.codeLength = codeLength;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(SMS_RECEIVED_ACTION)) {
            getSmsCodeFromReceiver(intent);
        }
    }

    /**
     * 从接收者中得到短信验证码
     *
     * @param intent
     */
    private void getSmsCodeFromReceiver(Intent intent) {
        Bundle bundle = intent.getExtras();
        Object[] objects = (Object[]) bundle.get("pdus");
        if (objects != null) {
            String phone = null;
            StringBuffer content = new StringBuffer();
            for (int i = 0; i < objects.length; i++) {
                SmsMessage sms = SmsMessage.createFromPdu((byte[]) objects[i]);
                phone = sms.getDisplayOriginatingAddress();
                content.append(sms.getDisplayMessageBody());
            }
            Log.e(TAG, "phone:" + phone + "\ncontent:" + content.toString());
            checkCodeAndSend(content.toString());
        }
    }

    /**
     * @param content
     */
    private void checkCodeAndSend(String content) {
        // 话说.如果我们的短信提供商的短信号码是固定的话.前面可以加一个判断
        // 不然的话就在我们的正则表达式中,加一些自己的判断,例如短信中含有自己应用名字啊什么的...
        //可以在正则表达式之前加入一句判断，这样只有特定的号码发送过来的短信才会进行读取。
                /*if (!address.equals("13162364720")) {
                    return;
                }*/
        // 正则表达式验证是否含有验证码
        Pattern pattern = Pattern.compile("\\d{" + codeLength + "}");// compile的是规则
        Matcher matcher = pattern.matcher(content);// matcher的是内容
        if (matcher.find()) {
            String code = matcher.group(0);
            Log.e(TAG, "短信中找到了符合规则的验证码:" + code);
            mHandler.obtainMessage(AutoReadSmsActivity1.BC_SMS_RECEIVE, code).sendToTarget();
            Log.e(TAG, "广播接收器接收到短信的时间:" + System.currentTimeMillis());
        } else {
            Log.e(TAG, "短信中没有找到符合规则的验证码");
        }
    }

}