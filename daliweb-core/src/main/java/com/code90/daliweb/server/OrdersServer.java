package com.code90.daliweb.server;

import com.code90.daliweb.domain.Orders;
import com.code90.daliweb.request.shop.OrderSearchReq;

import java.util.List;
import java.util.Map;

/**
 * 订单服务接口
 * @author Ray Lin
 * @create 2018-09-15 1:30
 **/
public interface OrdersServer extends BaseServer {


    /**
     * 获取全部订单
     * @param req 查询条件
     * @return 全部订单
     */
    List<Orders> getAll(OrderSearchReq req);

    /**
     * 分页获取订单
     * @param req 查询条件
     * @return 订单列表
     */
    List<Orders> findOrderCriteria(int page, int pageSize, OrderSearchReq req);

    /**
     * 根据用户帐号获取订单列表
     * @param userCode 用户帐号
     * @return 订单列表
     */
    List<Orders> getOrdersByCreateBy(String userCode);

}
