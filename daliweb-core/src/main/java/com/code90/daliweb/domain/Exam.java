package com.code90.daliweb.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 考试实体类
 * @author Ray Lin
 * @create 2018-10-06 0:24
 **/
@Entity
@Table(name="sys_exam")
public class Exam extends BaseDomain {
    private static final long serialVersionUID = 6084215440221946882L;
    @Id
    @GenericGenerator(name="UUIDGENERATE",strategy="uuid2")
    @GeneratedValue(generator="UUIDGENERATE")
    @Column(name="ID",length=36)
    private String id;
    //考试主题
    @Column
    private String title;
    //考试开始时间
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    //考试结束时间
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
    //考试时间(min)
    @Column
    private int times;
    //考试人员
    @Column
    private String examPersons;
    //出题类型(0.随机出题，1.按难度出题)
    @Column
    private int type;
    //开始状态（0.未开始，1.考试中，2.考试结束，分页查询根据开始时间和结束时间自动修改）
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public String getExamPersons() {
        return examPersons;
    }

    public void setExamPersons(String examPersons) {
        this.examPersons = examPersons;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
