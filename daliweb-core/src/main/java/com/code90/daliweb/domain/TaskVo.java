package com.code90.daliweb.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 任务实体展示类
 * @author Ray Lin
 * @create 2018-10-03 1:08
 **/
public class TaskVo extends BaseDomain{

    private static final long serialVersionUID = -5003193462319601633L;
    private String id;
    private String title;
    private Date publishTime;
    private int maxNum;
    private Date lastTime;
    private String description;
    private String files;
    private int status;
    private int isConllection;

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

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIsConllection() {
        return isConllection;
    }

    public void setIsConllection(int isConllection) {
        this.isConllection = isConllection;
    }
}
