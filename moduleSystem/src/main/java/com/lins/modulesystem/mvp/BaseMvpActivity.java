package com.lins.modulesystem.mvp;

import android.os.Bundle;

import com.lins.modulesystem.base.BaseActivity;

import androidx.annotation.Nullable;

public abstract class BaseMvpActivity<V, T extends BaseMvpPresenter<V>> extends BaseActivity {
    protected T mPresenter;

    protected abstract T initPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //允许为空，不是所有都要实现MVP模式
        if (initPresenter() != null) {
            mPresenter = initPresenter();
            mPresenter.attachView((V) this);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter.detachDisposable();
        }
        if (unbinder != null)
            unbinder.unbind();
    }

}
