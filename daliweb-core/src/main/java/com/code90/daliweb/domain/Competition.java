package com.code90.daliweb.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 赛事实体
 * @author Ray Lin
 * @create 2018-10-17 22:18
 **/
@Entity
@Table(name = "sys_competition")
public class Competition extends BaseDomain {
    private static final long serialVersionUID = -7584219530459915852L;
    @Id
    @GenericGenerator(name="UUIDGENERATE",strategy="uuid2")
    @GeneratedValue(generator="UUIDGENERATE")
    @Column(name="ID",length=36)
    private String id;
    //赛事名称
    @Column
    private String name;
    //赛事类型
    @Column
    private int type;
    //截至时间
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastTime;
    //发布时间
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date publishTime;
    //状态(0-待发布，1-进行中，2-已结束)
    @Column
    private int status;
    //赛事描述
    @Column
    @Lob
    private String description;
    //附件
    @Column
    private String files;

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
}
