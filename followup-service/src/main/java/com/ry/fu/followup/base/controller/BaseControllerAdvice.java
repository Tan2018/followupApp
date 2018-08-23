package com.ry.fu.followup.base.controller;

import com.ry.fu.followup.base.model.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2017/12/11 14:23
 * @description controller 增强器
 */
@ControllerAdvice
public class BaseControllerAdvice {

    private final static Logger logger = LoggerFactory.getLogger(BaseControllerAdvice.class);

    /**
     * 全局异常捕捉处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseData errorHandler(Exception ex) {
        ResponseData responseData = new ResponseData();
        responseData.setStatus(500);
        responseData.setMsg("后台内部异常");
        responseData.setData(ex.toString());

        logger.error(ex.toString());
//        return JsonUtils.toJSONString(responseData);
        return responseData;
    }

}
