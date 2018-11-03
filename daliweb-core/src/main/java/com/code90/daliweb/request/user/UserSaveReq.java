package com.code90.daliweb.request.user;

import com.code90.daliweb.request.CommonRequest;

import javax.persistence.Column;

/**
 * 用户保存请求类
 * @author Ray
 * @create 2018-05-28 0:14
 **/
public class UserSaveReq extends CommonRequest {
    private String userCode;
    private String userName;
    private String nickname;
    private String dlwPsw;
    private String pic;
    private String idCard;
    private int userType;
    private int point;
    private int isSubmission;
    private int isReview;
    private int isClassMember;
    private int teamLevel;
    private int collegeLevel;
    private int agencyLevel;
    private String phone;
    private String area;
    private String address;
    private String postalCode;
    private String sArea;
    private String sAddress;
    private String sPostalCode;
    private String wechatCode;
    private String aphorism;
    private int sex;
    private int age;
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
