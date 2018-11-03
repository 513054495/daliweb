package com.code90.daliweb.request.exchange;

import com.code90.daliweb.request.CommonRequest;

import java.io.Serializable;

/**
 * 竞赛查询请求类
 * @author Ray Lin
 * @create 2018-10-18 21:57
 **/
public class CompetitionSearchReq extends CommonRequest implements Serializable {
    private static final long serialVersionUID = 5698346597746285298L;
    private int page;
    private int pageSize;
    private String name;
    private int type;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
