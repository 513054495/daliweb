package com.code90.daliweb.service;

import com.code90.daliweb.domain.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * 购物车数据库服务接口
 * @author Ray Lin
 * @create 2018-09-16 0:05
 **/
public interface ShoppingCartService extends JpaRepository<ShoppingCart,Integer>,JpaSpecificationExecutor<ShoppingCart> {
    @Query("select s from ShoppingCart s where s.id=?1 ")
    ShoppingCart getShoppingCartById(String id);

    @Query("select s from ShoppingCart s where s.commodityId=?1 and s.createBy=?2 and s.normId=?3 order by s.createTime desc")
    ShoppingCart getShoppingCartByCommodityIdAndcreateBy(String commodityId, String createBy,String normId);
}
