package com.code90.daliweb.server.impl;

import com.code90.daliweb.domain.Exam;
import com.code90.daliweb.domain.LearnContent;
import com.code90.daliweb.request.learn.ExamSearchReq;
import com.code90.daliweb.server.ExamServer;
import com.code90.daliweb.service.ExamService;
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
 * 考试服务接口实现类
 * @author Ray Lin
 * @create 2018-10-06 0:44
 **/
@Service("examServer")
public class ExamServerImpl implements ExamServer {

    @Autowired
    private ExamService examService;

    @Override
    public void save(Object object) {
        examService.save((Exam)object);
    }

    @Override
    public void delete(Object object) {
        examService.delete((Exam)object);
    }

    @Override
    public Object getObjectById(String id) {
        return examService.getExamById(id);
    }

    @Override
    public List<Exam> getAll(ExamSearchReq req) {
        Page<Exam> subjects = examService.findAll(new Specification<Exam>(){
            @Override
            public Predicate toPredicate(Root<Exam> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getTitle())){
                    list.add(criteriaBuilder.like(root.get("title").as(String.class), "%"+req.getTitle()+"%"));
                }
                if(req.getStatus()!=-1){
                    list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), req.getStatus()));
                }
                if(req.getType()!=-1){
                    list.add(criteriaBuilder.equal(root.get("type").as(Integer.class), req.getType()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },Pageable.unpaged());
        List<Exam> list=subjects.getContent();
        return list;
    }

    @Override
    public List<Exam> findExamCriteria(int page, int pageSize, ExamSearchReq req) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "createTime");
        Page<Exam> subjects = examService.findAll(new Specification<Exam>(){
            @Override
            public Predicate toPredicate(Root<Exam> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getTitle())){
                    list.add(criteriaBuilder.like(root.get("title").as(String.class), "%"+req.getTitle()+"%"));
                }
                if(req.getStatus()!=-1){
                    list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), req.getStatus()));
                }
                if(req.getType()!=-1){
                    list.add(criteriaBuilder.equal(root.get("type").as(Integer.class), req.getType()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },pageable);
        List<Exam> list=subjects.getContent();
        return list;
    }

    @Override
    public List<Exam> getAll() {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        return examService.findAll(sort);
    }

}
