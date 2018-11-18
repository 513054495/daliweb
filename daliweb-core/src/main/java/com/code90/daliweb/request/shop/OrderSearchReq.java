package com.code90.daliweb.request.shop;

import com.code90.daliweb.request.CommonRequest;

import java.io.Serializable;

/**
 * 订单查询请求类
 * @author Ray Lin
 * @create 2018-09-15 15:56
 **/
public class OrderSearchReq extends CommonRequest implements Serializable {
    private static final long serialVersionUID = 1750374267644687316L;
    private String id;
    private String area;
    private int status;
    private int payType;
    private String startTime;
    private int minMoney;
    private int maxMoney;
    private String endTime;
    private String createBy;
    private int page;
    private int pageSize;
    private String orderPostalCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getMinMoney() {
        return minMoney;
    }

    public void setMinMoney(int minMoney) {
        this.minMoney = minMoney;
    }

    public int getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(int maxMoney) {
        this.maxMoney = maxMoney;
    }

    public String getOrderPostalCode() {
        return orderPostalCode;
    }

    public void setOrderPostalCode(String orderPostalCode) {
        this.orderPostalCode = orderPostalCode;
    }
}
