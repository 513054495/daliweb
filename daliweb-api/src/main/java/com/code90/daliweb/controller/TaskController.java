package com.code90.daliweb.controller;

import com.code90.daliweb.domain.*;
import com.code90.daliweb.request.exchange.*;
import com.code90.daliweb.response.CommonResponse;
import com.code90.daliweb.server.AnnouncementServer;
import com.code90.daliweb.server.TaskCollectionServer;
import com.code90.daliweb.server.TaskServer;
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
 * 任务控制类
 * @author Ray Lin
 * @create 2018-10-03 1:47
 **/
@RestController
@RequestMapping("/daliweb/task")
public class TaskController{
    private static final Logger logger=LoggerFactory.getLogger(TaskController.class);
    @Autowired
    private TaskServer taskServer;
    @Autowired
    private TaskCollectionServer taskCollectionServer;
    @Autowired
    private UserServer userServer;
    @Autowired
    private AnnouncementServer announcementServer;

    /**
     * 新增任务
     * @param req 任务内容
     * @return 新增结果
     */
    @RequestMapping(value = "/addTask",method = RequestMethod.POST)
    public CommonResponse addTask(@RequestBody TaskSaveReq req){
        try {
            Task task=new Task();
            BeanUtils.copyProperties(req,task);
            task.createBy=req.getCreateBy();
            taskServer.save(task);
            logger.info("保存成功");
            return new CommonResponse("保存成功");
        }catch (Exception e){
            logger.error("保存失败，原因："+e.getMessage());
            return new CommonResponse("保存失败",1,e);
        }
    }

    /**
     * 领取任务
     * @param req 领取任务内容
     * @return 领取结果
     */
    @RequestMapping(value = "/addTaskCollection",method = RequestMethod.POST)
    public CommonResponse addTaskCollection(@RequestBody TaskCollectionSaveReq req){
        try {
            TaskCollection taskCollection;
            Task task =(Task)taskServer.getObjectById(req.getTaskId());
            if(null==task||task.getStatus()==2){
                return new CommonResponse("获取失败，该任务已关闭");
            }else{
                taskCollection=taskCollectionServer.getAllByTaskIdAndCreateBy(req.getTaskId(),req.getCreateBy());
                if(null!=taskCollection){
                    return new CommonResponse("获取失败，该任务已被经领取",1);
                }else{
                    taskCollection=new TaskCollection();
                }
            }
            BeanUtils.copyProperties(req,taskCollection);
            taskCollection.createBy=req.getCreateBy();
            taskCollectionServer.save(taskCollection);
            logger.info("保存成功");
            return new CommonResponse("保存成功");
        }catch (Exception e){
            logger.error("保存失败，原因："+e.getMessage());
            return new CommonResponse("保存失败",1,e);
        }
    }

