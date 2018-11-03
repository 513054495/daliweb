package com.code90.daliweb.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 考试进度实体类
 * @author Ray Lin
 * @create 2018-10-06 0:34
 **/
@Entity
@Table(name="sys_exam_schedule")
public class ExamSchedule extends BaseDomain{
    private static final long serialVersionUID = -846079962132977412L;
    @Id
    @GenericGenerator(name="UUIDGENERATE",strategy="uuid2")
    @GeneratedValue(generator="UUIDGENERATE")
    @Column(name="ID",length=36)
    private String id;
    //考试编号
    @Column
    private String examId;
    //考试得分
    @Column
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
