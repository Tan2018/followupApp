package com.ry.fu.esb.doctorbook.controller;

import com.ry.fu.esb.common.enumstatic.StatusCode;
import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.doctorbook.service.ConsultationDoctorService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * @author Joker
 * @Date 2018/7/4 16:33
 * @description
 */
@RestController
@RequestMapping("${prefixPath}/v1/docBook/consultationDoctor")
public class ConsultationDoctorController {

    private Logger logger = LoggerFactory.getLogger(ConsultationDoctorController.class);

    @Autowired
    private ConsultationDoctorService consultationDoctorService;

    /**
     * 会诊患者列表
     *
     * @return
     */
    @RequestMapping(value = "/consultationPatient", method = RequestMethod.POST)
    public ResponseData consultationPatient(@RequestBody Map<String, String> params) throws SystemException, ESBException {
        //校验
    /*    if (CollectionUtils.isEmpty(params)) {
            return new ResponseData(StatusCode.ARGU_EMPTY.getCode(), StatusCode.ARGU_EMPTY.getMsg(), null);
        }
        if (params.get("content") == null || StringUtils.isBlank(params.get("content").toString())) {
            return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "content不能为空");

        }*/
        return consultationDoctorService.consultationPatient(params.get("consDoctorId"),params.get("orderMode"));
    }

}