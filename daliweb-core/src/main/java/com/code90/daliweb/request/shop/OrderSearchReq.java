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
    private String payNo;
    private int status;
    private String createBy;
    private int page;
    private int pageSize;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
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
}
