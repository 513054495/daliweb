package com.code90.daliweb.server.impl;

import com.code90.daliweb.domain.Orders;
import com.code90.daliweb.request.shop.OrderSearchReq;
import com.code90.daliweb.server.OrdersServer;
import com.code90.daliweb.service.OrdersService;
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
 * 订单服务实现类
 * @author Ray Lin
 * @create 2018-09-15 1:55
 **/
@Service("orderServer")
public class OrdersServerImpl implements OrdersServer {

    @Autowired
    private OrdersService ordersService;

    @Override
    public Object getObjectById(String id) {
        return ordersService.getOrdersById(id);
    }


    @Override
    public void save(Object orders) {
        ordersService.save((Orders)orders);
    }

    @Override
    public void delete(Object orders) {
        ordersService.delete((Orders)orders);
    }

    @Override
    public List<Orders> getAll(OrderSearchReq req) {
        Page<Orders> ordersPage = ordersService.findAll(new Specification<Orders>(){
            @Override
            public Predicate toPredicate(Root<Orders> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getCreateBy())){
                    list.add(criteriaBuilder.equal(root.get("createBy").as(String.class), req.getCreateBy()));
                }
                if(!StringUtil.isEmpty(req.getId())){
                    list.add(criteriaBuilder.like(root.get("id").as(String.class), "%"+req.getId()+"%"));
                }
                if(!StringUtil.isEmpty(req.getArea())){
                    list.add(criteriaBuilder.like(root.get("area").as(String.class), "%"+req.getArea()+"%"));
                }
                if(!StringUtil.isEmpty(req.getOrderPostalCode())){
                    list.add(criteriaBuilder.like(root.get("orderPostalCode").as(String.class), "%"+req.getOrderPostalCode()+"%"));
                }
                if(req.getPayType()!=-1){
                    list.add(criteriaBuilder.equal(root.get("payType").as(Integer.class), req.getPayType()));
                }
                if(req.getStatus()!=-1){
                    list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), req.getStatus()));
                }
                if(!StringUtil.isEmpty(req.getStartTime())){
                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("modifyTime").as(String.class), req.getStartTime()));
                }
                if(!StringUtil.isEmpty(req.getEndTime())){
                    list.add(criteriaBuilder.lessThanOrEqualTo(root.get("modifyTime").as(String.class), req.getEndTime()));
                }
                if(req.getMinMoney()!=-1){
                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("totalMoney").as(Integer.class), req.getMinMoney()));
                }
                if(req.getMaxMoney()!=-1){
                    list.add(criteriaBuilder.lessThan(root.get("totalMoney").as(Integer.class), req.getMaxMoney()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },Pageable.unpaged());
        List<Orders> list=ordersPage.getContent();
        return list;
    }

    @Override
    public List<Orders> findOrderCriteria(int page, int pageSize, OrderSearchReq req) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "id");
        Page<Orders> ordersPage = ordersService.findAll(new Specification<Orders>(){
            @Override
            public Predicate toPredicate(Root<Orders> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getCreateBy())){
                    list.add(criteriaBuilder.equal(root.get("createBy").as(String.class), req.getCreateBy()));
                }
                if(!StringUtil.isEmpty(req.getId())){
                    list.add(criteriaBuilder.like(root.get("id").as(String.class), "%"+req.getId()+"%"));
                }
                if(!StringUtil.isEmpty(req.getArea())){
                    list.add(criteriaBuilder.like(root.get("area").as(String.class), "%"+req.getArea()+"%"));
                }
                if(!StringUtil.isEmpty(req.getOrderPostalCode())){
                    list.add(criteriaBuilder.like(root.get("orderPostalCode").as(String.class), "%"+req.getOrderPostalCode()+"%"));
                }
                if(req.getPayType()!=-1){
                    list.add(criteriaBuilder.equal(root.get("payType").as(Integer.class), req.getPayType()));
                }
                if(req.getStatus()!=-1){
                    list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), req.getStatus()));
                }
                if(!StringUtil.isEmpty(req.getStartTime())){
                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(String.class), req.getStartTime()));
                }
                if(!StringUtil.isEmpty(req.getEndTime())){
                    list.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime").as(String.class), req.getEndTime()));
                }
                if(req.getMinMoney()!=-1){
                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("totalMoney").as(Integer.class), req.getMinMoney()));
                }
                if(req.getMaxMoney()!=-1){
                    list.add(criteriaBuilder.lessThan(root.get("totalMoney").as(Integer.class), req.getMaxMoney()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },pageable);
        List<Orders> list=ordersPage.getContent();
        return list;
    }

    @Override
    public List<Orders> getOrdersByCreateBy(String userCode) {
        return ordersService.getOrdersByCreateBy(userCode);
    }

    @Override
    public int getOrdersByStatus(int i) {
        return ordersService.getOrdersByStatus(i);
    }

    @Override
    public List<Object[]> getSalesByYear() {
        List<Object[]> sales=ordersService.getSalesByMonth();
        return sales;
    }
}
