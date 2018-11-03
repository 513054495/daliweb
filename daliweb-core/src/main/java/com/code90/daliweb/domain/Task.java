package com.code90.daliweb.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 任务实体类
 * @author Ray Lin
 * @create 2018-10-03 1:08
 **/
@Entity
@Table(name = "sys_task")
public class Task extends BaseDomain{
    private static final long serialVersionUID = 548673517327705287L;
    @Id
    @GenericGenerator(name="UUIDGENERATE",strategy="uuid2")
    @GeneratedValue(generator="UUIDGENERATE")
    @Column(name="ID",length=36)
    private String id;
    //任务标题
    @Column
    private String title;
    //任务发布时间
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date publishTime;
    //任务最大人数
    @Column
    private int maxNum;
    //任务最后时间
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastTime;
    //任务描述
    @Column
    private String description;
    //任务附件
    @Column
    private String files;
    //任务状态（0未发布，1进行中，2已关闭）
    @Column
    private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
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
}
