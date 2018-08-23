package com.ry.fu.esb.medicaljournal.service;

import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.medicaljournal.model.Order;
import com.ry.fu.esb.medicaljournal.model.PaySucesessRecordVO;
import com.ry.fu.esb.medicaljournal.model.RegisterSucesessRecordVO;
import com.ry.fu.esb.medicaljournal.model.RegisteredRecord;

import java.util.Map;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/4/2 10:37
 * @description 订单服务接口
 */
public interface OrderService {

    public boolean cancleReg() throws ESBException, SystemException;

    public boolean paySuccess(Map<String, String> retMap) throws SystemException, ESBException;

    /**
     * 通知支付
     */
    void payInform();

    void autoPayOrder();


    /**
     * 查询订单
     */
    Order selectByPrimaryKey(String orderId) ;

    /**
     * 支付成功页面(挂号)
     * @param orderId
     * @return
     */
    RegisterSucesessRecordVO registerSucesessRecord(String orderId);

    /**
     * 支付成功页面(缴费)
     * @param orderId
     * @return
     */
    PaySucesessRecordVO paySucesessRecord(String orderId);


    /**
     * 晚上9点通知次日上午就诊
     */
    void seeDoctorTomorrowInform();

    /**
     * 下午就诊当天9点通知
     */
    void seeDoctorTodayInform();


    /**
     * 同步就诊状态
     */
    void syncDiagnoseFlag();

    /**
     * 查询当前订单是否可支付
     * @param orderId
     */
    Boolean isValid(String orderId);

    RegisteredRecord selectRegisterRecordByOrderId(String orderId);
}
