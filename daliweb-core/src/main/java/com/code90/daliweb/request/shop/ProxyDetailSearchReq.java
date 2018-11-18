package com.code90.daliweb.request.shop;

import com.code90.daliweb.request.CommonRequest;

import java.io.Serializable;

/**
 * 代理详情查询请求类
 * @author Ray Lin
 * @create 2018-11-17 20:45
 **/
public class ProxyDetailSearchReq extends CommonRequest implements Serializable {
    private static final long serialVersionUID = 3426924738415844128L;
    private int page;
    private int pageSize;
    private String startTime;
    private String endTime;
    private String orderNo;
    private String createBy;
    private int minMoney;
    private int maxMoney;
    private int type;
    private String orderPostalCode;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getOrderPostalCode() {
        return orderPostalCode;
    }

    public void setOrderPostalCode(String orderPostalCode) {
        this.orderPostalCode = orderPostalCode;
    }
}
