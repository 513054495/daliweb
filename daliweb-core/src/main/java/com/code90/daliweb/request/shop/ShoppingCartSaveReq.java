package com.code90.daliweb.request.shop;

import com.code90.daliweb.request.CommonRequest;

/**
 * 加入购物车请求类
 * @author Ray Lin
 * @create 2018-09-15 23:50
 **/
public class ShoppingCartSaveReq extends CommonRequest {
    private String commodityId;
    private int num;
    private String createBy;
    private String normId;

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getNormId() {
        return normId;
    }

    public void setNormId(String normId) {
        this.normId = normId;
    }
}
