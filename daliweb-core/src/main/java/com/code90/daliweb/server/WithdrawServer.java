package com.code90.daliweb.server;

import com.code90.daliweb.domain.Withdraw;
import com.code90.daliweb.request.shop.WithdrawSearchReq;

import java.util.List;

/**
 * 提现服务接口
 * @author Ray Lin
 * @create 2018-10-28 18:51
 **/
public interface WithdrawServer extends BaseServer {
    /**
     * 获取全部提现记录
     * @param req 分页条件
     * @return 提现记录
     */
    List<Withdraw> getAll(WithdrawSearchReq req);
    /**
     * 分页获取全部提现记录
     * @param req 分页条件
     * @return 提现记录
     */
    List<Withdraw> findWithdrawCriteria(int page, int pageSize, WithdrawSearchReq req);

    /**
     * 根据用户帐号获取提现记录
     * @param userCode
     * @return 提现记录
     */
    List<Withdraw> getWithdrawByUserCode(String userCode);

    /**
     * 根据用户帐号获取所有提现金额
     * @param userCode
     * @return 所有提现金额
     */
    double getAllMoneyByUserCode(String userCode);
}
