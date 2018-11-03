package com.code90.daliweb.server.impl;

import com.code90.daliweb.domain.Exam;
import com.code90.daliweb.domain.ExamSchedule;
import com.code90.daliweb.request.learn.ExamScheduleSearchReq;
import com.code90.daliweb.server.ExamScheduleServer;
import com.code90.daliweb.service.ExamScheduleService;
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
 * 考试进度服务接口实现类
 * @author Ray Lin
 * @create 2018-10-07 16:31
 **/
@Service("examScheduleServer")
public class ExamScheduleServerImpl implements ExamScheduleServer {
    @Autowired
    private ExamScheduleService examScheduleService;

    @Override
    public void save(Object object) {
      examScheduleService.save((ExamSchedule)object);
    }

    @Override
    public void delete(Object object) {
        examScheduleService.delete((ExamSchedule)object);
    }

    @Override
    public Object getObjectById(String id) {
        return examScheduleService.getExamSchedleById(id);
    }

    @Override
    public List<ExamSchedule> getAll(ExamScheduleSearchReq req) {
        Page<ExamSchedule> subjects = examScheduleService.findAll(new Specification<ExamSchedule>(){
            @Override
            public Predicate toPredicate(Root<ExamSchedule> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getExamId())){
                    list.add(criteriaBuilder.equal(root.get("examId").as(String.class), req.getExamId()));
                }
                if(!StringUtil.isEmpty(req.getCreateBy())){
                    list.add(criteriaBuilder.equal(root.get("createBy").as(String.class), req.getCreateBy()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },Pageable.unpaged());
        List<ExamSchedule> list=subjects.getContent();
        return list;
    }

    @Override
    public List<ExamSchedule> findExamScheduleCriteria(int page, int pageSize, ExamScheduleSearchReq req) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "createTime");
        Page<ExamSchedule> subjects = examScheduleService.findAll(new Specification<ExamSchedule>(){
            @Override
            public Predicate toPredicate(Root<ExamSchedule> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getExamId())){
                    list.add(criteriaBuilder.equal(root.get("examId").as(String.class), req.getExamId()));
                }
                if(!StringUtil.isEmpty(req.getCreateBy())){
                    list.add(criteriaBuilder.equal(root.get("createBy").as(String.class), req.getCreateBy()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },pageable);
        List<ExamSchedule> list=subjects.getContent();
        return list;
    }

    @Override
    public List<ExamSchedule> getAllByExanId(String id) {
        return examScheduleService.getAllByExanId(id);
    }

    @Override
    public ExamSchedule getAllByExanIdAndCreateBy(String id, String userCode) {
        return examScheduleService.getAllByExanIdAndCreateBy(id,userCode);
    }
}
