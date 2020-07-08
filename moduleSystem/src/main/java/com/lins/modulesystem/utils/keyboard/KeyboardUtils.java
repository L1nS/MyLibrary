package com.lins.modulesystem.utils.keyboard;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.lins.modulesystem.base.BaseApp;

import java.util.Timer;
import java.util.TimerTask;

public class KeyboardUtils {
    static KeyboardUtils instance;

    private KeyboardUtils() {
        // construct
    }

    public static KeyboardUtils getInstance() {
        if (instance == null) {
            instance = new KeyboardUtils();
        }
        return instance;
    }

    /**
     * 隐藏软键盘
     *
     * @param editText
     */
    public void hideKeyBoard(View editText) {
        InputMethodManager imm =
                (InputMethodManager)
                        BaseApp.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    /**
     * 弹起软键盘
     *
     * @param editText
     */
    public void openKeyBoard(final EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager imm =
                        (InputMethodManager)
                                BaseApp.getInstance()
                                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, 0);
                editText.setSelection(editText.getText().length());
            }
        }, 200);

    }

    private boolean isInputMethodShowing() {
        //获取当前屏幕内容的高度
        int screenHeight = BaseApp.getInstance().getActivityManager().currentActivity().getWindow().getDecorView().getHeight();
        //获取View可见区域的bottom
        Rect rect = new Rect();
        BaseApp.getInstance().getActivityManager().currentActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);

        return screenHeight - rect.bottom - getSoftButtonsBarHeight() != 0;
    }

    /**
     * 底部虚拟按键栏的高度
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private int getSoftButtonsBarHeight() {
        DisplayMetrics metrics = new DisplayMetrics();
        //这个方法获取可能不是真实屏幕的高度
        BaseApp.getInstance().getActivityManager().currentActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        //获取当前屏幕的真实高度
        BaseApp.getInstance().getActivityManager().currentActivity().getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight) {
            return realHeight - usableHeight;
        } else {
            return 0;
        }
    }
}
