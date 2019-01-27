package com.code90.daliweb.server.impl;

import com.code90.daliweb.domain.Work;
import com.code90.daliweb.request.learn.WorkSearchReq;
import com.code90.daliweb.server.WorkServer;
import com.code90.daliweb.service.WorkService;
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
 * 作业服务接口实现类
 * @author Ray Lin
 * @create 2018-10-11 21:57
 **/
@Service("workServer")
public class WorkServerImpl implements WorkServer {

    @Autowired
    private WorkService workService;

    @Override
    public void save(Object object) {
        workService.save((Work)object);
    }

    @Override
    public void delete(Object object) {
        workService.delete((Work)object);
    }

    @Override
    public Object getObjectById(String id) {
        return workService.getById(id);
    }

    @Override
    public List<Work> getAllStartWork() {
        return workService.getAllStartWork();
    }

    @Override
    public List<Work> getAll(WorkSearchReq req) {
        Page<Work> subjects = workService.findAll(new Specification<Work>(){
            @Override
            public Predicate toPredicate(Root<Work> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getTitle())){
                    list.add(criteriaBuilder.like(root.get("title").as(String.class), "%"+req.getTitle()+"%"));
                }
                if(req.getStatus()!=-1){
                    list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), req.getStatus()));
                }
                if(!StringUtil.isEmpty(req.getCreateBy())){
                    list.add(criteriaBuilder.equal(root.get("createBy").as(Integer.class), req.getCreateBy()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },Pageable.unpaged());
        List<Work> list=subjects.getContent();
        return list;
    }

    @Override
    public List<Work> findWorkCriteria(int page, int pageSize, WorkSearchReq req) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "createTime");
        Page<Work> subjects = workService.findAll(new Specification<Work>(){
            @Override
            public Predicate toPredicate(Root<Work> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getTitle())){
                    list.add(criteriaBuilder.like(root.get("title").as(String.class), "%"+req.getTitle()+"%"));
                }
                if(req.getStatus()!=-1){
                    list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), req.getStatus()));
                }
                if(!StringUtil.isEmpty(req.getCreateBy())){
                    list.add(criteriaBuilder.equal(root.get("createBy").as(Integer.class), req.getCreateBy()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },pageable);
        List<Work> list=subjects.getContent();
        return list;
    }

    @Override
    public List<Work> getPublishWorks() {
        List<Work> works=workService.getPublishWorks();
        return works;
    }

    @Override
    public List<Work> getAllWork() {
        return workService.findAll();
    }
}
