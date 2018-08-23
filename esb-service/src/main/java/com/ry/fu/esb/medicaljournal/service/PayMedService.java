package com.ry.fu.esb.medicaljournal.service;

import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.response.ResponseData;

import java.util.Map;

/**
 * @author xuxu
 * @version V1.0.0
 * @Date date 2018/04/09 16:03
 * @description 医保支付service
 */
public interface PayMedService {

    public ResponseData preMedPay(Map<String, String> params);

    public ResponseData preMedAuth(Map<String, String> params);

    public ResponseData queryOrderStatus(Map<String, String> params);

    public ResponseData refundMedPay(Map<String, String> params) throws ESBException, SystemException;

    public ResponseData getPatientMessage(String patientId);

}
