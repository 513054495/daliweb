package com.code90.daliweb.controller;

import com.code90.daliweb.domain.LearnContent;
import com.code90.daliweb.domain.LearnContentVo;
import com.code90.daliweb.domain.LearnTopic;
import com.code90.daliweb.domain.LearnTopicVo;
import com.code90.daliweb.request.learn.LearnContentChangeReq;
import com.code90.daliweb.request.learn.LearnContentSaveReq;
import com.code90.daliweb.request.learn.LearnContentSearchReq;
import com.code90.daliweb.response.CommonResponse;
import com.code90.daliweb.server.LearnContentServer;
import com.code90.daliweb.server.LearnTopicServer;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 学习内容控制类
 * @author Ray Lin
 * @create 2018-10-01 16:10
 **/
@RestController
@RequestMapping("/daliweb/learnContent")
public class LearnContentController  {
    private static final Logger logger=LoggerFactory.getLogger(LearnContentController.class);
    @Autowired
    private LearnContentServer learnContentServer;
    @Autowired
    private LearnTopicServer learnTopicServer;

    /**
     * 新增
     * @param req 内容信息
     * @return 新增结果
     */
    @RequestMapping(value = "/addContent",method = RequestMethod.POST)
    public CommonResponse addContent(@RequestBody LearnContentSaveReq req){
        try {
            LearnContent learnContent=new LearnContent();
            BeanUtils.copyProperties(req,learnContent);
            learnContentServer.save(learnContent);
            logger.info("保存成功");
            return new CommonResponse("保存成功");
        }catch (Exception e){
            logger.error("保存失败，原因："+e.getMessage());
            return new CommonResponse("保存失败",1,e);
        }
    }

