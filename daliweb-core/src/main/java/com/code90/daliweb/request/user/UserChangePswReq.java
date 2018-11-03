package com.code90.daliweb.request.user;

import com.code90.daliweb.request.CommonRequest;

/**
 * 修改密码请求类
 * @author Ray Lin
 * @create 2018-09-10 0:34
 **/
public class UserChangePswReq extends CommonRequest {
    private String id;
    private String oldDlwPsw;
    private String newDlwPsw;

    public String getOldDlwPsw() {
        return oldDlwPsw;
    }

    public void setOldDlwPsw(String oldDlwPsw) {
        this.oldDlwPsw = oldDlwPsw;
    }

    public String getNewDlwPsw() {
        return newDlwPsw;
    }

    public void setNewDlwPsw(String newDlwPsw) {
        this.newDlwPsw = newDlwPsw;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
