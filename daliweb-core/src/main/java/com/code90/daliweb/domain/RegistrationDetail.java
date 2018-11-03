package com.code90.daliweb.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 报名详情实体类
 * @author Ray Lin
 * @create 2018-10-19 0:26
 **/
@Entity
@Table(name = "sys_registration_detail")
public class RegistrationDetail extends BaseDomain {
    private static final long serialVersionUID = 3808054832241925738L;
    @Id
    @GenericGenerator(name="UUIDGENERATE",strategy="uuid2")
    @GeneratedValue(generator="UUIDGENERATE")
    @Column(name="ID",length=36)
    private String id;
    //姓名
    @Column
    private String name;
    //性别
    @Column
    private int sex;
    //年龄
    @Column
    private int age;
    //手机号码
    @Column
    private String phone;
    //报名编号
    @Column
    private String registrationId;
    //报名订单号
    @Column
    private String orderId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
