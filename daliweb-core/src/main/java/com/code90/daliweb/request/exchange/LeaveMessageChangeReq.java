package com.code90.daliweb.request.exchange;

import com.code90.daliweb.request.CommonRequest;

/**
 * 留言修改请求类
 * @author Ray Lin
 * @create 2018-11-08 22:46
 **/
public class LeaveMessageChangeReq extends CommonRequest {
    private String id;
    private String content;

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
}
