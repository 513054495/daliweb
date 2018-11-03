package com.code90.daliweb.service;

import com.code90.daliweb.domain.TypeList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 分类集合数据操作接口
 * @author Ray Lin
 * @create 2018-10-18 1:30
 **/
public interface TypeListService extends JpaRepository<TypeList,Integer>,JpaSpecificationExecutor<TypeList> {
    @Query("select  t from TypeList t where t.mainType=?1 ")
    List<TypeList> getAllByMainType(int mainType);
    @Query("select  t from TypeList t where t.id=?1 ")
    TypeList getTypeListById(String id);
    @Query("select  max(t.subType) from TypeList t ")
    int getMaxSubjectId();
    @Query("select  t from TypeList t where t.subType=?1")
    TypeList getTypeNameBySubjectId(int subjectId);
}
