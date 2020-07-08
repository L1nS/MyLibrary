package com.lins.modulesystem.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;


/**
 * 自定义ScrollView
 * 用于解决ScrollView嵌套ScrollView时候内层ScrollView无法滑动的问题
 */
public class MyScrollView extends ScrollView {
    public MyScrollView(Context context) {
        this(context,null);
    }
    public MyScrollView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //�ؼ�������
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.onInterceptTouchEvent(ev);
    }
}