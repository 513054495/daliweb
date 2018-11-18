package com.code90.daliweb.server;

import com.code90.daliweb.domain.RedPackageDetail;

import java.util.List;

/**
 * 红包服务接口
 * @author Ray Lin
 * @create 2018-10-23 0:27
 **/
public interface RedPackageServer extends BaseServer {
    /**
     * 根据用户获取红包余额
     * @param userCode
     * @return 红包余额
     */
    double getTotalMoneyByUserCode(String userCode);
    /**
     * 根据用户获取红包详细
     * @param userCode
     * @return 红包详细
     */
    List<RedPackageDetail> getAllByUserCode(String userCode);

    /**
     * 根据用户、分享码获取红包记录
     * @param userCode
     * @param shareCode
     * @return 红包记录
     */
    RedPackageDetail getRedPackageDetail(String userCode, String shareCode);

    /**
     * 获取全部的红包金额
     * @return 红包金额
     */
    double getAllRedPackage();
}
