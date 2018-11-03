package com.code90.daliweb.request.user;


import com.code90.daliweb.request.CommonRequest;

import java.io.Serializable;

/**
 * 用户查询请求类
 * @author Ray Lin
 * @create 2018-09-09 20:42
 **/
public class UserSearchReq extends CommonRequest implements Serializable {
    private static final long serialVersionUID = -7422758390706583484L;
    private String userName;
    private String nickName;
    private String phone;
    private String startTime;
    private String endTime;
    private int userType;
    private int isClassMember;
    private int teamLevel;
    private int collegeLevel;
    private int agencyLevel;
    private String area;
    private int page;
    private int pageSize;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getIsClassMember() {
        return isClassMember;
    }

    public void setIsClassMember(int isClassMember) {
        this.isClassMember = isClassMember;
    }

    public int getTeamLevel() {
        return teamLevel;
    }

    public void setTeamLevel(int teamLevel) {
        this.teamLevel = teamLevel;
    }

    public int getCollegeLevel() {
        return collegeLevel;
    }

    public void setCollegeLevel(int collegeLevel) {
        this.collegeLevel = collegeLevel;
    }

    public int getAgencyLevel() {
        return agencyLevel;
    }

    public void setAgencyLevel(int agencyLevel) {
        this.agencyLevel = agencyLevel;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
