package com.code90.daliweb.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 考级申请
 * @author Ray Lin
 * @create 2018-10-15 23:25
 **/
@Entity
@Table(name = "sys_level_application")
public class LevelApplication extends BaseDomain {
    private static final long serialVersionUID = -5536385020562491806L;
    @Id
    @GenericGenerator(name="UUIDGENERATE",strategy="uuid2")
    @GeneratedValue(generator="UUIDGENERATE")
    @Column(name="ID",length=36)
    private String id;
    //申请人姓名
    @Column
    private String name;
    //国籍
    @Column
    private String country;
    //性别(0 - 女，1-男)
    @Column
    private int sex;
    //年龄
    @Column
    private int age;
    //申请等级
    @Column
    private int level;
    //手机号码
    @Column
    private String phone;
    //邮箱
    @Column
    private String email;
    //区域
    @Column
    private String area;
    //详细地址
    @Column
    private String address;
    //作品描述
    @Column
    @Lob
    private String worksDesc;
    //规划
    @Column
    @Lob
    private String planning;
    //附件
    @Column
    private String files;
    //状态(0待处理，1已处理)
    @Column
    private int status;


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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }
}
