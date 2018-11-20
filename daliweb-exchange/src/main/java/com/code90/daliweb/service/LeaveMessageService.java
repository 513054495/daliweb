package com.code90.daliweb.service;

import com.code90.daliweb.domain.LeaveMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 留言操作数据库服务接口
 * @author Ray Lin
 * @create 2018-11-08 22:30
 **/
public interface LeaveMessageService extends JpaRepository<LeaveMessage,Integer>,JpaSpecificationExecutor<LeaveMessage> {
    @Query("select l from LeaveMessage l where l.id=?1")
    LeaveMessage getLeaveMessageById(String id);
    @Query("select l from LeaveMessage l where l.createBy=?1 order by l.createTime desc")
    List<LeaveMessage> getLeaveMessageByUserCode(String userCode);
    @Query("select coalesce(count(l),0) from LeaveMessage l where l.status=?1")
    int getLeaveMessageByState(int i);
}
