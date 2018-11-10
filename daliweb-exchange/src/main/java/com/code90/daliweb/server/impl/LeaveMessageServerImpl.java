package com.code90.daliweb.server.impl;

import com.code90.daliweb.domain.LeaveMessage;
import com.code90.daliweb.domain.LevelApplication;
import com.code90.daliweb.request.exchange.LeaveMessageSearchReq;
import com.code90.daliweb.server.LeaveMessageServer;
import com.code90.daliweb.service.LeaveMessageService;
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
 * 留言服务接口实现类
 * @author Ray Lin
 * @create 2018-11-08 22:28
 **/
@Service("leaveMessageServer")
public class LeaveMessageServerImpl implements LeaveMessageServer {

    @Autowired
    private LeaveMessageService leaveMessageService;

    @Override
    public void save(Object object) {
        leaveMessageService.save((LeaveMessage)object);
    }

    @Override
    public void delete(Object object) {
        leaveMessageService.delete((LeaveMessage)object);
    }

    @Override
    public Object getObjectById(String id) {
        return leaveMessageService.getLeaveMessageById(id);
    }

    @Override
    public List<LeaveMessage> getAll(LeaveMessageSearchReq req) {
        Page<LeaveMessage> subjects = leaveMessageService.findAll(new Specification<LeaveMessage>(){
            @Override
            public Predicate toPredicate(Root<LeaveMessage> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getCreateBy())){
                    list.add(criteriaBuilder.equal(root.get("createBy").as(String.class), req.getCreateBy()));
                }
                if(req.getStatus()!=-1){
                    list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), req.getStatus()));
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
        List<LeaveMessage> list=subjects.getContent();
        return list;
    }

    @Override
    public List<LeaveMessage> findLeaveMessageCriteria(int page, int pageSize, LeaveMessageSearchReq req) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "createTime");
        Page<LeaveMessage> subjects = leaveMessageService.findAll(new Specification<LeaveMessage>(){
            @Override
            public Predicate toPredicate(Root<LeaveMessage> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getCreateBy())){
                    list.add(criteriaBuilder.equal(root.get("createBy").as(String.class), req.getCreateBy()));
                }
                if(req.getStatus()!=-1){
                    list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), req.getStatus()));
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
        List<LeaveMessage> list=subjects.getContent();
        return list;
    }

    @Override
    public List<LeaveMessage> getLeaveMessageByUserCode(String userCode) {
        return leaveMessageService.getLeaveMessageByUserCode(userCode);
    }
}
