package com.ry.fu.esb.medicaljournal.service;

import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.response.ResponseData;

import java.util.Map;

/**
 * @author xuxu
 * @version V1.0.0
 * @Date date 2018/04/09 16:03
 * @description 医保支付模块的异步通知接口类
 */
public interface PayMedNotifyService {
    //支付通知
    public String payMedNotify(Map<String, String> params) throws SystemException, ESBException;

    //授权通知
    public String authMedNotify(Map<String, String> params) throws SystemException, ESBException;

    //退费通知
    public ResponseData payMedRefundNotify(Map<String, String> params) throws ESBException, SystemException;


}
