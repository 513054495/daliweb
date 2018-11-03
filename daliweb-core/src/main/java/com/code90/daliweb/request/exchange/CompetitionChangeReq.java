package com.code90.daliweb.request.exchange;

import com.code90.daliweb.request.CommonRequest;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 竞赛修改请求类
 * @author Ray Lin
 * @create 2018-10-18 21:24
 **/
public class CompetitionChangeReq extends CommonRequest {
    private String id;
    private String name;
    private int type;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }
}
