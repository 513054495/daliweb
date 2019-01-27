package com.code90.daliweb.service;

import com.code90.daliweb.domain.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 报名数据库操作接口
 * @author Ray Lin
 * @create 2018-10-30 23:24
 **/
public interface RegistrationService extends JpaRepository<Registration,Integer>,JpaSpecificationExecutor<Registration> {
    @Query("select r from Registration r where r.id=?1")
    Registration getRegistrationById(String id);
    @Query("select r from Registration r where r.status<>0 order by r.createTime desc")
    List<Registration> getAllPublishRegistration();
}
