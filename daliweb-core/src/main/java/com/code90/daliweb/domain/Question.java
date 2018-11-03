package com.code90.daliweb.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 问题实体类
 * @author Ray Lin
 * @create 2018-10-01 23:01
 **/
@Entity
@Table(name="sys_question")
public class Question extends BaseDomain {
    private static final long serialVersionUID = 1394149412777058287L;
    @Id
    @GenericGenerator(name="UUIDGENERATE",strategy="uuid2")
    @GeneratedValue(generator="UUIDGENERATE")
    @Column(name="ID",length=36)
    private String id;
    //问题标题
    @Column
    private String title;
    //问题内容
    @Column
    private String content;
    //问题图片
    @Column
    private String pic;
    //问题答案
    @Column
    private String answer;
    //状态(0.未处理，1.已忽略，2.已回答，3.已公开)
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
