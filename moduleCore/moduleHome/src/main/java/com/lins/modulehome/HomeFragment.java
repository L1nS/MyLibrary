package com.lins.modulehome;

import android.content.Intent;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lins.modulehome.test.ContactActivity;
import com.lins.modulehome.test.HttpActivity;
import com.lins.modulehome.test.ibeacon.IBeaconActivity;
import com.lins.modulehome.test.MaterialDesignActivity;
import com.lins.modulehome.test.NotificationActivity;
import com.lins.modulehome.test.PlayMusicActivity;
import com.lins.modulehome.test.PlayVideoActivity;
import com.lins.modulehome.test.TakePhotoActivity;
import com.lins.modulehome.test.bitmapEdit.BitmapEditActivity;
import com.lins.modulehome.test.service.ServiceActivity;
import com.lins.modulesystem.base.BaseConstant;
import com.lins.modulesystem.base.BaseFragment;

import butterknife.OnClick;

/**
 * Created by Admin on 2017/6/19.
 */

@Route(path = BaseConstant.FRAGMENT_URL_MODULE_HOME_HOME)
public class HomeFragment extends BaseFragment {

    @Override
    public int initLayoutResID() {
        return R.layout.activity_home;
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initLoad() {

    }

    @OnClick({R2.id.id_btn_product_list, R2.id.id_btn_contact, R2.id.id_btn_notification,
            R2.id.id_btn_photo, R2.id.id_btn_music, R2.id.id_btn_video, R2.id.id_btn_http,
            R2.id.id_btn_service, R2.id.id_btn_md, R2.id.id_btn_bmp_edit, R2.id.id_btn_ibeacon})
    public void onViewClicked(View view) {
        int id = view.getId();

        if (id == R.id.id_btn_product_list) {
            ARouter.getInstance()
                    .build(BaseConstant.ACTIVITY_URL_MODULE_PRODUCT_PRODUCT_DETAIL)
                    .withString("title", "这是一条传入的消息2")
                    .navigation();
//            Intent intent = new Intent(mContext, ProductDetailActivity.class);
//            intent.putExtra("title","这是一条传入的消息");
//            startActivity(intent);
        } else {
            Intent intent = new Intent();
            if (id == R.id.id_btn_contact) {
                intent.setClass(mContext, ContactActivity.class);
            } else if (id == R.id.id_btn_notification) {
                intent.setClass(mContext, NotificationActivity.class);
            } else if (id == R.id.id_btn_photo) {
                intent.setClass(mContext, TakePhotoActivity.class);
            } else if (id == R.id.id_btn_music) {
                intent.setClass(mContext, PlayMusicActivity.class);
            } else if (id == R.id.id_btn_video) {
                intent.setClass(mContext, PlayVideoActivity.class);
            } else if (id == R.id.id_btn_http) {
                intent.setClass(mContext, HttpActivity.class);
            } else if (id == R.id.id_btn_service) {
                intent.setClass(mContext, ServiceActivity.class);
            } else if (id == R.id.id_btn_md) {
                intent.setClass(mContext, MaterialDesignActivity.class);
            } else if (id == R.id.id_btn_bmp_edit) {
                intent.setClass(mContext, BitmapEditActivity.class);
            } else if (id == R.id.id_btn_ibeacon) {
                intent.setClass(mContext, IBeaconActivity.class);
            }
            startActivity(intent);
        }
    }
}
