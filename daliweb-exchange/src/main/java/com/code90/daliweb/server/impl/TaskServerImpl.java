package com.code90.daliweb.server.impl;

import com.code90.daliweb.domain.Task;
import com.code90.daliweb.request.exchange.TaskSearchReq;
import com.code90.daliweb.server.TaskServer;
import com.code90.daliweb.service.TaskService;
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
 * 任务服务实现类
 * @author Ray Lin
 * @create 2018-10-03 1:55
 **/
@Service("taskServer")
public class TaskServerImpl implements TaskServer {

    @Autowired
    private TaskService taskService;

    @Override
    public void save(Object object) {
        taskService.save((Task)object);
    }

    @Override
    public void delete(Object object) {
        taskService.delete((Task)object);
    }

    @Override
    public Object getObjectById(String id) {
        return taskService.getTaskById(id);
    }

    @Override
    public List<Task> getAll(TaskSearchReq req) {
        Page<Task> subjects = taskService.findAll(new Specification<Task>(){
            @Override
            public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getTitle())){
                    list.add(criteriaBuilder.like(root.get("title").as(String.class), "%"+req.getTitle()+"%"));
                }
                if(req.getStatus()!=-1){
                    list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), req.getStatus()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },Pageable.unpaged());
        List<Task> list=subjects.getContent();
        return list;
    }

    @Override
    public List<Task> findTaskCriteria(int page, int pageSize, TaskSearchReq req) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "createTime");
        Page<Task> subjects = taskService.findAll(new Specification<Task>(){
            @Override
            public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getTitle())){
                    list.add(criteriaBuilder.like(root.get("title").as(String.class), "%"+req.getTitle()+"%"));
                }
                if(req.getStatus()!=-1){
                    list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), req.getStatus()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },pageable);
        List<Task> list=subjects.getContent();
        return list;
    }

    @Override
    public List<Task> getAllStartTask() {
        return taskService.getAllStartTask();
    }

}
