package com.ry.fu.esb.medicaljournal.controller;

import com.ry.fu.esb.common.enumstatic.StatusCode;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.medicaljournal.service.JPushNoticeService;
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
 * @create: 2018/5/30 16:41
 **/
@RestController
@RequestMapping("${prefixPath}/v1/medicalJournal/order")
public class OrderController {

    @Autowired
    private JPushNoticeService jPushNoticeService;

    @RequestMapping(value = "/sendPushTheItem", method = RequestMethod.POST)
    public ResponseData sendPushTheItem(@RequestBody  Map<String,Object> map){
//        if(map==null){
//
//        }
//        if(map.get("openId").toString()==null|| StringUtils.isBlank(map.get("openId").toString())){
//
//        }
//        if(map.get("patientId").toString()==null|| StringUtils.isBlank(map.get("patientId").toString())){}
//        if(map.get("patientName").toString()==null|| StringUtils.isBlank(map.get("patientName").toString())){}
//        if(map.get("orderId").toString()==null|| StringUtils.isBlank(map.get("orderId").toString())){}
//        if(map.get("time").toString()==null|| StringUtils.isBlank(map.get("time").toString())){}
//        if(map.get("pushType").toString()==null|| StringUtils.isBlank(map.get("pushType").toString())){}
        try{
            jPushNoticeService.sendPushTheItem( map.get("patientId").toString(),
                    map.get("patientName").toString(),
                    map.get("orderId").toString(),
                    map.get("time").toString(),
                    map.get("cause").toString(),
                    Integer.valueOf(map.get("pushType").toString()));
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseData("500","催缴款失败",null);
        }

        return new ResponseData(StatusCode.OK.getCode(),"催缴款成功",1);
    }
}
