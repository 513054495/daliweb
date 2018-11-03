package com.code90.daliweb.request.exchange;

import com.code90.daliweb.request.CommonRequest;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 任务领取修改请求类
 *
 * @author Ray Lin
 * @create 2018-10-03 17:44
 **/
public class TaskCollectionChangeReq extends CommonRequest {
    private String id;
    private String taskId;
    private String collectionCondition;
    private String plan;
    private String pic;
    private String files;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date planTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getCollectionCondition() {
        return collectionCondition;
    }

    public void setCollectionCondition(String collectionCondition) {
        this.collectionCondition = collectionCondition;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public Date getPlanTime() {
        return planTime;
    }

    public void setPlanTime(Date planTime) {
        this.planTime = planTime;
    }
}
