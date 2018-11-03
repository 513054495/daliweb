package com.code90.daliweb.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 考试进度展示实体类
 * @author Ray Lin
 * @create 2018-10-06 0:34
 **/
public class ExamScheduleVo extends BaseDomain{
    private static final long serialVersionUID = 2599105365491269868L;
    private String id;
    private String examId;
    private int point;
    private String createName;

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

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }
}
