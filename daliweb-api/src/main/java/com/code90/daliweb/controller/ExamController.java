package com.code90.daliweb.controller;

import com.code90.daliweb.domain.*;
import com.code90.daliweb.request.learn.*;
import com.code90.daliweb.response.CommonResponse;
import com.code90.daliweb.server.AnnouncementServer;
import com.code90.daliweb.server.ExamScheduleServer;
import com.code90.daliweb.server.ExamServer;
import com.code90.daliweb.server.UserServer;
import com.code90.daliweb.utils.DateUtils;
import com.code90.daliweb.utils.ExcelUtils;
import com.code90.daliweb.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 考试控制类
 * @author Ray Lin
 * @create 2018-10-06 0:38
 **/
@RestController
@RequestMapping("/daliweb/exam")
public class ExamController {
    private static final Logger logger=LoggerFactory.getLogger(ExamController.class);
    @Autowired
    private ExamServer examServer;
    @Autowired
    private ExamScheduleServer examScheduleServer;
    @Autowired
    private UserServer userServer;
    @Autowired
    private AnnouncementServer announcementServer;

    /**
     * 新增考试
     * @param req 考试内容
     * @return 新增结果
     */
    @RequestMapping(value = "/addExam",method = RequestMethod.POST)
    public CommonResponse addExam(@RequestBody ExamSaveReq req){
        try {
            Exam exam=new Exam();
            BeanUtils.copyProperties(req,exam);
            exam.createBy=req.getCreateBy();
            examServer.save(exam);
            logger.info("保存成功");
            return new CommonResponse("保存成功");
        }catch (Exception e){
            logger.error("保存失败，原因："+e.getMessage());
            return new CommonResponse("保存失败",1,e);
        }
    }

    /**
     * 新增考试进度
     * @param req 考试进度内容
     * @return 新增结果
     */
    @RequestMapping(value = "/addExamSchedule",method = RequestMethod.POST)
    public CommonResponse addExamSchedule(@RequestBody ExamScheduleSaveReq req){
        try {
            Exam exam=(Exam)examServer.getObjectById(req.getExamId());
            if(null==exam||exam.getStatus()==2){
                logger.error("保存失败，考试不存在或者已结束");
                return new CommonResponse("保存失败，考试不存在或者已结束",1);
            }
            ExamSchedule examSchedule;
            examSchedule=examScheduleServer.getAllByExanIdAndCreateBy(req.getExamId(),req.getCreateBy());
            if(null!=examSchedule){
                logger.error("保存失败，该用户已经考试");
                return new CommonResponse("保存失败，该用户已经考试",1);
            }else{
                examSchedule=new ExamSchedule();
            }
            BeanUtils.copyProperties(req,examSchedule);
            examSchedule.createBy=req.getCreateBy();
            examScheduleServer.save(examSchedule);
            logger.info("保存成功");
            return new CommonResponse("保存成功");
        }catch (Exception e){
            logger.error("保存失败，原因："+e.getMessage());
            return new CommonResponse("保存失败",1,e);
        }
    }

