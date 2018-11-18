package com.code90.daliweb.request.shop;

import com.code90.daliweb.request.CommonRequest;

import java.io.Serializable;

/**
 * 退款商品统计查询请求类
 * @author Ray Lin
 * @create 2018-11-17 23:29
 **/
public class CommoditySummaryReq extends CommonRequest implements Serializable {
    private static final long serialVersionUID = 4082543770571097958L;
    private int page;
    private int pageSize;
    private String startTime;
    private String endTime;
    private String commodityId;
    private int minMoney;
    private int MaxMoney;
    private int status;

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

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public int getMinMoney() {
        return minMoney;
    }

    public void setMinMoney(int minMoney) {
        this.minMoney = minMoney;
    }

    public int getMaxMoney() {
        return MaxMoney;
    }

    public void setMaxMoney(int maxMoney) {
        MaxMoney = maxMoney;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
