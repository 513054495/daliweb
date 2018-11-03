package com.code90.daliweb.request.user;

import com.code90.daliweb.request.CommonRequest;

/**
 * 登录请求类
 * @author Ray Lin
 * @create 2018-09-09 19:54
 **/
public class LoginReq extends CommonRequest {
    private String userCode;
    private String dlwPsw;
    private int isFront;

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

    public int getIsFront() {
        return isFront;
    }

    public void setIsFront(int isFront) {
        this.isFront = isFront;
    }
}
