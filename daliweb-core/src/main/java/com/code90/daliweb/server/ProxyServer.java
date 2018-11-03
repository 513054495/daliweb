package com.code90.daliweb.server;

import com.code90.daliweb.domain.Proxy;
import com.code90.daliweb.domain.ProxyDetail;
import com.code90.daliweb.request.shop.ProxySearchReq;

import java.util.List;

/**
 * 代理管理服务接口
 * @author Ray Lin
 * @create 2018-10-23 23:49
 **/
public interface ProxyServer extends BaseServer {
    /**
     * 获取全部代理
     * @param req 查询条件
     * @return 全部代理
     */
    List<Proxy> getAll(ProxySearchReq req);

    /**
     * 分页获取全部代理
     * @param page 页码
     * @param pageSize 页大小
     * @param req 查询条件
     * @return 全部代理
     */
    List<Proxy> findProxyCriteria(int page, int pageSize, ProxySearchReq req);

    /**
     * 根据用户帐号获取代理
     * @param recommendUserCode
     * @return 代理
     */
    Proxy getProxyByUserCode(String recommendUserCode);

    /**
     * 保存代理详情
     * @param proxyDetail
     */
    void saveProxyDetail(ProxyDetail proxyDetail);

    /**
     * 根据类型获取代理列表
     * @param i
     * @return 代理列表
     */
    List<Proxy> getProxiesByType(int i);

    /**
     * 根据订单获取代理明细
     * @param id
     * @return 代理明细
     */
    List<ProxyDetail> getProxyDetailByOrderId(String id);

    /**
     * 根据用户帐号获取所有代理金额
     * @param userCode
     * @return 所有代理金额
     */
    double getAllMoneyByUserCode(String userCode);
    /**
     * 根据用户帐号、代理类型获取本月代理详细
     * @param userCode
     * @return 本月代理详细
     */
    List<ProxyDetail> getCurrentMoneyByUserCode(String userCode, int type);

    /**
     * 根据地区获取代理列表
     * @param postalCode
     * @return 代理列表
     */
    List<Proxy> getProxyByPostalCode(String postalCode);

    /**
     * 根据订单编号删除代理记录
     * @param id
     */
    void deleteByOrdersId(String id);
}
