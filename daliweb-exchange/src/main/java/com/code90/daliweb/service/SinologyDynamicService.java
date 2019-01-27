package com.code90.daliweb.service;

import com.code90.daliweb.domain.SinologyDynamic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * 动态数据库操作类
 * @author Ray Lin
 * @create 2019-01-13 22:48
 **/
public interface SinologyDynamicService extends JpaRepository<SinologyDynamic,Integer>,JpaSpecificationExecutor<SinologyDynamic> {
    @Query("select s from SinologyDynamic s where s.id=?1")
    SinologyDynamic getObjectById(String id);
}
