package com.code90.daliweb.request.user;


import com.code90.daliweb.request.CommonRequest;

/**
 * 用户注册请求类
 * @author Ray Lin
 * @create 2018-09-11 1:22
 **/
public class UserRegisterReq extends CommonRequest {
    private String userCode;
    private String dlwPsw;
    private String phone;
    private String shareCode;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getDlwPsw() {
        return dlwPsw;
    }

    public void setDlwPsw(String dlwPsw) {
        this.dlwPsw = dlwPsw;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShareCode() {
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }
}
