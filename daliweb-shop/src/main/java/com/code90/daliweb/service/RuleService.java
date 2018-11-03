package com.code90.daliweb.service;

import com.code90.daliweb.domain.Rules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * 规则数据库操作接口
 * @author Ray Lin
 * @create 2018-10-22 22:53
 **/
public interface RuleService extends JpaRepository<Rules,Integer>,JpaSpecificationExecutor<Rules> {
    @Query("select r from Rules r where r.id=?1 ")
    Rules getRuleById(int id);
}
