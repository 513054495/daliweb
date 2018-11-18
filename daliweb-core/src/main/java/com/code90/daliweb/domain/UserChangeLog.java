package com.code90.daliweb.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 用户类型修改记录
 * @author Ray Lin
 * @create 2018-11-14 22:53
 **/
@Entity
@Table(name="sys_user_change_log")
public class UserChangeLog extends BaseDomain {
    private static final long serialVersionUID = 765850258779975329L;
    @Id
    @GenericGenerator(name="UUIDGENERATE",strategy="uuid2")
    @GeneratedValue(generator="UUIDGENERATE")
    @Column(name="ID",length=36)
    private String id;
    //升级类型（0.VIP用户，1.1星团员，2.2星团员，3.3星团员，4.4星团员，5.5星团员，6.汉学堂用户
    //          7.三级学士，8.二级学士，9.一级学士，10.三级院士，11.二级院士，12.一级院士）
    @Column
    private int type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
