package com.code90.daliweb.controller;

import com.code90.daliweb.domain.*;
import com.code90.daliweb.request.learn.SubjectChangeReq;
import com.code90.daliweb.request.learn.SubjectSaveReq;
import com.code90.daliweb.request.learn.SubjectSearchReq;
import com.code90.daliweb.response.CommonResponse;
import com.code90.daliweb.server.*;
import com.code90.daliweb.utils.IdUtils;
import com.code90.daliweb.utils.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * 题目控制类
 * @author Ray Lin
 * @create 2018-09-24 1:56
 **/
@RestController
@RequestMapping("/daliweb/subject")
public class SubjectController {
    private static final Logger logger=LoggerFactory.getLogger(UserController.class);

    @Autowired
    private SubjectServer subjectServer;
    @Autowired
    private SubjectScheduleServer subjectScheduleServer;
    @Autowired
    private UserServer userServer;
    @Autowired
    private WorkServer workServer;
    @Autowired
    private WorkScheduleServer workScheduleServer;
    @Autowired
    private WorkDetailServer workDetailServer;

    /**
     * 新增
     * @param req 题目信息
     * @return 新增结果
     */
    @RequestMapping(value = "/addSubject",method = RequestMethod.POST)
    public CommonResponse addSubject(@RequestBody SubjectSaveReq req){
        try {
            Subject subject=new Subject();
            BeanUtils.copyProperties(req,subject);
            int subjectId=subjectServer.getMaxSubject();
            subject.setId(IdUtils.createSubjectId(subjectId));
            subjectServer.save(subject);
            logger.info("保存成功");
            return new CommonResponse("保存成功");
        }catch (Exception e){
            logger.error("保存失败，原因："+e.getMessage());
            return new CommonResponse("保存失败",1,e);
        }
    }

