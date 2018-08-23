package com.ry.fu.esb.medicaljournal.controller;

import com.ry.fu.esb.common.enumstatic.StatusCode;
import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.medicaljournal.service.MedicalService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/3/16 14:15
 * @description description
 */
@RestController
@RequestMapping("${prefixPath}/v1/medicalJournal/medical")
public class MedicalController {

    @Autowired
    private MedicalService medicalService;

    private Logger logger = LoggerFactory.getLogger(MedicalController.class);

    /**
     * 模糊查询医生
     *
     * @return
     */
    @RequestMapping(value = "/searchDoctor", method = RequestMethod.POST)
    public ResponseData searchDoctor(@RequestBody Map<String, String> params) throws SystemException, ESBException, InterruptedException {
        //校验
        if (CollectionUtils.isEmpty(params)) {
            return new ResponseData(StatusCode.ARGU_EMPTY.getCode(), StatusCode.ARGU_EMPTY.getMsg(), null);
        }
        if (params.get("content") == null || StringUtils.isBlank(params.get("content").toString())) {
            return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "content不能为空");

        }
        if (params.get("pageSize") == null || StringUtils.isBlank(params.get("pageSize").toString())) {
            return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "pageSize不能为空");
        }
        if (params.get("pageNum") == null || StringUtils.isBlank(params.get("pageNum").toString())) {
            return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "pageNum不能为空");
        }
        return medicalService.searchDoctor(params.get("content"), params.get("pageSize"), params.get("pageNum"));
    }

    /**
     * 取药优化；
     */

    @RequestMapping(value = "/pharmacys", method = RequestMethod.POST)
    public ResponseData pharmacys(@RequestBody Map<String, String> params) throws SystemException, ESBException {
        if (CollectionUtils.isEmpty(params)) {
            return new ResponseData(StatusCode.ARGU_EMPTY.getCode(), StatusCode.ARGU_EMPTY.getMsg(), null);
        }
        if (params.get("medicalNo") == null || StringUtils.isBlank(params.get("medicalNo").toString())) {
            return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "medicalNo不能为空");
        }
        return medicalService.pharmacys(params.get("medicalNo").toString());

    }


    /**
     * 获取挂号的所有科室
     */
    @RequestMapping(value = "/registrationDepartment", method = RequestMethod.POST)
    public ResponseData registrationDepartment(@RequestBody Map<String, String> params) throws SystemException, ESBException {

        if (CollectionUtils.isEmpty(params)) {
            return new ResponseData(StatusCode.ARGU_EMPTY.getCode(), StatusCode.ARGU_EMPTY.getMsg(), null);
        }
        if (params.get("medicalNo") == null || StringUtils.isBlank(params.get("medicalNo").toString())) {
            return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "MedicalNo不能为空");
        }
        return medicalService.registrationDepartment(params.get("medicalNo").toString());

    }


    /**
     * 用户代缴费优化；
     */

    @RequestMapping(value = "/captureExpendsQuerys", method = RequestMethod.POST)
    public ResponseData PayTheFeesInquires(@RequestBody Map<String, String> params) {
        try {
            if (CollectionUtils.isEmpty(params)) {
                return new ResponseData(StatusCode.ARGU_EMPTY.getCode(), StatusCode.ARGU_EMPTY.getMsg(), null);
            }
            if (params.get("OperateType") == null || StringUtils.isBlank(params.get("OperateType").toString())) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "OperateType不能为空");
            }
            if (params.get("userCardType") == null || StringUtils.isBlank(params.get("userCardType").toString())) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "userCardType不能为空");
            }

            if (params.get("userCardId") == null || StringUtils.isBlank(params.get("userCardId").toString())) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "userCardId不能为空");
            }
            if (params.get("infoSeq") == null || StringUtils.isBlank(params.get("infoSeq").toString())) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "infoSeq不能为空");
            }
            if (params.get("orderStatus") == null || StringUtils.isBlank(params.get("orderStatus").toString())) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "orderStatus不能为空");
            }
            return medicalService.PayTheFeesInquires(params.get("OperateType").toString(), params.get("userCardType").toString(), params.get
                    ("userCardId").toString(), params.get("infoSeq").toString(), params.get("orderStatus").toString());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("===========================代缴费访问失败========================", e);
            return new ResponseData(StatusCode.INSIDE_ERROR.getCode(), StatusCode.INSIDE_ERROR.getCode(), "代缴费访问失败");
        }
    }
}