package com.code90.daliweb.request.exchange;

import com.code90.daliweb.request.CommonRequest;


/**
 * 提问请求类
 * @author Ray Lin
 * @create 2018-10-01 23:38
 **/
public class QuestionSaveReq extends CommonRequest {
    private String title;
    private String content;
    private String pic;
    private String createBy;

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

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
}
