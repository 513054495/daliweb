package com.code90.daliweb.service;

import com.code90.daliweb.domain.LevelApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 考级申请数据库操作类
 * @author Ray Lin
 * @create 2018-10-16 23:30
 **/
public interface LevelApplicationService extends JpaRepository<LevelApplication,Integer>,JpaSpecificationExecutor<LevelApplication> {
    @Query("select l from LevelApplication l where l.id=?1 ")
    LevelApplication getObjectById(String id);
    @Query("select l from LevelApplication l where l.createBy=?1")
    List<LevelApplication> getByUserCode(String userCode);
}
