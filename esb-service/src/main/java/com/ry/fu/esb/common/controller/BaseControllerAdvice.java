package com.ry.fu.esb.common.controller;

import com.ry.fu.esb.common.enumstatic.StatusCode;
import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.response.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 *@Author: telly
 *@Description:
 */
@ControllerAdvice
public class BaseControllerAdvice {

    private final static Logger logger = LoggerFactory.getLogger(BaseControllerAdvice.class);

    @ResponseBody
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseData httpMethodNotSupported(HttpServletRequest request, HttpRequestMethodNotSupportedException ex) {
        ResponseData responseData = new ResponseData();
        responseData.setStatus(StatusCode.HTTP_NOT_SUPPORT.getCode());
        responseData.setMsg(StatusCode.HTTP_NOT_SUPPORT.getMsg());
        responseData.setData(null);
        return responseData;
    }

    /**
     * 全局异常捕捉处理 ESBException
     * @param
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = ESBException.class)
    public ResponseData errorHandler(HttpServletRequest request, ESBException ex) {
        logger.error("error url:{}", request.getRequestURL());
        Object data=null;
        ResponseData responseData = new ResponseData();
        responseData.setStatus(ex.getStatus());
        responseData.setMsg(ex.getMsg());
        responseData.setData(ex.getMessage());
        logger.error("ESBException occurred with message: ",ex);
        return responseData;
    }

    /**
     * 系统内部 SystemException
     * @param
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = SystemException.class)
    public ResponseData errorHandler(HttpServletRequest request, SystemException ex) {
        logger.error("error url:{}", request.getRequestURL());
        Object data=null;
        ResponseData responseData = new ResponseData();
        responseData.setStatus(ex.getStatus());
        responseData.setMsg(ex.getMsg());
        responseData.setData(ex.getMessage());
        logger.error("SystemException occurred with message: ",ex);
        return responseData;
    }

    /**
     * 全局异常捕捉处理
     * @param
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseData errorHandler(HttpServletRequest request, Exception ex) {
        logger.error("error url:{}", request.getRequestURL());
        Object data=null;
        ResponseData responseData = new ResponseData();
        responseData.setStatus("500");
        responseData.setMsg(ex.getMessage());
        responseData.setData(ex.getMessage());
        logger.error("Exception occurred with message: ",ex);
        return responseData;
    }

}
