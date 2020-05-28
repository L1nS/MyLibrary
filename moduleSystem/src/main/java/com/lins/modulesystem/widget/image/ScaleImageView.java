package com.lins.modulesystem.widget.image;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.lins.modulesystem.R;


public class ScaleImageView extends RelativeLayout {

    private ImageView mImageView;
    private CardView idCardView;
    private ConstraintLayout idConstraintLayout;
    private float dimensionRatioWidth;
    private float dimensionRatioHeight;

    public ScaleImageView(Context context) {
        super(context);
    }

    public ScaleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //加载视图的布局
        LayoutInflater.from(context).inflate(R.layout.widget_scale_imageview, this, true);

        //加载自定义的属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ScaleImageView);

        dimensionRatioWidth = a.getFloat(R.styleable.ScaleImageView_dimensionRatioWidth, 1.0f);
        dimensionRatioHeight = a.getFloat(R.styleable.ScaleImageView_dimensionRatioHeight, 1.0f);
        //回收资源，这一句必须调用
        a.recycle();
    }

    /**
     * 此方法会在所有的控件都从xml文件中加载完成后调用
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mImageView = (ImageView) findViewById(R.id.id_img);
        idCardView = findViewById(R.id.id_cardView);
        idConstraintLayout = findViewById(R.id.id_constraint_layout);
        setImageViewLayoutParams();
    }

    public void setImageViewLayoutParams() {
        ConstraintLayout.LayoutParams imageViewParams =
                new ConstraintLayout.LayoutParams(0, 0);
        imageViewParams.dimensionRatio = "h," + dimensionRatioWidth + ":" + dimensionRatioHeight;
        imageViewParams.leftToLeft = R.id.id_constraint_layout;
        imageViewParams.rightToRight = R.id.id_constraint_layout;
        imageViewParams.topToTop = R.id.id_constraint_layout;
        idCardView.setLayoutParams(imageViewParams);
    }

    public ImageView getImageView() {
        return mImageView;
    }

    /**
     * 设置按钮点击事件监听器
     *
     * @param listener
     */
    public void setOnItemClickListener(OnClickListener listener) {
        mImageView.setOnClickListener(listener);
    }
}