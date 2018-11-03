package com.code90.daliweb.request.learn;

import com.code90.daliweb.request.CommonRequest;

import java.io.Serializable;

/**
 * 作业查询请求类
 * @author Ray Lin
 * @create 2018-10-12 0:57
 **/
public class WorkSearchReq extends CommonRequest implements Serializable {
    private static final long serialVersionUID = -6126098545694940098L;
    private int page;
    private int pageSize;
    private String title;
    private String createBy;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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
}
