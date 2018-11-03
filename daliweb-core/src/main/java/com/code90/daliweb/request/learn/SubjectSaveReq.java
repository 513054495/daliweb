package com.code90.daliweb.request.learn;

import com.code90.daliweb.request.CommonRequest;

/**
 * 题目保存请求类
 * @author Ray Lin
 * @create 2018-09-24 2:05
 **/
public class SubjectSaveReq extends CommonRequest {
    private int type;
    private String subjectName;
    private int subjectType;
    private String subA;
    private String subB;
    private String subC;
    private String subD;
    private String answer;
    private String answerAnalysis;
    private int num;
    private int correctNum;
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

    public int getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(int subjectType) {
        this.subjectType = subjectType;
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
}
