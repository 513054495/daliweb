package com.code90.daliweb.domain;


import java.util.Date;

/**
 * 考试展示实体类
 * @author Ray Lin
 * @create 2018-10-06 0:24
 **/
public class ExamVo extends BaseDomain {
    private static final long serialVersionUID = 5122614342383360963L;
    private String id;
    private String title;
    private Date startTime;
    private Date endTime;
    private int times;
    private String examPersons;
    private int type;
    private int status;
    private int isExam;

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

    public int getIsExam() {
        return isExam;
    }

    public void setIsExam(int isExam) {
        this.isExam = isExam;
    }
}
