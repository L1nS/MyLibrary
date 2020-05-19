package com.lins.modulehome.test.bitmapEdit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.lins.modulehome.R;
import com.lins.modulehome.test.utils.BitmapDecodeUtil;
import com.orhanobut.logger.Logger;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ImageRoundView extends View {
    private Bitmap oriBitmap;
    private Bitmap outBitmap;
    private Bitmap maskBitmap;
    private Matrix matrix;
    private int width;
    private int height;

    public ImageRoundView(Context context) {
        super(context);
        init();
    }

    public ImageRoundView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ImageRoundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);//关闭硬件加速
        maskBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mask_round);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        oriBitmap = BitmapDecodeUtil.decodeBitmapFromRes(getResources(), R.drawable.big_image, width, height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        outBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(outBitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        //圆角
//        mCanvas.drawRoundRect(0, 0, width, height, 10, 10, paint);
        //作为混合图片的背景边界
        mCanvas.drawBitmap(maskBitmap,0,0,null);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        mCanvas.drawBitmap(oriBitmap, 0, 0, paint);
        matrix = new Matrix();
        matrix.setScale(0.1f, 0.1f);


        canvas.drawBitmap(outBitmap, 0, 0, null);
    }

}
