package com.code90.daliweb.utils;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 编号生成工具类
 *
 * @author Ray Lin
 * @create 2018-09-12 23:33
 **/
public class IdUtils {
    /**
     * 商品编号生成
     * @return 商品编号
     */
    public static String createCommodityId() {
        return DateUtils.dateToDateString(new Date(),DateUtils.DATATIMEF_STR_MIS);
    }

    /**
     * 订单编号生成
     * @return 订单编号
     */
    public static String createOrderId() {
        String id=DateUtils.dateToDateString(new Date(),DateUtils.DATATIMEF_STR_MIS );
        for(int i=0;i<3;i++){
            id+=new Random().nextInt(10);
        }
        return id;
    }

    /**
     * 题目编号生成
     * @return 题目编号
     */
    public static int createSubjectId(int maxId) {
        int id=maxId+1;
        return id;
    }

    /**
     * 随机生成分享码
     * @return 分享码
     */
    public static String createShareCode(){
        return createRandomCharData(10);
    }

    /**
     * 随机生成红包金额
     * @return 红包金额
     */
    public static double createRedPackage(double min, double max) {
        int  scl =  2; // 小数最大位数
        int  pow = (int) Math.pow(10, scl); // 用于提取指定小数位
        double one=0.0;
        while(1==1){
            one = Math.floor((Math.random() * (max - min) + min) * pow) / pow;
            if(one>min&&one<max&&one>0){
                DecimalFormat df = new DecimalFormat("#.00");
                df.format(one);
                break;
            }
        }
        return one;
    }

    /**
     * 随机生成字母加数字组合
     * @param length
     */
    private static String createRandomCharData(int length)
    {
        String val = "";
        Random random = new Random();

        //参数length，表示生成几位随机数
        for(int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char)(random.nextInt(26) + temp);
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

}
