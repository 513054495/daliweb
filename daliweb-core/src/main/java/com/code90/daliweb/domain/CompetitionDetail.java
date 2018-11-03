package com.code90.daliweb.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 竞赛详情实体类
 * @author Ray Lin
 * @create 2018-10-17 23:13
 **/
@Entity
@Table(name="sys_competition_detail")
public class CompetitionDetail extends BaseDomain {
    private static final long serialVersionUID = -4537209542725773905L;
    @Id
    @GenericGenerator(name="UUIDGENERATE",strategy="uuid2")
    @GeneratedValue(generator="UUIDGENERATE")
    @Column(name="ID",length=36)
    private String id;
    //申请人姓名
    @Column
    private String name;
    //申请人国籍
    @Column
    private String country;
    //申请人性别
    @Column
    private int sex;
    //申请人年龄
    @Column
    private int age;
    //手机号码
    @Column
    private String phone;
    //邮箱
    @Column
    private String email;
    //区域代码
    @Column
    private String postalCode;
    //区域
    @Column
    private String area;
    //详细地址
    @Column
    private String address;
    //规划
    @Column
    @Lob
    private String description;
    //附件
    @Column
    private String files;
    //状态(0-待评审，1-已入选，2-未入选)
    @Column
    private int status;
    //所属竞赛
    private String competitionId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(String competitionId) {
        this.competitionId = competitionId;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
