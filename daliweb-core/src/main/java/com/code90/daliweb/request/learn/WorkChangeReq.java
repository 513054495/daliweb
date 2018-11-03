package com.code90.daliweb.request.learn;

import com.code90.daliweb.request.CommonRequest;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 作业修改请求类
 * @author Ray Lin
 * @create 2018-10-11 23:39
 **/
public class WorkChangeReq extends CommonRequest {
    private String id;
    private String title;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastTime;
    private String workPersons;
    private String subjects;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
}
