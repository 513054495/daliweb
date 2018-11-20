package com.code90.daliweb.server.impl;

import com.code90.daliweb.domain.WorkSchedule;
import com.code90.daliweb.request.learn.WorkScheduleSearchReq;
import com.code90.daliweb.server.WorkScheduleServer;
import com.code90.daliweb.service.WorkScheduleService;
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
 * 作业进度服务接口实现类
 * @author Ray Lin
 * @create 2018-10-11 22:37
 **/
@Service("workScheduleServer")
public class WorkScheduleServerImpl implements WorkScheduleServer {

    @Autowired
    private WorkScheduleService workScheduleService;

    @Override
    public void save(Object object) {
        workScheduleService.save((WorkSchedule)object);
    }

    @Override
    public void delete(Object object) {
        workScheduleService.delete((WorkSchedule)object);
    }

    @Override
    public Object getObjectById(String id) {
        return workScheduleService.getById(id);
    }

    @Override
    public WorkSchedule getAllByWorkIdAndCreateBy(String workId, String createBy) {
        return workScheduleService.getAllByWorkIdAndCreateBy(workId,createBy);
    }

    @Override
    public List<WorkSchedule> getAll(WorkScheduleSearchReq req) {
        Page<WorkSchedule> subjects = workScheduleService.findAll(new Specification<WorkSchedule>(){
            @Override
            public Predicate toPredicate(Root<WorkSchedule> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getWorkId())){
                    list.add(criteriaBuilder.equal(root.get("workId").as(String.class), req.getWorkId()));
                }
                if(req.getStatus()!=-1){
                    list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), req.getStatus()));
                }
                if(req.getAssess()!=-1){
                    list.add(criteriaBuilder.equal(root.get("assess").as(Integer.class),req.getAssess()));
                }
                if(!StringUtil.isEmpty(req.getCreateBy())){
                    list.add(criteriaBuilder.equal(root.get("createBy").as(Integer.class), req.getCreateBy()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },Pageable.unpaged());
        List<WorkSchedule> list=subjects.getContent();
        return list;
    }

    @Override
    public List<WorkSchedule> findWorkScheduleCriteria(int page, int pageSize, WorkScheduleSearchReq req) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "createTime");
        Page<WorkSchedule> subjects = workScheduleService.findAll(new Specification<WorkSchedule>(){
            @Override
            public Predicate toPredicate(Root<WorkSchedule> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getWorkId())){
                    list.add(criteriaBuilder.equal(root.get("workId").as(String.class), req.getWorkId()));
                }
                if(req.getStatus()!=-1){
                    list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), req.getStatus()));
                }
                if(req.getAssess()!=-1){
                    list.add(criteriaBuilder.equal(root.get("assess").as(Integer.class),req.getAssess()));
                }
                if(!StringUtil.isEmpty(req.getCreateBy())){
                    list.add(criteriaBuilder.equal(root.get("createBy").as(Integer.class), req.getCreateBy()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },pageable);
        List<WorkSchedule> list=subjects.getContent();
        return list;
    }

    @Override
    public List<WorkSchedule> getAllByWorkId(String id) {
        return workScheduleService.getAllByWorkId(id);
    }

}
