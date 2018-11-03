package com.code90.daliweb.request.shop;

import com.code90.daliweb.request.CommonRequest;

import javax.persistence.Column;

/**
 * 红包规则修改请求类
 * @author Ray Lin
 * @create 2018-10-22 23:03
 **/
public class RedPackageChangeReq extends CommonRequest {
    private int level1;
    private int level2;
    private int level3;
    private int level4;

    public int getLevel1() {
        return level1;
    }

    public void setLevel1(int level1) {
        this.level1 = level1;
    }

    public int getLevel2() {
        return level2;
    }

    public void setLevel2(int level2) {
        this.level2 = level2;
    }

    public int getLevel3() {
        return level3;
    }

    public void setLevel3(int level3) {
        this.level3 = level3;
    }

    public int getLevel4() {
        return level4;
    }

    public void setLevel4(int level4) {
        this.level4 = level4;
    }
}
