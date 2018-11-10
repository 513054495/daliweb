package com.code90.daliweb.server.impl;

import com.code90.daliweb.domain.Announcement;
import com.code90.daliweb.domain.AnnouncementDetail;
import com.code90.daliweb.domain.LeaveMessage;
import com.code90.daliweb.domain.User;
import com.code90.daliweb.request.exchange.AnnouncementSearchReq;
import com.code90.daliweb.server.AnnouncementServer;
import com.code90.daliweb.service.AnnouncementDetailService;
import com.code90.daliweb.service.AnnouncementService;
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
 * 公告服务接口实现类
 * @author Ray Lin
 * @create 2018-11-10 22:40
 **/
@Service("announcementServer")
public class AnnouncementServerImpl implements AnnouncementServer {
    @Autowired
    private AnnouncementService announcementService;
    @Autowired
    private AnnouncementDetailService announcementDetailService;

    @Override
    public void save(Object object) {
        announcementService.save((Announcement)object);
    }

    @Override
    public void delete(Object object) {
        announcementService.delete((Announcement)object);
    }

    @Override
    public Object getObjectById(String id) {
        return announcementService.getAnnouncementById(id);
    }

    @Override
    public List<Announcement> getAnnouncementByUser(User user) {
        List<Announcement> announcements=new ArrayList<>();
        List<Announcement> list=announcementService.getStartAnnouncement();
        for (Announcement announcement:list){
            if(announcement.getLevel()==0){
                announcements.add(announcement);
            }else if(announcement.getLevel()==1&&user.getTeamLevel()>0){
                announcements.add(announcement);
            }else if(announcement.getLevel()==2&&user.getIsClassMember()==1){
                announcements.add(announcement);
            }else if(announcement.getLevel()==3&&user.getCollegeLevel()>0){
                announcements.add(announcement);
            }else if(announcement.getLevel()==4&&user.getAgencyLevel()>0){
                announcements.add(announcement);
            }
        }
        return announcements;
    }

    @Override
    public void saveDetail(AnnouncementDetail announcementDetail) {
        announcementDetailService.save(announcementDetail);
    }

    @Override
    public List<AnnouncementDetail> getAnnouncementDetailByAnnouncementId(String id) {
        return announcementDetailService.getAnnouncementDetailByAnnouncementId(id);
    }

    @Override
    public void deleteDetail(AnnouncementDetail announcementDetail) {
        announcementDetailService.delete(announcementDetail);
    }

    @Override
    public AnnouncementDetail getAnnouncementDetailByAnnouncementIdAndUserCode(String id, String userCode) {
        return announcementDetailService.getAnnouncementDetailByAnnouncementIdAndUserCode(id,userCode);
    }

    @Override
    public List<Announcement> getAll(AnnouncementSearchReq req) {
        Page<Announcement> subjects = announcementService.findAll(new Specification<Announcement>(){
            @Override
            public Predicate toPredicate(Root<Announcement> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getTitle())){
                    list.add(criteriaBuilder.like(root.get("title").as(String.class), "%"+req.getTitle()+"%"));
                }
                if(req.getStatus()!=-1){
                    list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), req.getStatus()));
                }
                if(req.getLevel()!=-1){
                    list.add(criteriaBuilder.equal(root.get("level").as(Integer.class), req.getLevel()));
                }
                if(!StringUtil.isEmpty(req.getStartTime())){
                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("modifyTime").as(String.class), req.getStartTime()));
                }
                if(!StringUtil.isEmpty(req.getEndTime())){
                    list.add(criteriaBuilder.lessThanOrEqualTo(root.get("modifyTime").as(String.class), req.getEndTime()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },Pageable.unpaged());
        List<Announcement> list=subjects.getContent();
        return list;
    }

    @Override
    public List<Announcement> findAnnouncementCriteria(int page, int pageSize, AnnouncementSearchReq req) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "createTime");
        Page<Announcement> subjects = announcementService.findAll(new Specification<Announcement>(){
            @Override
            public Predicate toPredicate(Root<Announcement> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtil.isEmpty(req.getTitle())){
                    list.add(criteriaBuilder.like(root.get("title").as(String.class), "%"+req.getTitle()+"%"));
                }
                if(req.getStatus()!=-1){
                    list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), req.getStatus()));
                }
                if(req.getLevel()!=-1){
                    list.add(criteriaBuilder.equal(root.get("level").as(Integer.class), req.getLevel()));
                }
                if(!StringUtil.isEmpty(req.getStartTime())){
                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("modifyTime").as(String.class), req.getStartTime()));
                }
                if(!StringUtil.isEmpty(req.getEndTime())){
                    list.add(criteriaBuilder.lessThanOrEqualTo(root.get("modifyTime").as(String.class), req.getEndTime()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },pageable);
        List<Announcement> list=subjects.getContent();
        return list;
    }
}
