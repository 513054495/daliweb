package com.code90.daliweb.domain;
import javax.persistence.Column;
import java.util.Date;

/**
 * 作业展示类
 * @author Ray Lin
 * @create 2018-10-11 1:28
 **/

public class WorkVo extends BaseDomain {
    private static final long serialVersionUID = 5332142389579108106L;
    private String id;
    private String title;
    private Date lastTime;
    private String workPersons;
    private String subjects;
    private int status;
    private int isWork;
    private int point;
    private int assess;
    private String workScheduleId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public String getWorkPersons() {
        return workPersons;
    }

    public void setWorkPersons(String workPersons) {
        this.workPersons = workPersons;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIsWork() {
        return isWork;
    }

    public void setIsWork(int isWork) {
        this.isWork = isWork;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getAssess() {
        return assess;
    }

    public void setAssess(int assess) {
        this.assess = assess;
    }

    public String getWorkScheduleId() {
        return workScheduleId;
    }

    public void setWorkScheduleId(String workScheduleId) {
        this.workScheduleId = workScheduleId;
    }
}
