package com.code90.daliweb.controller;

import com.code90.daliweb.domain.Question;
import com.code90.daliweb.domain.User;
import com.code90.daliweb.request.exchange.QuestionChangeReq;
import com.code90.daliweb.request.exchange.QuestionSaveReq;
import com.code90.daliweb.request.exchange.QuestionSearchReq;
import com.code90.daliweb.response.CommonResponse;
import com.code90.daliweb.server.QuestionServer;
import com.code90.daliweb.server.UserServer;
import com.code90.daliweb.utils.DateUtils;
import com.code90.daliweb.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * 问题控制类
 * @author Ray Lin
 * @create 2018-10-01 23:32
 **/
@RestController
@RequestMapping("/daliweb/question")
public class QuestionController  {
    private static final Logger logger=LoggerFactory.getLogger(QuestionController.class);
    @Autowired
    private QuestionServer questionServer;
    @Autowired
    private UserServer userServer;

    /**
     * 提问
     * @param req 提问内容
     * @return 提问结果
     */
    @RequestMapping(value = "/addQuestion",method = RequestMethod.POST)
    public CommonResponse addQuestion(@RequestBody QuestionSaveReq req){
        try {
            Question question=new Question();
            BeanUtils.copyProperties(req,question);
            question.createBy=req.getCreateBy();
            questionServer.save(question);
            logger.info("保存成功");
            return new CommonResponse("保存成功");
        }catch (Exception e){
            logger.error("保存失败，原因："+e.getMessage());
            return new CommonResponse("保存失败",1,e);
        }
    }

