package com.code90.daliweb.request.exchange;

import com.code90.daliweb.request.CommonRequest;

/**
 * 问题修改请求类
 * @author Ray Lin
 * @create 2018-10-01 23:57
 **/
public class QuestionChangeReq extends CommonRequest {
    private String id;
    private String title;
    private String content;
    private String pic;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
