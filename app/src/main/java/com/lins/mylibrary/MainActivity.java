package com.lins.mylibrary;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hjq.toast.ToastUtils;
import com.lins.modulesystem.base.BaseActivity;
import com.lins.modulesystem.base.BaseConstant;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.id_ly_content)
    FrameLayout idLyContent;
    @BindView(R.id.id_img_home)
    ImageView idImgHome;
    @BindView(R.id.id_tv_home)
    TextView idTvHome;
    @BindView(R.id.id_img_shop)
    ImageView idImgShop;
    @BindView(R.id.id_tv_shop)
    TextView idTvShop;
    @BindView(R.id.id_img_cart)
    ImageView idImgCart;
    @BindView(R.id.id_tv_cart)
    TextView idTvCart;
    @BindView(R.id.id_img_user)
    ImageView idImgUser;
    @BindView(R.id.id_tv_user)
    TextView idTvUser;

    private Fragment homeFragment;
    private Fragment shopFragment;
    private Fragment cartFragment;
    private Fragment userFragment;
    private FragmentManager fm;

    private Context context;
    private long mExitTime;

    @Override
    public int initLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        fm = getSupportFragmentManager();
        context = this;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initLoad() {
        setTabSelection(0);
    }
    @OnClick({R.id.id_ly_home, R.id.id_ly_shop, R.id.id_ly_cart, R.id.id_ly_user})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_ly_home:
                setTabSelection(0);
                break;
            case R.id.id_ly_shop:
                setTabSelection(1);
                break;
            case R.id.id_ly_cart:
                setTabSelection(2);
                break;
            case R.id.id_ly_user:
                setTabSelection(3);
                break;
        }
    }

    /**
     * 根据传入的index参数来设置选中的tab页。
     */
    private void setTabSelection(int index) {
        resetBtn(index);
        // 开启一个Fragment事务
        FragmentTransaction ft = fm.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragment(ft);
        switch (index) {
            case 0:
                if (homeFragment == null) {
//                    homeFragment = new HomeFragment();
                    homeFragment = (Fragment) ARouter.getInstance().build(BaseConstant.FRAGMENT_URL_MODULE_HOME_HOME).navigation();
                    ft.add(R.id.id_ly_content, homeFragment);
                } else {
                    ft.show(homeFragment);
                }
                break;
            case 1:
                if (shopFragment == null) {
                    shopFragment = (Fragment) ARouter.getInstance().build(BaseConstant.FRAGMENT_URL_MODULE_SHOP_SHOP).navigation();
                    ft.add(R.id.id_ly_content, shopFragment);
                } else {
                    ft.show(shopFragment);
                }
                break;
            case 2:
                if (cartFragment == null) {
                    cartFragment =  (Fragment) ARouter.getInstance().build(BaseConstant.FRAGMENT_URL_MODULE_CART_CART).navigation();
                    ft.add(R.id.id_ly_content, cartFragment);
                } else {
                    ft.show(cartFragment);
                }
                break;
            case 3:
                if (userFragment == null) {
                    userFragment =(Fragment) ARouter.getInstance().build(BaseConstant.FRAGMENT_URL_MODULE_USER_USER)
                            .withString("str","用户界面2").navigation();
                    ft.add(R.id.id_ly_content, userFragment);
                } else {
                    ft.show(userFragment);
                }
                break;
        }
        ft.commit();
    }

    /**
     * 清除掉所有的选中状态。
     */
    private void resetBtn(int index) {
        idImgHome.setImageDrawable(ContextCompat.getDrawable(context, android.R.color.holo_blue_light));
        idImgShop.setImageDrawable(ContextCompat.getDrawable(context, android.R.color.holo_blue_light));
        idImgCart.setImageDrawable(ContextCompat.getDrawable(context, android.R.color.holo_blue_light));
        idImgUser.setImageDrawable(ContextCompat.getDrawable(context, android.R.color.holo_blue_light));
        idTvHome.setTextColor(ContextCompat.getColor(context, R.color.colorGray));
        idTvShop.setTextColor(ContextCompat.getColor(context, R.color.colorGray));
        idTvCart.setTextColor(ContextCompat.getColor(context, R.color.colorGray));
        idTvUser.setTextColor(ContextCompat.getColor(context, R.color.colorGray));
        switch (index) {
            case 0:
                idImgHome.setImageDrawable(ContextCompat.getDrawable(context, android.R.color.holo_blue_dark));
                idTvHome.setTextColor(ContextCompat.getColor(context, R.color.colorBlack));
                break;
            case 1:
                idImgShop.setImageDrawable(ContextCompat.getDrawable(context, android.R.color.holo_blue_dark));
                idTvShop.setTextColor(ContextCompat.getColor(context, R.color.colorBlack));
                break;
            case 2:
                idImgCart.setImageDrawable(ContextCompat.getDrawable(context, android.R.color.holo_blue_dark));
                idTvCart.setTextColor(ContextCompat.getColor(context, R.color.colorBlack));
                break;
            case 3:
                idImgUser.setImageDrawable(ContextCompat.getDrawable(context, android.R.color.holo_blue_dark));
                idTvUser.setTextColor(ContextCompat.getColor(context, R.color.colorBlack));
                break;
        }
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragment(FragmentTransaction transaction) {
        if (homeFragment != null)
            transaction.hide(homeFragment);
        if (shopFragment != null)
            transaction.hide(shopFragment);
        if (cartFragment != null)
            transaction.hide(cartFragment);
        if (userFragment != null)
        transaction.hide(userFragment);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                ToastUtils.show("再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
                MyApplication.getInstance().getActivityManager().finishAllActivity();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
    }
}