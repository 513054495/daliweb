package com.code90.daliweb.server.impl;

import com.code90.daliweb.domain.Commodity;
import com.code90.daliweb.domain.Proxy;
import com.code90.daliweb.domain.ProxyDetail;
import com.code90.daliweb.request.shop.ProxyDetailSearchReq;
import com.code90.daliweb.request.shop.ProxySearchReq;
import com.code90.daliweb.server.ProxyServer;
import com.code90.daliweb.service.ProxyDetailService;
import com.code90.daliweb.service.ProxyService;
import com.code90.daliweb.utils.DateUtils;
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
import java.util.Date;
import java.util.List;

/**
 * 代理服务接口实现类
 * @author Ray Lin
 * @create 2018-10-23 23:55
 **/
@Service("proxyServer")
public class ProxyServerImpl implements ProxyServer {

    @Autowired
    private ProxyService proxyService;
    @Autowired
    private ProxyDetailService proxyDetailService;

    @Override
    public void save(Object object) {
        proxyService.save((Proxy)object);
    }

    @Override
    public void delete(Object object) {
        proxyService.delete((Proxy)object);
    }

    @Override
    public Object getObjectById(String id) {
        return proxyService.getProxyById(id);
    }

    @Override
    public List<Proxy> getAll(ProxySearchReq req) {
        Page<Proxy> commodities = proxyService.findAll(new Specification<Proxy>(){
            @Override
            public Predicate toPredicate(Root<Proxy> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getCreateBy())){
                    list.add(criteriaBuilder.equal(root.get("createBy").as(String.class), req.getCreateBy()));
                }
                if(!StringUtil.isEmpty(req.getArea())){
                    list.add(criteriaBuilder.like(root.get("area").as(String.class), "%"+req.getArea()+"%"));
                }
                if(req.getType()!=-1){
                    list.add(criteriaBuilder.equal(root.get("type").as(Integer.class), req.getType()));
                }
                if(req.getStatus()!=-1){
                    list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), req.getStatus()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },Pageable.unpaged());
        List<Proxy> list=commodities.getContent();
        return list;
    }

    @Override
    public List<Proxy> findProxyCriteria(int page, int pageSize, ProxySearchReq req) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "createTime");
        Page<Proxy> commodities = proxyService.findAll(new Specification<Proxy>(){
            @Override
            public Predicate toPredicate(Root<Proxy> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getCreateBy())){
                    list.add(criteriaBuilder.equal(root.get("createBy").as(String.class), req.getCreateBy()));
                }
                if(!StringUtil.isEmpty(req.getArea())){
                    list.add(criteriaBuilder.like(root.get("area").as(String.class), "%"+req.getArea()+"%"));
                }
                if(req.getType()!=-1){
                    list.add(criteriaBuilder.equal(root.get("type").as(Integer.class), req.getType()));
                }
                if(req.getStatus()!=-1){
                    list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), req.getStatus()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },pageable);
        List<Proxy> list=commodities.getContent();
        return list;
    }

    @Override
    public Proxy getProxyByUserCode(String recommendUserCode) {
        return proxyService.getProxyByUserCode(recommendUserCode);
    }

    @Override
    public void saveProxyDetail(ProxyDetail proxyDetail) {
        proxyDetailService.save(proxyDetail);
    }

    @Override
    public List<Proxy> getProxiesByType(int i) {
        return proxyService.getProxiesByType(i);
    }

    @Override
    public List<ProxyDetail> getProxyDetailByOrderId(String id) {
        return proxyDetailService.getProxyDetailByOrderId(id);
    }

    @Override
    public double getAllMoneyByUserCode(String userCode) {
        return proxyDetailService.getAllMoneyByUserCode(userCode);
    }

    @Override
    public List<ProxyDetail> getCurrentMoneyByUserCode(String userCode, int type) {
        Page<ProxyDetail> commodities = proxyDetailService.findAll(new Specification<ProxyDetail>(){
            @Override
            public Predicate toPredicate(Root<ProxyDetail> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(userCode)){
                    list.add(criteriaBuilder.equal(root.get("createBy").as(String.class), userCode));
                }
                if(type!=-1){
                    list.add(criteriaBuilder.equal(root.get("type").as(Integer.class), type));
                }
                list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(String.class),DateUtils.getFirstDateOfMonth(new Date()) ));
                list.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime").as(String.class), DateUtils.getLastDateOfMonth(new Date())));
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },Pageable.unpaged());
        List<ProxyDetail> list=commodities.getContent();
        return list;
    }

    @Override
    public List<Proxy> getProxyByPostalCode(String postalCode) {
        List<Proxy> proxies=new ArrayList<>();
        List<Proxy> list=proxyService.getProxyList();
        for (Proxy proxy : list){
            if(proxy.getPostalCode().contains(postalCode)){
                proxies.add(proxy);
            }
        }
        return proxies;
    }

    @Override
    public void deleteByOrdersId(String id) {
        List<ProxyDetail> proxyDetails=proxyDetailService.getProxyDetailByOrderId(id);
        for (ProxyDetail proxyDetail : proxyDetails){
            proxyDetailService.delete(proxyDetail);
        }
    }

    @Override
    public double getAllMoney() {
        return proxyDetailService.getAllMoney();
    }

    @Override
    public List<Object[]> getProxyDetailByYear() {
        List<Object[]> proxyDetails=proxyDetailService.getProxyDetailByMonth();
        return proxyDetails;
    }

    @Override
    public List<ProxyDetail> getAllDetail(ProxyDetailSearchReq req) {
        Page<ProxyDetail> commodities = proxyDetailService.findAll(new Specification<ProxyDetail>(){
            @Override
            public Predicate toPredicate(Root<ProxyDetail> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getCreateBy())){
                    list.add(criteriaBuilder.equal(root.get("createBy").as(String.class), req.getCreateBy()));
                }
                if(!StringUtil.isEmpty(req.getOrderNo())){
                    list.add(criteriaBuilder.like(root.get("orderNo").as(String.class), "%"+req.getOrderNo()+"%"));
                }
                if(!StringUtil.isEmpty(req.getOrderPostalCode())){
                    list.add(criteriaBuilder.like(root.get("orderPostalCode").as(String.class), "%"+req.getOrderPostalCode()+"%"));
                }
                if(req.getType()!=-1){
                    list.add(criteriaBuilder.equal(root.get("type").as(Integer.class), req.getType()));
                }
                if(req.getMinMoney()!=-1){
                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("money").as(Integer.class), req.getMinMoney()));
                }
                if(req.getMaxMoney()!=-1){
                    list.add(criteriaBuilder.lessThan(root.get("money").as(Integer.class), req.getMaxMoney()));
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
        List<ProxyDetail> list=commodities.getContent();
        return list;
    }

    @Override
    public List<ProxyDetail> findProxyDteailCriteria(int page, int pageSize, ProxyDetailSearchReq req) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "createTime");
        Page<ProxyDetail> commodities = proxyDetailService.findAll(new Specification<ProxyDetail>(){
            @Override
            public Predicate toPredicate(Root<ProxyDetail> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getCreateBy())){
                    list.add(criteriaBuilder.equal(root.get("createBy").as(String.class), req.getCreateBy()));
                }
                if(!StringUtil.isEmpty(req.getOrderNo())){
                    list.add(criteriaBuilder.like(root.get("orderNo").as(String.class), "%"+req.getOrderNo()+"%"));
                }
                if(!StringUtil.isEmpty(req.getOrderPostalCode())){
                    list.add(criteriaBuilder.like(root.get("orderPostalCode").as(String.class), "%"+req.getOrderPostalCode()+"%"));
                }
                if(req.getType()!=-1){
                    list.add(criteriaBuilder.equal(root.get("type").as(Integer.class), req.getType()));
                }
                if(req.getMinMoney()!=-1){
                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("money").as(Integer.class), req.getMinMoney()));
                }
                if(req.getMaxMoney()!=-1){
                    list.add(criteriaBuilder.lessThan(root.get("money").as(Integer.class), req.getMaxMoney()));
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
        List<ProxyDetail> list=commodities.getContent();
        return list;
    }

}
