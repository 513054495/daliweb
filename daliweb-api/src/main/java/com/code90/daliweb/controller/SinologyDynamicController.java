package com.code90.daliweb.controller;

import com.code90.daliweb.domain.*;
import com.code90.daliweb.request.exchange.ForumSearchReq;
import com.code90.daliweb.request.exchange.SinologyDynamicChangeReq;
import com.code90.daliweb.request.exchange.SinologyDynamicSaveReq;
import com.code90.daliweb.request.exchange.SinologyDynamicSearchReq;
import com.code90.daliweb.request.learn.LearnTopicChangeReq;
import com.code90.daliweb.response.CommonResponse;
import com.code90.daliweb.server.SinologyDynamicServer;
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
 * 动态管理控制类
 * @author Ray Lin
 * @create 2019-01-13 22:37
 **/
@RestController
@RequestMapping("/daliweb/sinologyDynamic")
public class SinologyDynamicController {
    private static final Logger logger=LoggerFactory.getLogger(SinologyDynamicController.class);

    @Autowired
    private SinologyDynamicServer sinologyDynamicServer;

    /**
     * 新增动态
     * @param req 动态信息
     * @return 新增结果
     */
    @RequestMapping(value = "/addSinologyDynamic",method = RequestMethod.POST)
    public CommonResponse addSinologyDynamic(@RequestBody SinologyDynamicSaveReq req){
        try {
            SinologyDynamic sinologyDynamic=new SinologyDynamic();
            BeanUtils.copyProperties(req,sinologyDynamic);
            sinologyDynamic.createBy=req.getCreateBy();
            sinologyDynamicServer.save(sinologyDynamic);
            logger.info("保存成功");
            return new CommonResponse("保存成功");
        }catch (Exception e){
            logger.error("保存失败，原因："+e.getMessage());
            return new CommonResponse("保存失败",1,e);
        }
    }

    /**
     * 修改动态
     * @param req 修改动态
     * @return 修改结果
     */
    @RequestMapping(value="/editSinologyDynamic",method = RequestMethod.POST)
    public CommonResponse editSinologyDynamic(@RequestBody SinologyDynamicChangeReq req){
        try {
            SinologyDynamic sinologyDynamic= (SinologyDynamic) sinologyDynamicServer.getObjectById(req.getId());
            BeanUtils.copyProperties(req,sinologyDynamic);
            sinologyDynamicServer.save(sinologyDynamic);
            logger.info("动态修改成功");
            return new CommonResponse("修改成功");
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 修改状态
     * @param ids 编号
     * @param status 状态
     * @return 修改状态
     */
    @RequestMapping(value="/editState",method = RequestMethod.GET)
    public CommonResponse editState(@RequestParam("ids")String ids,@RequestParam("status")int status){
        if(!StringUtil.isEmpty(ids)){
            String[] id_list=ids.split(",");
            for (String id : id_list){
                SinologyDynamic sinologyDynamic=(SinologyDynamic) sinologyDynamicServer.getObjectById(id);
                sinologyDynamic.setStatus(status);
                sinologyDynamicServer.save(sinologyDynamic);
            }
            logger.info("动态修改成功");
            return new CommonResponse("修改成功");
        }else{
            logger.error("修改失败，动态编号不能为空");
            return new CommonResponse("修改失败，动态编号不能为空",3);
        }
    }

    /**
     * 根据编号获取动态
     * @param id 编号
     * @return 动态
     */
    @RequestMapping(value="/getSinologyDynamicById",method=RequestMethod.GET)
    public CommonResponse getSinologyDynamicById(@RequestParam("id")String id){
        CommonResponse response=new CommonResponse("获取成功");
        SinologyDynamic sinologyDynamic= (SinologyDynamic) sinologyDynamicServer.getObjectById(id);
        if(sinologyDynamic!=null){
            logger.info("获取成功");
            response.addNewDate("info",sinologyDynamic);
            return response;
        }else {
            logger.error("获取失败");
            return new CommonResponse("获取失败",2);
        }
    }

    /**
     * 删除动态
     * @param ids 编号
     * @return 删除结果
     */
    @RequestMapping(value="/delSinologyDynamic",method = RequestMethod.DELETE)
    public CommonResponse delSinologyDynamic(@RequestParam("ids") String ids){
        try {
            if(!StringUtil.isEmpty(ids)){
                String[] id_list=ids.split(",");
                for (String id : id_list){
                    SinologyDynamic sinologyDynamic=(SinologyDynamic) sinologyDynamicServer.getObjectById(id);
                    sinologyDynamicServer.delete(sinologyDynamic);
                }
                logger.info("动态删除成功");
                return new CommonResponse("删除成功");
            }else{
                logger.error("删除失败，动态编号不能为空");
                return new CommonResponse("删除失败，动态编号不能为空",3);
            }
        }catch (Exception e){
            logger.error("删除失败，原因："+e.getMessage());
            return new CommonResponse("删除失败",3,e);
        }
    }

    /**
     * 分页查询动态
     * @param req 分页条件
     * @return 动态列表
     */
    @RequestMapping(value="/getSinologyDynamics",method=RequestMethod.GET)
    public CommonResponse getSinologyDynamics(SinologyDynamicSearchReq req){
        try{
            int page=req.getPage()==0?0:req.getPage()-1;
            int pageSize=req.getPageSize()==0?10:req.getPageSize();
            List<SinologyDynamic> allSinologyDynamic=sinologyDynamicServer.getAll(req);
            int total=allSinologyDynamic.size();
            int totalPage=total%pageSize==0?total/pageSize:total/pageSize+1;
            List<SinologyDynamic> sinologyDynamics=sinologyDynamicServer.findSinologyDynamicCriteria(page,pageSize,req);
            logger.info("获取成功");
            CommonResponse response= new CommonResponse("获取成功","info",sinologyDynamics);
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
        String path = "./sinologyDynamics" ;
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
    public void downLoad(HttpServletResponse response, String filePath, int isDownload){
        File file = new File("./sinologyDynamics/"+filePath);

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
