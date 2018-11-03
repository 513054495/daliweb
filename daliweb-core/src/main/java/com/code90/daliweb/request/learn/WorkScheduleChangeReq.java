package com.code90.daliweb.request.learn;

import com.code90.daliweb.request.CommonRequest;

/**
 * 作业进度修改请求类
 * @author Ray Lin
 * @create 2018-10-11 23:46
 **/
public class WorkScheduleChangeReq extends CommonRequest {
    private String id;
    private int point;
    private int assess;
    private String description;
    private String workId;
    private int status;
    private String lastmodifiedBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getAssess() {
        return assess;
    }

    public void setAssess(int assess) {
        this.assess = assess;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLastmodifiedBy() {
        return lastmodifiedBy;
    }

    public void setLastmodifiedBy(String lastmodifiedBy) {
        this.lastmodifiedBy = lastmodifiedBy;
    }
}
