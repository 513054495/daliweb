package com.code90.daliweb.server;

import com.code90.daliweb.domain.ShoppingCart;
import com.code90.daliweb.request.shop.ShoppingCartSearchReq;

import java.util.List;

/**
 * 购物车服务接口
 * @author Ray Lin
 * @create 2018-09-16 0:01
 **/
public interface ShoppingCartServer extends BaseServer {


    /**
     * 获取全部购物车信息
     * @param req 查询条件
     * @return 购物车信息
     */
    List<ShoppingCart> getAll(ShoppingCartSearchReq req);

    /**
     * 分页获取购物车信息
     * @param page 页码
     * @param pageSize 页大小
     * @param req 查询条件
     * @return 购物车信息
     */
    List<ShoppingCart> findOrderCriteria(int page, int pageSize, ShoppingCartSearchReq req);

    /**
     * 根据商品编号和用户帐号获取购物车信息
     * @param commodityId 商品编号
     * @param createBy 用户帐号
     * @return 购物车信息
     */
    ShoppingCart getShoppingCartByCommodityIdAndcreateBy(String commodityId, String createBy);
}
