package com.ry.fu.esb.medicaljournal.service;

import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/3/19 17:11
 * @description 支付模块的异步通知接口类
 */
public interface PayNotifyService {

    public String payCashNotify(Map<String, String> params) throws ESBException, SystemException;

    public String payCashRefundNotify(Map<String, String> params);


}
