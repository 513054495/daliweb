package com.code90.daliweb.request.exchange;

import com.code90.daliweb.request.CommonRequest;

import javax.persistence.Column;

/**
 * 报名详情新增请求类
 * @author Ray Lin
 * @create 2018-10-30 23:40
 **/
public class RegistrationDetailSaveReq extends CommonRequest {
    private String name;
    private int sex;
    private int age;
    private String phone;
    private String registrationId;
    private String orderId;
    private String createBy;

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

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
