package com.code90.daliweb.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 汉学动态类
 * @author Ray Lin
 * @create 2019-01-13 22:06
 **/
@Entity
@Table(name="sys_sinology_dynamic")
public class SinologyDynamic extends BaseDomain {
    private static final long serialVersionUID = 7963687194033329449L;
    //编号
    @Id
    @GenericGenerator(name="UUIDGENERATE",strategy="uuid2")
    @GeneratedValue(generator="UUIDGENERATE")
    @Column(name="ID",length=36)
    private String id;
    //标题
    @Column
    private String title;
    //类型（0-普通动态，1-banner动态，2-图文动态，3-轮播动态，4-广告动态，5-广告动态，6-权威评审，7-汉书院，8-汉学风采，9-汉学汉字研究中心
    //      10-全国汉学培训基地，11-五星全球汉学大赛，12-传统文化人才库，13-汉学团，14-知书会，15-达礼网，16-联系我们）
    @Column
    private int type;
    //来源（0-全国汉学培训基地，1-达礼网）
    @Column
    private int source;
    //摘要
    @Column
    private String description;
    //内容
    @Column
    @Lob
    private String content;
    //附件路径
    @Column
    @Lob
    private String fileUrl;
    //图片路径
    @Column
    @Lob
    private String imgUrl;
    //视频类型
    @Column
    private int videoType;
    //视频文件路径
    @Column
    @Lob
    private String videoFileUrl;
    //外部视频连接
    @Column
    private String videoUrl;
    //状态（0-未发布，1-已发布）
    @Column
    private int status;
    //链接
    @Column
    private String url;

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


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getVideoType() {
        return videoType;
    }

    public void setVideoType(int videoType) {
        this.videoType = videoType;
    }

    public String getVideoFileUrl() {
        return videoFileUrl;
    }

    public void setVideoFileUrl(String videoFileUrl) {
        this.videoFileUrl = videoFileUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
