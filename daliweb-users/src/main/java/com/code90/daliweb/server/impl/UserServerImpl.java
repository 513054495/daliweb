package com.code90.daliweb.server.impl;

import com.code90.daliweb.domain.ProxyDetail;
import com.code90.daliweb.domain.Recommend;
import com.code90.daliweb.domain.User;
import com.code90.daliweb.domain.UserChangeLog;
import com.code90.daliweb.request.user.UserChangeLogReq;
import com.code90.daliweb.request.user.UserSearchReq;
import com.code90.daliweb.server.UserServer;
import com.code90.daliweb.service.RecommendService;
import com.code90.daliweb.service.UserChangeLogService;
import com.code90.daliweb.utils.DateUtils;
import com.code90.daliweb.utils.StringUtil;
import com.code90.daliweb.service.UserService;
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
import java.util.Date;
import java.util.List;

/**
 * 用户服务实现类
 * @author Ray
 * @create 2017-05-21 23:57
 */
@Service("userServer")
public class UserServerImpl implements UserServer {

    @Autowired
    private UserService userService;
    @Autowired
    private RecommendService recommendService;
    @Autowired
    private UserChangeLogService userChangeLogService;


    @Override
    public void save(Object user) {
        userService.save((User)user);
    }

    @Override
    public User getUserByUserCode(String userCode) {
        return userService.getUserByUserCode(userCode);
    }

    @Override
    public Object getObjectById(String id) {
        return userService.getUserById(id);
    }

    @Override
    public void delete(Object user) {
        userService.delete((User)user);
    }

