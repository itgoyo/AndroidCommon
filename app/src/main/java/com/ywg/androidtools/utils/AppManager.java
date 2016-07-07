package com.ywg.androidtools.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**
 * Activity管理类：用于管理Activity和退出程序
 */
public class AppManager {

    // Activity栈  
    private static Stack<Activity> activityStack;
    // 单例模式  
    private static AppManager instance;

    private AppManager() {
    }

    /**
     * 单一实例
     */
    public static AppManager getInstance() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.remove(activity);// 先移除掉已经存在的
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        if (activityStack == null) {
            return null;
        }
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 获取指定的Activity
     *
     * @param cls 类名
     * @return
     */
    public static Activity getActivity(Class<?> cls) {
        if (activityStack != null)
            for (Activity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    return activity;
                }
            }
        return null;
    }

    /**
     * 判断当前的界面的activity是否是cls
     *
     * @param cls 类名
     * @return
     */
    public boolean activityVisible(Class<?> cls) {
        if (activityStack == null) {
            return false;
        }
        Activity activity = activityStack.lastElement();
        if (activity.getClass().equals(cls)) {
            return true;
        }
        return false;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null && !activity.isFinishing()) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0,size = activityStack.size();i < size; i++) {
            if (null != activityStack.get(i)) {
                finishActivity(activityStack.get(i));
                break;
            }
        }
        activityStack.clear();
    }

    /**
     * 移除activity
     *
     * @param activity activity实例
     * @return 移除出来的activity
     */
    public boolean removeActivity(Activity activity) {
        if (activity == null) {
            return false;
        }

        if (activityStack == null) {
            return false;
        }
        return activityStack.remove(activity);
    }


    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
        }
    }
}