package com.code90.daliweb.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 论坛展示类
 * @author Ray Lin
 * @create 2018-11-11 23:59
 **/
public class ForumVo extends BaseDomain {
    private static final long serialVersionUID = -2018223091170426246L;
    private String id;
    private String title;
    private int type;
    private String content;
    private String files;
    private String nickName;
    private String userImg;
    private List<ForumDetailVo> forumDetailVos=new ArrayList<>();
    private int forumDetailNum;
    private String typeName;

    public ForumVo() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public List<ForumDetailVo> getForumDetailVos() {
        return forumDetailVos;
    }

    public void setForumDetailVos(List<ForumDetailVo> forumDetailVos) {
        this.forumDetailVos = forumDetailVos;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getForumDetailNum() {
        return forumDetailNum;
    }

    public void setForumDetailNum(int forumDetailNum) {
        this.forumDetailNum = forumDetailNum;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
