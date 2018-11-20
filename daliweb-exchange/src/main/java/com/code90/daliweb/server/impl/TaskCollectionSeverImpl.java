package com.code90.daliweb.server.impl;

import com.code90.daliweb.domain.TaskCollection;
import com.code90.daliweb.request.exchange.TaskCollectionSearchReq;
import com.code90.daliweb.server.TaskCollectionServer;
import com.code90.daliweb.service.TaskCollectionService;
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
 * 任务领取服务接口实现类
 * @author Ray Lin
 * @create 2018-10-03 16:27
 **/
@Service("taskCollectionServer")
public class TaskCollectionSeverImpl implements TaskCollectionServer {

    @Autowired
    private TaskCollectionService taskCollectionService;

    @Override
    public void save(Object object) {
        taskCollectionService.save((TaskCollection)object);
    }

    @Override
    public void delete(Object object) {
        taskCollectionService.delete((TaskCollection) object);
    }

    @Override
    public Object getObjectById(String id) {
        return taskCollectionService.getTaskCollectionById(id);
    }

    @Override
    public List<TaskCollection> getAll(TaskCollectionSearchReq req) {
        Page<TaskCollection> subjects = taskCollectionService.findAll(new Specification<TaskCollection>(){
            @Override
            public Predicate toPredicate(Root<TaskCollection> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getTaskId())){
                    list.add(criteriaBuilder.equal(root.get("taskId").as(String.class), req.getTaskId()));
                }
                if(!StringUtil.isEmpty(req.getCreateBy())){
                    list.add(criteriaBuilder.equal(root.get("createBy").as(String.class),req.getCreateBy()));
                }
                if(req.getStatus()!=-1){
                    list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), req.getStatus()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },Pageable.unpaged());
        List<TaskCollection> list=subjects.getContent();
        return list;
    }

    @Override
    public List<TaskCollection> findTaskCollectionCriteria(int page, int pageSize, TaskCollectionSearchReq req) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "createTime");
        Page<TaskCollection> subjects = taskCollectionService.findAll(new Specification<TaskCollection>(){
            @Override
            public Predicate toPredicate(Root<TaskCollection> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getTaskId())){
                    list.add(criteriaBuilder.equal(root.get("taskId").as(String.class), req.getTaskId()));
                }
                if(!StringUtil.isEmpty(req.getCreateBy())){
                    list.add(criteriaBuilder.equal(root.get("createBy").as(String.class),req.getCreateBy()));
                }
                if(req.getStatus()!=-1){
                    list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), req.getStatus()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },pageable);
        List<TaskCollection> list=subjects.getContent();
        return list;
    }

    @Override
    public int getCountByTaskId(String taskId) {
        return taskCollectionService.getCountByTaskId(taskId);
    }

    @Override
    public TaskCollection getAllByTaskIdAndCreateBy(String id, String userCode) {
        return taskCollectionService.getAllByTaskIdAndCreateBy(id,userCode);
    }

    @Override
    public List<TaskCollection> getTaskControllerByTaskId(String id) {
        return taskCollectionService.getTaskControllerByTaskId(id);
    }
}
