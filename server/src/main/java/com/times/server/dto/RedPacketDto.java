package com.times.server.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * 发红包请求时接收的参数对象
 */
@Data
@ToString
public class RedPacketDto {
    private Integer userId;
    @NotNull
    private Integer total;
    @NotNull
    private Integer amount;
}
