package com.code90.daliweb.domain;

import javax.persistence.*;

/**
 * 题目实体类
 * @author Ray Lin
 * @create 2018-09-22 15:31
 **/
@Entity
@Table(name="sys_subject")
public class Subject extends BaseDomain {
    private static final long serialVersionUID = -7578915845966756524L;
    @Id
    //题目编号
    @Column
    private int id;
    //题目类型（0-单项选择题，1-多项选择提，2-判断题 ,3-填空题，4-问答题）
    @Column
    private int type;
    //题目内容
    @Column
    private String subjectName;
    //题目类型(0-初级，1-中级，2-高级）
    @Column
    private int subjectType;
    //选择A或正确
    @Column
    private String subA;
    //选择B或错误
    @Column
    private String subB;
    //选择C
    @Column
    private String subC;
    //选择D
    @Column
    private String subD;
    //答案
    @Column
    private String answer;
    //答案解析
    @Column
    private String answerAnalysis;
    //回答次数
    @Column
    private int num;
    //回答正确次数
    @Column
    private int correctNum;
    //学分
    @Column
    private int point;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubA() {
        return subA;
    }

    public void setSubA(String subA) {
        this.subA = subA;
    }

    public String getSubB() {
        return subB;
    }

    public void setSubB(String subB) {
        this.subB = subB;
    }

    public String getSubC() {
        return subC;
    }

    public void setSubC(String subC) {
        this.subC = subC;
    }

    public String getSubD() {
        return subD;
    }

    public void setSubD(String subD) {
        this.subD = subD;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswerAnalysis() {
        return answerAnalysis;
    }

    public void setAnswerAnalysis(String answerAnalysis) {
        this.answerAnalysis = answerAnalysis;
    }

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


    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(int subjectType) {
        this.subjectType = subjectType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
