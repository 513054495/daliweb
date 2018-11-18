package com.code90.daliweb.request.user;

import com.code90.daliweb.request.CommonRequest;

import java.io.Serializable;

/**
 * 用户修改记录查询请求类
 * @author Ray Lin
 * @create 2018-11-18 0:36
 **/
public class UserChangeLogReq extends CommonRequest implements Serializable {
    private static final long serialVersionUID = -5662314551682726403L;
    private int page;
    private int pageSize;
    private String startTime;
    private String endTime;
    private String createBy;
    private int type;

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

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
