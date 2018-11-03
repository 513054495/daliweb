package com.code90.daliweb.request.learn;

import com.code90.daliweb.request.CommonRequest;

import java.io.Serializable;

/**
 * 考试查询请求类
 * @author Ray Lin
 * @create 2018-10-07 21:35
 **/
public class ExamSearchReq extends CommonRequest implements Serializable {
    private static final long serialVersionUID = 4884308800067506363L;
    private int page;
    private int pageSize;
    private int status;
    private int type;
    private String title;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
