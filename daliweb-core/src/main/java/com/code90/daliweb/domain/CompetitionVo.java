package com.code90.daliweb.domain;

import java.util.Date;

/**
 * 赛事展示类
 * @author Ray Lin
 * @create 2018-10-17 22:18
 **/

public class CompetitionVo extends BaseDomain {
    private static final long serialVersionUID = 5065171533296488614L;
    private String id;
    private String name;
    private int type;
    private String typeName;
    private Date lastTime;
    private Date publishTime;
    private int status;
    private String description;
    private String files;
    private int isJoin;
    private int detailState;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
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

    public int getIsJoin() {
        return isJoin;
    }

    public void setIsJoin(int isJoin) {
        this.isJoin = isJoin;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getDetailState() {
        return detailState;
    }

    public void setDetailState(int detailState) {
        this.detailState = detailState;
    }
}
