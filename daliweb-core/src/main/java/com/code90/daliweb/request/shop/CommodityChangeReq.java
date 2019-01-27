package com.code90.daliweb.request.shop;

import com.code90.daliweb.domain.CommodityNorm;
import com.code90.daliweb.request.CommonRequest;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品修改请求类
 * @author Ray Lin
 * @create 2018-09-12 22:48
 **/
public class CommodityChangeReq extends CommonRequest {
    private String id;
    private String name;
    private int type;
    private int totalNum;
    private double price;
    private String pic;
    private String descPic;
    private String description;
    private int isVip;
    private double deduction;
    private int status;
    private String specificationList;
    private int sham;
    private int typeId;
    private int isProxy;
    private int videoType;
    private String videoFileUrl;
    private String videoUrl;
    private String coverImgUrl;
    private String content;
    private List<CommodityNorm> commodityNorms=new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIsVip() {
        return isVip;
    }

    public void setIsVip(int isVip) {
        this.isVip = isVip;
    }

    public double getDeduction() {
        return deduction;
    }

    public void setDeduction(double deduction) {
        this.deduction = deduction;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getSpecificationList() {
        return specificationList;
    }

    public void setSpecificationList(String specificationList) {
        this.specificationList = specificationList;
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

    public List<CommodityNorm> getCommodityNorms() {
        return commodityNorms;
    }

    public void setCommodityNorms(List<CommodityNorm> commodityNorms) {
        this.commodityNorms = commodityNorms;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
