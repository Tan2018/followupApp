package com.ry.fu.esb.jpush.service;

import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.jpush.model.request.*;

import java.text.ParseException;
/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/4/26
 * @description 危急值controller
 */
public interface DoctoresManualService {


    /**
     * 危急值通知发布
     *
     * @param request
     * @return
     * @throws SystemException
     * @throws ESBException
     */
    public ResponseData sendCriticalValues(SendCriticalValuesRequest request) throws SystemException, ESBException, ParseException;

    /**
     * 查询危急值患者列表
     *
     * @param request
     * @return
     * @throws SystemException
     * @throws ESBException
     */
    public ResponseData selectByPatientInformation(PatientInformationsRequest request) throws SystemException, ESBException;

    /**
     * 危急值处理发布
     *
     * @param request
     * @return
     * @throws SystemException
     * @throws ESBException
     */
    public ResponseData sendCriticalValuesHandler(SendCriticalValuesHandlerRequest request) throws ParseException;

    /**
     * 危急值解除审核
     *
     * @param request
     * @return
     * @throws SystemException
     * @throws ESBException
     */
    public ResponseData sendCriticalValuesRelieve(SendCriticalValuesRelieveRequest request) throws ParseException;

    /**
     * 危急值处理(前端调用)
     *
     * @param request
     * @return
     * @throws SystemException
     * @throws ESBException
     */
    public ResponseData criticalValueProcessing(CriticalValueProcessingRequest request) throws SystemException, ESBException;

    /**
     * 查询医生手册推送条数
     *
     * @param request
     * @return
     */
    public ResponseData selectByPersonId(PushBarNumberRequest request);

    /**
     * 根据医生别名，清空某一类型的推送记录
     *
     * @param personId
     * @param noticeType
     * @return
     */
    public ResponseData deleteByPersonIdAndNoticeType(String personId, String noticeType);

    /**
     * 医生手册保存推送记录
     *
     * @param request
     * @return
     */
    public ResponseData savePushBarNumber(PushBarNumberRequest request);
}
