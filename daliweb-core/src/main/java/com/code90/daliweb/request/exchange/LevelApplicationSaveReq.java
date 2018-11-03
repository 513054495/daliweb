package com.code90.daliweb.request.exchange;

import com.code90.daliweb.request.CommonRequest;

/**
 * 考级申请请求类
 * @author Ray Lin
 * @create 2018-10-16 23:37
 **/
public class LevelApplicationSaveReq extends CommonRequest {
    private String name;
    private String country;
    private int sex;
    private int age;
    private int level;
    private String phone;
    private String email;
    private String area;
    private String address;
    private String worksDesc;
    private String planning;
    private String files;
    private int status;
    private String createBy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWorksDesc() {
        return worksDesc;
    }

    public void setWorksDesc(String worksDesc) {
        this.worksDesc = worksDesc;
    }

    public String getPlanning() {
        return planning;
    }

    public void setPlanning(String planning) {
        this.planning = planning;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
}
