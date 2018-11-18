package com.code90.daliweb.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 论坛详情实体类
 * @author Ray Lin
 * @create 2018-11-12 0:29
 **/
@Entity
@Table(name = "sys_forum_detail")
public class ForumDetail extends BaseDomain {
    private static final long serialVersionUID = 7783416668911301947L;
    @Id
    @GenericGenerator(name="UUIDGENERATE",strategy="uuid2")
    @GeneratedValue(generator="UUIDGENERATE")
    @Column(name="ID",length=36)
    private String id;
    //回复内容
    @Column
    @Lob
    private String content;
    //所属帖子
    @Column
    private String forumId;

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
}
