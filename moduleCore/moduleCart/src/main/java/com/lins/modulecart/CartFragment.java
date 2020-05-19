package com.lins.modulecart;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lins.modulesystem.base.BaseConstant;
import com.lins.modulesystem.base.BaseFragment;

import androidx.annotation.Nullable;

/**
 * Created by Admin on 2017/6/19.
 */

@Route(path = BaseConstant.FRAGMENT_URL_MODULE_CART_CART)
public class CartFragment extends BaseFragment {
    @Override
    public int initLayoutResID() {
        return R.layout.activity_cart;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initLoad() {

    }

}
