package com.code90.daliweb.server.impl;

import com.code90.daliweb.domain.LearnContent;
import com.code90.daliweb.request.learn.LearnContentSearchReq;
import com.code90.daliweb.server.LearnContentServer;
import com.code90.daliweb.service.LearnContentService;
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
 * 学习内容服务接口实现类
 * @author Ray Lin
 * @create 2018-10-01 16:19
 **/
@Service("learnContentServer")
public class LearnContentServerImpl implements LearnContentServer {

    @Autowired
    private LearnContentService learnContentService;

    @Override
    public void save(Object object) {
        learnContentService.save((LearnContent) object);
    }

    @Override
    public void delete(Object object) {
        learnContentService.delete((LearnContent) object);
    }

    @Override
    public Object getObjectById(String id) {
        return learnContentService.getLearnContentById(id);
    }

    @Override
    public int getMaxLevel() {
        return learnContentService.getMaxLevel();
    }

    @Override
    public List<LearnContent> getAll(LearnContentSearchReq req) {
        Page<LearnContent> subjects = learnContentService.findAll(new Specification<LearnContent>(){
            @Override
            public Predicate toPredicate(Root<LearnContent> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getTitle())){
                    list.add(criteriaBuilder.like(root.get("title").as(String.class), "%"+req.getTitle()+"%"));
                }
                if(!StringUtil.isEmpty(req.getAuthor())){
                    list.add(criteriaBuilder.like(root.get("author").as(String.class), "%"+req.getAuthor()+"%"));
                }
                if(!StringUtil.isEmpty(req.getTopicId())){
                    list.add(criteriaBuilder.equal(root.get("topicId").as(String.class), req.getTopicId()));
                }
                if(req.getStatus()!=-1){
                    list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), req.getStatus()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },Pageable.unpaged());
        List<LearnContent> list=subjects.getContent();
        return list;
    }

    @Override
    public List<LearnContent> findLearnContentCriteria(int page, int pageSize, LearnContentSearchReq req) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "level");
        Page<LearnContent> subjects = learnContentService.findAll(new Specification<LearnContent>(){
            @Override
            public Predicate toPredicate(Root<LearnContent> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getTitle())){
                    list.add(criteriaBuilder.like(root.get("title").as(String.class), "%"+req.getTitle()+"%"));
                }
                if(!StringUtil.isEmpty(req.getAuthor())){
                    list.add(criteriaBuilder.like(root.get("author").as(String.class), "%"+req.getAuthor()+"%"));
                }
                if(!StringUtil.isEmpty(req.getTopicId())){
                    list.add(criteriaBuilder.equal(root.get("topicId").as(String.class), req.getTopicId()));
                }
                if(req.getStatus()!=-1){
                    list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), req.getStatus()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },pageable);
        List<LearnContent> list=subjects.getContent();
        return list;
    }
}
