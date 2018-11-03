package com.code90.daliweb.request.exchange;

import com.code90.daliweb.request.CommonRequest;

import java.io.Serializable;

/**
 * 报名详情查询请求类
 * @author Ray Lin
 * @create 2018-11-01 0:30
 **/
public class RegistrationDetailSearchReq extends CommonRequest implements Serializable {
    private static final long serialVersionUID = -1712542174241527923L;
    private int page;
    private int pageSize;
    private String name;
    private String registrationId;
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


    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
}
