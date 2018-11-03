package com.code90.daliweb.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 作业详情实体类
 * @author Ray Lin
 * @create 2018-10-11 1:42
 **/
@Entity
@Table(name = "sys_work_detail")
public class WorkDetail extends BaseDomain {
    private static final long serialVersionUID = -4183771566908785582L;
    @Id
    @GenericGenerator(name="UUIDGENERATE",strategy="uuid2")
    @GeneratedValue(generator="UUIDGENERATE")
    @Column(name="ID",length=36)
    private String id;
    //所属作业进度编号
    @Column
    private String workScheduleId;
    //所属题目编号
    @Column
    private int subjectId;
    //回答答案
    @Column
    private String answer;
    //题目得分
    @Column
    private int point;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWorkScheduleId() {
        return workScheduleId;
    }

    public void setWorkScheduleId(String workScheduleId) {
        this.workScheduleId = workScheduleId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
