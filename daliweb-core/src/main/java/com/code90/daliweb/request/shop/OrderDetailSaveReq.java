package com.code90.daliweb.request.shop;

import com.code90.daliweb.request.CommonRequest;

/**
 * 订单详情生成请求类
 * @author Ray Lin
 * @create 2018-09-15 1:39
 **/
public class OrderDetailSaveReq extends CommonRequest {
    private String commodityId;
    private int orderNum;
    private double money;


    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
