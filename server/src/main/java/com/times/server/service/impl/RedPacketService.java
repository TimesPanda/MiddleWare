package com.times.server.service.impl;

import com.times.server.dto.RedPacketDto;
import com.times.server.service.IRedPacketService;
import com.times.server.service.IRedService;
import com.times.server.utils.RedPacketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 红包业务逻辑处理接口-实现类
 */
@Service
public class RedPacketService implements IRedPacketService {
    private static final Logger logger = LoggerFactory.getLogger(RedPacketService.class);
    //存储至缓存系统redis时定义的key前缀
    private static final String keyPrefix = "redis:red:packet:";
    //定义redis操作bean组件
    @Autowired
    private RedisTemplate redisTemplate;
    //自动注入红包业务逻辑处理过程数据记录接口服务
    @Autowired
    private IRedService redService;

    /**
     * 发红包逻辑
     * @param dto
     * @return
     * @throws Exception
     */
    @Override
    public String handOut(RedPacketDto dto) throws Exception {
        //判断参数的合法性
        if(dto.getTotal() >0 && dto.getAmount() > 0){
            //采用二倍均值法生成随机金额列表
            List<Integer> list = RedPacketUtil.divideRedPackage(dto.getAmount(),dto.getTotal());
            //生成红包全局唯一标识串
            String timestamp = String.valueOf(System.nanoTime());
            //根据缓存key的前缀与其他信息拼接成一个新的用于存储随机金额列表的key
            String redId = new StringBuffer(keyPrefix).append(dto.getUserId()).append(":").append(timestamp).toString();
            //将随机金额列表存入缓存list中
            redisTemplate.opsForList().leftPushAll(redId,list);
            //根据缓存key的前缀与其他信息拼接成一个新的用于存储红包总数的key
            String redTotalKey = redId + ":total";
            //将红包总数存入缓存中
            redisTemplate.opsForValue().set(redTotalKey,dto.getTotal());
            //异步记录红包的全局唯一标识串，红包个数与随机金额列表信息至数据库中
            redService.recordRedPacket(dto,redId,list);
            //将红包的全局唯一标识串返回前端
            return  redId;
        }else{
            throw new Exception("系统异常-分发红包-参数不合法!");
        }
    }

    /**
     * 抢红包逻辑
     * @param userId
     * @param redId
     * @return
     * @throws Exception
     */
    @Override
    public BigDecimal rob(Integer userId, String redId) throws Exception {
        return null;
    }
}
