package com.code90.daliweb.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 作业实体类
 * @author Ray Lin
 * @create 2018-10-11 1:28
 **/
@Entity
@Table(name = "sys_work")
public class Work extends BaseDomain {
    private static final long serialVersionUID = -6561461434977844873L;
    @Id
    @GenericGenerator(name="UUIDGENERATE",strategy="uuid2")
    @GeneratedValue(generator="UUIDGENERATE")
    @Column(name="ID",length=36)
    private String id;
    //作业主题
    @Column
    private String title;
    //作业截止时间
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastTime;
    //作业参与人数
    @Column
    private String workPersons;
    //作业题目
    @Column
    private String subjects;
    //作业状态（0未发布、1.进行中，2.已截止）
    @Column
    private int status;
    //分享码
    @Column
    private String shareCode;

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

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public String getWorkPersons() {
        return workPersons;
    }

    public void setWorkPersons(String workPersons) {
        this.workPersons = workPersons;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getShareCode() {
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }
}
