package com.code90.daliweb.server.impl;


import com.code90.daliweb.domain.Subject;
import com.code90.daliweb.request.learn.SubjectSearchReq;
import com.code90.daliweb.server.SubjectServer;
import com.code90.daliweb.service.SubjectService;
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
 * 题目服务实现类
 * @author Ray
 * @create 2017-05-21 23:57
 */
@Service("subjectServer")
public class SubjectServerImpl implements SubjectServer {

    @Autowired
    private SubjectService subjectService;

    @Override
    public void save(Object subject) {
        subjectService.save((Subject) subject);
    }

    @Override
    public void delete(Object object) {
        subjectService.delete((Subject)object);
    }

    @Override
    public Object getObjectById(String id) {
        return subjectService.getSubjectById(Integer.parseInt(id));
    }

    @Override
    public List<Subject> getAll(SubjectSearchReq req) {
        Page<Subject> subjects = subjectService.findAll(new Specification<Subject>(){
            @Override
            public Predicate toPredicate(Root<Subject> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getSubjectName())){
                    list.add(criteriaBuilder.like(root.get("subjectName").as(String.class), "%"+req.getSubjectName()+"%"));
                }
                if(req.getType()!=-1){
                    list.add(criteriaBuilder.equal(root.get("type").as(Integer.class), req.getType()));
                }
                if(req.getSubjectType()!=-1){
                    list.add(criteriaBuilder.equal(root.get("subjectType").as(Integer.class), req.getSubjectType()));
                }
                if(req.getPoint()!=-1){
                    list.add(criteriaBuilder.equal(root.get("point").as(Integer.class), req.getPoint()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },Pageable.unpaged());
        List<Subject> list=subjects.getContent();
        return list;
    }

    @Override
    public List<Subject> findSubjectCriteria(int page, int pageSize, SubjectSearchReq req) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.ASC, "id");
        Page<Subject> subjects = subjectService.findAll(new Specification<Subject>(){
            @Override
            public Predicate toPredicate(Root<Subject> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getSubjectName())){
                    list.add(criteriaBuilder.like(root.get("subjectName").as(String.class), "%"+req.getSubjectName()+"%"));
                }
                if(req.getType()!=-1){
                    list.add(criteriaBuilder.equal(root.get("type").as(Integer.class), req.getType()));
                }
                if(req.getSubjectType()!=-1){
                    list.add(criteriaBuilder.equal(root.get("subjectType").as(Integer.class), req.getSubjectType()));
                }
                if(req.getPoint()!=-1){
                    list.add(criteriaBuilder.equal(root.get("point").as(Integer.class), req.getPoint()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },pageable);
        List<Subject> list=subjects.getContent();
        return list;
    }

    @Override
    public void delete(Subject subject) {
        subjectService.delete(subject);
    }

    @Override
    public int getMaxSubject() {
       List<Subject> subjects=subjectService.findAll();
       if(subjects.size()==0){
           return 0;
       }else{
           return subjectService.getMaxSubject();
       }
    }

    @Override
    public List<Subject> getAllByAnwser() {
        return subjectService.getAllByAnwser();
    }

    @Override
    public int getMixSubjectByAnswer() {
        List<Subject> subjects=subjectService.findAll();
        if(subjects.size()==0){
            return 0;
        }else{
            return subjectService.getMixSubjectBynswer();
        }
    }

    @Override
    public List<Subject> getAllByRandom() {
        return subjectService.getAllByRandom();
    }

    @Override
    public int getMaxSubjectByExam() {
        List<Subject> subjects=subjectService.findAll();
        if(subjects.size()==0){
            return 0;
        }else{
            return subjectService.getMaxSubjectByExam();
        }
    }
}
