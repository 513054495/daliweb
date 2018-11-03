package com.code90.daliweb.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 任务领取实体类
 * @author Ray Lin
 * @create 2018-10-03 1:32
 **/
@Entity
@Table(name = "sys_task_collection")
public class TaskCollection extends BaseDomain {
    private static final long serialVersionUID = -674108594690845638L;
    @Id
    @GenericGenerator(name="UUIDGENERATE",strategy="uuid2")
    @GeneratedValue(generator="UUIDGENERATE")
    @Column(name="ID",length=36)
    private String id;
    //任务编号
    @Column
    private String taskId;
    //任务领取条件
    @Column
    private String collectionCondition;
    //任务计划
    @Column
    private String plan;
    //任务领取图片
    @Column
    private String pic;
    //任务领取附件
    @Column
    private String files;
    //计划完成时间
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date planTime;
    //任务领取状态（0-待审核，1-通过，和2-不通过）
    @Column
    private int status;

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


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getCollectionCondition() {
        return collectionCondition;
    }

    public void setCollectionCondition(String collectionCondition) {
        this.collectionCondition = collectionCondition;
    }
}
