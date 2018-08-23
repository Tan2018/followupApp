package com.ry.fu.esb.medicaljournal.service;

import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.response.ResponseData;

import java.util.Map;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/3/16 14:39
 * @description description
 */
public interface MedicalService {

    /**
     * 模糊搜索医生
     *
     * @param content
     * @param pageSize
     * @param pageNum
     * @return
     * @throws ESBException
     * @throws SystemException
     */
    public ResponseData searchDoctor(String content, String pageSize, String pageNum) throws ESBException, SystemException, InterruptedException;

    public void addOpOrgInfo(Map<String, Object> params) throws ESBException, SystemException;

    /**
     * 挂号支付
     *
     * @param orderId
     * @param payId
     * @param payFee
     * @param payTime
     * @param payDesc
     * @return
     */
    public ResponseData regPay(String orderId, String payId, String payFee, String payTime, String payDesc) throws ESBException,
            SystemException;

    public ResponseData pharmacys(String medicalNo) throws ESBException, SystemException;


    public ResponseData registrationDepartment(String medicalNo) throws ESBException, SystemException;

    public ResponseData PayTheFeesInquires(String operateType, String userCardType, String userCardId, String infoSeq, String
            orderStatus) throws
            ESBException, SystemException;

    void autoUpdatePatientToken();
}
