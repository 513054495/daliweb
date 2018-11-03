package com.code90.daliweb.request.learn;

import com.code90.daliweb.request.CommonRequest;

import java.io.Serializable;

/**
 * 作业查询请求类
 *
 * @author Ray Lin
 * @create 2018-10-12 1:06
 **/
public class WorkScheduleSearchReq extends CommonRequest implements Serializable {
    private static final long serialVersionUID = -7963803353796925573L;
    private int page;
    private int pageSize;
    private String createBy;
    private String workId;
    private int status;
    private int assess;

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

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAssess() {
        return assess;
    }

    public void setAssess(int assess) {
        this.assess = assess;
    }
}
