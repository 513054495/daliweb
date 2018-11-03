package com.code90.daliweb.service;

import com.code90.daliweb.domain.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 订单详情数据库服务接口
 * @author Ray Lin
 * @create 2018-09-15 2:14
 **/
public interface OrderDetailService extends JpaRepository<OrderDetail,Integer>,JpaSpecificationExecutor<OrderDetail> {

    @Query("select o from OrderDetail o where o.orderId=?1 ")
    List<OrderDetail> getOrderDetailByOrderId(String orderId);

    @Query("select o from OrderDetail o where o.commodityId=?1 ")
    List<OrderDetail> getOrderDetailByCommodityId(String commodityId);

    @Query("select o from OrderDetail o where o.id=?1 ")
    OrderDetail getOrderDetailById(String id);
}
