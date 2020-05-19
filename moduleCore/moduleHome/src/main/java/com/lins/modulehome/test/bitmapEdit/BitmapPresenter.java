package com.lins.modulehome.test.bitmapEdit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

import com.lins.modulesystem.mvp.BaseMvpPresenter;
import com.lins.modulesystem.utils.ToastUtil;

import java.math.BigDecimal;

public class BitmapPresenter extends BaseMvpPresenter<BitmapEditView> {

    private ColorMatrix hueMatrix = null;
    private ColorMatrix saturationMatrix = null;
    private ColorMatrix lumMatrix = null;

    //色调
    protected void setHue(Bitmap bmp, float hue) {
        if (hueMatrix == null) {
            hueMatrix = new ColorMatrix();
        }
        hueMatrix.setRotate(0, hue);//红
        hueMatrix.setRotate(1, hue);//绿
        hueMatrix.setRotate(2, hue);//蓝
        //正常值：hue = 0.0f
        //常用取值范围:hue ∈ (-180.0f, 180.0f)
        canvas(bmp);
    }

    //饱和度
    protected void setSaturation(Bitmap bmp, float saturation) {
        if (saturationMatrix == null) {
            saturationMatrix = new ColorMatrix();
        }
        saturationMatrix.setSaturation(saturation);
        //正常值：saturation  = 1.0f
        //常用取值范围:saturation ∈ (0.0f， 2.0f)
        canvas(bmp);

    }

    //亮度
    protected void setLum(Bitmap bmp, float lum) {
        if (lumMatrix == null) {
            lumMatrix = new ColorMatrix();
        }
        lumMatrix.setScale(lum, lum, lum, 1);
        //正常值：lum   = 1.0f
        //常用取值范围:lum  ∈ (0.0f， 2.0f)
        canvas(bmp);

    }

    private void canvas(Bitmap bitmap) {
        if (bitmap == null)
            return;
        ColorMatrix bitmapMatrix = new ColorMatrix();
        if (hueMatrix != null)
            bitmapMatrix.postConcat(hueMatrix);
        if (saturationMatrix != null)
            bitmapMatrix.postConcat(saturationMatrix);
        if (lumMatrix != null)
            bitmapMatrix.postConcat(lumMatrix);
        Bitmap bmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColorFilter(new ColorMatrixColorFilter(bitmapMatrix));
        canvas.drawBitmap(bitmap, 0, 0, paint);
        getView().displayImageEidted(bmp);
    }

    //底片
    protected void setBitmapFilm(Bitmap bitmap) {
        if (bitmap == null)
            return;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int r, g, b, a;

        int[] imgPix = new int[width * height];
        int[] newImgPix = new int[width * height];
        bitmap.getPixels(imgPix, 0, width, 0, 0, width, height);
        for (int i = 0; i < imgPix.length; i++) {
            r = Color.red(imgPix[i]);
            g = Color.green(imgPix[i]);
            b = Color.blue(imgPix[i]);
            a = Color.alpha(imgPix[i]);

            r = limit(255 - r);
            g = limit(255 - g);
            b = limit(255 - b);

            newImgPix[i] = Color.argb(a, r, g, b);
        }

        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bmp.setPixels(newImgPix, 0, width, 0, 0, width, height);
        getView().displayImageEidted(bmp);
    }

    /**
     * 限制argb取值范围
     *
     * @param value
     * @return
     */
    public int limit(int value) {
        if (value > 255) {
            return 255;
        } else if (value < 0) {
            return 0;
        }
        return value;
    }

    protected double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
