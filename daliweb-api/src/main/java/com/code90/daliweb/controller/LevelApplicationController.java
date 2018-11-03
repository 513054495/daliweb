package com.code90.daliweb.controller;

import com.code90.daliweb.domain.LevelApplication;
import com.code90.daliweb.request.exchange.*;
import com.code90.daliweb.response.CommonResponse;
import com.code90.daliweb.server.LevelApplicationServer;
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
 * 考级申请控制类
 * @author Ray Lin
 * @create 2018-10-16 23:21
 **/
@RestController
@RequestMapping("/daliweb/levelApplication")
public class LevelApplicationController {
    private static final Logger logger=LoggerFactory.getLogger(LevelApplicationController.class);
    @Autowired
    private LevelApplicationServer levelApplicationServer;

    /**
     * 考级申请
     * @param req 考级申请内容
     * @return 新增结果
     */
    @RequestMapping(value = "/addLevelApplication",method = RequestMethod.POST)
    public CommonResponse addLevelApplication(@RequestBody LevelApplicationSaveReq req){
        try {
            LevelApplication levelApplication=new LevelApplication();
            BeanUtils.copyProperties(req,levelApplication);
            levelApplication.createBy=req.getCreateBy();
            levelApplicationServer.save(levelApplication);
            logger.info("保存成功");
            return new CommonResponse("保存成功");
        }catch (Exception e){
            logger.error("保存失败，原因："+e.getMessage());
            return new CommonResponse("保存失败",1,e);
        }
    }

    /**
     * 修改考级申请
     * @param req 考级申请问题
     * @return 修改结果
     */
    @RequestMapping(value="/editLevelApplication",method = RequestMethod.POST)
    public CommonResponse editLevelApplication(@RequestBody LevelApplicationChangeReq req){
        try {
            LevelApplication levelApplication=(LevelApplication) levelApplicationServer.getObjectById(req.getId());
            BeanUtils.copyProperties(req,levelApplication);
            levelApplicationServer.save(levelApplication);
            logger.info("考级申请修改成功");
            return new CommonResponse("修改成功");
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 修改考级申请状态
     * @param ids 考级申请编号
     * @param status 状态
     * @return 修改结果
     */
    @RequestMapping(value="/editState",method = RequestMethod.GET)
    public CommonResponse editState(@RequestParam("ids")String ids, @RequestParam("status")int status){
        try {
            if(!StringUtil.isEmpty(ids)) {
                String[] id_list = ids.split(",");
                for (String id : id_list) {
                    LevelApplication levelApplication = (LevelApplication) levelApplicationServer.getObjectById(id);
                    levelApplication.setStatus(status);
                    levelApplicationServer.save(levelApplication);
                }
                logger.info("考级申请修改成功");
                return new CommonResponse("修改成功");
            }else{
                logger.error("考级申请修改失败，编号不能为空");
                return new CommonResponse("修改失败",2);
            }
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 根据编号获取考级申请
     * @param id 编号
     * @return 考级申请
     */
    @RequestMapping(value="/getLevelApplicationById",method=RequestMethod.GET)
    public CommonResponse getLevelApplicationById(@RequestParam("id")String id){
        CommonResponse response=new CommonResponse("获取成功");
        LevelApplication levelApplication=(LevelApplication) levelApplicationServer.getObjectById(id);
        if(levelApplication!=null){
            logger.info("获取成功");
            response.addNewDate("info",levelApplication);
            return response;
        }else {
            logger.error("获取失败");
            return new CommonResponse("获取失败");
        }
    }

    /**
     * 根据用户获取考级申请
     * @param userCode 编号
     * @return 考级申请
     */
    @RequestMapping(value="/getByUserCode",method=RequestMethod.GET)
    public CommonResponse getByUserCode(@RequestParam("userCode")String userCode){
        List<LevelApplication> levelApplications=levelApplicationServer.getByUserCode(userCode);
        return new CommonResponse("获取成功","info",levelApplications);
    }

    /**
     * 删除考级申请
     * @param ids 考级申请编号
     * @return 删除结果
     */
    @RequestMapping(value="/delLevelApplication",method = RequestMethod.DELETE)
    public CommonResponse delLevelApplication(@RequestParam("ids") String ids){
        try {
            if(!StringUtil.isEmpty(ids)){
                String[] id_list=ids.split(",");
                for (String id : id_list){
                    LevelApplication levelApplication=(LevelApplication) levelApplicationServer.getObjectById(id);
                    levelApplicationServer.delete(levelApplication);
                }
                logger.info("考级申请删除成功");
                return new CommonResponse("删除成功");
            }else{
                logger.error("删除失败，考级申请编号不能为空");
                return new CommonResponse("删除失败，考级申请编号不能为空",3);
            }
        }catch (Exception e){
            logger.error("删除失败，原因："+e.getMessage());
            return new CommonResponse("删除失败",3,e);
        }
    }

    /**
     * 分页查询考级申请
     * @param req 分页条件
     * @return 考级申请列表
     */
    @RequestMapping(value="/getLevelApplications",method=RequestMethod.GET)
    public CommonResponse getLevelApplications(LevelApplicationSearchReq req){
        int page=req.getPage()==0?0:req.getPage()-1;
        int pageSize=req.getPageSize()==0?10:req.getPageSize();
        List<LevelApplication> allLevelApplications=levelApplicationServer.getAll(req);
        int total=allLevelApplications.size();
        int totalPage=total%pageSize==0?total/pageSize:total/pageSize+1;
        List<LevelApplication> levelApplications=levelApplicationServer.findLevelApplicationCriteria(page,pageSize,req);
        CommonResponse response= new CommonResponse("获取成功","info",levelApplications);
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
        String path = "./levelApplicationFiles" ;
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
        File file = new File("./levelApplicationFiles/"+filePath);

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