    /**
     * 修改考试
     * @param req 考试内容
     * @return 修改结果
     */
    @RequestMapping(value="/editExam",method = RequestMethod.POST)
    public CommonResponse editExam(@RequestBody ExamChangeReq req){
        try {
            Exam exam=(Exam) examServer.getObjectById(req.getId());
            BeanUtils.copyProperties(req,exam);
            examServer.save(exam);
            logger.info("考试修改成功");
            return new CommonResponse("修改成功");
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 修改考试进度
     * @param req 考试进度内容
     * @return 修改结果
     */
    @RequestMapping(value="/editExamSchedule",method = RequestMethod.POST)
    public CommonResponse editExamSchedule(@RequestBody ExamScheduleChangeReq req){
        try {
            ExamSchedule examSchedule=(ExamSchedule) examScheduleServer.getObjectById(req.getId());
            BeanUtils.copyProperties(req,examSchedule);
            examScheduleServer.save(examSchedule);
            logger.info("考试进度修改成功");
            return new CommonResponse("修改成功");
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 修改考试状态
     * @param  ids 考试编号
     * @param  status 状态
     * @return 修改结果
     */
    @RequestMapping(value="/editState",method = RequestMethod.GET)
    public CommonResponse editState(@RequestParam("ids")String ids,@RequestParam("status")int status){
        try {
            if(!StringUtil.isEmpty(ids)) {
                String[] id_list = ids.split(",");
                for (String id : id_list) {
                    Exam exam = (Exam) examServer.getObjectById(id);
                    exam.setStatus(status);
                    examServer.save(exam);
                }
                logger.info("考试修改成功");
                return new CommonResponse("修改成功");
            }else{
                logger.error("考试修改失败，编号不能为空");
                return new CommonResponse("修改失败",2);
            }
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 根据编号获取考试
     * @param id 编号
     * @return 考试
     */
    @RequestMapping(value="/getExamById",method=RequestMethod.GET)
    public CommonResponse getExamById(@RequestParam("id")String id){
        CommonResponse response=new CommonResponse("获取成功");
        Exam exam=(Exam) examServer.getObjectById(id);
        User user=userServer.getUserByUserCode(exam.createBy);
        response.addNewDate("createBy",user.getUserName());
        if(exam!=null){
            logger.info("获取成功");
            response.addNewDate("info",exam);
            return response;
        }else {
            logger.error("获取失败");
            return new CommonResponse("获取失败");
        }
    }

    /**
     * 根据编号获取考试进度
     * @param id 编号
     * @return 考试进度
     */
    @RequestMapping(value="/getExamScheduleById",method=RequestMethod.GET)
    public CommonResponse getExamScheduleById(@RequestParam("id")String id){
        CommonResponse response=new CommonResponse("获取成功");
        ExamSchedule examSchedule=(ExamSchedule) examScheduleServer.getObjectById(id);
        if(examSchedule!=null){
            ExamScheduleVo examScheduleVo=new ExamScheduleVo();
            BeanUtils.copyProperties(examSchedule,examScheduleVo);
            User user=userServer.getUserByUserCode(examSchedule.createBy);
            if(null!=user){
                examScheduleVo.setCreateName(user.getUserName());
            }
            logger.info("获取成功");
            response.addNewDate("info",examScheduleVo);
            return response;
        }else {
            logger.error("获取失败");
            return new CommonResponse("获取失败");
        }
    }

    /**
     * 根据用户帐号获取考试列表
     * @param userCode 用户帐号
     * @param id 考试编号
     * @return 考试列表
     */
    @RequestMapping(value="/getExamByUserCode",method=RequestMethod.GET)
    public CommonResponse getExamByUserCode(String id,@RequestParam("userCode")String userCode){
        try{
           updateState();
           if(StringUtil.isEmpty(id)){
               List<Exam> exams=examServer.getAll();
               List<ExamVo> examVos=new ArrayList<>();
               for (Exam exam :exams){
                   ExamSchedule examSchedule=examScheduleServer.getAllByExanIdAndCreateBy(exam.getId(),userCode);
                   ExamVo examVo=new ExamVo();
                   BeanUtils.copyProperties(exam,examVo);
                   if(null!=examSchedule){
                       examVo.setIsExam(1);
                       examVo.setPoint(examSchedule.getPoint());
                   }
                   examVos.add(examVo);
               }
               logger.info("获取成功");
               return new CommonResponse("获取成功","info",examVos);
           }else{
               Exam exam= (Exam) examServer.getObjectById(id);
               ExamSchedule examSchedule=examScheduleServer.getAllByExanIdAndCreateBy(exam.getId(),userCode);
               ExamVo examVo=new ExamVo();
               BeanUtils.copyProperties(exam,examVo);
               if(null!=examSchedule){
                   examVo.setIsExam(1);
               }
               logger.info("获取成功");
               return new CommonResponse("获取成功","info",examVo);
           }
        }catch (Exception e){
            logger.error("获取失败，原因："+e.getMessage());
            return new CommonResponse("获取失败",4,e);
        }
    }


    /**
     * 删除考试
     * @param ids 考试编号
     * @return 删除结果
     */
    @RequestMapping(value="/delExam",method = RequestMethod.DELETE)
    public CommonResponse delExam(@RequestParam("ids") String ids){
        try {
            if(!StringUtil.isEmpty(ids)){
                String[] id_list=ids.split(",");
                for (String id : id_list){
                    Exam exam=(Exam) examServer.getObjectById(id);
                    if(exam.getStatus()==0) {
                        examServer.delete(exam);
                    }else{
                        logger.error("删除失败，只能删除未开始的考试");
                        return new CommonResponse("删除失败，只能删除未开始的考试",3);
                    }
                }
                logger.info("考试删除成功");
                return new CommonResponse("删除成功");
            }else{
                logger.error("删除失败，考试编号不能为空");
                return new CommonResponse("删除失败，考试编号不能为空",3);
            }
        }catch (Exception e){
            logger.error("删除失败，原因："+e.getMessage());
            return new CommonResponse("删除失败",3,e);
        }
    }

    /**
     * 删除考试进度
     * @param ids 考试进度编号
     * @return 删除结果
     */
    @RequestMapping(value="/delExamSchedule",method = RequestMethod.DELETE)
    public CommonResponse delExamSchedule(@RequestParam("ids") String ids){
        try {
            if(!StringUtil.isEmpty(ids)){
                String[] id_list=ids.split(",");
                for (String id : id_list){
                    ExamSchedule examSchedule=(ExamSchedule) examScheduleServer.getObjectById(id);
                    examScheduleServer.delete(examSchedule);
                }
                logger.info("考试进度删除成功");
                return new CommonResponse("删除成功");
            }else{
                logger.error("删除失败，考试进度编号不能为空");
                return new CommonResponse("删除失败，考试进度编号不能为空",3);
            }
        }catch (Exception e){
            logger.error("删除失败，原因："+e.getMessage());
            return new CommonResponse("删除失败",3,e);
        }
    }

    /**
     * 分页查询考试
     * @param req 分页条件
     * @return 考试列表
     */
    @RequestMapping(value="/getExams",method=RequestMethod.GET)
    public CommonResponse getExams(ExamSearchReq req){
        updateState();
        int page=req.getPage()==0?0:req.getPage()-1;
        int pageSize=req.getPageSize()==0?10:req.getPageSize();
        List<Exam> allExam=examServer.getAll(req);
        int total=allExam.size();
        int totalPage=total%pageSize==0?total/pageSize:total/pageSize+1;
        List<Exam> exams=examServer.findExamCriteria(page,pageSize,req);
        CommonResponse response= new CommonResponse("获取成功","info",exams);
        response.addNewDate("pageNum",page+1);
        response.addNewDate("pageSize",pageSize);
        response.addNewDate("total",total);
        response.addNewDate("totalPage",totalPage);
        return response;
    }

    /**
     * 分页查询考试进度
     * @param req 分页条件
     * @return 进度列表
     */
    @RequestMapping(value="/getExamSchedules",method=RequestMethod.GET)
    public CommonResponse getExamSchedules(ExamScheduleSearchReq req){
        updateState();
        int page=req.getPage()==0?0:req.getPage()-1;
        int pageSize=req.getPageSize()==0?10:req.getPageSize();
        List<ExamSchedule> allExamSchedule=examScheduleServer.getAll(req);
        int total=allExamSchedule.size();
        int totalPage=total%pageSize==0?total/pageSize:total/pageSize+1;
        List<ExamSchedule> examSchedules=examScheduleServer.findExamScheduleCriteria(page,pageSize,req);
        List<ExamScheduleVo> examScheduleVos=new ArrayList<>();
        for(ExamSchedule examSchedule:examSchedules){
            ExamScheduleVo examScheduleVo=new ExamScheduleVo();
            BeanUtils.copyProperties(examSchedule,examScheduleVo);
            User user=userServer.getUserByUserCode(examSchedule.createBy);
            if(null!=user){
                examScheduleVo.setCreateName(user.getUserName());
            }
            examScheduleVos.add(examScheduleVo);
        }
        CommonResponse response= new CommonResponse("获取成功","info",examScheduleVos);
        response.addNewDate("pageNum",page+1);
        response.addNewDate("pageSize",pageSize);
        response.addNewDate("total",total);
        response.addNewDate("totalPage",totalPage);
        return response;
    }

    /**
     * 导出考试成绩
     * @param response
     * @param id 考试编号
     * @throws Exception
     */
    @RequestMapping(value = "/exportExamDetail", method = RequestMethod.GET)
    public void exportExamDetail(HttpServletResponse response,@RequestParam("id")String id) throws Exception {
        List<ExcelData> excelDatas = new ArrayList<>();
        ExcelData examDatailExcel = new ExcelData();
        Exam exam= (Exam) examServer.getObjectById(id);
        examDatailExcel.setName(exam.getTitle()+"考试成绩信息数据");
        List<String> titles = new ArrayList();
        titles.add("序号");
        titles.add("考试人姓名");
        titles.add("考试人帐号");
        titles.add("考试完成时间");
        titles.add("考试成绩");
        examDatailExcel.setTitles(titles);
        //添加列
        List<List<Object>> rows = new ArrayList();
        List<Object> row = null;
        List<ExamScheduleVo> examScheduleVos = new ArrayList<>();
        List<ExamSchedule> examSchedules=examScheduleServer.getAllByExanId(id);
        for(ExamSchedule examSchedule :examSchedules){
            ExamScheduleVo examScheduleVo=new ExamScheduleVo();
            BeanUtils.copyProperties(examSchedule,examScheduleVo);
            User user=userServer.getUserByUserCode(examSchedule.createBy);
            if(null!=user){
                examScheduleVo.setCreateName(user.getUserName());
            }
            examScheduleVos.add(examScheduleVo);
        }
        for (int i = 0; i < examScheduleVos.size(); i++) {
            row = new ArrayList();
            row.add((i+1));
            row.add(examScheduleVos.get(i).getCreateName());
            row.add(examScheduleVos.get(i).createBy);
            row.add(DateUtils.dateToDateString(examScheduleVos.get(i).getCreateTime(),DateUtils.ZHCN_DATATIMEF_STR));
            row.add(examScheduleVos.get(i).getPoint());
            rows.add(row);
        }
        examDatailExcel.setRows(rows);
        excelDatas.add(examDatailExcel);
        String fileName = "考试成绩信息" + DateUtils.dateToDateString(new Date(), DateUtils.ZHCN_DATATIMEF_STR) + ".xls";
        ExcelUtils.exportExcel(response, fileName, excelDatas);
    }

    /**
     * 根据时间修改状态
     */
    private void updateState(){
        List<Exam> examList=examServer.getAll();
        for (Exam exam : examList){
            if(new Date().after(exam.getStartTime())&&exam.getStatus()==0){
                exam.setStatus(1);
                Announcement announcement=new Announcement();
                announcement.setTitle("\""+exam.getTitle()+"\"考试通知");
                announcement.setContent("管理员发布了\""+exam.getTitle()+"\"的考试，请您尽快到学习-->考试板块完成考试！");
                announcement.setLevel("2,3");
                announcement.setStatus(1);
                announcementServer.save(announcement);
            }
            if(new Date().after(exam.getEndTime())){
                exam.setStatus(2);
            }
            examServer.save(exam);
        }
    }
}
