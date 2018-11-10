package com.code90.daliweb.request.exchange;

import com.code90.daliweb.request.CommonRequest;

/**
 * 留言保存请求类
 * @author Ray Lin
 * @create 2018-11-08 22:23
 **/
public class LeaveMessageSaveReq extends CommonRequest {
    private String content;
    private String createBy;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
}
