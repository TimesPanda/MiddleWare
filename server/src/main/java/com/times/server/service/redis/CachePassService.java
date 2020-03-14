package com.times.server.service.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.times.model.entity.Item;
import com.times.model.mapper.ItemMapper;
import com.times.server.controller.redis.CachePassController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

//缓存穿透service
@Service
public class CachePassService {
    private static final Logger log = LoggerFactory.getLogger(CachePassService.class);
    //定义Mapper
    @Autowired
    private ItemMapper itemMapper;
    //定义redis操作组件redisTempLate
    @Autowired
    private RedisTemplate redisTemplate;
    //定义JSON序列化与反序列化框架类
    @Autowired
    private ObjectMapper objectMapper;
    //定义缓存中key命名的前缀
    private static final String keyPrefix = "item";
    /**
     * 获取商品详情，如果缓存有，则从缓存中获取；如果没有，则从数据库中查询，并将结果塞入缓存结果中
     */
    public Item getItemInfo(String itemCode) throws Exception{
        Item item = null;
        //定义缓存中真正的key：由前缀和商品编码组成
        final String key = keyPrefix + itemCode;
        //定义redis的操作组件valueOperations
        ValueOperations valueOperations = redisTemplate.opsForValue();
        if(redisTemplate.hasKey(key)){
            //如果缓存中存在，则从缓存中获取
            log.info("获取商品详情-缓存中存在该商品，商品编号为：{}",itemCode);
            //从缓存中查询该商品详情
            Object res = valueOperations.get(key);
            if(res !=null && Strings.isNullOrEmpty(res.toString())){
                //如果可以找到该商品，则进行json反序列化解析
                item = objectMapper.readValue(res.toString(),Item.class);
            }
        }else{
            //如果缓存中没有，则从数据库中获取
            log.info("获取商品详情-缓存中没有该商品，从数据库中需要查找商品编号为：{}",itemCode);
            log.info("获取itemMapper数据信息：{}",itemMapper.toString());
            item = itemMapper.selectByCode(itemCode);
            if(item != null){
                //如果数据库中查到该商品，则将其序列化后写入缓存中
                valueOperations.set(key,objectMapper.writeValueAsBytes(item));
            }else{
                //如果数据库中也没有查到，则设置过期失效时间，一般是防止攻击发生
                valueOperations.set(key,"",30L, TimeUnit.MINUTES);
            }
        }
        return item;
    }
}
