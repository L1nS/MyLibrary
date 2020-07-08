package com.lins.modulesystem.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class MyWebView extends WebView {

    private OnScrollChangedCallback mOnScrollChangedCallback;

    public MyWebView(Context context) {
        super(context);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangedCallback != null) {
            mOnScrollChangedCallback.onScroll(l, t, l - oldl, t - oldt);
        }
    }

    public void setOnScrollChangedCallback(OnScrollChangedCallback callback) {
        this.mOnScrollChangedCallback = callback;
    }

    public interface OnScrollChangedCallback {
        void onScroll(int dx, int dy, int dx_change, int dy_change);
    }
}
