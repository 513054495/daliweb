package com.code90.daliweb.server.impl;

import com.code90.daliweb.domain.TypeList;
import com.code90.daliweb.server.TypeListServer;
import com.code90.daliweb.service.TypeListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分类集合服务接口实现类
 * @author Ray Lin
 * @create 2018-10-18 1:29
 **/
@Service("typeListServer")
public class TypeListServerImpl implements TypeListServer {
    @Autowired
    private TypeListService typeListService;
    @Override
    public void save(Object object) {
        typeListService.save((TypeList)object);
    }

    @Override
    public void delete(Object object) {
        typeListService.delete((TypeList)object);
    }

    @Override
    public Object getObjectById(String id) {
        return typeListService.getTypeListById(id);
    }

    @Override
    public List<TypeList> getAllByMainType(int mainType) {
        return typeListService.getAllByMainType(mainType);
    }

    @Override
    public int getMaxSubjectId() {
        List<TypeList> typeLists=typeListService.findAll();
        if(null==typeLists||typeLists.size()==0){
            return 0;
        }
        return typeListService.getMaxSubjectId();
    }

    @Override
    public TypeList getTypeNameBySubjectId(int subjectId) {
        return typeListService.getTypeNameBySubjectId(subjectId);
    }
}
