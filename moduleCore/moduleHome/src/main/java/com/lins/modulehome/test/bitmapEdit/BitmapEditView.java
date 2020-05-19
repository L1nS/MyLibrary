package com.lins.modulehome.test.bitmapEdit;

import android.graphics.Bitmap;

import com.lins.modulesystem.mvp.BaseMvpView;

public interface BitmapEditView extends BaseMvpView {

    void displayImageEidted(Bitmap bmp);
}
