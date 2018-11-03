package com.code90.daliweb.request.learn;

import com.code90.daliweb.request.CommonRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

/**
 * 作业保存请求类
 * @author Ray Lin
 * @create 2018-10-11 21:52
 **/
public class WorkSaveReq extends CommonRequest {
    private String title;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastTime;
    private String workPersons;
    private String subjects;
    private String createBy;
    private String shareCode;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public String getWorkPersons() {
        return workPersons;
    }

    public void setWorkPersons(String workPersons) {
        this.workPersons = workPersons;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getShareCode() {
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }
}
