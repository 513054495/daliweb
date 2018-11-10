package com.code90.daliweb.controller;

import com.code90.daliweb.domain.*;
import com.code90.daliweb.request.exchange.*;
import com.code90.daliweb.response.CommonResponse;
import com.code90.daliweb.server.AnnouncementServer;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 公告控制类
 * @author Ray Lin
 * @create 2018-11-10 20:58
 **/
@RestController
@RequestMapping("/daliweb/announcement")
public class AnnouncementController {
    private static final Logger logger=LoggerFactory.getLogger(AnnouncementController.class);
    @Autowired
    private AnnouncementServer announcementServer;
    @Autowired
    private UserServer userServer;

    /**
     * 新增公告
     * @param req 公告信息
     * @return 新增结果
     */
    @RequestMapping(value = "/addAnnouncement",method = RequestMethod.POST)
    public CommonResponse addAnnouncement(@RequestBody AnnouncementSaveReq req){
        try {
            Announcement announcement=new Announcement();
            BeanUtils.copyProperties(req,announcement);
            announcement.createBy=req.getCreateBy();
            announcementServer.save(announcement);
            logger.info("保存成功");
            return new CommonResponse("保存成功");
        }catch (Exception e){
            logger.error("保存失败，原因："+e.getMessage());
            return new CommonResponse("保存失败",1,e);
        }
    }

    /**
     * 新增公告已读记录
     * @param id 公告编号
     * @param userCode 用户帐号
     * @return 新增结果
     */
    @RequestMapping(value = "/addAnnouncementDetail",method = RequestMethod.GET)
    public CommonResponse addAnnouncementDetail(@RequestParam("id")String id,@RequestParam("userCode")String userCode){
        try {
            AnnouncementDetail announcementDetail=new AnnouncementDetail();
            announcementDetail.setAnnouncementId(id);
            announcementDetail.createBy=userCode;
            announcementServer.saveDetail(announcementDetail);
            logger.info("保存成功");
            return new CommonResponse("保存成功");
        }catch (Exception e){
            logger.error("保存失败，原因："+e.getMessage());
            return new CommonResponse("保存失败",1,e);
        }
    }

    /**
     * 修改公告
     * @param req 公告内容
     * @return 修改结果
     */
    @RequestMapping(value="/editAnnouncement",method = RequestMethod.POST)
    public CommonResponse editAnnouncement(@RequestBody AnnouncementChangeReq req){
        try {
            Announcement announcement=(Announcement) announcementServer.getObjectById(req.getId());
            BeanUtils.copyProperties(req,announcement);
            announcementServer.save(announcement);
            logger.info("公告修改成功");
            return new CommonResponse("修改成功");
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 修改公告状态
     * @param ids 公告编号
     * @param status 状态
     * @return 修改结果
     */
    @RequestMapping(value="/editState",method = RequestMethod.GET)
    public CommonResponse editState(@RequestParam("ids")String ids, @RequestParam("status")int status){
        try {
            if(!StringUtil.isEmpty(ids)) {
                String[] id_list = ids.split(",");
                for (String id : id_list) {
                    Announcement announcement = (Announcement) announcementServer.getObjectById(id);
                    announcement.setStatus(status);
                    announcementServer.save(announcement);
                }
                logger.info("公告修改成功");
                return new CommonResponse("修改成功");
            }else{
                logger.error("公告修改失败，编号不能为空");
                return new CommonResponse("修改失败",2);
            }
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 根据编号获取公告
     * @param id 编号
     * @return 公告
     */
    @RequestMapping(value="/getAnnouncementById",method=RequestMethod.GET)
    public CommonResponse getAnnouncementById(@RequestParam("id")String id){
        CommonResponse response=new CommonResponse("获取成功");
        Announcement announcement=(Announcement) announcementServer.getObjectById(id);
        if(announcement!=null){
            logger.info("获取成功");
            response.addNewDate("info",announcement);
            return response;
        }else {
            logger.error("获取失败");
            return new CommonResponse("获取失败",2);
        }
    }

    /**
     * 根据用户获取留言
     * @param userCode 编号
     * @return 留言
     */
    @RequestMapping(value="/getAnnouncementByUserCode",method=RequestMethod.GET)
    public CommonResponse getLeaveMessageByUserCode(@RequestParam("userCode")String userCode,int isRead){
        CommonResponse response=new CommonResponse("获取成功");
        User user=userServer.getUserByUserCode(userCode);
        if(null!=user) {
            List<Announcement> list=new ArrayList<>();
            List<Announcement> announcements = announcementServer.getAnnouncementByUser(user);
            for (Announcement announcement : announcements) {
                AnnouncementDetail announcementDetail = announcementServer.getAnnouncementDetailByAnnouncementIdAndUserCode(announcement.getId(), userCode);
                if(isRead==1&&null!=announcementDetail) {
                    list.add(announcement);
                }else if(isRead==0&&null==announcementDetail){
                    list.add(announcement);
                }
            }
            logger.info("获取成功");
            response.addNewDate("info", list);
            return response;
        }else{
            logger.error("获取失败,用户不存在");
            return new CommonResponse("获取失败，用户不存在",2);
        }
    }

    /**
     * 删除公告
     * @param ids 公告编号
     * @return 删除结果
     */
    @RequestMapping(value="/delAnnouncement",method = RequestMethod.DELETE)
    public CommonResponse delAnnouncement(@RequestParam("ids") String ids){
        try {
            if(!StringUtil.isEmpty(ids)){
                String[] id_list=ids.split(",");
                for (String id : id_list){
                    Announcement announcement=(Announcement) announcementServer.getObjectById(id);
                    announcementServer.delete(announcement);
                    List<AnnouncementDetail> announcementDetails=announcementServer.getAnnouncementDetailByAnnouncementId(announcement.getId());
                    for (AnnouncementDetail announcementDetail:announcementDetails){
                        announcementServer.deleteDetail(announcementDetail);
                    }
                }
                logger.info("公告删除成功");
                return new CommonResponse("删除成功");
            }else{
                logger.error("删除失败，公告编号不能为空");
                return new CommonResponse("删除失败，公告编号不能为空",3);
            }
        }catch (Exception e){
            logger.error("删除失败，原因："+e.getMessage());
            return new CommonResponse("删除失败",3,e);
        }
    }

    /**
     * 分页查询公告
     * @param req 分页条件
     * @return 公告列表
     */
    @RequestMapping(value="/getAnnouncements",method=RequestMethod.GET)
    public CommonResponse getAnnouncements(AnnouncementSearchReq req){
        try{
            int page=req.getPage()==0?0:req.getPage()-1;
            int pageSize=req.getPageSize()==0?10:req.getPageSize();
            List<Announcement> allAnnouncement=announcementServer.getAll(req);
            int total=allAnnouncement.size();
            int totalPage=total%pageSize==0?total/pageSize:total/pageSize+1;
            List<Announcement> announcements=announcementServer.findAnnouncementCriteria(page,pageSize,req);
            logger.info("获取成功");
            CommonResponse response= new CommonResponse("获取成功","info",announcements);
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
        String path = "./announcementFiles" ;
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
        File file = new File("./announcementFiles/"+filePath);

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
