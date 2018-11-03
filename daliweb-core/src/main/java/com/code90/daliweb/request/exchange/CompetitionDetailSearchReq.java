package com.code90.daliweb.request.exchange;

import com.code90.daliweb.request.CommonRequest;

import java.io.Serializable;

/**
 * 竞赛详情查询请求类
 *
 * @author Ray Lin
 * @create 2018-10-18 22:09
 **/
public class CompetitionDetailSearchReq extends CommonRequest implements Serializable {
    private static final long serialVersionUID = 6266327784277361532L;
    private int page;
    private int pageSize;
    private String name;
    private int status;
    private String competitionId;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(String competitionId) {
        this.competitionId = competitionId;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
}
