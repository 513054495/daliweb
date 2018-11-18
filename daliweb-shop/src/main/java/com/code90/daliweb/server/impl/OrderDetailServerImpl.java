package com.code90.daliweb.server.impl;

import com.code90.daliweb.domain.OrderDetail;
import com.code90.daliweb.request.shop.CommoditySummaryReq;
import com.code90.daliweb.server.OrderDetailServer;
import com.code90.daliweb.service.OrderDetailService;
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
    public int getOrderDetailByStatus(int i) {
        return orderDetailService.getOrderDetailByStatus(i);
    }

    @Override
    public List<OrderDetail> getAll(CommoditySummaryReq req) {
        Page<OrderDetail> commodities = orderDetailService.findAll(new Specification<OrderDetail>(){
            @Override
            public Predicate toPredicate(Root<OrderDetail> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getCommodityId())){
                    list.add(criteriaBuilder.like(root.get("commodityId").as(String.class), req.getCommodityId()));
                }
                if(req.getMinMoney()!=-1){
                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("money").as(Integer.class), req.getMinMoney()));
                }
                if(req.getMaxMoney()!=-1){
                    list.add(criteriaBuilder.lessThan(root.get("money").as(Integer.class), req.getMaxMoney()));
                }
                if(req.getStatus()!=-1){
                    if(req.getStatus()==0) {
                        list.add(criteriaBuilder.or(criteriaBuilder.equal(root.get("status").as(Integer.class), 0),criteriaBuilder.equal(root.get("status").as(Integer.class), 3)));
                    }else{
                        list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), req.getStatus()));
                    }
                }
                if(!StringUtil.isEmpty(req.getStartTime())){
                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(String.class), req.getStartTime()));
                }
                if(!StringUtil.isEmpty(req.getEndTime())){
                    list.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime").as(String.class), req.getEndTime()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },Pageable.unpaged());
        List<OrderDetail> list=commodities.getContent();
        return list;
    }

    @Override
    public List<OrderDetail> findOrderDteailCriteria(int page, int pageSize, CommoditySummaryReq req) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "createTime");
        Page<OrderDetail> commodities = orderDetailService.findAll(new Specification<OrderDetail>(){
            @Override
            public Predicate toPredicate(Root<OrderDetail> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getCommodityId())){
                    list.add(criteriaBuilder.like(root.get("commodityId").as(String.class), req.getCommodityId()));
                }
                if(req.getMinMoney()!=-1){
                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("money").as(Integer.class), req.getMinMoney()));
                }
                if(req.getMaxMoney()!=-1){
                    list.add(criteriaBuilder.lessThan(root.get("money").as(Integer.class), req.getMaxMoney()));
                }
                if(req.getStatus()!=-1){
                    if(req.getStatus()==0) {
                        list.add(criteriaBuilder.or(criteriaBuilder.equal(root.get("status").as(Integer.class), 0),criteriaBuilder.equal(root.get("status").as(Integer.class), 3)));
                    }else{
                        list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), req.getStatus()));
                    }
                }
                if(!StringUtil.isEmpty(req.getStartTime())){
                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(String.class), req.getStartTime()));
                }
                if(!StringUtil.isEmpty(req.getEndTime())){
                    list.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime").as(String.class), req.getEndTime()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },pageable);
        List<OrderDetail> list=commodities.getContent();
        return list;
    }

    @Override
    public Object getObjectById(String id) {
        return orderDetailService.getOrderDetailById(id);
    }
}
