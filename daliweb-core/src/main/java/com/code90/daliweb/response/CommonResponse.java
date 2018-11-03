package com.code90.daliweb.response;

import java.util.HashMap;
import java.util.Map;

/**
 * 基础响应类
 * @author Ray
 * @create 2018-05-28 0:14
 **/
public class CommonResponse {
    //是否成功
    private boolean result;
    //响应消息
    private String message;
    //响应码
    private int code;
    //错误信息
    private String errorMsg;
    //返回数据
    private Map<String,Object> map;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Map<String,Object> getMap() {
        return map;
    }
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    /**
     * 增加数据
     * @param key 键
     * @param object 值
     */
    public void addNewDate(String key,Object object){
        if(map == null){
            map=new HashMap<>();
        }
        map.put(key,object);
    }

    public CommonResponse(){}

    public CommonResponse(String msg){
        this.result=true;
        this.code=0;
        this.message=msg;
    }

    public CommonResponse(String msg,String key,Object object){
        this.result=true;
        this.code=0;
        this.message=msg;
        addNewDate(key,object);
    }

    public CommonResponse(String msg, int code, Exception e) {
        this.result=false;
        this.code=code;
        this.message=msg;
        this.errorMsg=e.getMessage();
    }

    public CommonResponse(String msg, int code) {
        this.result=false;
        this.code=code;
        this.message=msg;
    }

}
