package com.code90.daliweb.server.impl;

import com.code90.daliweb.domain.Registration;
import com.code90.daliweb.domain.RegistrationDetail;
import com.code90.daliweb.request.exchange.RegistrationDetailSearchReq;
import com.code90.daliweb.request.exchange.RegistrationSearchReq;
import com.code90.daliweb.server.RegistrationServer;
import com.code90.daliweb.service.RegistrationDetailService;
import com.code90.daliweb.service.RegistrationService;
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
 * 报名服务接口实现类
 * @author Ray Lin
 * @create 2018-10-30 23:23
 **/
@Service("registrationServer")
public class RegistrationServerImpl implements RegistrationServer {
    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private RegistrationDetailService registrationDetailService;

    @Override
    public void save(Object object) {
        registrationService.save((Registration)object);
    }

    @Override
    public void delete(Object object) {
        registrationService.delete((Registration)object);
    }

    @Override
    public Object getObjectById(String id) {
        return registrationService.getRegistrationById(id);
    }

    @Override
    public void saveDetail(RegistrationDetail registrationDetail) {
        registrationDetailService.save(registrationDetail);
    }

    @Override
    public RegistrationDetail getRegistrationDetailById(String id) {
        return registrationDetailService.getRegistrationDetailById(id);
    }

    @Override
    public void deleteRegistrationDetail(RegistrationDetail registrationDetail) {
        registrationDetailService.delete(registrationDetail);
    }

    @Override
    public List<Registration> getAll(RegistrationSearchReq req) {
        Page<Registration> subjects = registrationService.findAll(new Specification<Registration>(){
            @Override
            public Predicate toPredicate(Root<Registration> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getName())){
                    list.add(criteriaBuilder.like(root.get("name").as(String.class), "%"+req.getName()+"%"));
                }
                if(!StringUtil.isEmpty(req.getArea())){
                    list.add(criteriaBuilder.like(root.get("area").as(String.class), "%"+req.getArea()+"%"));
                }
                if(!StringUtil.isEmpty(req.getCreateBy())){
                    list.add(criteriaBuilder.equal(root.get("createBy").as(String.class), req.getCreateBy()));
                }
                if(req.getStatus()!=-1){
                    list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), req.getStatus()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },Pageable.unpaged());
        List<Registration> list=subjects.getContent();
        return list;
    }

    @Override
    public List<Registration> findRegistrationCriteria(int page, int pageSize, RegistrationSearchReq req) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "createTime");
        Page<Registration> subjects = registrationService.findAll(new Specification<Registration>(){
            @Override
            public Predicate toPredicate(Root<Registration> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getName())){
                    list.add(criteriaBuilder.like(root.get("name").as(String.class), "%"+req.getName()+"%"));
                }
                if(!StringUtil.isEmpty(req.getArea())){
                    list.add(criteriaBuilder.like(root.get("area").as(String.class), "%"+req.getArea()+"%"));
                }
                if(!StringUtil.isEmpty(req.getCreateBy())){
                    list.add(criteriaBuilder.equal(root.get("createBy").as(String.class), req.getCreateBy()));
                }
                if(req.getStatus()!=-1){
                    list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), req.getStatus()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },pageable);
        List<Registration> list=subjects.getContent();
        return list;
    }

    @Override
    public List<RegistrationDetail> getAllDetail(RegistrationDetailSearchReq req) {
        Page<RegistrationDetail> subjects = registrationDetailService.findAll(new Specification<RegistrationDetail>(){
            @Override
            public Predicate toPredicate(Root<RegistrationDetail> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getName())){
                    list.add(criteriaBuilder.like(root.get("name").as(String.class), "%"+req.getName()+"%"));
                }
                if(!StringUtil.isEmpty(req.getRegistrationId())){
                    list.add(criteriaBuilder.equal(root.get("registrationId").as(String.class), req.getRegistrationId()));
                }
                if(!StringUtil.isEmpty(req.getCreateBy())){
                    list.add(criteriaBuilder.equal(root.get("createBy").as(String.class), req.getCreateBy()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },Pageable.unpaged());
        List<RegistrationDetail> list=subjects.getContent();
        return list;
    }

    @Override
    public List<RegistrationDetail> findRegistrationDetailCriteria(int page, int pageSize, RegistrationDetailSearchReq req) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "createTime");
        Page<RegistrationDetail> subjects = registrationDetailService.findAll(new Specification<RegistrationDetail>(){
            @Override
            public Predicate toPredicate(Root<RegistrationDetail> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getName())){
                    list.add(criteriaBuilder.like(root.get("name").as(String.class), "%"+req.getName()+"%"));
                }
                if(!StringUtil.isEmpty(req.getRegistrationId())){
                    list.add(criteriaBuilder.equal(root.get("registrationId").as(String.class), req.getRegistrationId()));
                }
                if(!StringUtil.isEmpty(req.getCreateBy())){
                    list.add(criteriaBuilder.equal(root.get("createBy").as(String.class), req.getCreateBy()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },pageable);
        List<RegistrationDetail> list=subjects.getContent();
        return list;
    }

    @Override
    public List<Registration> getAllPublishRegistration() {
        return registrationService.getAllPublishRegistration();
    }

    @Override
    public RegistrationDetail getRegistrationDetailByIdAndCreateBy(String id, String userCode) {
        return registrationDetailService.getRegistrationDetailByIdAndCreateBy(id,userCode);
    }
}
