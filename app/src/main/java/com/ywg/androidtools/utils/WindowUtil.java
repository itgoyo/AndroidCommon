package com.ywg.androidtools.utils;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * 窗口工具箱
 *
 * @author zhenguo
 */
public final class WindowUtil {

    /**
     * Don't let anyone instantiate this class.
     */
    private WindowUtil() {
        throw new Error("Do not need instantiate!");
    }

    /**
     * 获取当前窗口的旋转角度
     *
     * @param activity activity
     * @return  int
     */
    public static int getDisplayRotation(Activity activity) {
        switch (activity.getWindowManager().getDefaultDisplay().getRotation()) {
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return 90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return 270;
            default:
                return 0;
        }
    }

    /**
     * 当前是否是横屏
     *
     * @param context  context
     * @return  boolean
     */
    public static final boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * 当前是否是竖屏
     *
     * @param context  context
     * @return   boolean
     */
    public static final boolean isPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }
    /**
     *  调整窗口的透明度  1.0f,0.5f 变暗
     * @param from  from>=0&&from<=1.0f
     * @param to  to>=0&&to<=1.0f
     * @param context  当前的activity
     */
    public static void dimBackground(final float from, final float to, Activity context) {
        final Window window = context.getWindow();
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        WindowManager.LayoutParams params
                                = window.getAttributes();
                        params.alpha = (Float) animation.getAnimatedValue();
                        window.setAttributes(params);
                    }
                });
        valueAnimator.start();
    }


    private static WindowManager mWindowManager;

    private static WindowManager getWindowManager(Context context) {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }

    private static WindowManager.LayoutParams createDefaultLayoutParams() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();

        int type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            type = WindowManager.LayoutParams.TYPE_TOAST;
        }
        params.type = type;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        params.format = PixelFormat.TRANSLUCENT;

        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;

        params.gravity = Gravity.CENTER;
        return params;
    }

    /**
     * Adds a child view with the specified layout parameters.
     *
     * @param context
     * @param view
     */
    public static void addView(Context context, View view) {
        addView(context, view, createDefaultLayoutParams());
    }

    /**
     * Adds a child view with the specified layout parameters.
     *
     * @param context
     * @param view
     * @param params
     */
    public static void addView(Context context, View view, WindowManager.LayoutParams params) {
        if (context == null || view == null) {
            throw new IllegalArgumentException("argument cannot be null.");
        }
        mWindowManager = getWindowManager(context);
        mWindowManager.addView(view, params);
    }

    /**
     * Removes a view during layout.
     *
     * @param context
     * @param view
     */
    public static void removeView(Context context, View view) {
        if (context == null || view == null) {
            throw new IllegalArgumentException("argument cannot be null.");
        }
        mWindowManager = getWindowManager(context);
        mWindowManager.removeView(view);
    }

    /**
     * Update a view by params
     *
     * @param context
     * @param view
     * @param params
     */
    public static void updateView(Context context, View view, WindowManager.LayoutParams params) {
        if (context == null || view == null || params == null) {
            throw new IllegalArgumentException("argument cannot be null.");
        }
        mWindowManager = getWindowManager(context);
        mWindowManager.updateViewLayout(view, params);
    }
}