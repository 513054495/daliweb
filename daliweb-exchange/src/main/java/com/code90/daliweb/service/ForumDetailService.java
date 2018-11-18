package com.code90.daliweb.service;

import com.code90.daliweb.domain.ForumDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 论坛操作数据接口
 * @author Ray Lin
 * @create 2018-11-12 1:24
 **/
public interface ForumDetailService extends JpaRepository<ForumDetail,Integer>,JpaSpecificationExecutor<ForumDetail> {
    @Query("select f from ForumDetail f where f.id=?1")
    ForumDetail getForumDetailById(String id);
    @Query("select f from ForumDetail f where f.forumId=?1")
    List<ForumDetail> getForumDetailByForumId(String id);
}
