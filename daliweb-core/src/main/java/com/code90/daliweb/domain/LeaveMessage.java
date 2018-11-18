package com.code90.daliweb.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 留言实体类
 * @author Ray Lin
 * @create 2018-11-08 22:05
 **/
@Entity
@Table(name="sys_leave_message")
public class LeaveMessage extends BaseDomain {
    private static final long serialVersionUID = 4318001129946832620L;
    //编号
    @Id
    @GenericGenerator(name="UUIDGENERATE",strategy="uuid2")
    @GeneratedValue(generator="UUIDGENERATE")
    private String id;
    //留言内容
    @Column
    @Lob
    private String content;
    //状态(0.待处理 1.已处理 2.已忽略）
    @Column
    private int status;
    //回复内容
    @Column
    @Lob
    private String reply;

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

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
