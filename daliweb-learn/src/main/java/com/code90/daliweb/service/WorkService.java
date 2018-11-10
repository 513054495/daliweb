package com.code90.daliweb.service;

import com.code90.daliweb.domain.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 作业操作数据库接口
 * @author Ray Lin
 * @create 2018-10-11 21:58
 **/
public interface WorkService extends JpaRepository<Work,Integer>,JpaSpecificationExecutor<Work> {
    @Query("select w from Work w where w.id=?1")
    Work getById(String id);
    @Query("select w from Work w where w.status=1")
    List<Work> getAllStartWork();
    @Query("select w from Work w where w.status<>0")
    List<Work> getPublishWorks();
}
