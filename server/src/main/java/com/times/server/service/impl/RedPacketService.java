package com.times.server.service.impl;

import com.times.server.dto.RedPacketDto;
import com.times.server.service.IRedPacketService;
import com.times.server.service.IRedService;
import com.times.server.utils.RedPacketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
     * @param userId 当前用户，表示抢红包的人
     * @param redId  红包全局唯一标识串
     * @return   返回抢到的红包金额或者抢不到红包金额的NULL
     * @throws Exception
     */
    @Override
    public BigDecimal rob(Integer userId, String redId) throws Exception {
        //定义redis操作组件的值操作方法
        ValueOperations valueOperations = redisTemplate.opsForValue();
        //在处理用户抢红包之前，需要先判断一下当前用户是否已经抢过该红包了
        //如果抢过了，则直接返回红包金额，并在前端显示出来
        Object object = valueOperations.get(redId+":rob");
        if(object != null){
            return new BigDecimal(object.toString());
        }
        //“点红包”业务逻辑-主要用于判断缓存系统中是否仍然有红包，即红包剩余个数是否大于0
        Boolean res = click(redId);
        if(res){
            //res 为true，则可以进入“拆红包”业务逻辑的处理
            //从小红包随机金额列表中弹出一个随机金额
            Object value = redisTemplate.opsForList().rightPop(redId);
            if(value != null){
                //value != null，表示当前弹出的红包金额不为null，既有钱
                //当前用户抢到一个红包，则可以进入后续的更新缓存，并将信息记入信息库
                String redTotalKey = redId + ":total";
                //更新缓存系统中剩余的红包个数，即红包个数减1
                Integer currTotal = valueOperations.get(redTotalKey) != null ?(Integer)valueOperations.get(redTotalKey):0;
                valueOperations.set(redTotalKey,currTotal-1);
                //将红包金额返回给用户前，在这里金额的单位设置为“元”
                //因为在发红包业务木块中金额的单位是设置为“分”的
                BigDecimal result = new BigDecimal(value.toString()).divide(new BigDecimal(100));
                //将抢到红包时用户的账户信息及抢到的金额等信息记入数据库
                redService.recordRedPacket(userId,redId,new BigDecimal(value.toString()));
                //将当前抢到红包的用户设置进缓存系统中，用于表示当前用户已经抢过的红包
                valueOperations.set(redId+userId+":rob",result,24L, TimeUnit.HOURS);
                //打印当前用户抢到红包的记录信息
                logger.info("当前用户抢到红包了：userId={} key={} 金额={}",userId,redId,result);
                //返回结果
                return result;
            }
        }
        return null;
    }
    private Boolean click(String redId) throws Exception{
        //定义redis的bean操作组件-值操作组件
        ValueOperations valueOperations = redisTemplate.opsForValue();
        //定义用于查询缓存系统中红包剩余个数的key
        String redTotalKey = redId + ":total";
        //获取缓存系统redis中红包剩余个数
        Object total = valueOperations.get(redTotalKey);
        //判断红包剩余个数total是否大于0，如果大于0，则返回true，代表还有红包
        if(total != null && Integer.valueOf(total.toString()) > 0){
            return true;
        }
        return false;
    }
}
