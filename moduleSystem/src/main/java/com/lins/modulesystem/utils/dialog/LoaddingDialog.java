package com.lins.modulesystem.utils.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import com.lins.modulesystem.R;


/**
 */
public class LoaddingDialog {
    protected static final String TAG = "ProgressDialog";
    private static LoaddingDialog mProgressDialog = null;
    private Dialog dialog;
    private boolean isShow = false; // loading框 是否正在showing

    public static LoaddingDialog getInstance() {
        if (mProgressDialog == null) {
            mProgressDialog = new LoaddingDialog();
        }
        return mProgressDialog;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean isShow) {
        this.isShow = isShow;
    }

    /**
     * 得到自定义的progressDialog
     *
     * @param context
     * @return
     */
    public void createLoadingDialog(Context context) {
        if (dialog != null && !dialog.isShowing()) {
            isShow = false;
        }
        if (!isShow) {
            isShow = true;
            if (context instanceof Activity) {
                if (!((Activity) context).isFinishing()) {
                    dialog = new Dialog(context, R.style.LoadingDialog);
                    dialog.setCancelable(false);// 不可以用“返回键”取消
                    dialog.setContentView(R.layout.dialog_loading);// 设置布局
                    dialog.show();
                }
            } else {
                dialog = new Dialog(context,R.style.LoadingDialog);
                dialog.setCancelable(false);// 不可以用“返回键”取消
                dialog.setContentView(R.layout.dialog_loading);// 设置布局
                if (context != null) {
                    try {
                        dialog.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    public void dismissDialog() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            isShow = false;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
