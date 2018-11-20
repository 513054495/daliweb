package com.code90.daliweb.service;

import com.code90.daliweb.domain.CompetitionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 竞赛数据库操作接口
 * @author Ray Lin
 * @create 2018-10-18 1:07
 **/
public interface CompetitionDetailService extends JpaRepository<CompetitionDetail,Integer>,JpaSpecificationExecutor<CompetitionDetail> {
    @Query("select c from CompetitionDetail c where c.id=?1")
    CompetitionDetail getCompetitionDetailById(String id);
    @Query("select c from CompetitionDetail c where c.competitionId=?1 and c.createBy=?2 order by c.createTime desc")
    CompetitionDetail getByCompetitionIdAndUserCode(String id, String userCode);
    @Query("select c from CompetitionDetail c where c.competitionId=?1")
    List<CompetitionDetail> getDetailByCompetitionId(String id);
    @Query("select c from CompetitionDetail c where c.createBy=?1 order by c.createTime desc")
    List<CompetitionDetail> getCompetitionDetailByUserCode(String userCode);
    @Query("select coalesce(count(c),0) from CompetitionDetail c where c.status=?1")
    int getDetailByStatus(int i);
}