    /**
     * 修改问题
     * @param req 修改问题
     * @return 修改结果
     */
    @RequestMapping(value="/editQuestion",method = RequestMethod.POST)
    public CommonResponse editQuestion(@RequestBody QuestionChangeReq req){
        try {
            Question question=(Question) questionServer.getObjectById(req.getId());
            BeanUtils.copyProperties(req,question);
            questionServer.save(question);
            logger.info("问题修改成功");
            return new CommonResponse("修改成功");
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 修改问题状态
     * @param ids 问题编号
     * @param status 状态
     * @param answer 答案
     * @param modifiedBy 答题人
     * @return 修改结果
     */
    @RequestMapping(value="/editState",method = RequestMethod.GET)
    public CommonResponse editState(@RequestParam("ids")String ids, @RequestParam("status")int status,String answer,String modifiedBy){
        try {
            if(!StringUtil.isEmpty(ids)) {
                String[] id_list = ids.split(",");
                for (String id : id_list) {
                    Question question = (Question) questionServer.getObjectById(id);
                    question.setStatus(status);
                    if (!StringUtil.isEmpty(answer)) {
                        question.setAnswer(answer);
                    }
                    if (!StringUtil.isEmpty(modifiedBy)) {
                        question.lastmodifiedBy = modifiedBy;
                    }
                    questionServer.save(question);
                }
                logger.info("问题修改成功");
                return new CommonResponse("修改成功");
            }else{
                logger.error("问题修改失败，编号不能为空");
                return new CommonResponse("修改失败",2);
            }
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 根据编号获取问题
     * @param id 编号
     * @return 问题
     */
    @RequestMapping(value="/getQuestionById",method=RequestMethod.GET)
    public CommonResponse getQuestionById(@RequestParam("id")String id){
        CommonResponse response=new CommonResponse("获取成功");
        Question question=(Question) questionServer.getObjectById(id);
        User user=userServer.getUserByUserCode(question.createBy);
        response.addNewDate("createBy",user.getUserName());
        if(!StringUtil.isEmpty(question.lastmodifiedBy)){
            user=userServer.getUserByUserCode(question.lastmodifiedBy);
            response.addNewDate("answerBy",user.getUserName());
        }
        if(question!=null){
            logger.info("获取成功");
             response.addNewDate("info",question);
             return response;
        }else {
            logger.error("获取失败");
            return new CommonResponse("获取失败",2);
        }
    }

    /**
     * 删除问题
     * @param ids 问题编号
     * @return 删除结果
     */
    @RequestMapping(value="/delQuestion",method = RequestMethod.DELETE)
    public CommonResponse delQuestion(@RequestParam("ids") String ids){
        try {
            if(!StringUtil.isEmpty(ids)){
                String[] id_list=ids.split(",");
                for (String id : id_list){
                    Question question=(Question) questionServer.getObjectById(id);
                    questionServer.delete(question);
                }
                logger.info("问题删除成功");
                return new CommonResponse("删除成功");
            }else{
                logger.error("删除失败，问题编号不能为空");
                return new CommonResponse("删除失败，问题编号不能为空",3);
            }
        }catch (Exception e){
            logger.error("删除失败，原因："+e.getMessage());
            return new CommonResponse("删除失败",3,e);
        }
    }

    /**
     * 分页查询内容
     * @param req 分页条件
     * @return 内容列表
     */
    @RequestMapping(value="/getQuestions",method=RequestMethod.GET)
    public CommonResponse getQuestions(QuestionSearchReq req){
        try{
            int page=req.getPage()==0?0:req.getPage()-1;
            int pageSize=req.getPageSize()==0?10:req.getPageSize();
            List<Question> allQuestion=questionServer.getAll(req);
            int total=allQuestion.size();
            int totalPage=total%pageSize==0?total/pageSize:total/pageSize+1;
            List<Question> learnContents=questionServer.findQuestionCriteria(page,pageSize,req);
            logger.info("获取成功");
            CommonResponse response= new CommonResponse("获取成功","info",learnContents);
            response.addNewDate("pageNum",page+1);
            response.addNewDate("pageSize",pageSize);
            response.addNewDate("total",total);
            response.addNewDate("totalPage",totalPage);
            return response;
        }catch (Exception e){
            logger.info("获取失败，原因："+e.getMessage());
            return new CommonResponse("获取失败",2,e);
        }
    }

    /**
     * 文件上传
     * @param file 文件
     * @return
     */
    @RequestMapping(value = "/fileUpload",method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse fileUpload(@RequestParam("file") MultipartFile file){
        if(file.isEmpty()){
            logger.error("上传失败，文件不能为空");
            return new CommonResponse("上传失败，文件不能为空",6);
        }
        String fileName = DateUtils.dateToDateString(new Date(),DateUtils.DATATIMEF_STR_MIS)+file.getOriginalFilename();
        String path = "./questionPic" ;
        File dest = new File(path + "/" + fileName);
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            BufferedOutputStream out = new BufferedOutputStream(
                    new FileOutputStream(dest));
            out.write(file.getBytes());
            out.close();
            logger.info("上传成功");
            return new CommonResponse("上传成功","fileName",dest.getPath());
        } catch (Exception e) {
            logger.error("上传失败，原因："+e.getMessage());
            return new CommonResponse("上传失败",6,e);
        }
    }

    /**
     * 下载图片
     * @param response
     * @param filePath 文件路径
     */
    @RequestMapping(value = "/download",method = RequestMethod.GET)
    public void downLoad(HttpServletResponse response, String filePath){
        File file = new File("./questionPic/"+filePath);

        if(file.exists()){ //判断文件父目录是否存在
            //response.setContentType("application/image/jpeg");  --强制下载
            //response.setHeader("Content-Disposition", "attachment;fileName=" + file.getName()); --强制下载
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
                try{
                    bis.close();
                    fis.close();
                }catch(Exception e){
                    logger.error("流关闭出错，原因："+e.getMessage());
                }
                logger.info("文件下载成功");
            } catch (Exception e) {
                logger.error("文件下载出错，原因："+e.getMessage());
            }
        }else{
            logger.error("文件不存在");
        }
    }
}
