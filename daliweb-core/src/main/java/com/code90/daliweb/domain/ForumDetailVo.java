package com.code90.daliweb.domain;

/**
 * 论坛详情展示类
 * @author Ray Lin
 * @create 2018-11-12 0:29
 **/
public class ForumDetailVo extends BaseDomain {
    private static final long serialVersionUID = -3727641580279796229L;
    private String id;
    private String content;
    private String forumId;
    private String nickName;
    private String userImg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getForumId() {
        return forumId;
    }

    public void setForumId(String forumId) {
        this.forumId = forumId;
    }


    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
