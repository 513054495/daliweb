package com.code90.daliweb.controller;

import com.code90.daliweb.domain.*;
import com.code90.daliweb.request.learn.*;
import com.code90.daliweb.response.CommonResponse;
import com.code90.daliweb.server.*;
import com.code90.daliweb.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 作业控制类
 * @author Ray Lin
 * @create 2018-10-11 21:46
 **/
@RestController
@RequestMapping("/daliweb/work")
public class WorkController {
    private static final Logger logger=LoggerFactory.getLogger(WorkController.class);
    @Autowired
    private WorkServer workServer;
    @Autowired
    private WorkScheduleServer workScheduleServer;
    @Autowired
    private WorkDetailServer workDetailServer;
    @Autowired
    private SubjectServer subjectServer;
    @Autowired
    private AnnouncementServer announcementServer;

    /**
     * 发布作业
     * @param req 作业内容
     * @return 发布结果
     */
    @RequestMapping(value = "/addWork",method = RequestMethod.POST)
    public CommonResponse addWork(@RequestBody WorkSaveReq req){
        try {
            Work work=new Work();
            BeanUtils.copyProperties(req,work);
            work.createBy=req.getCreateBy();
            workServer.save(work);
            logger.info("保存成功");
            return new CommonResponse("保存成功");
        }catch (Exception e){
            logger.error("保存失败，原因："+e.getMessage());
            return new CommonResponse("保存失败",1,e);
        }
    }

    /**
     * 新增作业进度
     * @param req 作业进度内容
     * @return 新增结果
     */
    @RequestMapping(value = "/addWorkSchedule",method = RequestMethod.POST)
    public CommonResponse addWorkSchedule(@RequestBody WorkScheduleSaveReq req){
        try {
            WorkSchedule workSchedule;
            Work work= (Work) workServer.getObjectById(req.getWorkId());
            if(null==work||work.getStatus()==2){
                return new CommonResponse("新增失败，该作业不存在或已截止");
            }else{
                workSchedule=workScheduleServer.getAllByWorkIdAndCreateBy(req.getWorkId(),req.getCreateBy());
                if(null!=workSchedule){
                    return new CommonResponse("新增失败，该作业已完成",1);
                }else{
                    workSchedule=new WorkSchedule();
                }
            }
            BeanUtils.copyProperties(req,workSchedule);
            workSchedule.createBy=req.getCreateBy();
            workScheduleServer.save(workSchedule);
            workSchedule= workScheduleServer.getAllByWorkIdAndCreateBy(req.getWorkId(),req.getCreateBy());
            logger.info("保存成功");
            return new CommonResponse("保存成功","info",workSchedule);
        }catch (Exception e){
            logger.error("保存失败，原因："+e.getMessage());
            return new CommonResponse("保存失败",1,e);
        }
    }

    /**
     * 新增作业详情
     * @param req 作业进度内容
     * @return 新增结果
     */
    @RequestMapping(value = "/addWorkDetail",method = RequestMethod.POST)
    public CommonResponse addWorkDetail(@RequestBody WorkDetailSaveReq req){
        try {
            WorkDetail workDetail;
            WorkSchedule workSchedule= (WorkSchedule) workScheduleServer.getObjectById(req.getWorkScheduleId());
            if(null==workSchedule||workSchedule.getStatus()==1){
                return new CommonResponse("新增失败，该作业进度不存在或已批改");
            }else{
                WorkDetail workDetails=workDetailServer.getAllByWorkScheduleIdAndCreateByAndSubjectId(req.getWorkScheduleId(),req.getCreateBy(),req.getSubjectId());
                if(null!=workDetails){
                    return new CommonResponse("新增失败，该作业详情已存在",1);
                }else{
                    workDetail=new WorkDetail();
                }
            }
            BeanUtils.copyProperties(req,workDetail);
            workDetail.createBy=req.getCreateBy();
            workDetailServer.save(workDetail);

            logger.info("保存成功");
            return new CommonResponse("保存成功");
        }catch (Exception e){
            logger.error("保存失败，原因："+e.getMessage());
            return new CommonResponse("保存失败",1,e);
        }
    }

