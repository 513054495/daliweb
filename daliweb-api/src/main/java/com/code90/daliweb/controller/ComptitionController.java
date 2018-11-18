package com.code90.daliweb.controller;

import com.code90.daliweb.domain.*;
import com.code90.daliweb.request.exchange.*;
import com.code90.daliweb.response.CommonResponse;
import com.code90.daliweb.server.CompetitionDetailServer;
import com.code90.daliweb.server.CompetitionServer;
import com.code90.daliweb.server.TypeListServer;
import com.code90.daliweb.utils.DateUtils;
import com.code90.daliweb.utils.ExcelUtils;
import com.code90.daliweb.utils.StringUtil;
import com.code90.daliweb.utils.ZipUtils;
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
import java.util.zip.ZipOutputStream;

/**
 * 竞赛控制类
 * @author Ray Lin
 * @create 2018-10-18 0:55
 **/
@RestController
@RequestMapping("/daliweb/competition")
public class ComptitionController {
    private static final Logger logger=LoggerFactory.getLogger(ComptitionController.class);
    @Autowired
    private CompetitionServer competitionServer;
    @Autowired
    private CompetitionDetailServer competitionDetailServer;
    @Autowired
    private TypeListServer typeListServer;

    /**
     * 新增竞赛
     * @param req 竞赛内容
     * @return 新增结果
     */
    @RequestMapping(value = "/addCompetition",method = RequestMethod.POST)
    public CommonResponse addCompetition(@RequestBody CompetitionSaveReq req){
        try {
            Competition competition=new Competition();
            BeanUtils.copyProperties(req,competition);
            competition.createBy=req.getCreateBy();
            competitionServer.save(competition);
            logger.info("保存成功");
            return new CommonResponse("保存成功");
        }catch (Exception e){
            logger.error("保存失败，原因："+e.getMessage());
            return new CommonResponse("保存失败",1,e);
        }
    }

    /**
     * 新增竞赛详情
     * @param req 竞赛详情内容
     * @return 新增结果
     */
    @RequestMapping(value = "/addCompetitionDetail",method = RequestMethod.POST)
    public CommonResponse addCompetitionDetail(@RequestBody CompetitionDetailSaveReq req){
        try {
            CompetitionDetail competitionDetail=new CompetitionDetail();
            BeanUtils.copyProperties(req,competitionDetail);
            competitionDetail.createBy=req.getCreateBy();
            competitionDetailServer.save(competitionDetail);
            logger.info("保存成功");
            return new CommonResponse("保存成功");
        }catch (Exception e){
            logger.error("保存失败，原因："+e.getMessage());
            return new CommonResponse("保存失败",1,e);
        }
    }

