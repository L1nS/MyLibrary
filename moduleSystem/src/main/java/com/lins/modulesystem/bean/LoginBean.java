package com.lins.modulesystem.bean;

public class LoginBean {
    private String access_token;
    private String token_type;
    private String uid;
    //申请为知识服务者的信息
    private String userProvideId;
    private String userProvideStatus;
    private String userProvideDesc;
    //申请为资源方的信息
    private String resourceAuthenticationRecordId;
    private String resourceAuthenticationRecordStatus;
    //角色id
    private String identityRoleId;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token_type + access_token;
    }

    public String getUserProvideId() {
        return userProvideId;
    }

    public void setUserProvideId(String userProvideId) {
        this.userProvideId = userProvideId;
    }

    public String getUserProvideStatus() {
        return userProvideStatus;
    }

    public void setUserProvideStatus(String userProvideStatus) {
        this.userProvideStatus = userProvideStatus;
    }

    public String getUserProvideDesc() {
        return userProvideDesc;
    }

    public void setUserProvideDesc(String userProvideDesc) {
        this.userProvideDesc = userProvideDesc;
    }

    public String getIdentityRoleId() {
        return identityRoleId;
    }

    public void setIdentityRoleId(String identityRoleId) {
        this.identityRoleId = identityRoleId;
    }


    public String getResourceAuthenticationRecordId() {
        return resourceAuthenticationRecordId;
    }

    public void setResourceAuthenticationRecordId(String resourceAuthenticationRecordId) {
        this.resourceAuthenticationRecordId = resourceAuthenticationRecordId;
    }

    public String getResourceAuthenticationRecordStatus() {
        return resourceAuthenticationRecordStatus;
    }

    public void setResourceAuthenticationRecordStatus(String resourceAuthenticationRecordStatus) {
        this.resourceAuthenticationRecordStatus = resourceAuthenticationRecordStatus;
    }
}
