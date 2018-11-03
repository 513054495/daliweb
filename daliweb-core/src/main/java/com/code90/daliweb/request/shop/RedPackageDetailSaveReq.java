package com.code90.daliweb.request.shop;

import com.code90.daliweb.request.CommonRequest;

/**
 * 红包记录新增请求类
 * @author Ray Lin
 * @create 2018-10-23 0:25
 **/
public class RedPackageDetailSaveReq extends CommonRequest {
    private int type;
    private double money;
    private String answerUserCode;
    private String orderNo;
    private String shareCode;
    private String createBy;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getAnswerUserCode() {
        return answerUserCode;
    }

    public void setAnswerUserCode(String answerUserCode) {
        this.answerUserCode = answerUserCode;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getShareCode() {
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }
}
