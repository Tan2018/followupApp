package com.ry.fu.esb.doctorbook.controller;

import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.doctorbook.service.HandInformService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/5/8 11:05
 **/
@RestController
@RequestMapping("${prefixPath}/v1/docBook/handInform")
public class HandInformConstroller {

    @Autowired
    private HandInformService handInformService;

    @RequestMapping(value = "/queryHandInformInfo",method = RequestMethod.POST)
    public ResponseData queryHandInformInfo(@RequestBody Map<String,String> map) {
        if(map==null){
            return new ResponseData("500","参数不能为空",null);
        }
        if(map.get("doctorId")==null|| StringUtils.isBlank(map.get("doctorId").toString())){
            return new ResponseData("500","doctorId不能为空",null);
        }
        if(map.get("pageNum")==null|| StringUtils.isBlank(map.get("pageNum").toString())){
            return new ResponseData("500","pageNum不能为空",null);
        }
        if(map.get("pageSize")==null|| StringUtils.isBlank(map.get("pageSize").toString())){
            return new ResponseData("500","pageSize不能为空",null);
        }
        try{
            ResponseData responseData=handInformService.findHandInformInfo(map);
            return responseData;
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseData("500","请求失败",null);
        }
    }
}
