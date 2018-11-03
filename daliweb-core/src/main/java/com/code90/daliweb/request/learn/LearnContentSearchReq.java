package com.code90.daliweb.request.learn;

import com.code90.daliweb.request.CommonRequest;

import java.io.Serializable;

/**
 * 学习内容查询请求类
 *
 * @author Ray Lin
 * @create 2018-10-01 17:44
 **/
public class LearnContentSearchReq extends CommonRequest implements Serializable {
    private static final long serialVersionUID = 5278819907917429439L;
    private String title;
    private String author;
    private String topicId;
    private int status;
    private int page;
    private int pageSize;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
