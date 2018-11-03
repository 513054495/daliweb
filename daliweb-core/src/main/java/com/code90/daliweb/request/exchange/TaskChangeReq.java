package com.code90.daliweb.request.exchange;

import com.code90.daliweb.request.CommonRequest;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 任务修改请求类
 * @author Ray Lin
 * @create 2018-10-03 17:42
 **/
public class TaskChangeReq extends CommonRequest {
    private String id;
    private String title;
    private int maxNum;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastTime;
    private String description;
    private String files;

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

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }
}
