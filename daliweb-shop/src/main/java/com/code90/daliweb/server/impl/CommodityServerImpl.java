package com.code90.daliweb.server.impl;

import com.code90.daliweb.domain.Commodity;
import com.code90.daliweb.request.shop.CommoditySearchReq;
import com.code90.daliweb.server.CommodityServer;
import com.code90.daliweb.service.CommodityService;
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
 * 商品服务实现类
 *
 * @author Ray Lin
 * @create 2018-09-12 22:25
 **/
@Service("commodityServer")
public class CommodityServerImpl implements CommodityServer {
    @Autowired
    private CommodityService commodityService;

    @Override
    public void save(Object commodity) {
        commodityService.save((Commodity)commodity);
    }

    @Override
    public Object getObjectById(String id) {
        return commodityService.getCommodityById(id);
    }

    @Override
    public Commodity getCommodityAndDeleteById(String id) {
        return commodityService.getCommodityAndDeleteById(id);
    }

    @Override
    public void delete(Object commodity) {
        commodityService.delete((Commodity)commodity);
    }

    @Override
    public List<Commodity> findCommodityCriteria(Integer page, Integer size, CommoditySearchReq req) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "id");
        Page<Commodity> commodities = commodityService.findAll(new Specification<Commodity>(){
            @Override
            public Predicate toPredicate(Root<Commodity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(req.getDeleted()!=-1) {
                    list.add(criteriaBuilder.equal(root.get("deleted").as(Integer.class), req.getDeleted()));
                }
                if(!StringUtil.isEmpty(req.getName())){
                    list.add(criteriaBuilder.like(root.get("name").as(String.class), "%"+req.getName()+"%"));
                }
                if(req.getType()!=-1){
                    list.add(criteriaBuilder.equal(root.get("type").as(Integer.class), req.getType()));
                }
                if(req.getIsVip()!=-1){
                    list.add(criteriaBuilder.equal(root.get("isVip").as(Integer.class), req.getIsVip()));
                }
                if(req.getStatus()!=-1){
                    list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), req.getStatus()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },pageable);
        List<Commodity> list=commodities.getContent();
        return list;
    }

    @Override
    public List<Commodity> getAll(CommoditySearchReq req) {
        Page<Commodity> commodities = commodityService.findAll(new Specification<Commodity>(){
            @Override
            public Predicate toPredicate(Root<Commodity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                list.add(criteriaBuilder.equal(root.get("deleted").as(Integer.class),0));
                if(!StringUtil.isEmpty(req.getName())){
                    list.add(criteriaBuilder.like(root.get("name").as(String.class), "%"+req.getName()+"%"));
                }
                if(req.getType()!=-1){
                    list.add(criteriaBuilder.equal(root.get("type").as(Integer.class), req.getType()));
                }
                if(req.getIsVip()!=-1){
                    list.add(criteriaBuilder.equal(root.get("isVip").as(Integer.class), req.getIsVip()));
                }
                if(req.getStatus()!=-1){
                    list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), req.getStatus()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },Pageable.unpaged());
        List<Commodity> list=commodities.getContent();
        return list;
    }

    @Override
    public List<Commodity> getAllCommodity() {
        return commodityService.findAll();
    }
}
