package com.code90.daliweb.service;

import com.code90.daliweb.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 订单数据库服务接口
 * @author Ray List
 * @create 2018-09-15 1:56
 **/
public interface OrdersService extends JpaRepository<Orders,Integer>,JpaSpecificationExecutor<Orders> {
    @Query("select o from Orders o where o.id=?1 ")
    Orders getOrdersById(String id);
    @Query("select o from Orders o where o.createBy=?1 ")
    List<Orders> getOrdersByCreateBy(String userCode);
}
