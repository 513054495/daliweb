package com.code90.daliweb.request.exchange;

import com.code90.daliweb.request.CommonRequest;


/**
 * 公告新增请求类
 * @author Ray Lin
 * @create 2018-11-10 21:10
 **/
public class AnnouncementSaveReq extends CommonRequest {
    private String title;
    private int type;
    private int level;
    private String content;
    private String files;
    private String createBy;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
}
