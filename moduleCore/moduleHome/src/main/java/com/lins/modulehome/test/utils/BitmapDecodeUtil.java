package com.lins.modulehome.test.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.lins.modulesystem.utils.BaseMathUtils;
import com.lins.modulesystem.utils.image.BitmapOptionsUtils;
import com.orhanobut.logger.Logger;

public class BitmapDecodeUtil {

    public static Bitmap decodeBitmapFromRes(Resources res,int path, int reqWidth, int reqHeight) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, path, options);
        //计算原图片的宽高比
        double widthScale = widthScaleWithHeight(options);
        options.inSampleSize = BitmapOptionsUtils.calculateInSampleSize(options, reqWidth,
                reqHeight);
        options.inJustDecodeBounds = false;
        //获取缩略图
        Bitmap bmp = BitmapFactory.decodeResource(res, path, options);
        //按照控件比例进行缩放
        Bitmap resultBmp = BitmapOptionsUtils.createScaleBitmap(bmp, reqHeight, widthScale);
        bmp.recycle();
        return resultBmp;
    }

    public static Bitmap decodeBitmapFromFile(String path, int reqWidth, int reqHeight) {

        // 取得图片旋转角度
        int angle = BitmapOptionsUtils.readPictureDegree(path);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        //计算原图片的宽高比
        double widthScale = widthScaleWithHeight(options);
        options.inSampleSize = BitmapOptionsUtils.calculateInSampleSize(options, reqWidth,
                reqHeight);
        options.inJustDecodeBounds = false;
        //获取缩略图
        Bitmap bmp = BitmapFactory.decodeFile(path, options);
        //按照控件比例进行缩放
        Bitmap resultBmp = BitmapOptionsUtils.createScaleBitmap(bmp, reqHeight, widthScale);
        bmp.recycle();
        if (angle == 0) {
            return resultBmp;
        } else {
            // 修复图片被旋转的角度
            return BitmapOptionsUtils.rotaingImageView(angle, resultBmp);
        }
    }

    public static double widthScaleWithHeight(BitmapFactory.Options options) {
        int width = options.outWidth;
        int height = options.outHeight;
        return BaseMathUtils.div(width, height, 2);
    }


    public static void calculateBitmapSize(Bitmap bmp) {
        int bmSize = 0;
        bmSize += bmp.getByteCount(); // 得到bitmap的大小
        int kb = bmSize / 1024;
        int mb = kb / 1024;
        Logger.d("bitmap size = " + mb + "MB" + "  ," + kb + "KB");
    }
}
