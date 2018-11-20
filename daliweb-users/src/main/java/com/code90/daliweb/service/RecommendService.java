package com.code90.daliweb.service;

import com.code90.daliweb.domain.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 推荐关系数据库操作类
 * @author Ray Lin
 * @create 2018-10-24 22:08
 **/
public interface RecommendService extends JpaRepository<Recommend,Integer>,JpaSpecificationExecutor<Recommend> {
    @Query("select r from Recommend r where r.userCode=?1 order by r.createTime desc")
    List<Recommend> getRecommendByUserCode(String userCode);
    @Query("select r.userCode from Recommend r where r.createBy=?1 order by r.createTime desc")
    String getRecommendByCreateBy(String userCode);
}
