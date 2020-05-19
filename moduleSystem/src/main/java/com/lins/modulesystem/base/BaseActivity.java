package com.lins.modulesystem.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.lins.modulesystem.utils.screenUtil.StatusBarUtil;

import java.lang.ref.WeakReference;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity implements BaseFuncIml{
    public Handler handler;
    protected Context mContext;
    protected Unbinder unbinder;

    @LayoutRes
    public abstract int initLayoutResID();

    public abstract void initData();

    public abstract void initView();

    public abstract void initListener();

    public abstract void initLoad();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseApp) this.getApplication()).getActivityManager()
                .pushActivity(this); // 将activity推入管理栈
//        StatusBarUtil.statusBarTransparent(this);
//        StatusBarUtil.statusBarHide(this);
//        StatusBarUtil.statusBarTextColor(this, false);
        setContentView(initLayoutResID());
        unbinder = ButterKnife.bind(this);
        mContext = this;
        handler = new BaseHandler(this);

        initData();
        initView();
        initListener();
        initLoad();
    }

    protected void finishThis() {
        BaseApp.getInstance().getActivityManager().finishThis();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 销毁时activity出栈
        ((BaseApp) this.getApplication()).getActivityManager()
                .popActivity(this);
    }

    /**
     * 在程序消息队列中排队的消息保持了对目标Handler类的应用。如果Handler是个内部类，那么它也会保持它所在的外部类的引用。
     * 为了避免泄露这个外部类，应该将Handler声明为static嵌套类，并且使用对外部类的弱应用。
     */
    static class BaseHandler
            extends Handler {
        WeakReference<BaseActivity> mActivity;

        BaseHandler(BaseActivity activity) {
            mActivity = new WeakReference<BaseActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            BaseActivity activity = mActivity.get();
            super.handleMessage(msg);
            activity.handleMessage(msg);
        }
    }

    @Override
    public void handleMessage(Message msg) {

    }
}
