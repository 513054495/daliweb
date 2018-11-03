package com.code90.daliweb.utils;

/**
 * 字符串工具类
 *
 * @author Ray Lin
 * @create 2018-09-09 14:41
 **/
public class StringUtil {
    public static boolean isEmpty(String field){
        if("".equals(field)||null==field){
            return true;
        }else{
            return false;
        }
    }
}