    /**
     * 修改任务
     * @param req 修改任务
     * @return 修改结果
     */
    @RequestMapping(value="/editTask",method = RequestMethod.POST)
    public CommonResponse editQuestion(@RequestBody TaskChangeReq req){
        try {
            Task task=(Task) taskServer.getObjectById(req.getId());
            BeanUtils.copyProperties(req,task);
            taskServer.save(task);
            logger.info("任务修改成功");
            return new CommonResponse("修改成功");
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 修改领取任务
     * @param req 修改领取任务
     * @return 修改结果
     */
    @RequestMapping(value="/editTaskCollection",method = RequestMethod.POST)
    public CommonResponse editTaskCollection(@RequestBody TaskCollectionChangeReq req){
        try {
            TaskCollection taskCollection=(TaskCollection) taskCollectionServer.getObjectById(req.getId());
            BeanUtils.copyProperties(req,taskCollection);
            taskCollectionServer.save(taskCollection);
            logger.info("领取任务修改成功");
            return new CommonResponse("修改成功");
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 修改任务状态
     * @param ids 任务编号
     * @param status 状态
     * @return 修改结果
     */
    @RequestMapping(value="/editState",method = RequestMethod.GET)
    public CommonResponse editState(@RequestParam("ids")String ids, @RequestParam("status")int status){
        try {
            if(!StringUtil.isEmpty(ids)) {
                String[] id_list = ids.split(",");
                for (String id : id_list) {
                    Task task = (Task) taskServer.getObjectById(id);
                    task.setStatus(status);
                    if(status==1){
                        Announcement announcement=new Announcement();
                        announcement.setTitle("\""+task.getTitle()+"\"任务通知");
                        announcement.setContent("管理员发布了\""+task.getTitle()+"\"的任务，请您尽快到学习-->任务板块领取任务！");
                        announcement.setLevel("3");
                        announcement.setStatus(1);
                        announcementServer.save(announcement);
                    }
                    taskServer.save(task);
                }
                logger.info("任务修改成功");
                return new CommonResponse("修改成功");
            }else{
                logger.error("任务修改失败，编号不能为空");
                return new CommonResponse("修改失败",2);
            }
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 修改任务领取状态
     * @param ids 任务领取编号
     * @param status 状态
     * @return 修改结果
     */
    @RequestMapping(value="/editTaskCollectionState",method = RequestMethod.GET)
    public CommonResponse editTaskCollectionState(@RequestParam("ids")String ids, @RequestParam("status")int status){
        try {
            if(!StringUtil.isEmpty(ids)) {
                String[] id_list = ids.split(",");
                for (String id : id_list) {
                    TaskCollection taskCollection = (TaskCollection) taskCollectionServer.getObjectById(id);
                    Task task = (Task) taskServer.getObjectById(taskCollection.getTaskId());
                    if(null!=task&&task.getStatus()!=2){
                        taskCollection.setStatus(status);
                        taskCollectionServer.save(taskCollection);
                    }else{
                        logger.error("任务已经关闭");
                        return new CommonResponse("任务已经关闭",2);
                    }
                }
                logger.info("任务领取修改成功");
                return new CommonResponse("修改成功");
            }else{
                logger.error("任务领取修改失败，编号不能为空");
                return new CommonResponse("修改失败",2);
            }
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 根据编号获取任务
     * @param id 编号
     * @return 任务
     */
    @RequestMapping(value="/getTaskById",method=RequestMethod.GET)
    public CommonResponse getTaskById(@RequestParam("id")String id){
        CommonResponse response=new CommonResponse("获取成功");
        Task task=(Task) taskServer.getObjectById(id);
        if(task!=null){
            logger.info("获取成功");
            response.addNewDate("info",task);
            return response;
        }else {
            logger.error("获取失败");
            return new CommonResponse("获取失败");
        }
    }

    /**
     * 根据编号获取领取任务
     * @param id 编号
     * @return 领取任务
     */
    @RequestMapping(value="/getTaskCollectionById",method=RequestMethod.GET)
    public CommonResponse getTaskCollectionById(@RequestParam("id")String id){
        CommonResponse response=new CommonResponse("获取成功");
        TaskCollection taskCollection=(TaskCollection) taskCollectionServer.getObjectById(id);
        if(taskCollection!=null){
            TaskCollectionVo taskCollectionVo=new TaskCollectionVo();
            BeanUtils.copyProperties(taskCollection,taskCollectionVo);
            User user=userServer.getUserByUserCode(taskCollection.createBy);
            response.addNewDate("createBy",user.getUserName());
            Task task=(Task)taskServer.getObjectById(taskCollection.getTaskId());
            taskCollectionVo.setMaxNum(task.getMaxNum());
            taskCollectionVo.setTaskName(task.getTitle());
            taskCollectionVo.setCurrentNum(taskCollectionServer.getCountByTaskId(taskCollection.getTaskId()));
            logger.info("获取成功");
            response.addNewDate("info",taskCollectionVo);
            return response;
        }else {
            logger.error("获取失败");
            return new CommonResponse("获取失败");
        }
    }

    /**
     * 根据用户帐号获取任务列表
     * @param userCode 用户帐号
     * @param id 任务编号
     * @return 考试列表
     */
    @RequestMapping(value="/getTaskByUserCode",method=RequestMethod.GET)
    public CommonResponse getTaskByUserCode(String id,@RequestParam("userCode")String userCode){
        try{
            updateState();
            if(StringUtil.isEmpty(id)){
                List<Task> tasks=taskServer.getAllStartTask();
                List<TaskVo> taskVos=new ArrayList<>();
                for (Task task :tasks){
                    TaskCollection taskCollection=taskCollectionServer.getAllByTaskIdAndCreateBy(task.getId(),userCode);
                    TaskVo taskVo=new TaskVo();
                    BeanUtils.copyProperties(task,taskVo);
                    if(null!=taskCollection){
                        taskVo.setIsConllection(1);
                    }
                    taskVos.add(taskVo);
                }
                logger.info("获取成功");
                return new CommonResponse("获取成功","info",taskVos);
            }else{
                Task task= (Task) taskServer.getObjectById(id);
                TaskCollection taskCollection=taskCollectionServer.getAllByTaskIdAndCreateBy(task.getId(),userCode);
                TaskVo taskVo=new TaskVo();
                BeanUtils.copyProperties(task,taskVo);
                if(null!=taskCollection){
                    taskVo.setIsConllection(1);
                }
                logger.info("获取成功");
                return new CommonResponse("获取成功","info",taskVo);
            }
        }catch (Exception e){
            logger.error("获取失败，原因："+e.getMessage());
            return new CommonResponse("获取失败",4,e);
        }
    }


    /**
     * 根据用户帐号获取任务列表
     * @param userCode 用户帐号
     * @param id 任务编号
     * @return 考试列表
     */
    @RequestMapping(value="/getTaskConllectionByUserCodeAndTaskId",method=RequestMethod.GET)
    public CommonResponse getTaskConllectionByUserCodeAndTaskId(@RequestParam("id")String id,@RequestParam("userCode")String userCode){
        try{
             CommonResponse response=new CommonResponse("获取成功");
             Task task= (Task) taskServer.getObjectById(id);
             TaskCollection taskCollection=taskCollectionServer.getAllByTaskIdAndCreateBy(id,userCode);
             logger.info("获取成功");
             response.addNewDate("info",taskCollection);
             response.addNewDate("title",task.getTitle());
             response.addNewDate("lastTime",task.getLastTime());
             response.addNewDate("maxNum",task.getMaxNum());
             return response;
        }catch (Exception e){
            logger.error("获取失败，原因："+e.getMessage());
            return new CommonResponse("获取失败",4,e);
        }
    }

    /**
     * 删除任务
     * @param ids 任务编号
     * @return 删除结果
     */
    @RequestMapping(value="/delTask",method = RequestMethod.DELETE)
    public CommonResponse delTask(@RequestParam("ids") String ids){
        try {
            if(!StringUtil.isEmpty(ids)){
                String[] id_list=ids.split(",");
                for (String id : id_list){
                    Task task=(Task) taskServer.getObjectById(id);
                    taskServer.delete(task);
                    List<TaskCollection> taskControllers=taskCollectionServer.getTaskControllerByTaskId(task.getId());
                    for(TaskCollection taskCollection :taskControllers){
                        taskCollectionServer.delete(taskCollection);
                    }
                }
                logger.info("任务删除成功");
                return new CommonResponse("删除成功");
            }else{
                logger.error("删除失败，任务编号不能为空");
                return new CommonResponse("删除失败，任务编号不能为空",3);
            }
        }catch (Exception e){
            logger.error("删除失败，原因："+e.getMessage());
            return new CommonResponse("删除失败",3,e);
        }
    }

    /**
     * 删除领取任务
     * @param ids 领取任务编号
     * @return 删除结果
     */
    @RequestMapping(value="/delTaskCollection",method = RequestMethod.DELETE)
    public CommonResponse delTaskCollection(@RequestParam("ids") String ids){
        try {
            if(!StringUtil.isEmpty(ids)){
                String[] id_list=ids.split(",");
                for (String id : id_list){
                    TaskCollection taskCollection=(TaskCollection) taskCollectionServer.getObjectById(id);
                    taskCollectionServer.delete(taskCollection);
                }
                logger.info("任务领取删除成功");
                return new CommonResponse("删除成功");
            }else{
                logger.error("删除失败，领取任务编号不能为空");
                return new CommonResponse("删除失败，领取任务编号不能为空",3);
            }
        }catch (Exception e){
            logger.error("删除失败，原因："+e.getMessage());
            return new CommonResponse("删除失败",3,e);
        }
    }

    /**
     * 分页查询任务
     * @param req 分页条件
     * @return 任务列表
     */
    @RequestMapping(value="/getTasks",method=RequestMethod.GET)
    public CommonResponse getTasks(TaskSearchReq req){
        updateState();
        int page=req.getPage()==0?0:req.getPage()-1;
        int pageSize=req.getPageSize()==0?10:req.getPageSize();
        List<Task> allTasks=taskServer.getAll(req);
        int total=allTasks.size();
        int totalPage=total%pageSize==0?total/pageSize:total/pageSize+1;
        List<Task> tasks=taskServer.findTaskCriteria(page,pageSize,req);
        CommonResponse response= new CommonResponse("获取成功","info",tasks);
        response.addNewDate("pageNum",page+1);
        response.addNewDate("pageSize",pageSize);
        response.addNewDate("total",total);
        response.addNewDate("totalPage",totalPage);
        return response;
    }

    /**
     * 分页查询领取任务
     * @param req 分页条件
     * @return 领取任务列表
     */
    @RequestMapping(value="/getTaskCollections",method=RequestMethod.GET)
    public CommonResponse getTaskCollections(TaskCollectionSearchReq req){
        int page=req.getPage()==0?0:req.getPage()-1;
        int pageSize=req.getPageSize()==0?10:req.getPageSize();
        List<TaskCollection> allTaskCollections=taskCollectionServer.getAll(req);
        int total=allTaskCollections.size();
        int totalPage=total%pageSize==0?total/pageSize:total/pageSize+1;
        List<TaskCollection> taskCollections=taskCollectionServer.findTaskCollectionCriteria(page,pageSize,req);
        List<TaskCollectionVo> taskCollectionVos=new ArrayList<>();
        for (TaskCollection taskCollection : taskCollections){
            TaskCollectionVo taskCollectionVo=new TaskCollectionVo();
            BeanUtils.copyProperties(taskCollection,taskCollectionVo);
            Task task=(Task)taskServer.getObjectById(taskCollection.getTaskId());
            if(null!=task){
                taskCollectionVo.setMaxNum(task.getMaxNum());
                taskCollectionVo.setTaskName(task.getTitle());
                taskCollectionVo.setTaskStatus(task.getStatus());
                taskCollectionVo.setLastTime(task.getLastTime());
                taskCollectionVo.setCurrentNum(taskCollectionServer.getCountByTaskId(taskCollection.getTaskId()));
            }
            taskCollectionVos.add(taskCollectionVo);
        }
        CommonResponse response= new CommonResponse("获取成功","info",taskCollectionVos);
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
        String path = "./taskFiles" ;
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
        File file = new File("./taskFiles/"+filePath);

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

    /**
     * 根据时间修改状态
     */
    private void updateState(){
        List<Task> tasks=taskServer.getAllStartTask();
        for (Task task : tasks){
            if(new Date().after(task.getLastTime())){
                task.setStatus(2);
            }
            taskServer.save(task);
        }
    }
}
