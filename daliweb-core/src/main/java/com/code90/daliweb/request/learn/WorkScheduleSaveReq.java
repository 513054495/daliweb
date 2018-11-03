package com.code90.daliweb.request.learn;

import com.code90.daliweb.request.CommonRequest;


/**
 * 作业进度保存请求类
 * @author Ray Lin
 * @create 2018-10-11 22:26
 **/
public class WorkScheduleSaveReq extends CommonRequest {
    private int point;
    private int assess;
    private String description;
    private String workId;
    private String createBy;

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

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
}
