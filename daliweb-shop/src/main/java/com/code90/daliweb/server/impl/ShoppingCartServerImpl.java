package com.code90.daliweb.server.impl;

import com.code90.daliweb.domain.Orders;
import com.code90.daliweb.domain.ShoppingCart;
import com.code90.daliweb.request.shop.ShoppingCartSearchReq;
import com.code90.daliweb.server.ShoppingCartServer;
import com.code90.daliweb.service.ShoppingCartService;
import com.code90.daliweb.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车服务实现类
 * @author Ray Lin
 * @create 2018-09-16 0:04
 **/
@Service("shoppingCartServer")
public class ShoppingCartServerImpl implements ShoppingCartServer {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Override
    public void save(Object shoppingCart) {
        shoppingCartService.save((ShoppingCart)shoppingCart);
    }

    @Override
    public Object getObjectById(String id) {
        return shoppingCartService.getShoppingCartById(id);
    }

    @Override
    public void delete(Object shoppingCart) {
        shoppingCartService.delete((ShoppingCart)shoppingCart);
    }

    @Override
    public List<ShoppingCart> getAll(ShoppingCartSearchReq req) {
        Page<ShoppingCart> shoppingCarts = shoppingCartService.findAll(new Specification<ShoppingCart>(){
            @Override
            public Predicate toPredicate(Root<ShoppingCart> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getCreateBy())){
                    list.add(criteriaBuilder.equal(root.get("createBy").as(String.class), req.getCreateBy()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },Pageable.unpaged());
        List<ShoppingCart> list=shoppingCarts.getContent();
        return list;
    }

    @Override
    public List<ShoppingCart> findOrderCriteria(int page, int pageSize, ShoppingCartSearchReq req) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "createTime");
        Page<ShoppingCart> shoppingCarts = shoppingCartService.findAll(new Specification<ShoppingCart>(){
            @Override
            public Predicate toPredicate(Root<ShoppingCart> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getCreateBy())){
                    list.add(criteriaBuilder.equal(root.get("createBy").as(String.class), req.getCreateBy()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },pageable);
        List<ShoppingCart> list=shoppingCarts.getContent();
        return list;
    }

    @Override
    public ShoppingCart getShoppingCartByCommodityIdAndcreateBy(String commodityId, String createBy) {
        return shoppingCartService.getShoppingCartByCommodityIdAndcreateBy(commodityId,createBy);
    }
}
