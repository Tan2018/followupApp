package com.ry.fu.esb.medicaljournal.service;

import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.response.ResponseData;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/3/19 17:11
 * @description 支付Service
 */
public interface PayService {

    /**
     * 预支付申请
     * @param params
     * @return
     */
    public ResponseData preCashierPay(Map<String, String> params);

    /**
     * 支付Return，同步
     * @param params
     * @return
     */
    public ResponseData payReturn(Map<String, String> params);

    /**
     * 查询支付
     * @param params
     * @param orderId
     * @return
     */
    public ResponseData searchCashierPay(Map<String, String> params, String orderId);

    /**
     * 申请退费
     * @param params
     * @return
     * @throws ESBException
     * @throws SystemException
     */
    public ResponseData refundCashierPay(Map<String, String> params) throws ESBException, SystemException;

    /**
     * 退费查询
     * @param params
     * @return
     */
    public ResponseData searchCashierRefund(Map<String, String> params);


}
