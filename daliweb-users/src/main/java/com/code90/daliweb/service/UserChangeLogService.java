package com.code90.daliweb.service;

import com.code90.daliweb.domain.UserChangeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 用户修改记录数据库操作接口
 * @author Ray Lin
 * @create 2018-11-14 23:02
 **/
public interface UserChangeLogService extends JpaRepository<UserChangeLog,Integer>,JpaSpecificationExecutor<UserChangeLog> {
}
