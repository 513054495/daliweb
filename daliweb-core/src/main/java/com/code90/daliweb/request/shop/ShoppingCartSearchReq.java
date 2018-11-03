package com.code90.daliweb.request.shop;

import com.code90.daliweb.request.CommonRequest;

import java.io.Serializable;

/**
 * 购物车查询请求类
 * @author Ray Lin
 * @create 2018-09-16 0:30
 **/
public class ShoppingCartSearchReq extends CommonRequest implements Serializable {
    private static final long serialVersionUID = -1913304403086157450L;
    private String createBy;
    private int page;
    private int pageSize;

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
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
}
