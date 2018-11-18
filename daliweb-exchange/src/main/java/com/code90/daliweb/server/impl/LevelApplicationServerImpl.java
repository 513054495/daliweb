package com.code90.daliweb.server.impl;

import com.code90.daliweb.domain.LevelApplication;
import com.code90.daliweb.request.exchange.LevelApplicationSearchReq;
import com.code90.daliweb.server.LevelApplicationServer;
import com.code90.daliweb.service.LevelApplicationService;
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
 * 考级申请服务接口实现类
 * @author Ray Lin
 * @create 2018-10-16 23:29
 **/
@Service("levelApplicationServer")
public class LevelApplicationServerImpl implements LevelApplicationServer {

    @Autowired
    private LevelApplicationService levelApplicationService;

    @Override
    public void save(Object object) {
        levelApplicationService.save((LevelApplication)object);
    }

    @Override
    public void delete(Object object) {
        levelApplicationService.delete((LevelApplication)object);
    }

    @Override
    public Object getObjectById(String id) {
        return levelApplicationService.getObjectById(id);
    }

    @Override
    public List<LevelApplication> getAll(LevelApplicationSearchReq req) {
        Page<LevelApplication> subjects = levelApplicationService.findAll(new Specification<LevelApplication>(){
            @Override
            public Predicate toPredicate(Root<LevelApplication> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getName())){
                    list.add(criteriaBuilder.like(root.get("name").as(String.class), "%"+req.getName()+"%"));
                }
                if(!StringUtil.isEmpty(req.getPhone())){
                    list.add(criteriaBuilder.like(root.get("phone").as(String.class), "%"+req.getPhone()+"%"));
                }
                if(!StringUtil.isEmpty(req.getCreateBy())){
                    list.add(criteriaBuilder.equal(root.get("createBy").as(String.class), req.getCreateBy()));
                }
                if(req.getLevel()!=-1){
                    list.add(criteriaBuilder.equal(root.get("level").as(Integer.class), req.getLevel()));
                }
                if(req.getStatus()!=-1){
                    list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), req.getStatus()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },Pageable.unpaged());
        List<LevelApplication> list=subjects.getContent();
        return list;
    }

    @Override
    public List<LevelApplication> findLevelApplicationCriteria(int page, int pageSize, LevelApplicationSearchReq req) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "createTime");
        Page<LevelApplication> subjects = levelApplicationService.findAll(new Specification<LevelApplication>(){
            @Override
            public Predicate toPredicate(Root<LevelApplication> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getName())){
                    list.add(criteriaBuilder.like(root.get("name").as(String.class), "%"+req.getName()+"%"));
                }
                if(!StringUtil.isEmpty(req.getPhone())){
                    list.add(criteriaBuilder.like(root.get("phone").as(String.class), "%"+req.getPhone()+"%"));
                }
                if(!StringUtil.isEmpty(req.getCreateBy())){
                    list.add(criteriaBuilder.equal(root.get("createBy").as(String.class), req.getCreateBy()));
                }
                if(req.getLevel()!=-1){
                    list.add(criteriaBuilder.equal(root.get("level").as(Integer.class), req.getLevel()));
                }
                if(req.getStatus()!=-1){
                    list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), req.getStatus()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },pageable);
        List<LevelApplication> list=subjects.getContent();
        return list;
    }

    @Override
    public List<LevelApplication> getByUserCode(String userCode) {
        return levelApplicationService.getByUserCode(userCode);
    }

    @Override
    public int getLevelApplicationByStatus(int i) {
        return levelApplicationService.getLevelApplicationByStatus(i);
    }
}
