package com.code90.daliweb.request.exchange;

import com.code90.daliweb.request.CommonRequest;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * 任务查询请求类
 * @author Ray Lin
 * @create 2018-10-03 20:13
 **/
public class TaskSearchReq extends CommonRequest implements Serializable {
    private static final long serialVersionUID = 2555581451015787299L;
    private String title;
    private int status;
    private int page;
    private int pageSize;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
