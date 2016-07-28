package com.ywg.androidcommon.receiver;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.Telephony;
import android.util.Log;

import com.ywg.androidcommon.sample.AutoReadSmsActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AutoReadSmsObserver extends ContentObserver {

    private static final String TAG = "AutoReadSmsObserver";
    private Handler mHandler;
    private Context mContext;
    private String compileValue;

    private static final String SMS_INBOX_URI = "content://sms/inbox";//API level>=23,可直接使用Telephony.Sms.Inbox.CONTENT_URI，用于获取cusor
    private static final String SMS_URI = "content://sms";//API level>=23,可直接使用Telephony.Sms.CONTENT_URI,用于注册内容观察者
    private static final String[] PROJECTION = new String[]{
            Telephony.Sms._ID,
            Telephony.Sms.ADDRESS,
            Telephony.Sms.BODY,
            Telephony.Sms.DATE
    };

    public AutoReadSmsObserver(Context context, Handler handler, int codeLength) {
        super(handler);
        mHandler = handler;
        mContext = context;
        compileValue = "\\d{" + codeLength + "}";
    }

    /**
     * 接收到短信时调用此方法
     */
    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
        // 短信内容变化时，第一次调用该方法时短信内容并没有写入到数据库中,return
        if (uri.toString().equals("content://sms/raw")) {
            return;
        }

        getValidateCode();//获取短信验证码

    }

    private void getValidateCode() {
        //当短信内容存储到数据库时再取出短信内容
        Uri inboxUri = Uri.parse(SMS_INBOX_URI);
        // 读取短信收件箱，只读取未读短信，即read=0，并按照默认排序
        Cursor cursor = mContext.getContentResolver().query(inboxUri, null,
                null, null, Telephony.Sms.Inbox.DEFAULT_SORT_ORDER);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                // 读取短信发送人
                String address = cursor.getString(cursor.getColumnIndex(Telephony.Sms.ADDRESS));
                // 读取短息内容
                String smsBody = cursor.getString(cursor.getColumnIndex(Telephony.Sms.BODY));
                Log.i(TAG, "发件人:" + address + ",内容:" + smsBody); // 在这里我们的短信提供商的号码如果是固定的话.我们可以再加一个判断,这样就不会受到别的短信应用的验证码的影响了
                // 不然的话就在我们的正则表达式中,加一些自己的判断,例如短信中含有自己应用名字啊什么的...
                //可以在正则表达式之前加入一句判断，这样只有特定的号码发送过来的短信才会进行读取。
                /*if (!address.equals("13162364720")) {
                    return;
                }*/
                // 正则表达式的使用,从一段字符串中取出六位连续的数字
                Pattern pattern = Pattern.compile(compileValue);
                Matcher matcher = pattern.matcher(smsBody);
                if (matcher.find()) {
                    //去除验证码
                    String code = matcher.group(0);
                    Log.i(TAG, code);
                    // 利用handler将得到的验证码发送给主界面
                    //发送消息给指定对象
                    mHandler.obtainMessage(AutoReadSmsActivity.MSG_RECEIVED_CODE, code).sendToTarget();
                }
            }
            // 关闭cursor的方法
            cursor.close();
        }
    }


}