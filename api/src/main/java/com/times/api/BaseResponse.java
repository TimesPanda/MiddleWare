package com.times.api;

import com.times.api.enums.StatusCode.StatusCode;

/**
 * 信息响应类
 */
public class BaseResponse<T> {
    private Integer code; //状态码
    private String msg; //描述信息
    private T data; //响应数据-采用泛型表示可以接受通用的数据类型
    public BaseResponse(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }
    public BaseResponse(StatusCode statusCode){
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
    }
    public BaseResponse(Integer code,String msg,T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}