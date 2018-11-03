package com.code90.daliweb.request.exchange;

import com.code90.daliweb.request.CommonRequest;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * 领取任务查询接口类
 * @author Ray Lin
 * @create 2018-10-03 20:33
 **/
public class TaskCollectionSearchReq extends CommonRequest implements Serializable {
    private static final long serialVersionUID = 983720068372179387L;
    private String taskId;
    private int status;
    private String createBy;
    private int page;
    private int pageSize;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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
