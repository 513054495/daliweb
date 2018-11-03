package com.code90.daliweb.request.learn;

import com.code90.daliweb.request.CommonRequest;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 考试保存请求类
 * @author Ray Lin
 * @create 2018-10-06 0:42
 **/
public class ExamSaveReq extends CommonRequest {
    private String title;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    private int times;
    private String examPersons;
    private int type;
    private String createBy;

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

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
}
