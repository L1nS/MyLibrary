package com.lins.modulesystem.utils.screenUtil;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import com.lins.modulesystem.base.BaseApp;


public class ScreenParameterUtil {

    // 屏幕像素点
    private static final Point screenSize = new Point();

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int screentWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    public static int screentHeight(Context context) {
        //此方法获取到的高度不包含底部虚拟按键。
//        DisplayMetrics dm = context.getResources().getDisplayMetrics();
//        return dm.heightPixels;
        WindowManager windowManager =
                (WindowManager) context.getSystemService(Context.
                        WINDOW_SERVICE);
        final Display display = windowManager.getDefaultDisplay();
        Point outPoint = new Point();
        if (Build.VERSION.SDK_INT >= 19) {
            // 可能有虚拟按键的情况
            display.getRealSize(outPoint);
        } else {
            // 不可能有虚拟按键
            display.getSize(outPoint);
        }
        int mRealSizeHeight;//手机屏幕真实高度
        mRealSizeHeight = outPoint.y;
        return mRealSizeHeight;
    }

    /**
     * 获取状态栏高度
     *
     * @param activity
     * @return 注意* 如果单单获取statusBar高度而不获取titleBar高度时，这种方法并不推荐大家使用
     * 因为这种方法依赖于WMS（窗口管理服务的回调）。正是因为窗口回调机制，
     * 所以在Activity初始化时执行此方法得到的高度是0，这就是很多人获取到statusBar高度为0的原因。
     * 这个方法推荐在回调方法onWindowFocusChanged()中执行，才能得到预期结果
     */
//    public static int getStatusBarHeight(Activity activity) {
//        Rect frame = new Rect();
//        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
//        int statusBarHeight = frame.top;
//        return statusBarHeight;
//    }


    /**
     * 获取导航栏的高度
     *
     * @return
     */
    public static int getStatusBarHeight() {
        Resources resources = BaseApp.getInstance().getApplicationContext().getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    public static int getStatusBarHeight2(Activity activity) {
        int result = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取导航栏高度
     *
     * @param context
     * @return
     */
    public static int getDaoHangHeight(Context context) {
        int result = 0;
        int resourceId = 0;
        int rid = context.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        if (rid != 0) {
            resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            return context.getResources().getDimensionPixelSize(resourceId);
        } else
            return 0;
    }

    public static int toDip(Context context, float paramFloat) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                paramFloat, context.getResources().getDisplayMetrics());
    }

    public static int toSp(Context context, float paramFloat) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                paramFloat, context.getResources().getDisplayMetrics());
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     */
    public static int topx(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public static int dpToPx(int dp) {
        DisplayMetrics metrics = getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }

    public static int spToPx(int sp) {
        DisplayMetrics metrics = getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, metrics);
    }

    /**
     * 密度转换像素
     */
    public static int dip2px(Context context, float dipValue) {

        return (int) (dipValue * context.getResources().getDisplayMetrics().density + 0.5f);

    }

    /**
     * sp转化为px工具
     */
    public static int sp2px(Context context, int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                context.getResources().getDisplayMetrics());
    }

    /**
     * 获取屏幕像素点
     *
     * @param context
     * @return
     */
    public static Point getScreenSize(Activity context) {

        if (context == null) {
            return screenSize;
        }
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            DisplayMetrics mDisplayMetrics = new DisplayMetrics();
            Display diplay = wm.getDefaultDisplay();
            if (diplay != null) {
                diplay.getMetrics(mDisplayMetrics);
                int W = mDisplayMetrics.widthPixels;
                int H = mDisplayMetrics.heightPixels;
                if (W * H > 0 && (W > screenSize.x || H > screenSize.y)) {
                    screenSize.set(W, H);
                }
            }
        }
        return screenSize;
    }

    public static DisplayMetrics getDisplayMetrics() {
        DisplayMetrics metrics = BaseApp.getInstance().getApplicationContext()
                .getResources()
                .getDisplayMetrics();
        return metrics;
    }

}
