package com.times.server.service.impl;

import com.times.model.entity.RedDetail;
import com.times.model.entity.RedRecord;
import com.times.model.mapper.RedDetailMapper;
import com.times.model.mapper.RedRecordMapper;
import com.times.model.mapper.RedRobRecordMapper;
import com.times.server.dto.RedPacketDto;
import com.times.server.service.IRedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 红包业务逻辑处理过程数据记录接口
 */
@Service
@EnableAsync
public class RedService implements IRedService {
    private static final Logger logger = LoggerFactory.getLogger(RedService.class);
    @Autowired
    private RedRecordMapper redRecordMapper;
    @Autowired
    private RedDetailMapper redDetailMapper;
    @Autowired
    private RedRobRecordMapper redRobRecordMapper;

    /**
     * 发红包记录-异步方式
     * @param dto 红包总金额+个数
     * @param redId 红包全局唯一标识串
     * @param list list红包随机金额列表
     * @throws Exception
     */
    @Override
    @Async
    @Transactional(rollbackFor =  Exception.class)
    public void recordRedPacket(RedPacketDto dto, String redId, List<Integer> list) throws Exception {
        //定义实体类对象
        RedRecord redRecord = new RedRecord();
        //设置字段的取值信息
        redRecord.setUserId(dto.getUserId());
        redRecord.setRedPacket(redId);
        redRecord.setTotal(dto.getTotal());
        redRecord.setAmount(BigDecimal.valueOf(dto.getAmount()));
        redRecord.setCreateTime(new Date());
        //将对象信息插入数据库
        redRecordMapper.insertSelective(redRecord);
        logger.info("获取数据库插入的id为:{}",redRecord.getId());
        //定义红包随机金额明细实体类对象
        RedDetail detail;
        //遍历随机金额列表，将金额等信息设置到相应的字段中
        for (Integer i:list){
            detail = new RedDetail();
            detail.setRecordId(redRecord.getId());
            detail.setAmount(BigDecimal.valueOf(i));
            detail.setCreateTime(new Date());
            //将对象信息插入数据库
            redDetailMapper.insertSelective(detail);
        }
    }

    @Override
    public void recordRedPacket(Integer userId, String redId, BigDecimal amount) throws Exception {

    }
}
