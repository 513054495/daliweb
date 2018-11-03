package com.code90.daliweb.request.shop;

import com.code90.daliweb.request.CommonRequest;

/**
 * 提取新增请求类
 * @author Ray Lin
 * @create 2018-10-28 17:44
 **/
public class WithdrawSaveReq extends CommonRequest {
    private double money;
    private String createBy;

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
}
