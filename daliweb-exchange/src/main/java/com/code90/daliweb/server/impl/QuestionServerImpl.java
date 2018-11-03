package com.code90.daliweb.server.impl;

import com.code90.daliweb.domain.LearnContent;
import com.code90.daliweb.domain.Question;
import com.code90.daliweb.request.exchange.QuestionSearchReq;
import com.code90.daliweb.server.QuestionServer;
import com.code90.daliweb.service.QuestionService;
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
 * 问题服务实现类
 * @author Ray Lin
 * @create 2018-10-01 23:42
 **/
@Service("questionServer")
public class QuestionServerImpl implements QuestionServer {

    @Autowired
    private QuestionService questionService;

    @Override
    public void save(Object object) {
        questionService.save((Question)object);
    }

    @Override
    public void delete(Object object) {
        questionService.delete((Question)object);
    }

    @Override
    public Object getObjectById(String id) {
        return questionService.getQuestionById(id);
    }

    @Override
    public List<Question> getAll(QuestionSearchReq req) {
        Page<Question> subjects = questionService.findAll(new Specification<Question>(){
            @Override
            public Predicate toPredicate(Root<Question> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getTitle())){
                    list.add(criteriaBuilder.like(root.get("title").as(String.class), "%"+req.getTitle()+"%"));
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
        List<Question> list=subjects.getContent();
        return list;
    }

    @Override
    public List<Question> findQuestionCriteria(int page, int pageSize, QuestionSearchReq req) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "createTime");
        Page<Question> subjects = questionService.findAll(new Specification<Question>(){
            @Override
            public Predicate toPredicate(Root<Question> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getTitle())){
                    list.add(criteriaBuilder.like(root.get("title").as(String.class), "%"+req.getTitle()+"%"));
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
        List<Question> list=subjects.getContent();
        return list;
    }
}
