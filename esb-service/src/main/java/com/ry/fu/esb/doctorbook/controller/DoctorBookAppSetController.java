package com.ry.fu.esb.doctorbook.controller;

import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.doctorbook.service.DoctorBookAppSetService;
import com.ry.fu.esb.medicaljournal.model.request.DoctorQueryRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/5/24 17:36
 **/
@RestController
@RequestMapping("${prefixPath}/v1/docBook/doctorAppSet")
public class DoctorBookAppSetController {

    @Autowired
    private DoctorBookAppSetService doctorBookAppSetService;

    @RequestMapping("/queryDoctorPersonalData")
    public ResponseData queryDoctorPersonalData(@RequestBody  DoctorQueryRequest request)throws ESBException, SystemException {
        if(request.getDoctorId()==null|| StringUtils.isBlank(request.getDoctorId())){
            return new ResponseData("500","doctorId参数不能为空",null);
        }
        return doctorBookAppSetService.findDoctorPersonalData(request);
    }
}
