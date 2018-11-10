package com.code90.daliweb.request.exchange;

import com.code90.daliweb.request.CommonRequest;

import java.io.Serializable;

/**
 * 留言查询请求类
 * @author Ray Lin
 * @create 2018-11-08 23:33
 **/
public class LeaveMessageSearchReq extends CommonRequest implements Serializable {
    private static final long serialVersionUID = -3800779315039524026L;
    private int page;
    private int pageSize;
    private int status;
    private String createBy;
    private String startTime;
    private String endTime;

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

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
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
}
