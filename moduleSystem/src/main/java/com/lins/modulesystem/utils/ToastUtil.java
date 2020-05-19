package com.lins.modulesystem.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Admin on 2017/3/13.
 */

public class ToastUtil {
    private static Toast mToast;

    /**
     * 非阻塞式显示Toast，防止出现连续点击Toast时的显示问题
     */
    public static void showToast(Context context, CharSequence text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, duration);
        } else {
            mToast.setText(text);
            mToast.setDuration(duration);
        }
        mToast.show();
    }

    public static void showToast(Context context, CharSequence text) {
        showToast(context, text, Toast.LENGTH_SHORT);
    }
}
