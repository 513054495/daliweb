package com.code90.daliweb.request.shop;

import com.code90.daliweb.request.CommonRequest;

import java.io.Serializable;

/**
 * 代理查询请求类
 * @author Ray Lin
 * @create 2018-10-24 1:01
 **/
public class ProxySearchReq extends CommonRequest implements Serializable {
    private static final long serialVersionUID = -915082790575620271L;
    private int page;
    private int pageSize;
    private int status;
    private int type;
    private String area;
    private String createBy;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
