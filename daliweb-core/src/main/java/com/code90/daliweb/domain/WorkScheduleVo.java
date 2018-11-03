package com.code90.daliweb.domain;

/**
 * 作业进度展示类
 * @author Ray Lin
 * @create 2018-10-11 1:35
 **/
public class WorkScheduleVo extends BaseDomain{
    private static final long serialVersionUID = 3356535285508360864L;
    private String id;
    private int point;
    private int assess;
    private String description;
    private int status;
    private String workId;
    private String shareCode;
    private int workStatus;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShareCode() {
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }


    public int getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(int workStatus) {
        this.workStatus = workStatus;
    }
}
