package com.code90.daliweb.server.impl;

import com.code90.daliweb.domain.WorkDetail;
import com.code90.daliweb.request.learn.WorkDetailSearchReq;
import com.code90.daliweb.server.WorkDetailServer;
import com.code90.daliweb.service.WorkDetailService;
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
 * 作业详情服务接口实现类
 * @author Ray Lin
 * @create 2018-10-11 23:00
 **/
@Service("workDetailServer")
public class WorkDetailServerImpl implements WorkDetailServer {

    @Autowired
    private WorkDetailService workDetailService;

    @Override
    public void save(Object object) {
        workDetailService.save((WorkDetail)object);
    }

    @Override
    public void delete(Object object) {
        workDetailService.delete((WorkDetail)object);
    }

    @Override
    public Object getObjectById(String id) {
        return workDetailService.getById(id);
    }

    @Override
    public List<WorkDetail> getAllByWorkScheduleIdAndCreateBy(String workScheduleId, String createBy) {
        return workDetailService.getAllByWorkScheduleIdAndCreateBy(workScheduleId,createBy);
    }

    @Override
    public List<WorkDetail> getAll(WorkDetailSearchReq req) {
        Page<WorkDetail> subjects = workDetailService.findAll(new Specification<WorkDetail>(){
            @Override
            public Predicate toPredicate(Root<WorkDetail> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getWorkScheduleId())){
                    list.add(criteriaBuilder.equal(root.get("workScheduleId").as(String.class), req.getWorkScheduleId()));
                }
                if(req.getSubjectId()!=-1){
                    list.add(criteriaBuilder.equal(root.get("subjectId").as(Integer.class), req.getSubjectId()));
                }
                if(!StringUtil.isEmpty(req.getCreateBy())){
                    list.add(criteriaBuilder.equal(root.get("createBy").as(Integer.class), req.getCreateBy()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },Pageable.unpaged());
        List<WorkDetail> list=subjects.getContent();
        return list;
    }

    @Override
    public List<WorkDetail> findWorkDetailCriteria(int page, int pageSize, WorkDetailSearchReq req) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "createTime");
        Page<WorkDetail> subjects = workDetailService.findAll(new Specification<WorkDetail>(){
            @Override
            public Predicate toPredicate(Root<WorkDetail> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getWorkScheduleId())){
                    list.add(criteriaBuilder.equal(root.get("workScheduleId").as(String.class), req.getWorkScheduleId()));
                }
                if(req.getSubjectId()!=-1){
                    list.add(criteriaBuilder.equal(root.get("subjectId").as(Integer.class), req.getSubjectId()));
                }
                if(!StringUtil.isEmpty(req.getCreateBy())){
                    list.add(criteriaBuilder.equal(root.get("createBy").as(Integer.class), req.getCreateBy()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },pageable);
        List<WorkDetail> list=subjects.getContent();
        return list;
    }

    @Override
    public WorkDetail getAllByWorkScheduleIdAndCreateByAndSubjectId(String id, String userCode, int subjectId) {
        return workDetailService.getAllByWorkScheduleIdAndCreateByAndSubjectId(id,userCode,subjectId);
    }

    @Override
    public List<WorkDetail> getAllByWorkScheduleId(String id) {
        return workDetailService.getAllByWorkScheduleId(id);
    }
}
