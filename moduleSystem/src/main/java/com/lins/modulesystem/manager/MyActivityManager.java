package com.lins.modulesystem.manager;

import android.app.Activity;

import java.util.Stack;

/**
 * activity管理类，防止activity跳转混乱
 * Created by Admin on 2017/3/13.
 */

public class MyActivityManager {
    /**
     * 接收activity的Stack
     */
    private static Stack<Activity> activityStack = null;
    private static MyActivityManager activityManager = null;

    private MyActivityManager() {
    }

    /**
     * 单实例
     *
     * @return
     */
    public static MyActivityManager getInstance() {
        if (activityManager == null) {
            activityManager = new MyActivityManager();
        }
        return activityManager;
    }

    /**
     * 将activity移出栈
     *
     * @param activity
     */
    public void popActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
        }
    }

    /**
     * 结束指定activity
     *
     * @param activity
     */
    public void endActivity(Activity activity) {
        if (activity != null) {
            activity.finish();
            activityStack.remove(activity);
            activity = null;
        }
    }

    /**
     * 获得当前的activity(即最上层)
     *
     * @return
     */
    public Activity currentActivity() {
        Activity activity = null;
        if (!activityStack.empty())
            activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前的activity
     */
    public void finishThis() {
        Activity activity;
        if (!activityStack.empty()) {
            activity = activityStack.lastElement();
            activity.finish();
        }
    }

    /**
     * 将activity推入栈内
     *
     * @param activity
     */
    public void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 弹出除cls外的所有activity
     *
     * @param cls
     */
    public void popAllActivityExceptOne(Class<? extends Activity> cls) {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                break;
            }
            popActivity(activity);
        }
    }

    /**
     * 结束除cls之外的所有activity,执行结果都会清空Stack
     *
     * @param cls
     */
    public void finishAllActivityExceptOne(Class<? extends Activity> cls) {
        while (!activityStack.empty()) {
            Activity activity = currentActivity();
            if (activity.getClass().equals(cls)) {
                popActivity(activity);
            } else {
                endActivity(activity);
            }
        }
    }

    /**
     * 结束所有activity,返回主页
     */
//    public void finishToIndex() {
//        while (!activityStack.empty()) {
//            Activity activity = currentActivity();
//            if (activity.getClass().equals(MainActivity.class)) {
//                popActivity(activity);
//            } else {
//                endActivity(activity);
//            }
//        }
//    }

    /**
     * 结束所有activity
     */
    public void finishAllActivity() {
        while (!activityStack.empty()) {
            Activity activity = currentActivity();
            endActivity(activity);
        }
        System.exit(0);
    }
}
