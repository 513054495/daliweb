package com.code90.daliweb.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 学习内容实体类
 * @author Ray Lin
 * @create 2018-09-25 22:51
 **/
@Entity
@Table(name = "sys_learn_content")
public class LearnContent extends BaseDomain {
    private static final long serialVersionUID = -6207166963023844017L;
    @Id
    @GenericGenerator(name="UUIDGENERATE",strategy="uuid2")
    @GeneratedValue(generator="UUIDGENERATE")
    @Column(name="ID",length=36)
    private String id;
    //学习内容标题
    @Column
    private String title;
    //学习内容关键字
    @Column
    private String keyword;
    //作者
    @Column
    private String author;
    //内容
    @Lob
    @Column
    private String content;
    //外部视频链接
    @Column
    private String url;
    //学习内容状态（0.未发布，1.已发布）
    @Column
    private int status;
    //浏览次数
    @Column
    private int viewNum;
    //等级（数字越大，排最前）
    @Column
    private int level;
    //所属主题编号
    @Column
    private String topicId;
    //是否置顶
    @Column
    private int isTop;
    //封面图
    @Column
    private String pic;
    //附件
    @Column
    private String filePath;

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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getViewNum() {
        return viewNum;
    }

    public void setViewNum(int viewNum) {
        this.viewNum = viewNum;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public int getIsTop() {
        return isTop;
    }

    public void setIsTop(int isTop) {
        this.isTop = isTop;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
