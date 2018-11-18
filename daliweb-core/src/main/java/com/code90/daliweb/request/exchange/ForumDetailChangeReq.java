package com.code90.daliweb.request.exchange;

import com.code90.daliweb.request.CommonRequest;

/**
 * 论坛详情修改请求类
 * @author Ray Lin
 * @create 2018-11-12 1:33
 **/
public class ForumDetailChangeReq extends CommonRequest {
    private String id;
    private String content;
    private String forumId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getForumId() {
        return forumId;
    }

    public void setForumId(String forumId) {
        this.forumId = forumId;
    }
}
