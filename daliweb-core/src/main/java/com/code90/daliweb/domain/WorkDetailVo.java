package com.code90.daliweb.domain;

import java.util.Date;

/**
 * 作业详情展示类
 * @author Ray Lin
 * @create 2018-10-11 1:42
 **/

public class WorkDetailVo extends BaseDomain {

    private static final long serialVersionUID = 741555005577464548L;
    private String id;
    private String workScheduleId;
    private int subjectId;
    private String answer;
    private int point;
    private String subjectName;
    private String correctAnswer;
    private int subjectType;
    private int subjectPoint;
    private String answerAnalysis;
    private Date lastTime;
    private String title;
    private String subA;
    private String subB;
    private String subC;
    private String subD;

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

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public int getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(int subjectType) {
        this.subjectType = subjectType;
    }

    public int getSubjectPoint() {
        return subjectPoint;
    }

    public void setSubjectPoint(int subjectPoint) {
        this.subjectPoint = subjectPoint;
    }

    public String getAnswerAnalysis() {
        return answerAnalysis;
    }

    public void setAnswerAnalysis(String answerAnalysis) {
        this.answerAnalysis = answerAnalysis;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}
