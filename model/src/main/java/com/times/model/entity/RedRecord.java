package com.times.model.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 发红包记录实体类
 */
public class RedRecord {
    private Integer id;  //主键ID
    private Integer userId; //用户ID
    private String redPacket; // 红包全局唯一标识串
    private Integer total; //发红包总数，即可以抢的总人数
    private BigDecimal amount; //红包总金额
    private Byte isActive;   //是否有效
    private Date createTime; //创建时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRedPacket() {
        return redPacket;
    }

    public void setRedPacket(String redPacket) {
        this.redPacket = redPacket;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Byte getIsActive() {
        return isActive;
    }

    public void setIsActive(Byte isActive) {
        this.isActive = isActive;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
