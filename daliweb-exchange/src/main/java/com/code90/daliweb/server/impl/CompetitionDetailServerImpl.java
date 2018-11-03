package com.code90.daliweb.server.impl;

import com.code90.daliweb.domain.Competition;
import com.code90.daliweb.domain.CompetitionDetail;
import com.code90.daliweb.request.exchange.CompetitionDetailSearchReq;
import com.code90.daliweb.server.CompetitionDetailServer;
import com.code90.daliweb.server.CompetitionServer;
import com.code90.daliweb.service.CompetitionDetailService;
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
 * 竞赛详情服务接口实现类
 * @author Ray Lin
 * @create 2018-10-18 1:05
 **/
@Service("competitionDetailServer")
public class CompetitionDetailServerImpl implements CompetitionDetailServer {
    @Autowired
    private CompetitionDetailService competitionDetailService;
    @Override
    public void save(Object object) {
        competitionDetailService.save((CompetitionDetail)object);
    }

    @Override
    public void delete(Object object) {
        competitionDetailService.delete((CompetitionDetail)object);
    }

    @Override
    public Object getObjectById(String id) {
        return competitionDetailService.getCompetitionDetailById(id);
    }

    @Override
    public List<CompetitionDetail> getAll(CompetitionDetailSearchReq req) {
        Page<CompetitionDetail> subjects = competitionDetailService.findAll(new Specification<CompetitionDetail>(){
            @Override
            public Predicate toPredicate(Root<CompetitionDetail> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getName())){
                    list.add(criteriaBuilder.like(root.get("name").as(String.class), "%"+req.getName()+"%"));
                }
                if(!StringUtil.isEmpty(req.getCompetitionId())){
                    list.add(criteriaBuilder.equal(root.get("competitionId").as(String.class), req.getCompetitionId()));
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
        List<CompetitionDetail> list=subjects.getContent();
        return list;
    }

    @Override
    public List<CompetitionDetail> findCompetitionDetailCriteria(int page, int pageSize, CompetitionDetailSearchReq req) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "createTime");
        Page<CompetitionDetail> subjects = competitionDetailService.findAll(new Specification<CompetitionDetail>(){
            @Override
            public Predicate toPredicate(Root<CompetitionDetail> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getName())){
                    list.add(criteriaBuilder.like(root.get("name").as(String.class), "%"+req.getName()+"%"));
                }
                if(!StringUtil.isEmpty(req.getCompetitionId())){
                    list.add(criteriaBuilder.equal(root.get("competitionId").as(String.class), req.getCompetitionId()));
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
        List<CompetitionDetail> list=subjects.getContent();
        return list;
    }

    @Override
    public CompetitionDetail getByCompetitionIdAndUserCode(String id, String userCode) {
        return competitionDetailService.getByCompetitionIdAndUserCode(id,userCode);
    }

    @Override
    public List<CompetitionDetail> getDetailByCompetitionId(String id) {
        return competitionDetailService.getDetailByCompetitionId(id);
    }
}
