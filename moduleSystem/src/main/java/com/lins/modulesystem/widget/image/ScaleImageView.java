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
    //设定宽高比例
    private float dimensionRatioWidth;
    private float dimensionRatioHeight;
    private final float default_dimension_ratio = 1.0f;
    //CardView的角度
    private float cardCornerRadius;

    public ScaleImageView(Context context) {
        super(context);
    }

    public ScaleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //加载视图的布局
        LayoutInflater.from(context).inflate(R.layout.widget_scale_imageview, this, true);

        //加载自定义的属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ScaleImageView);

        dimensionRatioWidth = a.getFloat(R.styleable.ScaleImageView_dimensionRatioWidth, default_dimension_ratio);
        dimensionRatioHeight = a.getFloat(R.styleable.ScaleImageView_dimensionRatioHeight, default_dimension_ratio);
        cardCornerRadius = a.getDimension(R.styleable.ScaleImageView_cardCornerRadius, 0f);
        //回收资源，这一句必须调用
        a.recycle();
    }

    /**
     * 此方法会在所有的控件都从xml文件中加载完成后调用
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mImageView = (ImageView) findViewById(R.id.widget_scaleimageview_img);
        idCardView = findViewById(R.id.widget_scaleimageview_cardView);
        idConstraintLayout = findViewById(R.id.id_constraint_layout);
        if (dimensionRatioHeight != default_dimension_ratio || dimensionRatioWidth != default_dimension_ratio)
            setImageViewLayoutParams();
        idCardView.setRadius(cardCornerRadius);
    }

    /**
     * 初始化宽高比例
     */
    private void setImageViewLayoutParams() {
        ConstraintLayout.LayoutParams imageViewParams =
                new ConstraintLayout.LayoutParams(0, 0);
        imageViewParams.dimensionRatio = "h," + dimensionRatioWidth + ":" + dimensionRatioHeight;
        imageViewParams.leftToLeft = R.id.id_constraint_layout;
        imageViewParams.rightToRight = R.id.id_constraint_layout;
        imageViewParams.topToTop = R.id.id_constraint_layout;
        idCardView.setLayoutParams(imageViewParams);
    }

    /**
     * 自定义宽高比例
     *
     * @param ratioWidth
     * @param ratioHeight
     */
    public void initDimensionRatio(float ratioWidth, float ratioHeight) {
        dimensionRatioHeight = ratioHeight;
        dimensionRatioWidth = ratioWidth;
        setImageViewLayoutParams();
    }

    public void initDimensionRatio(float ratioHeight) {
        initDimensionRatio(1, ratioHeight);
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