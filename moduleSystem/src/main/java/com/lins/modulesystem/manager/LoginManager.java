package com.lins.modulesystem.manager;


import android.text.TextUtils;

import com.lins.modulesystem.bean.LoginBean;
import com.lins.modulesystem.bean.UserInfoBean;
import com.lins.modulesystem.utils.data.SharedPrefsUtil;

/**
 * Created by Admin on 2017/5/11.
 */

public class LoginManager {

    private final String SP_ACCESS_TOKEN = "access_token";
    private final String SP_TOKEN_TYPE = "token_type";
    private final String SP_UID = "uid";
    private final String SP_USER_PROVIDE_ID = "userProvideId";
    private final String SP_USER_PROVIDE_STATUS = "userProvideStatus";
    private final String SP_USER_PROVIDE_DESC = "userProvideDesc";
    private final String SP_USER_NAME = "username";
    private final String SP_NICK_NAME = "nickname";
    private final String SP_AVATAR = "avatar";
    private final String SP_SEX = "sex";
    private final String SP_IDENTITY_ROL_NAME = "identityRoleName";
    private final String SP_IDENTITY_ROLE_ID = "identityRoleId";
    private final String SP_RESOURCE_AUTHENTICATION_ID = "resourceAuthenticationRecordId";
    private final String SP_RESOURCE_AUTHENTICATION_STATUS = "resourceAuthenticationRecordStatus";

    private static volatile LoginManager sInstance;

    public static LoginManager getInstance() {
        if (sInstance == null) {
            synchronized (LoginManager.class) {
                if (sInstance == null) {
                    sInstance = new LoginManager();
                }
            }
        }
        return sInstance;
    }

    /**
     * 判断用户是否已经登录
     *
     * @return
     */
    public boolean isLogin() {
        String token = SharedPrefsUtil.getInstance().getString(SP_ACCESS_TOKEN, "");
        String type = SharedPrefsUtil.getInstance().getString(SP_TOKEN_TYPE, "");
        if (TextUtils.isEmpty(token) || TextUtils.isEmpty(type))
            return false;
        else
            return true;
    }

    public void initLoginData(LoginBean bean) {
        SharedPrefsUtil sharedPreUtil = SharedPrefsUtil.getInstance();
        sharedPreUtil.putString(SP_ACCESS_TOKEN, bean.getAccess_token());
        sharedPreUtil.putString(SP_TOKEN_TYPE, bean.getToken_type());
    }

    public void initUserInfo(UserInfoBean bean) {
        SharedPrefsUtil sharedPreUtil = SharedPrefsUtil.getInstance();
        sharedPreUtil.putString(SP_UID, bean.getId());
        sharedPreUtil.putString(SP_USER_PROVIDE_ID, bean.getUserProvideId());
        sharedPreUtil.putString(SP_USER_PROVIDE_STATUS, bean.getUserProvideStatus());
        sharedPreUtil.putString(SP_USER_PROVIDE_DESC, bean.getUserProvideDesc());
        sharedPreUtil.putString(SP_USER_NAME, bean.getUsername());
        sharedPreUtil.putString(SP_NICK_NAME, bean.getNickname());
        sharedPreUtil.putString(SP_AVATAR, bean.getHeadImgUrl());
        sharedPreUtil.putInt(SP_SEX, bean.getSex());
        sharedPreUtil.putString(SP_IDENTITY_ROL_NAME, bean.getIdentityRoleName());
        sharedPreUtil.putString(SP_IDENTITY_ROLE_ID, bean.getIdentityRoleId());
        sharedPreUtil.putString(SP_RESOURCE_AUTHENTICATION_ID, bean.getResourceAuthenticationRecordId());
        sharedPreUtil.putString(SP_RESOURCE_AUTHENTICATION_STATUS, bean.getResourceAuthenticationRecordStatus());
    }

    /**
     * 清除账号信息
     */
    public void clearUserInfo() {
        SharedPrefsUtil sharedPreUtil = SharedPrefsUtil.getInstance();
        sharedPreUtil.putString(SP_ACCESS_TOKEN, "0");
        sharedPreUtil.putString(SP_TOKEN_TYPE, "");
        sharedPreUtil.putString(SP_UID, "");
        sharedPreUtil.putString(SP_USER_PROVIDE_ID, "");
        sharedPreUtil.putString(SP_USER_PROVIDE_STATUS, "");
        sharedPreUtil.putString(SP_USER_PROVIDE_DESC, "");
        sharedPreUtil.putString(SP_USER_NAME, "");
        sharedPreUtil.putString(SP_NICK_NAME, "");
        sharedPreUtil.putString(SP_AVATAR, "");
        sharedPreUtil.putInt(SP_SEX, -1);
        sharedPreUtil.putString(SP_IDENTITY_ROL_NAME, "");
        sharedPreUtil.putString(SP_IDENTITY_ROLE_ID, "");
        sharedPreUtil.putString(SP_RESOURCE_AUTHENTICATION_ID, "");
        sharedPreUtil.putString(SP_RESOURCE_AUTHENTICATION_STATUS, "");
    }

    public String getAccessToken() {
        return SharedPrefsUtil.getInstance().getString(SP_ACCESS_TOKEN, "");
    }

    public String getTokenType() {
        return SharedPrefsUtil.getInstance().getString(SP_TOKEN_TYPE, "");
    }

    public String getUID() {
        return SharedPrefsUtil.getInstance().getString(SP_UID, "");
    }

    public String getUserProvideId() {
        return SharedPrefsUtil.getInstance().getString(SP_USER_PROVIDE_ID, "");
    }

    public String getUserProvideStatus() {
        return SharedPrefsUtil.getInstance().getString(SP_USER_PROVIDE_STATUS, "");
    }

    public String getUserProvideDesc() {
        return SharedPrefsUtil.getInstance().getString(SP_USER_PROVIDE_DESC, "");
    }
    public String getResourceAuthenticationId() {
        return SharedPrefsUtil.getInstance().getString(SP_RESOURCE_AUTHENTICATION_ID, "");
    }
    public String getResourceAuthenticationStatus() {
        return SharedPrefsUtil.getInstance().getString(SP_RESOURCE_AUTHENTICATION_STATUS, "");
    }

    public String getNickname() {
        return SharedPrefsUtil.getInstance().getString(SP_NICK_NAME, "");
    }

    public String getUsername() {
        return SharedPrefsUtil.getInstance().getString(SP_USER_NAME, "");
    }

    public String getAvatar() {
        return SharedPrefsUtil.getInstance().getString(SP_AVATAR, "");
    }

    public String getIdentityRoleName() {
        return SharedPrefsUtil.getInstance().getString(SP_IDENTITY_ROL_NAME, "");
    }

    public String getIdentityRoleId() {
        return SharedPrefsUtil.getInstance().getString(SP_IDENTITY_ROLE_ID, "");
    }

    public int getSex() {
        return SharedPrefsUtil.getInstance().getInt(SP_SEX, -1);
    }

}
