package com.times.model.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 红包随机金额明细实体类
 */
public class RedDetail {
    private Integer id;
    private Integer recordId; //红包记录ID
    private BigDecimal amount; //红包随机金额
    private Byte isActive; //是否有效
    private Date createTime; //创建时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
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
