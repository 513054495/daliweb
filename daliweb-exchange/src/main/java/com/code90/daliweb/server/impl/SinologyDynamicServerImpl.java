package com.code90.daliweb.server.impl;

import com.code90.daliweb.domain.SinologyDynamic;
import com.code90.daliweb.request.exchange.SinologyDynamicSearchReq;
import com.code90.daliweb.server.SinologyDynamicServer;
import com.code90.daliweb.service.SinologyDynamicService;
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
 * 动态接口实现类
 * @author Ray Lin
 * @create 2019-01-13 22:46
 **/
@Service("sinologyDynamicServer")
public class SinologyDynamicServerImpl implements SinologyDynamicServer {

    @Autowired
    private SinologyDynamicService sinologyDynamicService;

    @Override
    public void save(Object object) {
        sinologyDynamicService.save((SinologyDynamic)object);
    }

    @Override
    public void delete(Object object) {
        sinologyDynamicService.delete((SinologyDynamic)object);
    }

    @Override
    public Object getObjectById(String id) {
        return sinologyDynamicService.getObjectById(id);
    }

    @Override
    public List<SinologyDynamic> getAll(SinologyDynamicSearchReq req) {
        Page<SinologyDynamic> sinologyDynamics = sinologyDynamicService.findAll(new Specification<SinologyDynamic>(){
            @Override
            public Predicate toPredicate(Root<SinologyDynamic> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getTitle())){
                    list.add(criteriaBuilder.like(root.get("title").as(String.class), "%"+req.getTitle()+"%"));
                }
                if(req.getType()!=-1){
                    list.add(criteriaBuilder.equal(root.get("type").as(Integer.class), req.getType()));
                }
                if(req.getSource()!=-1){
                    list.add(criteriaBuilder.equal(root.get("source").as(Integer.class), req.getSource()));
                }
                if(req.getStatus()!=-1){
                    list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), req.getStatus()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },Pageable.unpaged());
        List<SinologyDynamic> list=sinologyDynamics.getContent();
        return list;
    }

    @Override
    public List<SinologyDynamic> findSinologyDynamicCriteria(int page, int pageSize, SinologyDynamicSearchReq req) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "createTime");
        Page<SinologyDynamic> sinologyDynamics = sinologyDynamicService.findAll(new Specification<SinologyDynamic>(){
            @Override
            public Predicate toPredicate(Root<SinologyDynamic> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getTitle())){
                    list.add(criteriaBuilder.like(root.get("title").as(String.class), "%"+req.getTitle()+"%"));
                }
                if(req.getType()!=-1){
                    list.add(criteriaBuilder.equal(root.get("type").as(Integer.class), req.getType()));
                }
                if(req.getSource()!=-1){
                    list.add(criteriaBuilder.equal(root.get("source").as(Integer.class), req.getSource()));
                }
                if(req.getStatus()!=-1){
                    list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), req.getStatus()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },pageable);
        List<SinologyDynamic> list=sinologyDynamics.getContent();
        return list;
    }
}
