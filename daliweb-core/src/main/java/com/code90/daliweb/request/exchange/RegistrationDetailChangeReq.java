package com.code90.daliweb.request.exchange;

import com.code90.daliweb.request.CommonRequest;

/**
 * 报名详情修改请求类
 *
 * @author Ray Lin
 * @create 2018-10-31 23:57
 **/
public class RegistrationDetailChangeReq extends CommonRequest {
    private String id;
    private String name;
    private int sex;
    private int age;
    private String phone;

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
}