    /**
     * 修改内容
     * @param req 修改内容
     * @return 修改结果
     */
    @RequestMapping(value="/editContent",method = RequestMethod.POST)
    public CommonResponse editContent(@RequestBody LearnContentChangeReq req){
        try {
            LearnContent learnContent=(LearnContent) learnContentServer.getObjectById(req.getId());
            BeanUtils.copyProperties(req,learnContent);
            learnContentServer.save(learnContent);
            logger.info("内容修改成功");
            return new CommonResponse("修改成功");
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 修改主题
     * @param ids 主题编号
     * @param status 状态
     * @return 修改结果
     */
    @RequestMapping(value="/editState",method = RequestMethod.GET)
    public CommonResponse editState(@RequestParam("ids")String ids, @RequestParam("status")int status){
        try {
            if(!StringUtil.isEmpty(ids)) {
                String[] id_list = ids.split(",");
                for (String id : id_list) {
                    LearnContent learnContent = (LearnContent) learnContentServer.getObjectById(id);
                    learnContent.setStatus(status);
                    learnContentServer.save(learnContent);
                }
                logger.info("内容修改成功");
                return new CommonResponse("修改成功");
            }else{
                logger.error("内容修改失败，编号不能为空");
                return new CommonResponse("修改失败",2);
            }
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 置顶内容
     * @param id 内容编号
     * @param isTop 是否置顶
     * @return 修改结果
     */
    @RequestMapping(value="/topContent",method = RequestMethod.GET)
    public CommonResponse topContent(@RequestParam("id")String id,@RequestParam("isTop")int isTop ){
        try {
            LearnContent learnContent=(LearnContent) learnContentServer.getObjectById(id);
            if(isTop==1) {
                int maxLevel = learnContentServer.getMaxLevel();
                learnContent.setLevel(maxLevel + 1);
                learnContent.setIsTop(1);
                learnContentServer.save(learnContent);
                logger.info("内容置顶成功");
                return new CommonResponse("置顶成功");
            }else{
                learnContent.setLevel(0);
                learnContent.setIsTop(0);
                learnContentServer.save(learnContent);
                logger.info("内容取消置顶成功");
                return new CommonResponse("取消置顶成功");
            }
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 根据编号获取内容
     * @param id 编号
     * @return 内容
     */
    @RequestMapping(value="/getContentById",method=RequestMethod.GET)
    public CommonResponse getContentById(@RequestParam("id")String id){
        try{
            LearnContent learnContent=(LearnContent) learnContentServer.getObjectById(id);
            LearnContentVo learnContentVo=new LearnContentVo();
            BeanUtils.copyProperties(learnContent,learnContentVo);
            LearnTopic childTopic= (LearnTopic) learnTopicServer.getObjectById(learnContent.getTopicId());
            LearnTopic parentTopic= (LearnTopic) learnTopicServer.getObjectById(childTopic.getParentId());
            learnContentVo.setTopicName(childTopic.getTopicName());
            if(null!=parentTopic){
                learnContentVo.setParentId(parentTopic.getId());
                learnContentVo.setParentName(parentTopic.getTopicName());
            }
            if(learnContentVo!=null){
                logger.info("获取成功");
                return new CommonResponse("获取成功","info",learnContentVo);
            }else {
                logger.error("获取失败");
                return new CommonResponse("获取失败",2);
            }
        }catch (Exception e){
            logger.error("获取失败,原因："+e.getMessage());
            return new CommonResponse("获取失败",2,e);
        }
    }

    /**
     * 根据主题父编号获取内容
     * @param id 编号
     * @return 内容
     */
    @RequestMapping(value="/getTopicParentById",method=RequestMethod.GET)
    public CommonResponse getTopicParentById(@RequestParam("id")String id){
        try{
            LearnTopic parentlearnTopic= (LearnTopic) learnTopicServer.getObjectById(id);
            List<LearnTopic> learnTopics=learnTopicServer.getLearnTopicByParentId(id,0);
            learnTopics.add(parentlearnTopic);
            List<LearnContentVo> learnContentVos=new ArrayList<>();
            for(LearnTopic learnTopic :learnTopics){
                List<LearnContent> learnContents=learnContentServer.getLearnContentByTopicId(learnTopic.getId());
                for (LearnContent learnContent:learnContents){
                    LearnContentVo learnContentVo=new LearnContentVo();
                    BeanUtils.copyProperties(learnContent,learnContentVo);
                    learnContentVo.setTopicName(learnTopic.getTopicName());
                    LearnTopic parentTopic= (LearnTopic) learnTopicServer.getObjectById(learnTopic.getParentId());
                    if(null!=parentTopic){
                        learnContentVo.setParentId(parentTopic.getId());
                        learnContentVo.setParentName(parentTopic.getTopicName());
                    }
                    learnContentVos.add(learnContentVo);
                }
            }
            logger.info("获取成功");
            return new CommonResponse("获取成功","info",learnContentVos);
        }catch (Exception e){
            logger.error("获取失败,原因："+e.getMessage());
            return new CommonResponse("获取失败",2,e);
        }
    }

    /**
     * 删除内容
     * @param ids 内容编号
     * @return 删除结果
     */
    @RequestMapping(value="/delContent",method = RequestMethod.DELETE)
    public CommonResponse delContent(@RequestParam("ids") String ids){
        try {
            if(!StringUtil.isEmpty(ids)){
                String[] id_list=ids.split(",");
                for (String id : id_list){
                    LearnContent learnContent=(LearnContent) learnContentServer.getObjectById(id);
                    learnContentServer.delete(learnContent);
                }
                logger.info("内容删除成功");
                return new CommonResponse("删除成功");
            }else{
                logger.error("删除失败，内容编号不能为空");
                return new CommonResponse("删除失败，内容编号不能为空",3);
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
    @RequestMapping(value="/getContents",method=RequestMethod.GET)
    public CommonResponse getContents(LearnContentSearchReq req){
        int page=req.getPage()==0?0:req.getPage()-1;
        int pageSize=req.getPageSize()==0?10:req.getPageSize();
        List<LearnContent> allLearnContent=learnContentServer.getAll(req);
        int total=allLearnContent.size();
        int totalPage=total%pageSize==0?total/pageSize:total/pageSize+1;
        List<LearnContent> learnContents=learnContentServer.findLearnContentCriteria(page,pageSize,req);
        List<LearnContentVo> learnContentVos=new ArrayList<>();
        for(LearnContent learnContent : learnContents){
            LearnContentVo learnContentVo=new LearnContentVo();
            BeanUtils.copyProperties(learnContent,learnContentVo);
            LearnTopic learnTopic=(LearnTopic) learnTopicServer.getObjectById(learnContent.getTopicId());
            if(null!=learnTopic){
                learnContentVo.setTopicName(learnTopic.getTopicName());
                LearnTopic parentTopic= (LearnTopic) learnTopicServer.getObjectById(learnTopic.getParentId());
                if(null!=parentTopic){
                    learnContentVo.setParentId(parentTopic.getId());
                    learnContentVo.setParentName(parentTopic.getTopicName());
                }
            }
            learnContentVos.add(learnContentVo);
        }
        CommonResponse response= new CommonResponse("获取成功","info",learnContentVos);
        response.addNewDate("pageNum",page+1);
        response.addNewDate("pageSize",pageSize);
        response.addNewDate("total",total);
        response.addNewDate("totalPage",totalPage);
        return response;
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
        String path = "./learnContentFiles" ;
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
    public void downLoad(HttpServletResponse response, String filePath,int isDownload){
        File file = new File("./learnContentFiles/"+filePath);

        if(file.exists()){ //判断文件父目录是否存在
            //response.setContentType("application/image/jpeg");  --强制下载
            //response.setHeader("Content-Disposition", "attachment;fileName=" + file.getName()); --强制下载
            if(isDownload==1){
                response.setHeader("content-type", "application/octet-stream");
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;fileName=" + file.getName());
            }
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
