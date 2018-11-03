package com.code90.daliweb.request.learn;

/**
 * 学习主题修改请求类
 * @author Ray Lin
 * @create 2018-09-28 23:52
 **/
public class LearnTopicChangeReq {
    private String id;
    private String topicName;
    private int status;
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
