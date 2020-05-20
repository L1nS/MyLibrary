package com.lins.modulesystem.widget.xrefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lins.modulesystem.R;


/**
 * Created by Admin on 2017/3/23.
 */

public class LoadingMoreFooter extends LinearLayout {

    public final static int STATE_LOADING = 0;
    public final static int STATE_COMPLETE = 1;
    public final static int STATE_NOMORE = 2;
    private TextView mText;
    //    private AnimationDrawable mAnimationDrawable;
//    private ImageView mIvProgress;
    private ProgressBar idPb;

    public LoadingMoreFooter(Context context) {
        super(context);
        initView(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public LoadingMoreFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.widget_x_refresh_footer, this);
        mText = (TextView) findViewById(R.id.msg);
        idPb = (ProgressBar) findViewById(R.id.id_pb);
//        mIvProgress = (ImageView) findViewById(R.id.iv_progress);
//        mAnimationDrawable = (AnimationDrawable) mIvProgress.getDrawable();
//        if (!mAnimationDrawable.isRunning()) {
//            mAnimationDrawable.start();
//        }
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void setState(int state) {
        switch (state) {
            case STATE_LOADING:
//                if (!mAnimationDrawable.isRunning()) {
//                    mAnimationDrawable.start();
//                }
                idPb.setVisibility(View.VISIBLE);
                mText.setText(getContext().getText(R.string.widget_x_listview_loading));
                this.setVisibility(View.VISIBLE);
                break;
            case STATE_COMPLETE:
//                if (mAnimationDrawable.isRunning()) {
//                    mAnimationDrawable.stop();
//                }
                mText.setText(getContext().getText(R.string.widget_x_listview_loading));
                this.setVisibility(View.GONE);
                break;
            case STATE_NOMORE:
//                if (mAnimationDrawable.isRunning()) {
//                    mAnimationDrawable.stop();
//                }
                mText.setText(getContext().getText(R.string.widget_x_nomore_loading));
                idPb.setVisibility(View.GONE);
                this.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void reSet() {
        this.setVisibility(GONE);
    }
}

