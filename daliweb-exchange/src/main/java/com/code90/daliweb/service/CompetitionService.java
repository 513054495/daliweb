package com.code90.daliweb.service;

import com.code90.daliweb.domain.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 竞赛数据库操作接口
 * @author Ray Lin
 * @create 2018-10-18 1:07
 **/
public interface CompetitionService extends JpaRepository<Competition,Integer>,JpaSpecificationExecutor<Competition> {
    @Query("select c from Competition c where c.id=?1 ")
    Competition getCompetitionById(String id);
    @Query("select c from Competition c where c.status=1 ")
    List<Competition> getAllStartCompetition();
}
