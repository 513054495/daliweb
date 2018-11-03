package com.code90.daliweb.request.learn;

import com.code90.daliweb.request.CommonRequest;

/**
 * 考试进度修改类
 * @author Ray Lin
 * @create 2018-10-07 16:43
 **/
public class ExamScheduleChangeReq extends CommonRequest {
    private String id;
    private String examId;
    private int point;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
