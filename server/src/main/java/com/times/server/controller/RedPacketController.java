package com.times.server.controller;

import com.times.api.BaseResponse;
import com.times.api.enums.StatusCode.StatusCode;
import com.times.server.dto.RedPacketDto;
import com.times.server.service.IRedPacketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedPacketController {
    private static final Logger logger = LoggerFactory.getLogger(RedPacketController.class);

    private static final String prefix="red/packet";
    //注入红包业务逻辑处理接口服务
    @Autowired
    private IRedPacketService redPacketService;

    /**
     * 发红包请求-请求方法为post，请求参数采用json格式进行交互
     * @param dto
     * @param result
     * @return
     */
    @RequestMapping(value = prefix+"/hand/out",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse handOut(@Validated @RequestBody RedPacketDto dto, BindingResult result){
        //参数校验
        if(result.hasErrors()){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try{
            //核心业务逻辑处理服务-最终返回红包全局唯一标识串
            String redId = redPacketService.handOut(dto);
            //将红包全局唯一标识串返回给前端
            response.setData(redId);
        }catch (Exception e){
            logger.error("发红包异常：dto={}",dto,e.fillInStackTrace());
            response = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }
}