    /**
     * 修改题目
     * @param req 修改信息
     * @return 修改结果
     */
    @RequestMapping(value="/editSubject",method = RequestMethod.POST)
    public CommonResponse editSubject(@RequestBody SubjectChangeReq req){
        try {
            Subject subject=(Subject) subjectServer.getObjectById(req.getId()+"");
            BeanUtils.copyProperties(req,subject);
            subjectServer.save(subject);
            logger.info("题目修改成功");
            return new CommonResponse("修改成功");
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }
    /**
     * 根据题目编号获取题目
     * @param id 题目编号
     * @return 题目信息
     */
    @RequestMapping(value="/getSubject",method=RequestMethod.GET)
    public CommonResponse getSubject(@RequestParam("id") String id){
        Subject subject=(Subject) subjectServer.getObjectById(id);
        if(subject!=null){
            return new CommonResponse("获取题目成功","info",subject);
        }else{
            return new CommonResponse("获取失败，题目不存在",5);
        }
    }

    /**
     * 分页查询题目
     * @param req 分页条件
     * @return 题目列表
     */
    @RequestMapping(value="/getSubjects",method=RequestMethod.GET)
    public CommonResponse getSubjects(SubjectSearchReq req){
        int page=req.getPage()==0?0:req.getPage()-1;
        int pageSize=req.getPageSize()==0?10:req.getPageSize();
        List<Subject> allSubject=subjectServer.getAll(req);
        int total=allSubject.size();
        int totalPage=total%pageSize==0?total/pageSize:total/pageSize+1;
        List<Subject> subjects=subjectServer.findSubjectCriteria(page,pageSize,req);
        CommonResponse response= new CommonResponse("获取成功","info",subjects);
        response.addNewDate("pageNum",page+1);
        response.addNewDate("pageSize",pageSize);
        response.addNewDate("total",total);
        response.addNewDate("totalPage",totalPage);
        return response;
    }

    /**
     * 删除题目
     * @param ids 题目编号
     * @return 删除结果
     */
    @RequestMapping(value="/delSubject",method = RequestMethod.DELETE)
    public CommonResponse delSubject(@RequestParam("ids") String ids){
        try {
            if(!StringUtil.isEmpty(ids)){
                String[] id_list=ids.split(",");
                for (String id : id_list){
                    Subject subject=(Subject) subjectServer.getObjectById(id);
                    subjectServer.delete(subject);
                }
                logger.info("题目删除成功");
                return new CommonResponse("删除成功");
            }else{
                logger.error("删除失败，题目编号不能为空");
                return new CommonResponse("删除失败，题目编号不能为空",3);
            }
        }catch (Exception e){
            logger.error("删除失败，原因："+e.getMessage());
            return new CommonResponse("删除失败",3,e);
        }
    }

    /**
     * 导入题目
     * @param file 题目文件
     * @return 导入结果
     */
    @RequestMapping(value="/impSubject",method = RequestMethod.POST)
    public CommonResponse addUser(@RequestParam("file") MultipartFile file) {
        boolean flag= false;
        String fileName = file.getOriginalFilename();
        try {
            flag = batchImport(fileName, file);
            if(flag){
                logger.info("导入成功");
                return new CommonResponse("导入成功");
            }else{
                logger.info("导入失败");
                return new CommonResponse("导入失败",4);
            }
        } catch (Exception e) {
            logger.error("导入失败，原因："+e.getMessage());
            return new CommonResponse("导入失败",4,e);
        }
    }

    /**
     * 出题
     * @param userCode 用户
     * @param subjectType 出题类型
     * @param workId 作业编号（非必填）
     * @return 题目
     */
    @RequestMapping(value="/problem",method = RequestMethod.GET)
    public CommonResponse problem(@RequestParam("userCode")String userCode,@RequestParam("subjectType")int subjectType,String workId,String subjectId) {
        try{
            //全部题目
            List<Subject> subjects=new ArrayList<>();
            CommonResponse response = new CommonResponse("出题成功");
            if(subjectType==0){
                int currentId=0;
                //查询汉学团题库题目
                subjects=subjectServer.getAllByAnwser();
                if(subjects.size()==0){
                    logger.info("出题失败,原因：题库没有题");
                    return new CommonResponse("出题失败，题库没有题",6);
                }
                //查询当前学员进度
                SubjectSchedule subjectSchedule=subjectScheduleServer.getScheduleByUserCode(userCode);
                //如果学员没进度，则从最小题号答题；如果有进度，则从当前进度答题
                if(subjectSchedule==null){
                    currentId=subjectServer.getMixSubjectByAnswer();
                    subjectSchedule=new SubjectSchedule();
                    subjectSchedule.setCorrectNum(0);
                    subjectSchedule.setNum(0);
                    subjectSchedule.setSubjectId(currentId);
                    subjectSchedule.setCreateBy(userCode);
                    subjectScheduleServer.save(subjectSchedule);
                }else{
                    currentId=subjectSchedule.getSubjectId();
                }
                response.addNewDate("answerNum", subjectSchedule.getNum());
                response.addNewDate("collectNum", subjectSchedule.getCorrectNum());
                if(currentId!=0) {
                    Subject subject=(Subject) subjectServer.getObjectById(currentId+"");
                    subject.setAnswer("");
                    subject.setAnswerAnalysis("");
                    logger.info("出题成功");
                    response.addNewDate("info", subject);
                    response.addNewDate("totalNum", subjects.size());
                }else{
                    logger.info("出题失败,获取不到题目");
                    return new CommonResponse("出题失败",6);
                }
            }else if(subjectType==1){
                //查看初级全部题目
                subjects=subjectServer.getAllByRandom();
                if(subjects.size()==0){
                    logger.info("出题失败,原因：题库没有题");
                    return new CommonResponse("出题失败，题库没有题",6);
                }
                //查询随机题库最大编号
                int size=subjects.size();
                //通过最大编号获取随机题目编号
                int current=new Random().nextInt(size);
                Subject subject=subjects.get(current);
                subject.setAnswer("");
                subject.setAnswerAnalysis("");
                logger.info("出题成功");
                response.addNewDate("info", subject);
                response.addNewDate("totalNum", subjects.size());
            }else if(subjectType==2){
                subjects=subjectServer.getAllByExam();
                int count= subjects.size()>100?100:subjects.size();
                List<Subject> allExamSubjects=new ArrayList<>();
                for(int i=0;i<count;i++){
                    int maxId=subjectServer.getMaxSubjectByExam();
                    getRandomSubject(maxId,allExamSubjects);
                }
                response.addNewDate("info",allExamSubjects);
                response.addNewDate("totalNum",count);
            }else if(subjectType==3){
                Work work= (Work) workServer.getObjectById(workId);
                WorkSchedule workSchedule=workScheduleServer.getAllByWorkIdAndCreateBy(work.getId(),userCode);
                if(null!=workSchedule) {
                    WorkDetail workDetail = workDetailServer.getAllByWorkScheduleIdAndCreateByAndSubjectId(workSchedule.getId(),userCode,Integer.parseInt(subjectId));
                    String[] subjectIds=work.getSubjects().split(",");
                    Subject subject=(Subject) subjectServer.getObjectById(Integer.parseInt(subjectId)+"");
                    subject.setAnswer("");
                    subject.setAnswerAnalysis("");
                    if(subject.getType()==3){
                        if(!StringUtil.isEmpty(subject.getSubA())) {
                            subject.setSubA("hasAnswer");
                        }
                        if(!StringUtil.isEmpty(subject.getSubB())) {
                            subject.setSubB("hasAnswer");
                        }
                        if(!StringUtil.isEmpty(subject.getSubC())) {
                            subject.setSubC("hasAnswer");
                        }
                        if(!StringUtil.isEmpty(subject.getSubD())) {
                            subject.setSubD("hasAnswer");
                        }
                    }
                    int isAnswer=0;
                    if(null!=workDetail){
                        response.addNewDate("answer", workDetail.getAnswer());
                        response.addNewDate("workDetailId",workDetail.getId());
                        isAnswer=1;
                    }
                    logger.info("出题成功");
                    response.addNewDate("isAnswer",isAnswer);
                    response.addNewDate("info", subject);
                    response.addNewDate("totalNum",subjectIds.length);
                    response.addNewDate("subjectIds",work.getSubjects());
                }
            }
            return response;
        }catch (Exception e){
            logger.info("出题失败,原因："+e.getMessage());
            return new CommonResponse("出题失败",6,e);
        }
    }

    private void getRandomSubject(int maxId, List<Subject> allExamSubjects) {
        int currentId=new Random().nextInt(maxId);
        boolean flag=true;
        while(flag){
            int count=allExamSubjects.size();
            Subject subject= (Subject) subjectServer.getObjectById(currentId+"");
            if(null!=subject && subject.getType()!=3 &&subject.getType()!=4 && subject.getSubjectType()==2){
                subject.setAnswer("");
                subject.setAnswerAnalysis("");
                if(count==0){
                    allExamSubjects.add(subject);
                    flag=false;
                }else{
                    for(int i=0;i<allExamSubjects.size();i++){
                        if(currentId==allExamSubjects.get(i).getId()){
                            if(currentId<maxId) {
                                currentId++;
                            }else{
                                currentId=0;
                            }
                            break;
                        }
                        if(i==(allExamSubjects.size()-1)){
                            allExamSubjects.add(subject);
                            flag=false;
                            break;
                        }
                    }
                }
            }else{
                if(currentId<maxId) {
                    currentId++;
                }else{
                    currentId=0;
                }
            }
        }
    }

    /**
     * 答题
     * @param userCode 答题用户
     * @param id 答题ID
     * @param answer 答案
     * @return 答题结果
     */
    @RequestMapping(value="/answer",method = RequestMethod.GET)
    public CommonResponse answer(@RequestParam("userCode")String userCode,@RequestParam("id")int id,@RequestParam("answer")String answer,
                                 @RequestParam("subjectType")int subjectType) {
        CommonResponse response=new CommonResponse("答题成功");
       try{
           //根据题号获取题目
           Subject subject=(Subject) subjectServer.getObjectById(id+"");
           //答题结果
           boolean flag=false;
           //判断是否正确
           if (subject.getAnswer().equals(answer)) {
               flag=true;
               //修改题目正确率
               subject.setNum(subject.getNum()+1);
               subject.setCorrectNum(subject.getCorrectNum()+1);
               subjectServer.save(subject);
               if(subjectType==0){
                   //获取当前学员的进度
                   SubjectSchedule subjectSchedule = subjectScheduleServer.getScheduleByUserCode(userCode);
                   int maxNo=subjectServer.getMaxSubject();
                   while(id<maxNo){
                       int nextId=id+1;
                       Subject nextSubject= (Subject) subjectServer.getObjectById(nextId+"");
                       if(nextSubject.getType()!=3&&nextSubject.getType()!=4){
                           subjectSchedule.setSubjectId((nextId));
                           break;
                       }else{
                           id++;
                       }
                   }
                   //答题正确，进度内容更新
                   subjectSchedule.setNum(subjectSchedule.getNum() + 1);
                   subjectSchedule.setCorrectNum(subjectSchedule.getCorrectNum() + 1);
                   subjectScheduleServer.save(subjectSchedule);
                   //根据当前获取用户，增加学分
                   User user=userServer.getUserByUserCode(userCode);
                   user.setPoint(user.getPoint()+subject.getPoint());
                   userServer.save(user);
                   response.addNewDate("answerNum",subjectSchedule.getNum());
                   response.addNewDate("collectNum",subjectSchedule.getCorrectNum());
               }
           }else {
               flag=false;
               //修改题目正确率
               subject.setNum(subject.getNum()+1);
               subjectServer.save(subject);
               if(subjectType==0){
                   //获取当前学员的进度
                   SubjectSchedule subjectSchedule = subjectScheduleServer.getScheduleByUserCode(userCode);
                   int maxNo=subjectServer.getMaxSubject();
                   while(id<maxNo){
                       int nextId=id+1;
                       Subject nextSubject= (Subject) subjectServer.getObjectById(nextId+"");
                       if(nextSubject.getType()!=3&&nextSubject.getType()!=4){
                           subjectSchedule.setSubjectId((nextId));
                           break;
                       }else{
                           id++;
                       }
                   }
                   //答题错误，进度内容更新
                   subjectSchedule.setNum(subjectSchedule.getNum() + 1);
                   subjectScheduleServer.save(subjectSchedule);
                   response.addNewDate("answerNum",subjectSchedule.getNum());
                   response.addNewDate("collectNum",subjectSchedule.getCorrectNum());
               }
           }
           response.addNewDate("flag",flag);
           response.addNewDate("info",subject);
           return response;
       }catch (Exception e){
           logger.info("答题失败,原因："+e.getMessage());
           return new CommonResponse("答题失败",7,e);
       }
    }

    /**
     * 根据用户帐号获取答题进度
     * @param userCode 用户帐号
     * @return 答题进度
     */
    @RequestMapping(value="/getSubjectSchedule",method=RequestMethod.GET)
    public CommonResponse getSubjectSchedule(@RequestParam("userCode") String userCode){
        try{
            //查询汉学团题库题目
            List<Subject> subjects=subjectServer.getAllByAnwser();
            User user=userServer.getUserByUserCode(userCode);
            SubjectSchedule subjectSchedule=subjectScheduleServer.getScheduleByUserCode(userCode);
            if(subjectSchedule==null){
                int currentId=subjectServer.getMixSubjectByAnswer();
                subjectSchedule=new SubjectSchedule();
                subjectSchedule.setCorrectNum(0);
                subjectSchedule.setNum(0);
                subjectSchedule.setSubjectId(currentId);
                subjectSchedule.setCreateBy(userCode);
                subjectScheduleServer.save(subjectSchedule);
                subjectSchedule=subjectScheduleServer.getScheduleByUserCode(userCode);
            }
            CommonResponse response= new CommonResponse("获取进度成功","info",subjectSchedule);
            if(null!=user){
                response.addNewDate("point",user.getPoint());
                response.addNewDate("totalNum",subjects.size());
            }
            logger.info("获取成功");
            return response;
        }catch (Exception e){
            logger.error("获取失败，原因："+e.getMessage());
            return new CommonResponse("获取失败",5,e);
        }
    }


    /**
     * 导入文件
     * @param fileName 文件名
     * @param file 文件
     * @return 导入成功
     * @throws Exception
     */
    private boolean batchImport(String fileName, MultipartFile file) throws Exception {
        boolean notNull = false;
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new Exception("上传文件格式不正确");
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if(sheet!=null){
            notNull = true;
        }
        Subject subject;
        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null){
                continue;
            }
            subject=new Subject();
            subject.setSubjectName(row.getCell(0).getStringCellValue());
            subject.setType((int)row.getCell(1).getNumericCellValue());
            subject.setSubjectType((int)row.getCell(2).getNumericCellValue());
            if(null!=row.getCell(3)) {
                subject.setSubA(row.getCell(3).getStringCellValue());
            }
            if(null!=row.getCell(4)) {
                subject.setSubB(row.getCell(4).getStringCellValue());
            }
            if(null!=row.getCell(5)) {
                subject.setSubC(row.getCell(5).getStringCellValue());
            }
            if(null!=row.getCell(6)) {
                subject.setSubD(row.getCell(6).getStringCellValue());
            }
            if(null!=row.getCell(7)) {
                subject.setAnswer(row.getCell(7).getStringCellValue());
            }
            if(null!=row.getCell(8)) {
                subject.setAnswerAnalysis(row.getCell(8).getStringCellValue());
            }
            subject.setPoint((int)row.getCell(9).getNumericCellValue());
            int subjectId=subjectServer.getMaxSubject();
            subject.setId(IdUtils.createSubjectId(subjectId));
            subjectServer.save(subject);
        }
        return notNull;
    }

    @RequestMapping(value = "/download",method = RequestMethod.GET)
    public void downLoad(HttpServletResponse response,String filename){
        String filePath = "./downloadFile" ;
        File file = new File(filePath + "/" + filename);
        if(file.exists()){ //判断文件父目录是否存在
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" + filename);
            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;
            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                logger.error("下载失败，原因："+e.getMessage());
            }
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                logger.error("下载失败，原因："+e.getMessage());
            }
        }
    }
}
