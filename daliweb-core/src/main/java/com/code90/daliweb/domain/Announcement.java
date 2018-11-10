package com.code90.daliweb.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 公告实体类
 * @author Ray Lin
 * @create 2018-11-10 20:47
 **/
@Entity
@Table(name = "sys_announcement")
public class Announcement extends BaseDomain {
    private static final long serialVersionUID = -3889276237404813302L;
    @Id
    @GenericGenerator(name="UUIDGENERATE",strategy="uuid2")
    @GeneratedValue(generator="UUIDGENERATE")
    @Column(name="ID",length=36)
    private String id;
    //标题
    @Column
    private String title;
    //公告类型（0.系统消息，1.通知公告，2.赛果公告）
    @Column
    private int type;
    //接收人类型（0.全部用户，1.汉学团用户，2.汉学堂用户 3.汉书院用户 4.代理用户）
    @Column
    private int level;
    //内容
    @Column
    @Lob
    private String content;
    //附件
    @Column
    private String files;
    //状态(0.待发布，1.已发布)
    @Column
    private int status;

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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
