package com.code90.daliweb.request.learn;

import com.code90.daliweb.request.CommonRequest;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 考试进度请求类
 * @author Ray Lin
 * @create 2018-10-06 0:42
 **/
public class ExamScheduleSaveReq extends CommonRequest {
    private String examId;
    private int point;
    private String createBy;

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
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
