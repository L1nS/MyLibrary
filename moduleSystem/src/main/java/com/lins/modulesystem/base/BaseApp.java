package com.lins.modulesystem.base;

import android.app.Application;

import com.lins.modulesystem.http.OkGoUtil;
import com.lins.modulesystem.manager.MyActivityManager;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class BaseApp extends Application {
    private static BaseApp instance;
    private MyActivityManager activityManager = null; // activity管理类

    @Override
    public void onCreate() {
        activityManager = MyActivityManager.getInstance();
        super.onCreate();
        instance = this;
        OkGoUtil.initOkGo(this);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public static BaseApp getInstance() {
        return instance;
    }

    public MyActivityManager getActivityManager() {
        return activityManager;
    }
}
