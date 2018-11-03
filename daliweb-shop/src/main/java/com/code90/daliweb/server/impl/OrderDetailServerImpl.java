package com.code90.daliweb.server.impl;

import com.code90.daliweb.domain.OrderDetail;
import com.code90.daliweb.server.OrderDetailServer;
import com.code90.daliweb.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单详情服务实现类
 * @author Ray Lin
 * @create 2018-09-15 2:11
 **/
@Service("orderDetailServer")
public class OrderDetailServerImpl implements OrderDetailServer {

    @Autowired
    private OrderDetailService orderDetailService;

    @Override
    public void save(Object orderDetail) {
        orderDetailService.save((OrderDetail)orderDetail);
    }

    @Override
    public void delete(Object object) {
        orderDetailService.delete((OrderDetail) object);
    }

    @Override
    public void delOrderDetailByOrderId(String orderId) {
        List<OrderDetail> orderDetails=orderDetailService.getOrderDetailByOrderId(orderId);
        for (OrderDetail orderDetail:orderDetails){
            orderDetailService.delete(orderDetail);
        }
    }

    @Override
    public List<OrderDetail> getOrderDetailByOrderId(String orderId) {
        return orderDetailService.getOrderDetailByOrderId(orderId);
    }

    @Override
    public List<OrderDetail> getOrderDetailByCommodityId(String commodityId) {
        return orderDetailService.getOrderDetailByCommodityId(commodityId);
    }

    @Override
    public Object getObjectById(String id) {
        return orderDetailService.getOrderDetailById(id);
    }
}
