package com.lins.modulesystem.mvp;


import com.lins.modulesystem.utils.dialog.LoadingDialog;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseMvpPresenter<V> {
    protected Reference<V> mViewRef;
    protected LoadingDialog loadingDialog = LoadingDialog.getInstance();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void attachView(V view) {
        mViewRef = new WeakReference<V>(view);
    }

    protected V getView() {
        return mViewRef.get();
    }

    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    public void addDisposable(Disposable disposable) {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.add(disposable);
        }
    }

    public void removeDisposable(Disposable disposable) {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.remove(disposable);
        }
    }

    public void detachDisposable() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
    }

}
