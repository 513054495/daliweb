package com.code90.daliweb.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 作业进度实体类
 * @author Ray Lin
 * @create 2018-10-11 1:35
 **/
@Entity
@Table(name = "sys_work_schedule")
public class WorkSchedule extends BaseDomain{
    private static final long serialVersionUID = -2212597621304794692L;
    @Id
    @GenericGenerator(name="UUIDGENERATE",strategy="uuid2")
    @GeneratedValue(generator="UUIDGENERATE")
    @Column(name="ID",length=36)
    private String id;
    //作业得分
    @Column
    private int point;
    //作业综合评价（0.优，1，良，2.一般，3.差）
    @Column
    private int assess;
    //作业批语
    @Column
    private String description;
    //作业进度状态(0.未批改，1.已批改)
    @Column
    private int status;
    //所属作业
    @Column
    private String workId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getAssess() {
        return assess;
    }

    public void setAssess(int assess) {
        this.assess = assess;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
