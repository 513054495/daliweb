package com.code90.daliweb.request.learn;

import com.code90.daliweb.request.CommonRequest;

import java.io.Serializable;

/**
 * 作业详情查询请求类
 * @author Ray Lin
 * @create 2018-10-12 1:14
 **/
public class WorkDetailSearchReq extends CommonRequest implements Serializable {
    private static final long serialVersionUID = -2255655851840874101L;
    private int page;
    private int pageSize;
    private String createBy;
    private int subjectId;
    private String workScheduleId;

    public String getWorkScheduleId() {
        return workScheduleId;
    }

    public void setWorkScheduleId(String workScheduleId) {
        this.workScheduleId = workScheduleId;
    }


    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
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

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }
}
