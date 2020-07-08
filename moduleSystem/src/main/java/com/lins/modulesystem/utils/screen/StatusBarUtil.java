package com.lins.modulesystem.utils.screen;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.lins.modulesystem.R;

import java.lang.reflect.Field;

import androidx.core.content.ContextCompat;

public class StatusBarUtil {

    /**
     * 解决沉浸式状态栏变灰问题
     *
     * @param activity
     */
    public static void statusBarTransparent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                Class decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
                Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
                field.setAccessible(true);
                field.setInt(activity.getWindow().getDecorView(), Color.TRANSPARENT);  //改为透明
            } catch (Exception e) {
            }
        }
    }


    /**
     * 隐藏系统状态栏
     *
     * @param activity
     */
    public static void statusBarHide(Activity activity) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Window window = activity.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 创建状态栏管理实例
            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
            // 激活状态栏设置
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setTintColor(ContextCompat.getColor(activity, R.color.colorTransparent));
            tintManager.setStatusBarAlpha(0.0f);
        }
    }


    /**
     * 这是状态栏文字图案颜色。
     *
     * @param activity
     * @param becomeLight true:变成亮色,false:变成暗色
     */
    public static void statusBarTextColor(Activity activity, boolean becomeLight) {
        if (becomeLight) {
            //实现状态栏图标和文字颜色为浅色
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        } else {
            //实现状态栏图标和文字颜色为暗色
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }


    /**
     * 隐藏系统状态栏后，增加view的高度，设置状态栏高度。
     *
     * @param view
     */
    public static void statusBarColorWithToolbar(Activity activity, View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) view.getLayoutParams();
            lp.width = FrameLayout.LayoutParams.MATCH_PARENT;
            lp.height = ScreenParameterUtil.getStatusBarHeight2(activity) + ScreenParameterUtil.topx(activity, 48);
            view.setLayoutParams(lp);
        }
    }

    /**
     * 修改状态栏颜色，支持5.0以上版本
     *
     * @param activity
     * @param colorId
     */
    public static void setStatusBarColor(Activity activity, int colorId, boolean becomeLight) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(colorId));
            if (becomeLight) {
                //实现状态栏图标和文字颜色为浅色
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            } else {
                //实现状态栏图标和文字颜色为暗色
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }
}
