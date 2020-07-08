package com.lins.modulesystem.utils.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.ViewTreeObserver;

public class DetachKeyClickListener implements DialogInterface.OnKeyListener {


    public static DetachKeyClickListener wrap(DialogInterface.OnKeyListener delegate) {
        return new DetachKeyClickListener(delegate);
    }

    private DialogInterface.OnKeyListener mDelegate;

    private DetachKeyClickListener(DialogInterface.OnKeyListener delegate) {
        this.mDelegate = delegate;
    }

    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (mDelegate != null) {
            mDelegate.onKey(dialog, keyCode, event);
        }
        return false;
    }

    public void clearOnDetach(Dialog dialog) {
        dialog.getWindow()
                .getDecorView()
                .getViewTreeObserver()
                .addOnWindowAttachListener(new ViewTreeObserver.OnWindowAttachListener() {
                    @Override
                    public void onWindowAttached() {
                    }

                    @Override
                    public void onWindowDetached() {
                        mDelegate = null;
                    }
                });
    }
}
