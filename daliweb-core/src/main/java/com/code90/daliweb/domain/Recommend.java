package com.code90.daliweb.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 推荐实体表
 * @author Ray Lin
 * @create 2018-10-24 21:50
 **/
@Entity
@Table(name = "sys_recommend")
public class Recommend extends BaseDomain{
    private static final long serialVersionUID = -3578965286487518001L;
    @Id
    @GenericGenerator(name="UUIDGENERATE",strategy="uuid2")
    @GeneratedValue(generator="UUIDGENERATE")
    @Column(name="ID",length=36)
    private String id;
    //推荐帐号
    private String userCode;
    //推荐关系类型(0-用户关系，1.未绑定关系）
    private int type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
