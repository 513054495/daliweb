package com.code90.daliweb.service;

import com.code90.daliweb.domain.RegistrationDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * 报名详情数据库操作接口
 *
 * @author Ray Lin
 * @create 2018-10-30 23:54
 **/
public interface RegistrationDetailService extends JpaRepository<RegistrationDetail,Integer>,JpaSpecificationExecutor<RegistrationDetail> {
    @Query("select r from RegistrationDetail r where r.id=?1")
    RegistrationDetail getRegistrationDetailById(String id);
    @Query("select r from RegistrationDetail r where r.registrationId=?1 and r.createBy=?2 and r.status=1 order by c.createTime desc")
    RegistrationDetail getRegistrationDetailByIdAndCreateBy(String id, String userCode);
    @Query("select r from RegistrationDetail r where r.orderId=?1 and r.status=0")
    RegistrationDetail getRegistrationDetailByOrderId(String id);
}
