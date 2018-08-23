package com.ry.fu.esb.medicaljournal.controller;

import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.medicaljournal.service.AppSetService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/4/26 16:54
 **/
@RestController
@RequestMapping("${prefixPath}/v1/medicalJournal/appSet")
public class AppSetController {

    @Autowired
    private AppSetService appSetService;

    @RequestMapping(value = "/queryNewAppVersion",method = RequestMethod.POST)
    public ResponseData queryNewAppVersion(@RequestBody Map<String,Object> map){
        if(map == null ){
            return new ResponseData("500","参数不能为空",null);
        }
        if(map.get("useType") == null || StringUtils.isBlank(map.get("useType").toString())){
            return new ResponseData("500","useType不能为空",null);
        }
        if(map.get("appId") == null || StringUtils.isBlank(map.get("appId").toString())){
            return new ResponseData("500","appId不能为空",null);
        }
        return appSetService.findNewAppVersion(map.get("useType").toString(),map.get("appId").toString());
    }


    @RequestMapping(value ="/addQuesFeedbackInfo",method = RequestMethod.POST)
    public ResponseData addQuesFeedbackInfo(@RequestBody Map<String,Object> map){
        if(map == null) {
            return new ResponseData("500","参数不能为空",null);
        }
        if(map.get("accountId") == null || StringUtils.isBlank(map.get("accountId").toString())) {
            return new ResponseData("500","accountId不能为空",null);
        }
        if(map.get("verNo") == null || StringUtils.isBlank(map.get("verNo").toString())) {
            return new ResponseData("500","verNo不能为空",null);
        }
        if(map.get("ques") == null || StringUtils.isBlank(map.get("ques").toString())) {
            return new ResponseData("500","ques不能为空",null);
        }
        if(map.get("userType") == null || StringUtils.isBlank(map.get("userType").toString())) {
            return new ResponseData("500","userType不能为空",null);
        }
        return appSetService.insertQuesFeedbackInfo(map);
    }

    @RequestMapping(value = "/queryAboutAppInfo",method = RequestMethod.POST)
    public ResponseData queryAboutAppInfo(@RequestBody Map<String,Object> map){
        if(map == null ){
            return new ResponseData("500","参数不能为空",null);
        }
        if(map.get("useType") == null || StringUtils.isBlank(map.get("useType").toString())){
            return new ResponseData("500","useType不能为空",null);
        }
        return appSetService.findAboutAppInfo(map.get("useType").toString());
    }
}
