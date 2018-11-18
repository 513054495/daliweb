package com.code90.daliweb.controller;

import com.code90.daliweb.domain.*;
import com.code90.daliweb.request.exchange.*;
import com.code90.daliweb.response.CommonResponse;
import com.code90.daliweb.server.ForumServer;
import com.code90.daliweb.server.TypeListServer;
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
 * 论坛控制类
 * @author Ray Lin
 * @create 2018-11-12 0:24
 **/
@RestController
@RequestMapping("/daliweb/forum")
public class ForumController {
    private static final Logger logger=LoggerFactory.getLogger(ForumController.class);
    @Autowired
    private ForumServer forumServer;
    @Autowired
    private UserServer userServer;
    @Autowired
    private TypeListServer typeListServer;

    /**
     * 新增帖子
     * @param req 帖子信息
     * @return 新增结果
     */
    @RequestMapping(value = "/addForum",method = RequestMethod.POST)
    public CommonResponse addForum(@RequestBody ForumSaveReq req){
        try {
            Forum forum=new Forum();
            BeanUtils.copyProperties(req,forum);
            forum.createBy=req.getCreateBy();
            forumServer.save(forum);
            logger.info("保存成功");
            return new CommonResponse("保存成功");
        }catch (Exception e){
            logger.error("保存失败，原因："+e.getMessage());
            return new CommonResponse("保存失败",1,e);
        }
    }

    /**
     * 新增帖子回复记录
     * @param req 回复信息
     * @return 新增结果
     */
    @RequestMapping(value = "/addForumDetail",method = RequestMethod.POST)
    public CommonResponse addForumDetail(@RequestBody ForumDetailSaveReq req){
        try {
            ForumDetail forumDetail=new ForumDetail();
            BeanUtils.copyProperties(req,forumDetail);
            forumServer.saveDetail(forumDetail);
            logger.info("保存成功");
            return new CommonResponse("保存成功");
        }catch (Exception e){
            logger.error("保存失败，原因："+e.getMessage());
            return new CommonResponse("保存失败",1,e);
        }
    }

