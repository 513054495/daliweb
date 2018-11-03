package com.code90.daliweb.domain;

import java.util.Date;

/**
 * 报名展示类
 * @author Ray Lin
 * @create 2018-10-19 0:21
 **/

public class RegistrationVo extends BaseDomain {
    private static final long serialVersionUID = 3694477698269112896L;
    private String id;
    private String name;
    private int maxNum;
    private Date lastTime;
    private String area;
    private String postalCode;
    private String address;
    private double money;
    private String description;
    private String files;
    private int status;
    private int isRegistration;

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

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
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

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIsRegistration() {
        return isRegistration;
    }

    public void setIsRegistration(int isRegistration) {
        this.isRegistration = isRegistration;
    }
}