    /**
     * 修改竞赛
     * @param req 竞赛问题
     * @return 修改结果
     */
    @RequestMapping(value="/editCompetition",method = RequestMethod.POST)
    public CommonResponse editCompetition(@RequestBody CompetitionChangeReq req){
        try {
            Competition competition=(Competition) competitionServer.getObjectById(req.getId());
            BeanUtils.copyProperties(req,competition);
            competitionServer.save(competition);
            logger.info("竞赛修改成功");
            return new CommonResponse("修改成功");
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 修改竞赛详情
     * @param req 竞赛详情问题
     * @return 修改结果
     */
    @RequestMapping(value="/editCompetitionDetail",method = RequestMethod.POST)
    public CommonResponse editCompetitionDetail(@RequestBody CompetitionDetailChangeReq req){
        try {
            CompetitionDetail competitionDetail=(CompetitionDetail) competitionDetailServer.getObjectById(req.getId());
            BeanUtils.copyProperties(req,competitionDetail);
            competitionDetailServer.save(competitionDetail);
            logger.info("竞赛修改成功");
            return new CommonResponse("修改成功");
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 修改竞赛状态
     * @param ids 竞赛编号
     * @param status 状态
     * @return 修改结果
     */
    @RequestMapping(value="/editState",method = RequestMethod.GET)
    public CommonResponse editState(@RequestParam("ids")String ids, @RequestParam("status")int status){
        try {
            if(!StringUtil.isEmpty(ids)) {
                String[] id_list = ids.split(",");
                for (String id : id_list) {
                    Competition competition = (Competition) competitionServer.getObjectById(id);
                    if(status==1){
                        competition.setPublishTime(new Date());
                    }
                    competition.setStatus(status);
                    competitionServer.save(competition);
                    if(status==3){
                        List<CompetitionDetail> competitionDetails=competitionDetailServer.getDetailByCompetitionId(competition.getId());
                        for(CompetitionDetail competitionDetail :competitionDetails){
                            if(competitionDetail.getStatus()==0){
                                competitionDetail.setStatus(2);
                                competitionDetailServer.save(competitionDetail);
                            }
                        }
                    }
                }
                logger.info("竞赛修改成功");
                return new CommonResponse("修改成功");
            }else{
                logger.error("竞赛修改失败，编号不能为空");
                return new CommonResponse("修改失败",2);
            }
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 修改竞赛详情状态
     * @param ids 竞赛详情编号
     * @param status 状态
     * @return 修改结果
     */
    @RequestMapping(value="/editDetailState",method = RequestMethod.GET)
    public CommonResponse editDetailState(@RequestParam("ids")String ids, @RequestParam("status")int status){
        try {
            if(!StringUtil.isEmpty(ids)) {
                String[] id_list = ids.split(",");
                for (String id : id_list) {
                    CompetitionDetail competitionDetail = (CompetitionDetail) competitionDetailServer.getObjectById(id);
                    competitionDetail.setStatus(status);
                    competitionDetailServer.save(competitionDetail);
                }
                logger.info("竞赛详情修改成功");
                return new CommonResponse("修改成功");
            }else{
                logger.error("竞赛修改失败，编号不能为空");
                return new CommonResponse("修改失败",2);
            }
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 根据编号获取竞赛
     * @param id 编号
     * @return 考级申请
     */
    @RequestMapping(value="/getCompetitionById",method=RequestMethod.GET)
    public CommonResponse getCompetitionById(@RequestParam("id")String id,String userCode){
        CommonResponse response=new CommonResponse("获取成功");
        Competition competition=(Competition) competitionServer.getObjectById(id);
        CompetitionVo competitionVo=new CompetitionVo();
        BeanUtils.copyProperties(competition,competitionVo);
        TypeList typeList=typeListServer.getTypeNameBySubjectId(competition.getType());
        if(null!=typeList) {
            competitionVo.setTypeName(typeList.getTypeName());
        }
        if(!StringUtil.isEmpty(userCode)) {
            CompetitionDetail competitionDetail = competitionDetailServer.getByCompetitionIdAndUserCode(competitionVo.getId(), userCode);
            if (null != competitionDetail) {
                competitionVo.setIsJoin(1);
                competitionVo.setDetailState(competitionDetail.getStatus());
            }
        }
        if(competition!=null){
            logger.info("获取成功");
            response.addNewDate("info",competitionVo);
            return response;
        }else {
            logger.error("获取失败");
            return new CommonResponse("获取失败");
        }
    }

    /**
     * 根据编号获取竞赛详情
     * @param id 编号
     * @return 竞赛详情
     */
    @RequestMapping(value="/getCompetitionDetailById",method=RequestMethod.GET)
    public CommonResponse getCompetitionDetailById(@RequestParam("id")String id){
        CommonResponse response=new CommonResponse("获取成功");
        CompetitionDetail competitionDetail=(CompetitionDetail) competitionDetailServer.getObjectById(id);
        if(competitionDetail!=null){
            Competition competition= (Competition) competitionServer.getObjectById(competitionDetail.getCompetitionId());
            response.addNewDate("competitionStatus",competition.getStatus());
            logger.info("获取成功");
            response.addNewDate("info",competitionDetail);
            return response;
        }else {
            logger.error("获取失败");
            return new CommonResponse("获取失败");
        }
    }

    /**
     * 根据用户获取全部竞赛
     * @param userCode 用户帐号
     * @return 全部竞赛
     */
    @RequestMapping(value="/getAllCompetitionlls",method=RequestMethod.GET)
    public CommonResponse getAllCompetitionlls(@RequestParam("userCode")String userCode,@RequestParam("isShow")int isShow){
       updateState();
       List<Competition> competitions=competitionServer.getAll();
       List<CompetitionVo> competitionVos=new ArrayList<>();
       for(Competition competition : competitions){
           CompetitionVo competitionVo=new CompetitionVo();
           BeanUtils.copyProperties(competition,competitionVo);
           CompetitionDetail competitionDetail=competitionDetailServer.getByCompetitionIdAndUserCode(competitionVo.getId(),userCode);
           TypeList typeList=typeListServer.getTypeNameBySubjectId(competition.getType());
           if(null!=typeList) {
               competitionVo.setTypeName(typeList.getTypeName());
           }
           if(null!=competitionDetail){
               competitionVo.setIsJoin(1);
               competitionVo.setDetailState(competitionDetail.getStatus());
           }
           if((isShow==0&&competitionVo.getIsJoin()==1)||isShow==1) {
               if(competition.getStatus()!=0) {
                   competitionVos.add(competitionVo);
               }
           }
       }
       return new CommonResponse("获取成功","info",competitionVos);
    }

    /**
     * 删除竞赛
     * @param ids 竞赛编号
     * @return 删除结果
     */
    @RequestMapping(value="/delCompetition",method = RequestMethod.DELETE)
    public CommonResponse delCompetition(@RequestParam("ids") String ids){
        try {
            if(!StringUtil.isEmpty(ids)){
                String[] id_list=ids.split(",");
                for (String id : id_list){
                    Competition competition=(Competition) competitionServer.getObjectById(id);
                    List<CompetitionDetail> competitionDetails=competitionDetailServer.getDetailByCompetitionId(competition.getId());
                    for(CompetitionDetail competitionDetail : competitionDetails){
                        competitionDetailServer.delete(competitionDetail);
                    }
                    competitionServer.delete(competition);
                }
                logger.info("竞赛删除成功");
                return new CommonResponse("删除成功");
            }else{
                logger.error("删除失败，竞赛编号不能为空");
                return new CommonResponse("删除失败，竞赛编号不能为空",3);
            }
        }catch (Exception e){
            logger.error("删除失败，原因："+e.getMessage());
            return new CommonResponse("删除失败",3,e);
        }
    }

    /**
     * 删除竞赛详情
     * @param ids 竞赛详情编号
     * @return 删除结果
     */
    @RequestMapping(value="/delCompetitionDetail",method = RequestMethod.DELETE)
    public CommonResponse delCompetitionDetail(@RequestParam("ids") String ids){
        try {
            if(!StringUtil.isEmpty(ids)){
                String[] id_list=ids.split(",");
                for (String id : id_list){
                    CompetitionDetail competitionDetail=(CompetitionDetail) competitionDetailServer.getObjectById(id);
                    competitionDetailServer.delete(competitionDetail);
                }
                logger.info("竞赛详情删除成功");
                return new CommonResponse("删除成功");
            }else{
                logger.error("删除失败，竞赛详情编号不能为空");
                return new CommonResponse("删除失败，竞赛详情编号不能为空",3);
            }
        }catch (Exception e){
            logger.error("删除失败，原因："+e.getMessage());
            return new CommonResponse("删除失败",3,e);
        }
    }

    /**
     * 分页查询竞赛
     * @param req 分页条件
     * @return 竞赛列表
     */
    @RequestMapping(value="/getCompetitions",method=RequestMethod.GET)
    public CommonResponse getCompetitions(CompetitionSearchReq req){
        updateState();
        int page=req.getPage()==0?0:req.getPage()-1;
        int pageSize=req.getPageSize()==0?10:req.getPageSize();
        List<Competition> allCompetition=competitionServer.getAll(req);
        int total=allCompetition.size();
        int totalPage=total%pageSize==0?total/pageSize:total/pageSize+1;
        List<Competition> competitions=competitionServer.findCompetitionCriteria(page,pageSize,req);
        List<CompetitionVo> competitionVos=new ArrayList<>();
        for(Competition competition : competitions){
            CompetitionVo competitionVo=new CompetitionVo();
            BeanUtils.copyProperties(competition,competitionVo);
            TypeList typeList=typeListServer.getTypeNameBySubjectId(competition.getType());
            if(null!=typeList) {
                competitionVo.setTypeName(typeList.getTypeName());
            }
            competitionVos.add(competitionVo);
        }
        CommonResponse response= new CommonResponse("获取成功","info",competitionVos);
        response.addNewDate("pageNum",page+1);
        response.addNewDate("pageSize",pageSize);
        response.addNewDate("total",total);
        response.addNewDate("totalPage",totalPage);
        return response;
    }

    /**
     * 分页查询竞赛详情
     * @param req 分页条件
     * @return 竞赛详情列表
     */
    @RequestMapping(value="/getCompetitionDetails",method=RequestMethod.GET)
    public CommonResponse getCompetitionDetails(CompetitionDetailSearchReq req){
        int page=req.getPage()==0?0:req.getPage()-1;
        int pageSize=req.getPageSize()==0?10:req.getPageSize();
        List<CompetitionDetail> allCompetitionDetail=competitionDetailServer.getAll(req);
        int total=allCompetitionDetail.size();
        int totalPage=total%pageSize==0?total/pageSize:total/pageSize+1;
        List<CompetitionDetail> competitionDetails=competitionDetailServer.findCompetitionDetailCriteria(page,pageSize,req);
        CommonResponse response= new CommonResponse("获取成功","info",competitionDetails);
        if(competitionDetails.size()>0){
            Competition competition= (Competition) competitionServer.getObjectById(competitionDetails.get(0).getCompetitionId());
            response.addNewDate("competitionStatus",competition.getStatus());
        }
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
        String path = "./competitionFiles" ;
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
     * 竞赛作品上传
     * @param file 文件
     * @param competitionId 竞赛编号
     * @param name 作品名称
     * @return
     */
    @RequestMapping(value = "/competitionFileUpload",method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse competitionFileUpload(@RequestParam("file") MultipartFile file,@RequestParam("competitionId")String competitionId,@RequestParam("name")String name){
        if(file.isEmpty()){
            logger.error("上传失败，文件不能为空");
            return new CommonResponse("上传失败，文件不能为空",6);
        }
        String oldName=file.getOriginalFilename();
        String fileTyle=oldName.substring(oldName.lastIndexOf("."),oldName.length());
        String fileName = name+fileTyle;
        String path = "./competitionFiles/"+competitionId ;
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
     * 下载文件
     * @param response
     * @param filePath 文件路径
     */
    @RequestMapping(value = "/download",method = RequestMethod.GET)
    public void downLoad(HttpServletResponse response, String filePath){
        File file = new File("./competitionFiles/"+filePath);
        if(file.exists()){ //判断文件父目录是否存在
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;fileName=" + file.getName());
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
     * 下载参数所有作品
     * @param response
     * @param competitionId 参数编号
     * @throws IOException
     */
    @RequestMapping(value = "/downLoadZipFile",method = RequestMethod.GET)
    public void downLoadZipFile(HttpServletResponse response,@RequestParam("competitionId")String competitionId) throws IOException{
        Competition competition= (Competition) competitionServer.getObjectById(competitionId);
        String zipName = competition.getName()+"--参数作品集合.zip";
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition","attachment; filename="+java.net.URLEncoder.encode(zipName, "UTF-8"));
        ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
        try {
            List<CompetitionDetail> competitionDetails=competitionDetailServer.getDetailByCompetitionId(competitionId);
             for(CompetitionDetail competitionDetail : competitionDetails){
                String files=competitionDetail.getFiles();
                String[] file_list=files.split(",");
                for(String filePath : file_list){
                    File file = new File("./competitionFiles/"+competitionId+"/"+filePath);
                    ZipUtils.doCompress(file,out);
                }
                response.flushBuffer();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            out.close();
        }
    }

    /**
     * 导出竞赛
     * @param response
     * @param ids
     * @throws Exception
     */
    @RequestMapping(value = "/exportCompetition", method = RequestMethod.GET)
    public void exportCompetition(HttpServletResponse response, @RequestParam("ids") String ids) throws Exception {
        List<ExcelData> excelDatas = new ArrayList<>();
        ExcelData competitionExcel = new ExcelData();
        competitionExcel.setName("竞赛结果信息数据");
        List<String> titles = new ArrayList();
        titles.add("姓名");
        titles.add("作品提交时间");
        titles.add("竞赛结果");
        competitionExcel.setTitles(titles);
        //添加列
        List<List<Object>> rows = new ArrayList();
        List<Object> row = null;
        List<CompetitionDetail> competitionDetails = new ArrayList<>();
        if (!StringUtil.isEmpty(ids)) {
            String[] id_list = ids.split(",");
            for (String id : id_list) {
                CompetitionDetail competitionDetail = (CompetitionDetail) competitionDetailServer.getObjectById(id);
                if (competitionDetail != null) {
                    competitionDetails.add(competitionDetail);
                }
            }
        }
        for (int i = 0; i < competitionDetails.size(); i++) {
            row = new ArrayList();
            row.add(competitionDetails.get(i).getName());
            row.add(DateUtils.dateToDateString(competitionDetails.get(i).getCreateTime(),DateUtils.ZHCN_DATATIMEF_STR));
            row.add(competitionDetails.get(i).getStatus());
            rows.add(row);
        }
        competitionExcel.setRows(rows);
        excelDatas.add(competitionExcel);
        String fileName = "竞赛结果信息数据" + DateUtils.dateToDateString(new Date(), DateUtils.ZHCN_DATATIMEF_STR) + ".xls";
        ExcelUtils.exportExcel(response, fileName, excelDatas);
    }

    private void updateState() {
        List<Competition> competitions=competitionServer.getAllStartCompetition();
        for (Competition competition : competitions){
            if(new Date().after(competition.getLastTime())){
                competition.setStatus(2);
            }
            competitionServer.save(competition);
        }
    }

}
