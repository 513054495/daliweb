package com.code90.daliweb.service;

import com.code90.daliweb.domain.Forum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * 论坛操作数据库接口
 * @author Ray Lin
 * @create 2018-11-12 0:39
 **/
public interface ForumService extends JpaRepository<Forum,Integer>,JpaSpecificationExecutor<Forum> {
    @Query("select f from Forum f where f.id=?1")
    Forum getForumById(String id);
}
