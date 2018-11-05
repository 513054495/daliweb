package com.code90.daliweb.conf;

import com.code90.daliweb.domain.Commodity;
import com.code90.daliweb.server.CommodityServer;
import com.code90.daliweb.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 * @author Ray Lin
 * @create 2018-11-04 16:11
 **/
@Service
public class RedisServer {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private CommodityServer commodityServer;

    public void setValue(String key, String obj){
        stringRedisTemplate.opsForValue().set(key,obj);
    }

    public void setValue(String key, String obj,long time){
        stringRedisTemplate.opsForValue().set(key,obj,time,TimeUnit.MINUTES);
    }

    public String getValue(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    public int getNum(String key){
        String str_num=stringRedisTemplate.opsForValue().get(key);
        if(StringUtil.isEmpty(str_num)){
            Commodity commodity= (Commodity) commodityServer.getObjectById(key);
            str_num=commodity.getTotalNum()+"";
            stringRedisTemplate.opsForValue().set(key,str_num);
        }
        return Integer.parseInt(str_num);
    }
}
