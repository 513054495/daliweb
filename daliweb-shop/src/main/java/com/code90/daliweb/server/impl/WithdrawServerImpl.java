package com.code90.daliweb.server.impl;

import com.code90.daliweb.domain.ProxyDetail;
import com.code90.daliweb.domain.Withdraw;
import com.code90.daliweb.request.shop.WithdrawSearchReq;
import com.code90.daliweb.server.WithdrawServer;
import com.code90.daliweb.service.WithdrawService;
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
import java.util.List;

/**
 * 提现服务接口实现类
 * @author Ray Lin
 * @create 2018-10-28 18:53
 **/
@Service("withdrawServer")
public class WithdrawServerImpl implements WithdrawServer {
    @Autowired
    private WithdrawService withdrawService;

    @Override
    public void save(Object object) {
        withdrawService.save((Withdraw)object);
    }

    @Override
    public void delete(Object object) {

    }

    @Override
    public Object getObjectById(String id) {
        return withdrawService.getObjectById(id);
    }

    @Override
    public List<Withdraw> getAll(WithdrawSearchReq req) {
        Page<Withdraw> commodities = withdrawService.findAll(new Specification<Withdraw>(){
            @Override
            public Predicate toPredicate(Root<Withdraw> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getCreateBy())){
                    list.add(criteriaBuilder.equal(root.get("createBy").as(String.class), req.getCreateBy()));
                }
                if(req.getStatus()!=-1){
                    list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), req.getStatus()));
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
        List<Withdraw> list=commodities.getContent();
        return list;
    }

    @Override
    public List<Withdraw> findWithdrawCriteria(int page, int pageSize, WithdrawSearchReq req) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "createTime");
        Page<Withdraw> commodities = withdrawService.findAll(new Specification<Withdraw>(){
            @Override
            public Predicate toPredicate(Root<Withdraw> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getCreateBy())){
                    list.add(criteriaBuilder.equal(root.get("createBy").as(String.class), req.getCreateBy()));
                }
                if(req.getStatus()!=-1){
                    list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), req.getStatus()));
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
        List<Withdraw> list=commodities.getContent();
        return list;
    }

    @Override
    public List<Withdraw> getWithdrawByUserCode(String userCode) {
        return withdrawService.getWithdrawByUserCode(userCode);
    }

    @Override
    public double getAllMoneyByUserCode(String userCode) {
        return withdrawService.getAllMoneyByUserCode(userCode);
    }

    @Override
    public int getWithdrawByStatus(int i) {
        return withdrawService.getWithdrawByStatus(i);
    }

    @Override
    public double getAllMoney() {
        return withdrawService.getAllMoney();
    }

    @Override
    public List<Object[]> getWithdrawByYear(int status) {
        List<Object[]> withdraws=null;
        if(status==0){
            withdraws = withdrawService.getWithdrawByMonth();
        }else {
            withdraws = withdrawService.getWithdrawByMonth(status);
        }
        return withdraws;
    }
}
