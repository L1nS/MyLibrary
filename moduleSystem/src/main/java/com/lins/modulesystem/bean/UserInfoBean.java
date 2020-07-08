package com.lins.modulesystem.bean;

public class UserInfoBean {

    private Object permissions;
    private String id;
    private long createTime;
    private long updateTime;
    private String username;
    private String nickname;
    private String headImgUrl;
    private String mobile;
    private int sex;
    private boolean enabled;
    private String type;
    private String openId;
    private boolean isDel;
    private Object roleId;
    private Object oldPassword;
    private Object newPassword;
    private Object roleIds;
    private Object code;
    private String identityRoleId;
    private String identityRoleName;
    private String userProvideId;
    private String userProvideStatus = "";//1:等待审核，2：通过，3被拒绝。
    private String userProvideDesc;
    private String resourceAuthenticationRecordId;
    private String resourceAuthenticationRecordStatus;
    private int totalIntegral;
    private int totalRevenue;
    private int focusMeCount;
    private int focusOnCount;

    public Object getPermissions() {
        return permissions;
    }

    public void setPermissions(Object permissions) {
        this.permissions = permissions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public boolean isIsDel() {
        return isDel;
    }

    public void setIsDel(boolean isDel) {
        this.isDel = isDel;
    }

    public Object getRoleId() {
        return roleId;
    }

    public void setRoleId(Object roleId) {
        this.roleId = roleId;
    }

    public Object getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(Object oldPassword) {
        this.oldPassword = oldPassword;
    }

    public Object getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(Object newPassword) {
        this.newPassword = newPassword;
    }

    public Object getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Object roleIds) {
        this.roleIds = roleIds;
    }

    public Object getCode() {
        return code;
    }

    public void setCode(Object code) {
        this.code = code;
    }

    public String getIdentityRoleId() {
        return identityRoleId;
    }

    public void setIdentityRoleId(String identityRoleId) {
        this.identityRoleId = identityRoleId;
    }

    public String getIdentityRoleName() {
        return identityRoleName;
    }

    public void setIdentityRoleName(String identityRoleName) {
        this.identityRoleName = identityRoleName;
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

    public int getTotalIntegral() {
        return totalIntegral;
    }

    public void setTotalIntegral(int totalIntegral) {
        this.totalIntegral = totalIntegral;
    }

    public int getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(int totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public int getFocusMeCount() {
        return focusMeCount;
    }

    public void setFocusMeCount(int focusMeCount) {
        this.focusMeCount = focusMeCount;
    }

    public int getFocusOnCount() {
        return focusOnCount;
    }

    public void setFocusOnCount(int focusOnCount) {
        this.focusOnCount = focusOnCount;
    }
}
