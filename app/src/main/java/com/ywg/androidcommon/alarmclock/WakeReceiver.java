package com.ywg.androidcommon.alarmclock;

import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import com.ywg.androidcommon.utils.LogUtil;

public class WakeReceiver extends BroadcastReceiver {

    private final static String TAG = WakeReceiver.class.getSimpleName();
    private final static int WAKE_SERVICE_ID = -1111;

    /**
     * 灰色保活手段唤醒广播的action
     */
    public final static String GRAY_WAKE_ACTION = "com.kaku.weac.wake.gray";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (GRAY_WAKE_ACTION.equals(action)) {
            LogUtil.i(TAG, "wake !! wake !! ");

            Intent wakeIntent = new Intent(context, WakeNotifyService.class);
            context.startService(wakeIntent);
        }
    }

    /**
     * 用于其他进程来唤醒UI进程用的Service
     */
    public static class WakeNotifyService extends Service {

        @Override
        public void onCreate() {
            LogUtil.i(TAG, "WakeNotifyService->onCreate");
            super.onCreate();
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            LogUtil.i(TAG, "WakeNotifyService->onStartCommand");
            if (Build.VERSION.SDK_INT < 18) {
                startForeground(WAKE_SERVICE_ID, new Notification());//API < 18 ，此方法能有效隐藏Notification上的图标
            } else {
                Intent innerIntent = new Intent(this, WakeGrayInnerService.class);
                startService(innerIntent);
                startForeground(WAKE_SERVICE_ID, new Notification());
            }
            return START_STICKY;
        }

        @Override
        public IBinder onBind(Intent intent) {
            throw new UnsupportedOperationException("Not yet implemented");
        }

        @Override
        public void onDestroy() {
            LogUtil.i(TAG, "WakeNotifyService->onDestroy");
            super.onDestroy();
        }
    }

    /**
     * 给 API >= 18 的平台上用的灰色保活手段
     */
    public static class WakeGrayInnerService extends Service {

        @Override
        public void onCreate() {
            LogUtil.i(TAG, "InnerService -> onCreate");
            super.onCreate();
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            LogUtil.i(TAG, "InnerService -> onStartCommand");
            startForeground(WAKE_SERVICE_ID, new Notification());
            //stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }

        @Override
        public IBinder onBind(Intent intent) {
            throw new UnsupportedOperationException("Not yet implemented");
        }

        @Override
        public void onDestroy() {
            LogUtil.i(TAG, "InnerService -> onDestroy");
            super.onDestroy();
        }
    }
}