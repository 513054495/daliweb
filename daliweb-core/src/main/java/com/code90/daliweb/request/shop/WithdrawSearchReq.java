package com.code90.daliweb.request.shop;

import com.code90.daliweb.request.CommonRequest;

import java.io.Serializable;

/**
 * 提现查询请求类
 * @author Ray Lin
 * @create 2018-10-28 19:46
 **/
public class WithdrawSearchReq extends CommonRequest implements Serializable {
    private static final long serialVersionUID = -1111307573662150639L;
    private int page;
    private int pageSize;
    private int status;
    private String createBy;

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
