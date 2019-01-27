package com.code90.daliweb.domain;

import javax.persistence.*;

/**
 * 商品实体类
 * @author Ray Lin
 * @create 2018-09-11 22:02
 **/
@Entity
@Table(name = "sys_commodity")
public class Commodity extends BaseDomain {
    @Id
    @Column(name="ID",length=36)
    //商品编号
    private String id;
    //商品名称
    @Column
    private String name;
    //商品类型（0.普通商品，1.虚拟商品）
    @Column
    private int type;
    //商品图片
    @Column
    @Lob
    private String pic;
    //商品详情图片
    @Column
    @Lob
    private String descPic;
    //商品描述
    @Column
    @Lob
    private String description;
    //是否购买升VIP用户（0.否，1.是）
    @Column
    private int isVip;
    //商品状态(0.下架，1.上架)
    @Column
    private int status;
    //是否删除
    @Column
    private int deleted;
    //虚拟销量
    @Column
    private int sham;
    //商品分类
    @Column
    private int typeId;
    //是否分销(0-不，1-是)
    @Column
    private int isProxy;
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
    //封面图路径
    @Column
    private String coverImgUrl;
    //商品内容
    @Column
    @Lob
    private String content;
    //是否置顶(0.否，1.是)
    @Column
    private int isTop;
    //商品等级(数字越大，排得越前)
    @Column
    private int level;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public int getIsVip() {
        return isVip;
    }

    public void setIsVip(int isVip) {
        this.isVip = isVip;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescPic() {
        return descPic;
    }

    public void setDescPic(String descPic) {
        this.descPic = descPic;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public int getSham() {
        return sham;
    }

    public void setSham(int sham) {
        this.sham = sham;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getIsProxy() {
        return isProxy;
    }

    public void setIsProxy(int isProxy) {
        this.isProxy = isProxy;
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

    public String getCoverImgUrl() {
        return coverImgUrl;
    }

    public void setCoverImgUrl(String coverImgUrl) {
        this.coverImgUrl = coverImgUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getIsTop() {
        return isTop;
    }

    public void setIsTop(int isTop) {
        this.isTop = isTop;
    }
}
