package com.times.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

//通用化配置
@Configuration
public class CommonConfig {
    //redis连接工厂
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    //缓存操作组件redisTemplate的自定义配置
    @Bean
    public RedisTemplate<String,Object> redisTemplate(){
        //定义redistemplate示例
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<String,Object>();
        //设置redis的链接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
    //缓存操作组件StringRedisTemplate
    @Bean
    public StringRedisTemplate stringRedisTemplate(){
        //采用默认配置即可-后续有自定义配置时则在此处添加即可
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
        return stringRedisTemplate;
    }
}
