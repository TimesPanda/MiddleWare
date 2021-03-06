package com.times.server.service;

import com.times.server.dto.RedPacketDto;

import java.math.BigDecimal;
import java.util.List;

public interface IRedService {
    /**
     * 记录发红包时红包的全局唯一标识串、随机金额列表和个数等信息入数据库
     * @param dto
     * @param redId
     * @param list
     * @throws Exception
     */
    void recordRedPacket(RedPacketDto dto, String redId, List<Integer> list) throws Exception;

    /**
     * 记录抢红包时用户抢到的红包金额等信息入数据库
     * @param userId
     * @param redId
     * @param amount
     * @throws Exception
     */
    void recordRedPacket(Integer userId, String redId, BigDecimal amount) throws Exception;
}
