package com.lins.mylibrary;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lins.modulesystem.base.BaseApp;

public class MyApplication extends BaseApp {

    //ARouter调试开关
    private boolean isDebugARouter = true;

    @Override
    public void onCreate() {
        super.onCreate();
        initARouter();
    }

    private void initARouter(){
        if(isDebugARouter){
            //下面两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();//打印日志
            //开启调试模式（如果在InstantRun模式运行，必须开启调试模式
            // 线上模式需要关闭，否则有安全风险）
            ARouter.openDebug();
        }
        //推荐在Application中初始化
        ARouter.init(MyApplication.this);
    }
}
