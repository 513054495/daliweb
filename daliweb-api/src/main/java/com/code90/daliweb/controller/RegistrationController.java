package com.code90.daliweb.controller;

import com.code90.daliweb.domain.*;
import com.code90.daliweb.request.exchange.*;
import com.code90.daliweb.response.CommonResponse;
import com.code90.daliweb.server.RegistrationServer;
import com.code90.daliweb.utils.DateUtils;
import com.code90.daliweb.utils.ExcelUtils;
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
 * 报名控制类
 * @author Ray Lin
 * @create 2018-10-30 23:08
 **/
@RestController
@RequestMapping("/daliweb/registration")
public class RegistrationController {
    private static final Logger logger=LoggerFactory.getLogger(RegistrationController.class);
    @Autowired
    private RegistrationServer registrationServer;

    /**
     * 新增报名
     * @param req 报名内容
     * @return 新增结果
     */
    @RequestMapping(value = "/addRegistration",method = RequestMethod.POST)
    public CommonResponse addRegistration(@RequestBody RegistrationSaveReq req){
        try {
            Registration registration=new Registration();
            BeanUtils.copyProperties(req,registration);
            registration.createBy=req.getCreateBy();
            registrationServer.save(registration);
            logger.info("保存成功");
            return new CommonResponse("保存成功");
        }catch (Exception e){
            logger.error("保存失败，原因："+e.getMessage());
            return new CommonResponse("保存失败",1,e);
        }
    }

    /**
     * 新增报名详情
     * @param req 报名详情内容
     * @return 新增结果
     */
    @RequestMapping(value = "/addRegistrationDetail",method = RequestMethod.POST)
    public CommonResponse addRegistration(@RequestBody RegistrationDetailSaveReq req){
        try {
            RegistrationDetail registrationDetail=new RegistrationDetail();
            BeanUtils.copyProperties(req,registrationDetail);
            registrationDetail.createBy=req.getCreateBy();
            registrationServer.saveDetail(registrationDetail);
            logger.info("保存成功");
            return new CommonResponse("保存成功");
        }catch (Exception e){
            logger.error("保存失败，原因："+e.getMessage());
            return new CommonResponse("保存失败",1,e);
        }
    }