    /**
     * 修改作业
     * @param req 作业内容
     * @return 修改结果
     */
    @RequestMapping(value="/editWork",method = RequestMethod.POST)
    public CommonResponse editWork(@RequestBody WorkChangeReq req){
        try {
            Work work=(Work) workServer.getObjectById(req.getId());
            BeanUtils.copyProperties(req,work);
            workServer.save(work);
            logger.info("作业修改成功");
            return new CommonResponse("修改成功");
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 修改作业状态
     * @param  ids 作业编号
     * @param  status 状态
     * @return 修改结果
     */
    @RequestMapping(value="/editState",method = RequestMethod.GET)
    public CommonResponse editState(@RequestParam("ids")String ids,@RequestParam("status")int status){
        try {
            if(!StringUtil.isEmpty(ids)) {
                String[] id_list = ids.split(",");
                for (String id : id_list) {
                    Work work = (Work) workServer.getObjectById(id);
                    work.setStatus(status);
                    workServer.save(work);
                    if(status==1){
                        Announcement announcement=new Announcement();
                        announcement.setTitle("\""+work.getTitle()+"\"作业通知");
                        announcement.setContent("管理员发布了\""+work.getTitle()+"\"的作业，请您尽快到学习-->作业板块完成作业！");
                        announcement.setLevel("2,3");
                        announcement.setStatus(1);
                        announcementServer.save(announcement);
                    }
                }
                logger.info("作业修改成功");
                return new CommonResponse("修改成功");
            }else{
                logger.error("作业修改失败，编号不能为空");
                return new CommonResponse("修改失败",2);
            }
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 校验作业分享码
     * @param  id 作业编号
     * @param  shareCode 分享码
     * @return 检验结果
     */
    @RequestMapping(value="/checkShareCode",method = RequestMethod.GET)
    public CommonResponse checkShareCode(@RequestParam("id")String id,@RequestParam("shareCode")String shareCode){
        try {
            Work work=(Work) workServer.getObjectById(id);
            if(null!=work&&shareCode.equals(work.getShareCode())){
                logger.info("校验成功");
                return new CommonResponse("校验成功");
            }else{
                logger.info("校验失败");
                return new CommonResponse("校验失败",7);
            }
        }catch (Exception e){
            logger.error("校验失败，原因："+e.getMessage());
            return new CommonResponse("校验失败",7,e);
        }
    }

    /**
     * 修改作业进度
     * @param req 作业进度内容
     * @return 修改结果
     */
    @RequestMapping(value="/editWorkSchedule",method = RequestMethod.POST)
    public CommonResponse editWorkSchedule(@RequestBody WorkScheduleChangeReq req){
        try {
            WorkSchedule workSchedule=(WorkSchedule) workScheduleServer.getObjectById(req.getId());
            BeanUtils.copyProperties(req,workSchedule);
            workSchedule.lastmodifiedBy=req.getLastmodifiedBy();
            workScheduleServer.save(workSchedule);
            logger.info("作业进度修改成功");
            return new CommonResponse("修改成功");
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 修改作业详情
     * @param req 作业详情内容
     * @return 修改结果
     */
    @RequestMapping(value="/editWorkDetail",method = RequestMethod.POST)
    public CommonResponse editWorkDetail(@RequestBody WorkDetailChangeReq req){
        try {
            WorkDetail workDetail=(WorkDetail) workDetailServer.getObjectById(req.getId());
            BeanUtils.copyProperties(req,workDetail);
            workDetailServer.save(workDetail);
            logger.info("作业详情修改成功");
            return new CommonResponse("修改成功");
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 作业详情打分
     * @param ids 作业详情编号
     * @param points 分数列表
     * @return 修改结果
     */
    @RequestMapping(value="/editWorkDetailState",method = RequestMethod.GET)
    public CommonResponse editWorkDetailState(@RequestParam("ids")String ids,@RequestParam("points")String points){
        try {
            if(!StringUtil.isEmpty(ids)) {
                String[] id_list = ids.split(",");
                String[] point_list=points.split(",");
                for (int i=0;i<id_list.length;i++) {
                    WorkDetail workDetail = (WorkDetail) workDetailServer.getObjectById(id_list[i]);
                    workDetail.setPoint(Integer.parseInt(point_list[i]));
                    workDetailServer.save(workDetail);
                }
                logger.info("作业详情修改成功");
                return new CommonResponse("修改成功");
            }else{
                logger.error("作业详情修改失败，编号不能为空");
                return new CommonResponse("修改失败",2);
            }
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 根据编号获取作业
     * @param id 编号
     * @return 作业
     */
    @RequestMapping(value="/getWorkById",method=RequestMethod.GET)
    public CommonResponse getWorkById(@RequestParam("id")String id){
        CommonResponse response=new CommonResponse("获取成功");
        Work work=(Work) workServer.getObjectById(id);
        if(work!=null){
            logger.info("获取成功");
            response.addNewDate("info",work);
            return response;
        }else {
            logger.error("获取失败");
            return new CommonResponse("获取失败");
        }
    }

    /**
     * 根据用户帐号获取作业
     * @param userCode 用户帐号
     * @return 作业
     */
    @RequestMapping(value="/getWorkByUserCode",method=RequestMethod.GET)
    public CommonResponse getWorkByUserCode(@RequestParam("userCode")String userCode){
        CommonResponse response=new CommonResponse("获取成功");
        List<Work> works= workServer.getPublishWorks();
        List<WorkVo> workVos=new ArrayList<>();
        for (Work work : works){
            WorkSchedule workSchedule=workScheduleServer.getAllByWorkIdAndCreateBy(work.getId(),userCode);
            WorkVo workVo=new WorkVo();
            BeanUtils.copyProperties(work,workVo);
            if(null!=workSchedule){
                workVo.setAssess(workSchedule.getAssess());
                workVo.setIsWork(1);
                workVo.setPoint(workSchedule.getPoint());
                workVo.setWorkScheduleId(workSchedule.getId());
            }else{
                workVo.setAssess(-1);
            }
            if(work.getStatus()!=0) {
                workVos.add(workVo);
            }
        }
        logger.info("获取成功");
        response.addNewDate("info",workVos);
        return response;
    }

    /**
     * 根据用户帐号和作业编号获取作业详情
     * @param userCode 用户帐号
     * @param workScheduleId 作业编号
     * @return 作业详情
     */
    @RequestMapping(value="/getWorkDetailByUserCodeAndWorkScheduleId",method=RequestMethod.GET)
    public CommonResponse getWorkDetailByUserCodeAndWorkScheduleId(@RequestParam("userCode")String userCode,@RequestParam("workScheduleId")String workScheduleId,@RequestParam("isShow")int isShow){
        CommonResponse response=new CommonResponse("获取成功");
        List<WorkDetailVo> workDetailVos=new ArrayList<>();
        WorkSchedule workSchedule= (WorkSchedule) workScheduleServer.getObjectById(workScheduleId);
        if(null!=workSchedule){
           Work work= (Work) workServer.getObjectById(workSchedule.getWorkId());
           String[] subjectIds=work.getSubjects().split(",");
           for(String subjectId : subjectIds){
               WorkDetail workDetail=workDetailServer.getAllByWorkScheduleIdAndCreateByAndSubjectId(workSchedule.getId(),userCode,Integer.parseInt(subjectId));
               WorkDetailVo workDetailVo=new WorkDetailVo();
               workDetailVo.setLastTime(work.getLastTime());
               workDetailVo.setTitle(work.getTitle());
               if(null!=workDetail){
                   BeanUtils.copyProperties(workDetail,workDetailVo);
                   Subject subject= (Subject) subjectServer.getObjectById(subjectId);
                   if(null!=subject){
                       workDetailVo.setCorrectAnswer(subject.getAnswer());
                       workDetailVo.setSubjectName(subject.getSubjectName());
                       workDetailVo.setSubjectPoint(subject.getPoint());
                       workDetailVo.setSubjectType(subject.getType());
                       workDetailVo.setAnswerAnalysis(subject.getAnswerAnalysis());
                       workDetailVo.setSubA(subject.getSubA());
                       workDetailVo.setSubB(subject.getSubB());
                       workDetailVo.setSubC(subject.getSubC());
                       workDetailVo.setSubD(subject.getSubD());
                       workDetailVos.add(workDetailVo);
                   }
               }else if(isShow==1){
                   Subject subject= (Subject) subjectServer.getObjectById(subjectId);
                   if(null!=subject){
                       workDetailVo.setCorrectAnswer(subject.getAnswer());
                       workDetailVo.setSubjectName(subject.getSubjectName());
                       workDetailVo.setSubjectPoint(subject.getPoint());
                       workDetailVo.setSubjectType(subject.getType());
                       workDetailVo.setAnswerAnalysis(subject.getAnswerAnalysis());
                       workDetailVo.setSubA(subject.getSubA());
                       workDetailVo.setSubB(subject.getSubB());
                       workDetailVo.setSubC(subject.getSubC());
                       workDetailVo.setSubD(subject.getSubD());
                       workDetailVos.add(workDetailVo);

                   }
               }
           }
           logger.info("获取成功");
           response.addNewDate("info",workDetailVos);
           response.addNewDate("subjects",work.getSubjects());
           response.addNewDate("assess",workSchedule.getAssess());
           response.addNewDate("description",workSchedule.getDescription());
        }
        return response;
    }

    /**
     * 删除作业
     * @param ids 作业编号
     * @return 删除结果
     */
    @RequestMapping(value="/delWork",method = RequestMethod.DELETE)
    public CommonResponse delWork(@RequestParam("ids") String ids){
        try {
            if(!StringUtil.isEmpty(ids)){
                String[] id_list=ids.split(",");
                for (String id : id_list){
                    Work work=(Work) workServer.getObjectById(id);
                    workServer.delete(work);
                    List<WorkSchedule> workSchedules=workScheduleServer.getAllByWorkId(work.getId());
                    for(WorkSchedule workSchedule:workSchedules){
                        workScheduleServer.delete(workSchedule);
                        List<WorkDetail> workDetails=workDetailServer.getAllByWorkScheduleId(workSchedule.getId());
                        for(WorkDetail workDetail :workDetails){
                            workDetailServer.delete(workDetail);
                        }
                    }
                }
                logger.info("作业删除成功");
                return new CommonResponse("删除成功");
            }else{
                logger.error("删除失败，作业编号不能为空");
                return new CommonResponse("删除失败，作业编号不能为空",3);
            }
        }catch (Exception e){
            logger.error("删除失败，原因："+e.getMessage());
            return new CommonResponse("删除失败",3,e);
        }
    }

    /**
     * 删除作业进度
     * @param ids 作业进度编号
     * @return 删除结果
     */
    @RequestMapping(value="/delWorkSchedule",method = RequestMethod.DELETE)
    public CommonResponse delWorkSchedule(@RequestParam("ids") String ids){
        try {
            if(!StringUtil.isEmpty(ids)){
                String[] id_list=ids.split(",");
                for (String id : id_list){
                    WorkSchedule workSchedule=(WorkSchedule) workScheduleServer.getObjectById(id);
                    workScheduleServer.delete(workSchedule);
                }
                logger.info("作业进度删除成功");
                return new CommonResponse("删除成功");
            }else{
                logger.error("删除失败，作业进度编号不能为空");
                return new CommonResponse("删除失败，作业进度编号不能为空",3);
            }
        }catch (Exception e){
            logger.error("删除失败，原因："+e.getMessage());
            return new CommonResponse("删除失败",3,e);
        }
    }

    /**
     * 删除作业详情
     * @param ids 作业详情编号
     * @return 删除结果
     */
    @RequestMapping(value="/delWorkDetail",method = RequestMethod.DELETE)
    public CommonResponse delWorkDetail(@RequestParam("ids") String ids){
        try {
            if(!StringUtil.isEmpty(ids)){
                String[] id_list=ids.split(",");
                for (String id : id_list){
                    WorkDetail workDetail=(WorkDetail) workDetailServer.getObjectById(id);
                    workDetailServer.delete(workDetail);
                }
                logger.info("作业详情删除成功");
                return new CommonResponse("删除成功");
            }else{
                logger.error("删除失败，作业详情编号不能为空");
                return new CommonResponse("删除失败，作业详情编号不能为空",3);
            }
        }catch (Exception e){
            logger.error("删除失败，原因："+e.getMessage());
            return new CommonResponse("删除失败",3,e);
        }
    }

    /**
     * 分页查询作业
     * @param req 分页条件
     * @return 作业列表
     */
    @RequestMapping(value="/getWorks",method=RequestMethod.GET)
    public CommonResponse getWorks(WorkSearchReq req){
        updateState();
        int page=req.getPage()==0?0:req.getPage()-1;
        int pageSize=req.getPageSize()==0?10:req.getPageSize();
        List<Work> allWorks=workServer.getAll(req);
        int total=allWorks.size();
        int totalPage=total%pageSize==0?total/pageSize:total/pageSize+1;
        List<Work> works=workServer.findWorkCriteria(page,pageSize,req);
        CommonResponse response= new CommonResponse("获取成功","info",works);
        response.addNewDate("pageNum",page+1);
        response.addNewDate("pageSize",pageSize);
        response.addNewDate("total",total);
        response.addNewDate("totalPage",totalPage);
        return response;
    }

    /**
     * 分页查询作业进度
     * @param req 分页条件
     * @return 作业进度列表
     */
    @RequestMapping(value="/getWorkSchedules",method=RequestMethod.GET)
    public CommonResponse getWorkSchedules(WorkScheduleSearchReq req){
        int page=req.getPage()==0?0:req.getPage()-1;
        int pageSize=req.getPageSize()==0?10:req.getPageSize();
        List<WorkSchedule> allWorkSchedules=workScheduleServer.getAll(req);
        int total=allWorkSchedules.size();
        int totalPage=total%pageSize==0?total/pageSize:total/pageSize+1;
        List<WorkSchedule> workSchedules=workScheduleServer.findWorkScheduleCriteria(page,pageSize,req);
        List<WorkScheduleVo> workScheduleVos=new ArrayList<>();
        for(WorkSchedule workSchedule : workSchedules){
            WorkScheduleVo workScheduleVo=new WorkScheduleVo();
            BeanUtils.copyProperties(workSchedule,workScheduleVo);
            Work work= (Work) workServer.getObjectById(workSchedule.getWorkId());
            if(null!=work){
                workScheduleVo.setShareCode(work.getShareCode());
                workScheduleVo.setWorkStatus(work.getStatus());
            }
            workScheduleVos.add(workScheduleVo);
        }
        CommonResponse response= new CommonResponse("获取成功","info",workScheduleVos);
        response.addNewDate("pageNum",page+1);
        response.addNewDate("pageSize",pageSize);
        response.addNewDate("total",total);
        response.addNewDate("totalPage",totalPage);
        return response;
    }

    /**
     * 分页查询作业详情
     * @param req 分页条件
     * @return 作业详情列表
     */
    @RequestMapping(value="/getWorkDetails",method=RequestMethod.GET)
    public CommonResponse getWorkDetails(WorkDetailSearchReq req){
        int page=req.getPage()==0?0:req.getPage()-1;
        int pageSize=req.getPageSize()==0?10:req.getPageSize();
        List<WorkDetail> allWorkDetails=workDetailServer.getAll(req);
        int total=allWorkDetails.size();
        int totalPage=total%pageSize==0?total/pageSize:total/pageSize+1;
        List<WorkDetail> workDetails=workDetailServer.findWorkDetailCriteria(page,pageSize,req);
        List<WorkDetailVo> workDetailVos=new ArrayList<>();
        for(WorkDetail workDetail :workDetails){
            WorkDetailVo workDetailVo=new WorkDetailVo();
            BeanUtils.copyProperties(workDetail,workDetailVo);
            Subject subject= (Subject) subjectServer.getObjectById(workDetail.getSubjectId()+"");
            if(null!=subject){
                workDetailVo.setCorrectAnswer(subject.getAnswer());
                workDetailVo.setSubjectName(subject.getSubjectName());
                workDetailVo.setSubjectPoint(subject.getPoint());
                workDetailVo.setSubjectType(subject.getType());
            }
            workDetailVos.add(workDetailVo);
        }
        CommonResponse response= new CommonResponse("获取成功","info",workDetailVos);
        response.addNewDate("pageNum",page+1);
        response.addNewDate("pageSize",pageSize);
        response.addNewDate("total",total);
        response.addNewDate("totalPage",totalPage);
        return response;
    }

    /**
     * 根据作业编号获取已选题目
     * @param workId 作业编号
     * @return 已选题目
     */
    @RequestMapping(value="/getSelectSubject",method=RequestMethod.GET)
    public CommonResponse getSelectSubject(@RequestParam("workId")String workId){
        CommonResponse response=new CommonResponse("获取成功");
        Work work= (Work) workServer.getObjectById(workId);
        List<Subject> subjects=new ArrayList<>();
        if(null!=work){
            String[] selectSubjectIds=work.getSubjects().split(",");
            for (String selectSubject : selectSubjectIds){
                Subject subject= (Subject) subjectServer.getObjectById(selectSubject);
                subjects.add(subject);
            }
        }
        logger.info("获取成功");
        response.addNewDate("info",subjects);
        return response;
    }



    private void updateState() {
        List<Work> works=workServer.getAllStartWork();
        for (Work work : works){
            if(new Date().after(work.getLastTime())){
                work.setStatus(2);
            }
            workServer.save(work);
        }
    }

}
