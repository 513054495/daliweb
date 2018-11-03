package com.code90.daliweb.request.learn;

import com.code90.daliweb.request.CommonRequest;

/**
 * 作业详情修改请求类
 * @author Ray Lin
 * @create 2018-10-11 23:50
 **/
public class WorkDetailChangeReq extends CommonRequest {
    private String workScheduleId;
    private int subjectId;
    private String answer;
    private int point;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getWorkScheduleId() {
        return workScheduleId;
    }

    public void setWorkScheduleId(String workScheduleId) {
        this.workScheduleId = workScheduleId;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
