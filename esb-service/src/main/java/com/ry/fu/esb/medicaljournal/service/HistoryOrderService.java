package com.ry.fu.esb.medicaljournal.service;

import com.ry.fu.esb.common.response.ResponseData;

/**
 * @Author jane
 * @Date 2018/5/4
 * 获取历史订单
 */
public interface HistoryOrderService {

    ResponseData selectOrderHistory(Long accountId) ;

    ResponseData selectOrderPayHistory(Long accountId) ;

    ResponseData selectOrderNoPayHistory(Long accountId);


}
