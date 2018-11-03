package com.code90.daliweb.request.learn;

import com.code90.daliweb.request.CommonRequest;

import javax.persistence.Column;

/**
 * 学习主题新增请求类
 *
 * @author Ray Lin
 * @create 2018-09-25 23:37
 **/
public class LearnTopicSaveReq extends CommonRequest {
    private String topicName;
    private int status;
    private int level;
    private String description;
    private String parentId;

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
