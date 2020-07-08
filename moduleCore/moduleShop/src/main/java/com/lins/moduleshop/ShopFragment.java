package com.lins.moduleshop;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lins.modulesystem.base.BaseConstant;
import com.lins.modulesystem.base.BaseFragment;

/**
 * Created by Admin on 2017/6/19.
 */

@Route(path = BaseConstant.FRAGMENT_URL_MODULE_SHOP_SHOP)
public class ShopFragment extends BaseFragment {

    @Override
    public int initLayoutResID() {
        return R.layout.activity_shop;
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

}
