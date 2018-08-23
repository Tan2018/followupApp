package com.ry.fu.esb.medicaljournal.controller;


import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.medicaljournal.model.HospitalInfo;
import com.ry.fu.esb.medicaljournal.service.HospitalInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/4/12 16:02
 **/
@RestController
@RequestMapping("${prefixPath}/v1/medicalJournal/hospital")
public class HospitalInfoController {
    private static Logger logger = LoggerFactory.getLogger(HospitalInfoController.class);

    @Autowired
    private HospitalInfoService hospitalInfoService;

    @RequestMapping(value = "/queryHospitalIntroductionInfo",method =  RequestMethod.POST)
    public ResponseData selectHospitalIntroductionInfo()  throws SystemException, ESBException {
        try{
            ResponseData responseData=hospitalInfoService.findHospitalIntroductionInfo();
            return responseData;
        }catch (Exception e){
            return new ResponseData("500","操作失败",null);
        }
    }

    @RequestMapping(value = "/queryFloorDistributionAndTrafficGuidanceInfo",method =  RequestMethod.POST)
    public ResponseData queryFloorDistributionAndTrafficGuidanceInfo()  throws SystemException, ESBException {
        try{
            ResponseData responseData=hospitalInfoService.findFloorDistributionAndTrafficGuidanceInfo();
            return  responseData;
        }catch (Exception e){
            return new ResponseData("500","操作失败",null);
        }
    }
}
