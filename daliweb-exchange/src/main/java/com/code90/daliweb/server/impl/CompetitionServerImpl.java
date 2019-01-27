package com.code90.daliweb.server.impl;

import com.code90.daliweb.domain.Competition;
import com.code90.daliweb.domain.LevelApplication;
import com.code90.daliweb.request.exchange.CompetitionSearchReq;
import com.code90.daliweb.server.CompetitionServer;
import com.code90.daliweb.service.CompetitionService;
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
 * 竞赛服务接口实现类
 * @author Ray Lin
 * @create 2018-10-18 1:05
 **/
@Service("competitionServer")
public class CompetitionServerImpl implements CompetitionServer {
    @Autowired
    private CompetitionService competitionService;
    @Override
    public void save(Object object) {
        competitionService.save((Competition)object);
    }

    @Override
    public void delete(Object object) {
        competitionService.delete((Competition)object);
    }

    @Override
    public Object getObjectById(String id) {
        return competitionService.getCompetitionById(id);
    }

    @Override
    public List<Competition> getAll(CompetitionSearchReq req) {
        Page<Competition> subjects = competitionService.findAll(new Specification<Competition>(){
            @Override
            public Predicate toPredicate(Root<Competition> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getName())){
                    list.add(criteriaBuilder.like(root.get("name").as(String.class), "%"+req.getName()+"%"));
                }
                if(req.getType()!=-1){
                    list.add(criteriaBuilder.equal(root.get("type").as(Integer.class), req.getType()));
                }
                if(req.getStatus()!=-1){
                    list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), req.getStatus()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },Pageable.unpaged());
        List<Competition> list=subjects.getContent();
        return list;
    }

    @Override
    public List<Competition> findCompetitionCriteria(int page, int pageSize, CompetitionSearchReq req) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "createTime");
        Page<Competition> subjects = competitionService.findAll(new Specification<Competition>(){
            @Override
            public Predicate toPredicate(Root<Competition> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getName())){
                    list.add(criteriaBuilder.like(root.get("name").as(String.class), "%"+req.getName()+"%"));
                }
                if(req.getType()!=-1){
                    list.add(criteriaBuilder.equal(root.get("type").as(Integer.class), req.getType()));
                }
                if(req.getStatus()!=-1){
                    list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), req.getStatus()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },pageable);
        List<Competition> list=subjects.getContent();
        return list;
    }

    @Override
    public List<Competition> getAll() {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        return competitionService.findAll(sort);
    }

    @Override
    public List<Competition> getAllStartCompetition() {
        return competitionService.getAllStartCompetition();
    }
}
