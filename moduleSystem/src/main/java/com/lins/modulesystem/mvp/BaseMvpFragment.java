package com.lins.modulesystem.mvp;

import android.os.Bundle;

import com.lins.modulesystem.base.BaseFragment;

import androidx.annotation.Nullable;

public abstract class BaseMvpFragment<V, T extends BaseMvpPresenter<V>> extends BaseFragment {
    protected T mPresenter;

    protected abstract T initPresenter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (initPresenter() != null) {
			mPresenter = initPresenter();
			mPresenter.attachView((V) this);
		} 

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter.detachDisposable();
        }
        if (unbinder != null)
            unbinder.unbind();

    }
}
