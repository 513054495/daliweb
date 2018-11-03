package com.code90.daliweb.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 学习主题实体类
 * @author Ray Lin
 * @create 2018-09-25 22:38
 **/
@Entity
@Table(name = "sys_learn_topic")
public class LearnTopic extends BaseDomain {
    private static final long serialVersionUID = -9132994801662570667L;
    @Id
    @GenericGenerator(name="UUIDGENERATE",strategy="uuid2")
    @GeneratedValue(generator="UUIDGENERATE")
    @Column(name="ID",length=36)
    private String id;
    //主题名称
    @Column
    private String topicName;
    //主题状态（0.停用，1.启用）
    @Column
    private int status;
    //主题等级(数字越大，排得越前)
    @Column
    private int level;
    //主题描述
    @Column
    private String description;
    //是否置顶(0.否，1.是)
    @Column
    private int isTop;
    //父级主题
    @Column
    private String parentId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIsTop() {
        return isTop;
    }

    public void setIsTop(int isTop) {
        this.isTop = isTop;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
