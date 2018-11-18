package com.code90.daliweb.server.impl;

import com.code90.daliweb.domain.Forum;
import com.code90.daliweb.domain.ForumDetail;
import com.code90.daliweb.request.exchange.ForumSearchReq;
import com.code90.daliweb.server.ForumServer;
import com.code90.daliweb.service.ForumDetailService;
import com.code90.daliweb.service.ForumService;
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
 * 论坛服务接口实现类
 * @author Ray Lin
 * @create 2018-11-12 0:38
 **/
@Service("forumServer")
public class ForumServerImpl implements ForumServer {
    @Autowired
    private ForumService forumService;
    @Autowired
    private ForumDetailService forumDetailService;

    @Override
    public void save(Object object) {
        forumService.save((Forum)object);
    }

    @Override
    public void delete(Object object) {
        forumService.delete((Forum)object);
    }

    @Override
    public Object getObjectById(String id) {
        return forumService.getForumById(id);
    }

    @Override
    public void saveDetail(ForumDetail forumDetail) {
        forumDetailService.save(forumDetail);
    }

    @Override
    public ForumDetail getForumDetailById(String id) {
        return forumDetailService.getForumDetailById(id);
    }

    @Override
    public List<ForumDetail> getForumDetailByForumId(String id) {
        return forumDetailService.getForumDetailByForumId(id);
    }

    @Override
    public void deleteDetail(ForumDetail forumDetail) {
        forumDetailService.delete(forumDetail);
    }

    @Override
    public List<Forum> getAll(ForumSearchReq req) {
        Page<Forum> subjects = forumService.findAll(new Specification<Forum>(){
            @Override
            public Predicate toPredicate(Root<Forum> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getTitle())){
                    list.add(criteriaBuilder.like(root.get("title").as(String.class), "%"+req.getTitle()+"%"));
                }
                if(req.getType()!=-1){
                    list.add(criteriaBuilder.equal(root.get("type").as(Integer.class), req.getType()));
                }
                if(!StringUtil.isEmpty(req.getCreateBy())){
                    list.add(criteriaBuilder.equal(root.get("createBy").as(String.class), req.getCreateBy()));
                }
                if(!StringUtil.isEmpty(req.getStartTime())){
                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(String.class), req.getStartTime()));
                }
                if(!StringUtil.isEmpty(req.getEndTime())){
                    list.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime").as(String.class), req.getEndTime()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },Pageable.unpaged());
        List<Forum> list=subjects.getContent();
        return list;
    }

    @Override
    public List<Forum> findForumCriteria(int page, int pageSize, ForumSearchReq req) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "createTime");
        Page<Forum> subjects = forumService.findAll(new Specification<Forum>(){
            @Override
            public Predicate toPredicate(Root<Forum> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getTitle())){
                    list.add(criteriaBuilder.like(root.get("title").as(String.class), "%"+req.getTitle()+"%"));
                }
                if(req.getType()!=-1){
                    list.add(criteriaBuilder.equal(root.get("type").as(Integer.class), req.getType()));
                }
                if(!StringUtil.isEmpty(req.getCreateBy())){
                    list.add(criteriaBuilder.equal(root.get("createBy").as(String.class), req.getCreateBy()));
                }
                if(!StringUtil.isEmpty(req.getStartTime())){
                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(String.class), req.getStartTime()));
                }
                if(!StringUtil.isEmpty(req.getEndTime())){
                    list.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime").as(String.class), req.getEndTime()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },pageable);
        List<Forum> list=subjects.getContent();
        return list;
    }
}