    @Override
    public List<User> findUserCriteria(Integer page, Integer size, UserSearchReq req) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "createTime");
        Page<User> users = userService.findAll(new Specification<User>(){
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getUserCode())){
                    list.add(criteriaBuilder.like(root.get("userCode").as(String.class), "%"+req.getUserCode()+"%"));
                }
                if(!StringUtil.isEmpty(req.getUserName())){
                    list.add(criteriaBuilder.like(root.get("userName").as(String.class), "%"+req.getUserName()+"%"));
                }
                if(!StringUtil.isEmpty(req.getNickName())){
                    list.add(criteriaBuilder.like(root.get("nickname").as(String.class), "%"+req.getNickName()+"%"));
                }
                if(!StringUtil.isEmpty(req.getArea())){
                    list.add(criteriaBuilder.like(root.get("area").as(String.class), "%"+req.getArea()+"%"));
                }
                if(!StringUtil.isEmpty(req.getPhone())){
                    list.add(criteriaBuilder.like(root.get("phone").as(String.class), req.getPhone()+"%"));
                }
                if(!StringUtil.isEmpty(req.getStartTime())){
                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(String.class), req.getStartTime()));
                }
                if(!StringUtil.isEmpty(req.getEndTime())){
                    list.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime").as(String.class), req.getEndTime()));
                }
                if(req.getUserType()!=-1){
                    list.add(criteriaBuilder.equal(root.get("userType").as(Integer.class), req.getUserType()));
                }
                if(req.getIsClassMember()!=-1){
                    list.add(criteriaBuilder.equal(root.get("isClassMember").as(Integer.class), req.getIsClassMember()));
                }
                if(req.getTeamLevel()!=-1){
                    list.add(criteriaBuilder.equal(root.get("teamLevel").as(Integer.class), req.getTeamLevel()));
                }
                if(req.getCollegeLevel()!=-1){
                    list.add(criteriaBuilder.equal(root.get("collegeLevel").as(Integer.class), req.getCollegeLevel()));
                }
                if(req.getAgencyLevel()!=-1){
                    list.add(criteriaBuilder.equal(root.get("agencyLevel").as(Integer.class), req.getAgencyLevel()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },pageable);
        List<User> list=users.getContent();
        return list;
    }

    @Override
    public List<User> getAll(UserSearchReq req) {
        Page<User> users = userService.findAll(new Specification<User>(){
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getUserCode())){
                    list.add(criteriaBuilder.like(root.get("userCode").as(String.class), "%"+req.getUserCode()+"%"));
                }
                if(!StringUtil.isEmpty(req.getUserName())){
                    list.add(criteriaBuilder.like(root.get("userName").as(String.class), "%"+req.getUserName()+"%"));
                }
                if(!StringUtil.isEmpty(req.getNickName())){
                    list.add(criteriaBuilder.like(root.get("nickname").as(String.class), "%"+req.getNickName()+"%"));
                }
                if(!StringUtil.isEmpty(req.getArea())){
                    list.add(criteriaBuilder.like(root.get("area").as(String.class), "%"+req.getArea()+"%"));
                }
                if(!StringUtil.isEmpty(req.getPhone())){
                    list.add(criteriaBuilder.like(root.get("phone").as(String.class), req.getPhone()+"%"));
                }
                if(!StringUtil.isEmpty(req.getStartTime())){
                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(String.class), req.getStartTime()));
                }
                if(!StringUtil.isEmpty(req.getEndTime())){
                    list.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime").as(String.class), req.getEndTime()));
                }
                if(req.getUserType()!=-1){
                    list.add(criteriaBuilder.equal(root.get("userType").as(Integer.class), req.getUserType()));
                }
                if(req.getIsClassMember()!=-1){
                    list.add(criteriaBuilder.equal(root.get("isClassMember").as(Integer.class), req.getIsClassMember()));
                }
                if(req.getTeamLevel()!=-1){
                    list.add(criteriaBuilder.equal(root.get("teamLevel").as(Integer.class), req.getTeamLevel()));
                }
                if(req.getCollegeLevel()!=-1){
                    list.add(criteriaBuilder.equal(root.get("collegeLevel").as(Integer.class), req.getCollegeLevel()));
                }
                if(req.getAgencyLevel()!=-1){
                    list.add(criteriaBuilder.equal(root.get("agencyLevel").as(Integer.class), req.getAgencyLevel()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },Pageable.unpaged());
        List<User> list=users.getContent();
        return list;
    }

    @Override
    public List<User> findUserCriteriaByHonor(int page, int pageSize, UserSearchReq req) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "point");
        Page<User> users = userService.findAll(new Specification<User>(){
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getUserCode())){
                    list.add(criteriaBuilder.like(root.get("userCode").as(String.class), "%"+req.getUserCode()+"%"));
                }
                if(!StringUtil.isEmpty(req.getUserName())){
                    list.add(criteriaBuilder.like(root.get("userName").as(String.class), "%"+req.getUserName()+"%"));
                }
                if(!StringUtil.isEmpty(req.getNickName())){
                    list.add(criteriaBuilder.like(root.get("nickname").as(String.class), "%"+req.getNickName()+"%"));
                }
                if(!StringUtil.isEmpty(req.getArea())){
                    list.add(criteriaBuilder.like(root.get("area").as(String.class), "%"+req.getArea()+"%"));
                }
                if(!StringUtil.isEmpty(req.getPhone())){
                    list.add(criteriaBuilder.like(root.get("phone").as(String.class), req.getPhone()+"%"));
                }
                if(!StringUtil.isEmpty(req.getStartTime())){
                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(String.class), req.getStartTime()));
                }
                if(!StringUtil.isEmpty(req.getEndTime())){
                    list.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime").as(String.class), req.getEndTime()));
                }
                if(req.getUserType()!=-1){
                    list.add(criteriaBuilder.equal(root.get("userType").as(Integer.class), req.getUserType()));
                }
                if(req.getIsClassMember()!=-1){
                    list.add(criteriaBuilder.equal(root.get("isClassMember").as(Integer.class), req.getIsClassMember()));
                }
                if(req.getTeamLevel()!=-1){
                    list.add(criteriaBuilder.equal(root.get("teamLevel").as(Integer.class), req.getTeamLevel()));
                }
                if(req.getCollegeLevel()!=-1){
                    list.add(criteriaBuilder.equal(root.get("collegeLevel").as(Integer.class), req.getCollegeLevel()));
                }
                if(req.getAgencyLevel()!=-1){
                    list.add(criteriaBuilder.equal(root.get("agencyLevel").as(Integer.class), req.getAgencyLevel()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },pageable);
        List<User> list=users.getContent();
        return list;
    }

    @Override
    public User getUserByShareCode(String shareCode) {
        return userService.getUserByShareCode(shareCode);
    }

    @Override
    public void saveRecommend(Recommend recommend) {
        recommendService.save(recommend);
    }

    @Override
    public List<Recommend> getRecommendByUserCode(String userCode) {
        return recommendService.getRecommendByUserCode(userCode);
    }

    @Override
    public String getRecommendByCreateBy(String userCode) {
        return recommendService.getRecommendByCreateBy(userCode);
    }

    @Override
    public List<User> getAll() {
        return userService.findAll();
    }

    @Override
    public int getNewUserByCurrentMonth() {
        Page<User> commodities = userService.findAll(new Specification<User>(){
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(String.class),DateUtils.getFirstDateOfMonth(new Date()) ));
                list.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime").as(String.class), DateUtils.getLastDateOfMonth(new Date())));
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },Pageable.unpaged());
        List<User> list=commodities.getContent();
        return list.size();
    }

    @Override
    public void saveUserChangeLog(UserChangeLog userChangeLog) {
        userChangeLogService.save(userChangeLog);
    }

    @Override
    public int getNewVipUserByCurrentMonth() {
        Page<UserChangeLog> commodities = userChangeLogService.findAll(new Specification<UserChangeLog>(){
            @Override
            public Predicate toPredicate(Root<UserChangeLog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                list.add(criteriaBuilder.equal(root.get("type").as(Integer.class), 0));
                list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(String.class),DateUtils.getFirstDateOfMonth(new Date()) ));
                list.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime").as(String.class), DateUtils.getLastDateOfMonth(new Date())));
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },Pageable.unpaged());
        List<UserChangeLog> list=commodities.getContent();
        return list.size();
    }

    @Override
    public List<UserChangeLog> getAllChangeLog(UserChangeLogReq req) {
        Page<UserChangeLog> users = userChangeLogService.findAll(new Specification<UserChangeLog>(){
            @Override
            public Predicate toPredicate(Root<UserChangeLog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getCreateBy())){
                    list.add(criteriaBuilder.equal(root.get("createBy").as(String.class), req.getCreateBy()));
                }
                if(!StringUtil.isEmpty(req.getStartTime())){
                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(String.class), req.getStartTime()));
                }
                if(!StringUtil.isEmpty(req.getEndTime())){
                    list.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime").as(String.class), req.getEndTime()));
                }
                if(req.getType()!=-1){
                    list.add(criteriaBuilder.equal(root.get("type").as(Integer.class), req.getType()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },Pageable.unpaged());
        List<UserChangeLog> list=users.getContent();
        return list;
    }

    @Override
    public List<UserChangeLog> findUserChangeLogCriteria(int page, int pageSize, UserChangeLogReq req) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "createTime");
        Page<UserChangeLog> users = userChangeLogService.findAll(new Specification<UserChangeLog>(){
            @Override
            public Predicate toPredicate(Root<UserChangeLog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getCreateBy())){
                    list.add(criteriaBuilder.equal(root.get("createBy").as(String.class), req.getCreateBy()));
                }
                if(!StringUtil.isEmpty(req.getStartTime())){
                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(String.class), req.getStartTime()));
                }
                if(!StringUtil.isEmpty(req.getEndTime())){
                    list.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime").as(String.class), req.getEndTime()));
                }
                if(req.getType()!=-1){
                    list.add(criteriaBuilder.equal(root.get("type").as(Integer.class), req.getType()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },pageable);
        List<UserChangeLog> list=users.getContent();
        return list;
    }

}
