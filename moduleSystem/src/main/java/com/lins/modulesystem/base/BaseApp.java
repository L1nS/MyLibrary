package com.lins.modulesystem.base;

import android.app.Application;
import android.content.Context;

import com.hjq.toast.ToastUtils;
import com.lins.modulesystem.R;
import com.lins.modulesystem.http.OkGoUtil;
import com.lins.modulesystem.manager.MyActivityManager;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.lins.modulesystem.utils.crash.CrashHandler;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;

public class BaseApp extends Application {
    private static BaseApp instance;
    private MyActivityManager activityManager = null; // activity管理类

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorTheme, android.R.color.white);//全局设置主题颜色
                return new MaterialHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }


    @Override
    public void onCreate() {
        activityManager = MyActivityManager.getInstance();
        super.onCreate();
        instance = this;
        OkGoUtil.initOkGo(this);
        Logger.addLogAdapter(new AndroidLogAdapter());
        ToastUtils.init(this);
        CrashHandler.getInstance().init(this);
    }

    public static BaseApp getInstance() {
        return instance;
    }

    public MyActivityManager getActivityManager() {
        return activityManager;
    }
}
