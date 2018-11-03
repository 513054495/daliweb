package com.code90.daliweb.request.shop;


import com.code90.daliweb.request.CommonRequest;

import java.io.Serializable;

/**
 * 商品查询请求类
 * @author Ray Lin
 * @create 2018-09-09 20:42
 **/
public class CommoditySearchReq extends CommonRequest implements Serializable {
    private static final long serialVersionUID = 764415337061857509L;
    private String name;
    private int type;
    private int isVip;
    private int status;
    private int page;
    private int pageSize;
    private int deleted;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIsVip() {
        return isVip;
    }

    public void setIsVip(int isVip) {
        this.isVip = isVip;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }
}
