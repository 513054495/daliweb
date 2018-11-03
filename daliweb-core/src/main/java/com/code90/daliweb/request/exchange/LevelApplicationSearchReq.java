package com.code90.daliweb.request.exchange;

import com.code90.daliweb.request.CommonRequest;

import java.io.Serializable;

/**
 * 考级申请查询类
 * @author Ray Lin
 * @create 2018-10-17 0:10
 **/
public class LevelApplicationSearchReq extends CommonRequest implements Serializable {
    private static final long serialVersionUID = -6302616643111704709L;
    private int page;
    private int pageSize;
    private String name;
    private int level;
    private String phone;
    private int status;
    private String createBy;

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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
}
