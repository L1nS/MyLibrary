package com.lins.moduleuser;

import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lins.modulesystem.base.BaseConstant;
import com.lins.modulesystem.base.BaseFragment;
import com.lins.modulesystem.utils.ToastUtil;

import androidx.annotation.Nullable;
import butterknife.BindView;


/**
 * Created by Admin on 2017/6/19.
 */

@Route(path = BaseConstant.FRAGMENT_URL_MODULE_USER_USER)
public class UserFragment extends BaseFragment {

    @BindView(R2.id.id_tv)
    TextView idTv;

//    public static UserFragment newInstance(String str) {
//
//        Bundle args = new Bundle();
//        args.putString("str",str);
//        UserFragment fragment = new UserFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }

    public String getString(){
        return getArguments().getString("str");
    }
    @Override
    public int initLayoutResID() {
        return R.layout.activity_user;
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {

    }

    @Override
    public void initLoad() {
        idTv.setText(getString());
    }
}
