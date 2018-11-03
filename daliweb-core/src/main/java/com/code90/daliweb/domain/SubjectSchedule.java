package com.code90.daliweb.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 答题进度实体类
 * @author Ray Lin
 * @create 2018-09-22 15:47
 **/
@Entity
@Table(name="sys_subject_schedule")
public class SubjectSchedule extends BaseDomain {
    private static final long serialVersionUID = 2141228168106573097L;
    @Id
    @GenericGenerator(name="UUIDGENERATE",strategy="uuid2")
    @GeneratedValue(generator="UUIDGENERATE")
    @Column(name="ID",length=36)
    //编号
    private String id;
    //进度
    private int subjectId;
    //回答次数
    private int num;
    //回答正确次数
    private int correctNum;


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getCorrectNum() {
        return correctNum;
    }

    public void setCorrectNum(int correctNum) {
        this.correctNum = correctNum;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }
}
