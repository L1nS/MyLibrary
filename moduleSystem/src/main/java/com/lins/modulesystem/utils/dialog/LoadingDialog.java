package com.lins.modulesystem.utils.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;

import com.lzy.okgo.OkGo;
import com.lins.modulesystem.R;


/**
 *
 */
public class LoadingDialog {
    protected static final String TAG = "ProgressDialog";
    private static LoadingDialog mProgressDialog = null;
    private Dialog dialog;
    private boolean isShow = false; // loading框 是否正在showing

    public static LoadingDialog getInstance() {
        if (mProgressDialog == null) {
            mProgressDialog = new LoadingDialog();
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
        createLoadingDialog(context, false);
    }

    public void createLoadingDialog(Context context, boolean ableCancel) {
        if (dialog != null && !dialog.isShowing()) {
            isShow = false;
        }
        if (!isShow) {
            isShow = true;
            if (context instanceof Activity) {
                if (!((Activity) context).isFinishing()) {
                    dialog = new Dialog(context, R.style.LoadingDialog);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setContentView(R.layout.dialog_loading);// 设置布局
                    if (!ableCancel) {
                        dialog.setCancelable(false);// 不可以用“返回键”取消
                    } else {
                        dialog.setOnKeyListener(keyClickListener);
                    }
                    dialog.show();
                }
            } else {
                dialog = new Dialog(context, R.style.LoadingDialog);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setContentView(R.layout.dialog_loading);// 设置布局
                if (!ableCancel) {
                    dialog.setCancelable(false);// 不可以用“返回键”取消
                } else {
                    dialog.setOnKeyListener(keyClickListener);
                }
                dialog.show();
            }
        }
    }

    DetachKeyClickListener keyClickListener = DetachKeyClickListener.wrap(new DialogInterface.OnKeyListener() {
        @Override
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                OkGo.getInstance().cancelAll();
                dismissDialog();
                return true;
            }
            return false;
        }
    });

    public void dismissDialog() {
        try {
            if (dialog != null && dialog.isShowing()) {
                keyClickListener.clearOnDetach(dialog);
                dialog.dismiss();
                dialog = null;
            }
            isShow = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