    /**
     * 修改帖子
     * @param req 帖子内容
     * @return 修改结果
     */
    @RequestMapping(value="/editForum",method = RequestMethod.POST)
    public CommonResponse editForum(@RequestBody ForumChangeReq req){
        try {
            Forum forum=(Forum) forumServer.getObjectById(req.getId());
            BeanUtils.copyProperties(req,forum);
            forumServer.save(forum);
            logger.info("帖子修改成功");
            return new CommonResponse("修改成功");
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 修改帖子回复
     * @param req 帖子回复内容
     * @return 修改结果
     */
    @RequestMapping(value="/editForumDetail",method = RequestMethod.POST)
    public CommonResponse editForumDetail(@RequestBody ForumDetailChangeReq req){
        try {
            ForumDetail forumDetail=forumServer.getForumDetailById(req.getId());
            BeanUtils.copyProperties(req,forumDetail);
            forumServer.saveDetail(forumDetail);
            logger.info("帖子回复修改成功");
            return new CommonResponse("修改成功");
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 根据编号获取帖子
     * @param id 编号
     * @return 帖子
     */
    @RequestMapping(value="/getForumById",method=RequestMethod.GET)
    public CommonResponse getForumById(@RequestParam("id")String id){
        CommonResponse response=new CommonResponse("获取成功");
        Forum forum=(Forum) forumServer.getObjectById(id);
        ForumVo forumVo=new ForumVo();
        BeanUtils.copyProperties(forum,forumVo);
        User user=userServer.getUserByUserCode(forum.createBy);
        forumVo.setNickName(user.getNickname());
        forumVo.setUserImg(user.getPic());
        TypeList typeList=typeListServer.getTypeNameBySubjectId(forum.getType());
        forumVo.setTypeName(typeList.getTypeName());
        List<ForumDetail> forumDetails=forumServer.getForumDetailByForumId(forum.getId());
        for(ForumDetail forumDetail :forumDetails){
            ForumDetailVo forumDetailVo=new ForumDetailVo();
            BeanUtils.copyProperties(forumDetail,forumDetailVo);
            user=userServer.getUserByUserCode(forumDetail.createBy);
            forumDetailVo.setNickName(user.getNickname());
            forumDetailVo.setUserImg(user.getPic());
            forumVo.getForumDetailVos().add(forumDetailVo);
        }
        if(forumVo!=null){
            logger.info("获取成功");
            response.addNewDate("info",forumVo);
            return response;
        }else {
            logger.error("获取失败");
            return new CommonResponse("获取失败",2);
        }
    }

    /**
     * 删除论坛
     * @param ids 论坛编号
     * @return 删除结果
     */
    @RequestMapping(value="/delForum",method = RequestMethod.DELETE)
    public CommonResponse delForum(@RequestParam("ids") String ids){
        try {
            if(!StringUtil.isEmpty(ids)){
                String[] id_list=ids.split(",");
                for (String id : id_list){
                    Forum forum=(Forum) forumServer.getObjectById(id);
                    forumServer.delete(forum);
                    List<ForumDetail> forumDetails=forumServer.getForumDetailByForumId(forum.getId());
                    for (ForumDetail forumDetail:forumDetails){
                        forumServer.deleteDetail(forumDetail);
                    }
                }
                logger.info("帖子删除成功");
                return new CommonResponse("删除成功");
            }else{
                logger.error("删除失败，帖子编号不能为空");
                return new CommonResponse("删除失败，帖子编号不能为空",3);
            }
        }catch (Exception e){
            logger.error("删除失败，原因："+e.getMessage());
            return new CommonResponse("删除失败",3,e);
        }
    }

    /**
     * 删除论坛详情
     * @param ids 论坛详情编号
     * @return 删除结果
     */
    @RequestMapping(value="/delForumDetail",method = RequestMethod.DELETE)
    public CommonResponse delForumDetail(@RequestParam("ids") String ids){
        try {
            if(!StringUtil.isEmpty(ids)){
                String[] id_list=ids.split(",");
                for (String id : id_list){
                    ForumDetail forumDetail=forumServer.getForumDetailById(id);
                    forumServer.deleteDetail(forumDetail);
                }
                logger.info("帖子回复删除成功");
                return new CommonResponse("删除成功");
            }else{
                logger.error("删除失败，帖子回复编号不能为空");
                return new CommonResponse("删除失败，帖子回复编号不能为空",3);
            }
        }catch (Exception e){
            logger.error("删除失败，原因："+e.getMessage());
            return new CommonResponse("删除失败",3,e);
        }
    }

    /**
     * 分页查询论坛
     * @param req 分页条件
     * @return 论坛列表
     */
    @RequestMapping(value="/getForums",method=RequestMethod.GET)
    public CommonResponse getForums(ForumSearchReq req){
        try{
            int page=req.getPage()==0?0:req.getPage()-1;
            int pageSize=req.getPageSize()==0?10:req.getPageSize();
            List<Forum> allForum=forumServer.getAll(req);
            int total=allForum.size();
            int totalPage=total%pageSize==0?total/pageSize:total/pageSize+1;
            List<Forum> forums=forumServer.findForumCriteria(page,pageSize,req);
            List<ForumVo> forumVos=new ArrayList<>();
            for (Forum forum :forums){
                ForumVo forumVo=new ForumVo();
                BeanUtils.copyProperties(forum,forumVo);
                User user=userServer.getUserByUserCode(forum.createBy);
                forumVo.setNickName(user.getNickname());
                forumVo.setUserImg(user.getPic());
                TypeList typeList=typeListServer.getTypeNameBySubjectId(forum.getType());
                forumVo.setTypeName(typeList.getTypeName());
                List<ForumDetail> forumDetails=forumServer.getForumDetailByForumId(forum.getId());
                forumVo.setForumDetailNum(forumDetails.size());
                forumVos.add(forumVo);
            }
            logger.info("获取成功");
            CommonResponse response= new CommonResponse("获取成功","info",forumVos);
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
        String path = "./forumFiles" ;
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
        File file = new File("./forumFiles/"+filePath);

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
