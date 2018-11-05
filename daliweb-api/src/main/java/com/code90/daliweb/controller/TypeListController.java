package com.code90.daliweb.controller;

import com.code90.daliweb.domain.Competition;
import com.code90.daliweb.domain.LevelApplication;
import com.code90.daliweb.domain.TypeList;
import com.code90.daliweb.domain.TypeListVo;
import com.code90.daliweb.request.exchange.CompetitionSaveReq;
import com.code90.daliweb.response.CommonResponse;
import com.code90.daliweb.server.TypeListServer;
import com.code90.daliweb.utils.IdUtils;
import com.code90.daliweb.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类集合控制类
 * @author Ray Lin
 * @create 2018-10-18 1:25
 **/
@RestController
@RequestMapping("/daliweb/typeList")
public class TypeListController {
    private static final Logger logger=LoggerFactory.getLogger(TypeListController.class);
    @Autowired
    private TypeListServer typeListServer;

    /**
     * 新增分类
     * @param mainType 主分类
     * @param typeName 分类名字
     * @return 新增结果
     */
    @RequestMapping(value = "/addTypeList",method = RequestMethod.GET)
    public CommonResponse addCompetition(@RequestParam("mainType")int mainType,@RequestParam("typeName")String typeName){
        try {
            TypeList typeList=new TypeList();
            typeList.setMainType(mainType);
            int maxId=typeListServer.getMaxSubjectId();
            typeList.setSubType(IdUtils.createSubjectId(maxId));
            typeList.setTypeName(typeName);
            typeListServer.save(typeList);
            logger.info("保存成功");
            return new CommonResponse("保存成功");
        }catch (Exception e){
            logger.error("保存失败，原因："+e.getMessage());
            return new CommonResponse("保存失败",1,e);
        }
    }

    /**
     * 修改分类
     * @param id 编号
     * @param typeName 分类名字
     * @return 新增结果
     */
    @RequestMapping(value="/editTypeList",method = RequestMethod.GET)
    public CommonResponse editTypeList(@RequestParam("id")String id,@RequestParam("typeName")String typeName){
        try {
            TypeList typeList= (TypeList) typeListServer.getObjectById(id);
            typeList.setTypeName(typeName);
            typeListServer.save(typeList);
            logger.info("修改成功");
            return new CommonResponse("修改成功");
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",1,e);
        }
    }

    /**
     * 删除分类
     * @param id 分类编号
     * @return 删除结果
     */
    @RequestMapping(value="/delTypeList",method = RequestMethod.DELETE)
    public CommonResponse delTypeList(@RequestParam("id") String id){
        try {
            TypeList typeList= (TypeList) typeListServer.getObjectById(id);
            typeListServer.delete(typeList);
            logger.info("分类删除成功");
            return new CommonResponse("删除成功");
        }catch (Exception e){
            logger.error("删除失败，原因："+e.getMessage());
            return new CommonResponse("删除失败",3,e);
        }
    }

    /**
     * 根据主分类获取子分类列表
     * @param mainType 主分类
     * @return 子分类列表
     */
    @RequestMapping(value="/getAllByMainType",method=RequestMethod.GET)
    public CommonResponse getAllByMainType(@RequestParam("mainType")int mainType){
        List<TypeList> typeLists=typeListServer.getAllByMainType(mainType);
        logger.info("获取成功");
        return new CommonResponse("获取成功","info",typeLists);
    }

    /**
     * 根据子分类列表
     * @return 子分类列表
     */
    @RequestMapping(value="/getAlls",method=RequestMethod.GET)
    public CommonResponse getAlls(){
        List<TypeListVo> typeListVos=new ArrayList<>();
        List<TypeList> typeLists1=typeListServer.getAllByMainType(1);
        TypeListVo typeListVo1=new TypeListVo();
        typeListVo1.setId(1);
        typeListVo1.setTypeName("赛事类型");
        typeListVo1.setChildren(typeLists1);
        typeListVos.add(typeListVo1);
        List<TypeList> typeLists2=typeListServer.getAllByMainType(2);
        TypeListVo typeListVo2=new TypeListVo();
        typeListVo2.setId(2);
        typeListVo2.setTypeName("帖子类型");
        typeListVo2.setChildren(typeLists2);
        typeListVos.add(typeListVo2);
        logger.info("获取成功");
        return new CommonResponse("获取成功","info",typeListVos);
    }
}
