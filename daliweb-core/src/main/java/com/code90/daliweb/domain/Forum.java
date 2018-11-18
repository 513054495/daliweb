package com.code90.daliweb.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 论坛实体类
 * @author Ray Lin
 * @create 2018-11-11 23:59
 **/
@Entity
@Table(name="sys_forum")
public class Forum extends BaseDomain {
    private static final long serialVersionUID = 8882651853142333345L;
    @Id
    @GenericGenerator(name="UUIDGENERATE",strategy="uuid2")
    @GeneratedValue(generator="UUIDGENERATE")
    @Column(name="ID",length=36)
    private String id;
    //标题
    @Column
    private String title;
    //类型
    @Column
    private int type;
    //内容
    @Column
    @Lob
    private String content;
    //附件
    @Column
    private String files;


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
}
