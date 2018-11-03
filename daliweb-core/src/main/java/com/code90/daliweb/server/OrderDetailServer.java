package com.code90.daliweb.server;

import com.code90.daliweb.domain.OrderDetail;

import java.util.List;

/**
 * 订单详情服务接口
 * @author Ray Lin
 * @create 2018-09-15 2:09
 **/
public interface OrderDetailServer extends BaseServer {

    /**
     * 根据订单编号删除订单详情
     * @param orderId 订单编号
     */
    void delOrderDetailByOrderId(String orderId);

    /**
     * 根据订单编号获取商品详情
     * @param orderId 订单编号
     * @return 商品详情
     */
    List<OrderDetail> getOrderDetailByOrderId(String orderId);

    /**
     * 根据商品编号获取商品详情
     * @param commodityId 商品编号
     * @return 商品详情
     */
    List<OrderDetail> getOrderDetailByCommodityId(String commodityId);

}