    /**
     * 修改报名
     * @param req 修改报名
     * @return 修改结果
     */
    @RequestMapping(value="/editRegistration",method = RequestMethod.POST)
    public CommonResponse editQuestion(@RequestBody RegistrationChangeReq req){
        try {
            Registration registration=(Registration) registrationServer.getObjectById(req.getId());
            BeanUtils.copyProperties(req,registration);
            registrationServer.save(registration);
            logger.info("报名修改成功");
            return new CommonResponse("修改成功");
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 修改报名详情
     * @param req 修改报名详情
     * @return 修改结果
     */
    @RequestMapping(value="/editRegistrationDetail",method = RequestMethod.POST)
    public CommonResponse editQuestion(@RequestBody RegistrationDetailChangeReq req){
        try {
            RegistrationDetail registrationDetail= registrationServer.getRegistrationDetailById(req.getId());
            BeanUtils.copyProperties(req,registrationDetail);
            registrationServer.saveDetail(registrationDetail);
            logger.info("报名详情修改成功");
            return new CommonResponse("修改成功");
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 修改报名状态
     * @param ids 报名编号
     * @param status 状态
     * @return 修改结果
     */
    @RequestMapping(value="/editState",method = RequestMethod.GET)
    public CommonResponse editState(@RequestParam("ids")String ids, @RequestParam("status")int status){
        try {
            if(!StringUtil.isEmpty(ids)) {
                String[] id_list = ids.split(",");
                for (String id : id_list) {
                    Registration registration = (Registration) registrationServer.getObjectById(id);
                    registration.setStatus(status);
                    registrationServer.save(registration);
                }
                logger.info("报名修改成功");
                return new CommonResponse("修改成功");
            }else{
                logger.error("报名修改失败，编号不能为空");
                return new CommonResponse("修改失败",2);
            }
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 根据编号获取报名
     * @param id 编号
     * @return 报名
     */
    @RequestMapping(value="/getRegistrationById",method=RequestMethod.GET)
    public CommonResponse getRegistrationById(@RequestParam("id")String id,String userCode){
        try{
            CommonResponse response=new CommonResponse("获取成功");
            Registration registration=(Registration) registrationServer.getObjectById(id);
            RegistrationVo registrationVo=new RegistrationVo();
            BeanUtils.copyProperties(registration,registrationVo);
            RegistrationDetail registrationDetail=registrationServer.getRegistrationDetailByIdAndCreateBy(registration.getId(),userCode);
            if(null!=registrationDetail){
                registrationVo.setIsRegistration(1);
            }
            if(registrationVo!=null){
                logger.info("获取成功");
                response.addNewDate("info",registrationVo);
                return response;
            }else {
                logger.error("获取失败");
                return new CommonResponse("获取失败",2);
            }
        }catch (Exception e){
            logger.error("获取失败，原因："+e.getMessage());
            return new CommonResponse("获取失败",2,e);
        }
    }

    /**
     * 根据编号获取报名详情
     * @param id 编号
     * @return 报名
     */
    @RequestMapping(value="/getRegistrationDetailById",method=RequestMethod.GET)
    public CommonResponse getRegistrationDetailById(@RequestParam("id")String id){
        try{
            CommonResponse response=new CommonResponse("获取成功");
            RegistrationDetail registrationDetail=registrationServer.getRegistrationDetailById(id);
            if(registrationDetail!=null){
                logger.info("获取成功");
                response.addNewDate("info",registrationDetail);
                return response;
            }else {
                logger.error("获取失败");
                return new CommonResponse("获取失败",2);
            }
        }catch (Exception e){
            logger.error("获取失败，原因："+e.getMessage());
            return new CommonResponse("获取失败",2,e);
        }
    }

    /**
     * 根据用户获取所有报名
     * @param userCode 用户帐号
     * @return 报名列表
     */
    @RequestMapping(value="/getRegistrationByUserCode",method=RequestMethod.GET)
    public CommonResponse getRegistrationDetailByUserCode(@RequestParam("userCode")String userCode){
        try{
            updateState();
            CommonResponse response=new CommonResponse("获取成功");
            List<Registration> registrations=registrationServer.getAllPublishRegistration();
            List<RegistrationVo> registrationVos=new ArrayList<>();
            for (Registration registration :registrations){
                RegistrationVo registrationVo=new RegistrationVo();
                BeanUtils.copyProperties(registration,registrationVo);
                if(!StringUtil.isEmpty(userCode)) {
                    RegistrationDetail registrationDetail = registrationServer.getRegistrationDetailByIdAndCreateBy(registration.getId(), userCode);
                    if (null != registrationDetail) {
                        registrationVo.setIsRegistration(1);
                    }
                }
                registrationVos.add(registrationVo);
            }
            response.addNewDate("info",registrationVos);
            return response;
        }catch (Exception e){
            logger.error("获取失败，原因："+e.getMessage());
            return new CommonResponse("获取失败",2,e);
        }
    }

    /**
     * 删除报名
     * @param ids 报名编号
     * @return 删除结果
     */
    @RequestMapping(value="/delRegistration",method = RequestMethod.DELETE)
    public CommonResponse delRegistration(@RequestParam("ids") String ids){
        try {
            if(!StringUtil.isEmpty(ids)){
                String[] id_list=ids.split(",");
                for (String id : id_list){
                    Registration registration=(Registration) registrationServer.getObjectById(id);
                    registrationServer.delete(registration);
                }
                logger.info("报名删除成功");
                return new CommonResponse("删除成功");
            }else{
                logger.error("删除失败，报名编号不能为空");
                return new CommonResponse("删除失败，报名编号不能为空",3);
            }
        }catch (Exception e){
            logger.error("删除失败，原因："+e.getMessage());
            return new CommonResponse("删除失败",3,e);
        }
    }

    /**
     * 删除报名详情
     * @param ids 报名详情编号
     * @return 删除结果
     */
    @RequestMapping(value="/delRegistrationDetail",method = RequestMethod.DELETE)
    public CommonResponse delRegistrationDetail(@RequestParam("ids") String ids){
        try {
            if(!StringUtil.isEmpty(ids)){
                String[] id_list=ids.split(",");
                for (String id : id_list){
                    RegistrationDetail registrationDetail=registrationServer.getRegistrationDetailById(id);
                    registrationServer.deleteRegistrationDetail(registrationDetail);
                }
                logger.info("报名详情删除成功");
                return new CommonResponse("删除成功");
            }else{
                logger.error("删除失败，报名详情编号不能为空");
                return new CommonResponse("删除失败，报名详情编号不能为空",3);
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
    @RequestMapping(value="/getRegistrations",method=RequestMethod.GET)
    public CommonResponse getRegistrations(RegistrationSearchReq req){
        updateState();
        int page=req.getPage()==0?0:req.getPage()-1;
        int pageSize=req.getPageSize()==0?10:req.getPageSize();
        List<Registration> allRegistration=registrationServer.getAll(req);
        int total=allRegistration.size();
        int totalPage=total%pageSize==0?total/pageSize:total/pageSize+1;
        List<Registration> registrations=registrationServer.findRegistrationCriteria(page,pageSize,req);
        CommonResponse response= new CommonResponse("获取成功","info",registrations);
        response.addNewDate("pageNum",page+1);
        response.addNewDate("pageSize",pageSize);
        response.addNewDate("total",total);
        response.addNewDate("totalPage",totalPage);
        return response;
    }

    /**
     * 分页查询内容
     * @param req 分页条件
     * @return 内容列表
     */
    @RequestMapping(value="/getRegistrationDetails",method=RequestMethod.GET)
    public CommonResponse getRegistrationDetails(RegistrationDetailSearchReq req){
        int page=req.getPage()==0?0:req.getPage()-1;
        int pageSize=req.getPageSize()==0?10:req.getPageSize();
        List<RegistrationDetail> allRegistrationDetail=registrationServer.getAllDetail(req);
        int total=allRegistrationDetail.size();
        int totalPage=total%pageSize==0?total/pageSize:total/pageSize+1;
        List<RegistrationDetail> registrationDetails=registrationServer.findRegistrationDetailCriteria(page,pageSize,req);
        List<RegistrationDetailVo> registrationDetailVos=new ArrayList<>();
        for(RegistrationDetail registrationDetail:registrationDetails){
            RegistrationDetailVo registrationDetailVo=new RegistrationDetailVo();
            BeanUtils.copyProperties(registrationDetail,registrationDetailVo);
            Registration registration= (Registration) registrationServer.getObjectById(registrationDetail.getRegistrationId());
            if(null!=registration){
                registrationDetailVo.setLastTime(registration.getLastTime());
                registrationDetailVo.setPostalCode(registration.getPostalCode());
                registrationDetailVo.setArea(registration.getArea());
                registrationDetailVo.setRegistrationName(registration.getName());
                registrationDetailVo.setMoney(registration.getMoney());
            }
            registrationDetailVos.add(registrationDetailVo);
        }
        CommonResponse response= new CommonResponse("获取成功","info",registrationDetailVos);
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
        String path = "./registrationFiles" ;
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
        File file = new File("./registrationFiles/"+filePath);

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

    @RequestMapping(value = "/exportRegistration", method = RequestMethod.GET)
    public void exportRegistration(HttpServletResponse response, @RequestParam("ids") String ids) throws Exception {
        List<ExcelData> excelDatas = new ArrayList<>();
        ExcelData registrationExcel = new ExcelData();
        registrationExcel.setName("汉学堂报名信息数据");
        List<String> titles = new ArrayList();
        titles.add("姓名");
        titles.add("性别");
        titles.add("年龄");
        titles.add("联系电话");
        titles.add("订单编号");
        titles.add("报名时间");
        registrationExcel.setTitles(titles);
        //添加列
        List<List<Object>> rows = new ArrayList();
        List<Object> row = null;
        List<RegistrationDetail> registrationDetails = new ArrayList<>();
        if (!StringUtil.isEmpty(ids)) {
            String[] id_list = ids.split(",");
            for (String id : id_list) {
                RegistrationDetail registrationDetail = registrationServer.getRegistrationDetailById(id);
                if (registrationDetail != null) {
                    registrationDetails.add(registrationDetail);
                }
            }
        }
        String registrationName="";
        for (int i = 0; i < registrationDetails.size(); i++) {
            if(i==0){
                Registration registration=(Registration)registrationServer.getObjectById(registrationDetails.get(i).getRegistrationId());
                registrationName=registration.getName();
            }
            row = new ArrayList();
            row.add(registrationDetails.get(i).getName());
            row.add(registrationDetails.get(i).getSex()==0?"女":"男");
            row.add(registrationDetails.get(i).getAge());
            row.add(registrationDetails.get(i).getPhone());
            row.add(registrationDetails.get(i).getOrderId());
            row.add(DateUtils.dateToDateString(registrationDetails.get(i).createTime,DateUtils.ZHCN_DATATIMEF_STR));
            rows.add(row);
        }
        registrationExcel.setRows(rows);
        excelDatas.add(registrationExcel);
        String fileName = registrationName+"报名信息" + DateUtils.dateToDateString(new Date(), DateUtils.ZHCN_DATATIMEF_STR) + ".xls";
        ExcelUtils.exportExcel(response, fileName, excelDatas);
    }

    private void updateState() {
        List<Registration> registrations=registrationServer.getAllPublishRegistration();
        for (Registration registration : registrations){
            if(registration.getStatus()==1&&new Date().after(registration.getLastTime())){
                registration.setStatus(2);
            }
            registrationServer.save(registration);
        }
    }
}
