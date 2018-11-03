package com.code90.daliweb.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 用户实体
 * @author Ray
 * @create 2018-05-28 0:14
 **/
@Entity
@Table(name = "sys_user")
public class User extends BaseDomain {

    private static final long serialVersionUID = -3113770074284679436L;

    @Id
    @GenericGenerator(name="UUIDGENERATE",strategy="uuid2")
    @GeneratedValue(generator="UUIDGENERATE")
    @Column(name="ID",length=36)
    //编号
    private String id;
    @Column
    //用户名称
    private String userCode;
    //用户名
    @Column
    private String userName;
    //昵称
    @Column
    private String nickname;
    //密码
    @Column
    private String dlwPsw;
    //头像
    @Column
    private String pic;
    //身份证
    @Column
    private String idCard;
    //用户类型(0.普通用户，1.VIP用户，2.管理员)
    @Column
    private int userType;
    //用户学分（用户类型为1有效）
    @Column
    private int point;
    //是否投稿(用户类型为1有效)
    @Column
    private int isSubmission;
    //是否审核（用户类型为1有效）
    @Column
    private int isReview;
    //汉修班用户
    @Column
    private int isClassMember;
    //汉学团等级
    @Column
    private int teamLevel;
    //汉书院等级
    @Column
    private int collegeLevel;
    //代理等级
    @Column
    private int agencyLevel;
    //手机号码
    @Column
    private String phone;
    //区域
    @Column
    private String area;
    //详细地址
    @Column
    private String address;
    //邮编
    @Column
    private String postalCode;
    //默认收货区域
    @Column
    private String sArea;
    //默认收货详细地址
    @Column
    private String sAddress;
    //默认收货邮编
    @Column
    private String sPostalCode;
    //微信号
    @Column
    private String wechatCode;
    //格言
    @Column
    private String aphorism;
    //性别
    @Column
    private int sex;
    //年龄
    @Column
    private int age;
    //推荐码(新增自动生成，不能修改)
    @Column
    private String shareCode;
    //个人简介
    @Column
    @Lob
    private String description;

    public User() {
    }


    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDlwPsw() {
        return dlwPsw;
    }

    public void setDlwPsw(String dlwPsw) {
        this.dlwPsw = dlwPsw;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getsAddress() {
        return sAddress;
    }

    public void setsAddress(String sAddress) {
        this.sAddress = sAddress;
    }

    public String getWechatCode() {
        return wechatCode;
    }

    public void setWechatCode(String wechatCode) {
        this.wechatCode = wechatCode;
    }

    public String getAphorism() {
        return aphorism;
    }

    public void setAphorism(String aphorism) {
        this.aphorism = aphorism;
    }

    public int getAgencyLevel() {
        return agencyLevel;
    }

    public void setAgencyLevel(int agencyLevel) {
        this.agencyLevel = agencyLevel;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getTeamLevel() {
        return teamLevel;
    }

    public void setTeamLevel(int teamLevel) {
        this.teamLevel = teamLevel;
    }

    public int getIsSubmission() {
        return isSubmission;
    }

    public void setIsSubmission(int isSubmission) {
        this.isSubmission = isSubmission;
    }

    public int getIsClassMember() {
        return isClassMember;
    }

    public void setIsClassMember(int isClassMember) {
        this.isClassMember = isClassMember;
    }

    public int getCollegeLevel() {
        return collegeLevel;
    }

    public void setCollegeLevel(int collegeLevel) {
        this.collegeLevel = collegeLevel;
    }

    public int getIsReview() {
        return isReview;
    }

    public void setIsReview(int isReview) {
        this.isReview = isReview;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getsArea() {
        return sArea;
    }

    public void setsArea(String sArea) {
        this.sArea = sArea;
    }

    public String getsPostalCode() {
        return sPostalCode;
    }

    public void setsPostalCode(String sPostalCode) {
        this.sPostalCode = sPostalCode;
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

    public String getShareCode() {
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
