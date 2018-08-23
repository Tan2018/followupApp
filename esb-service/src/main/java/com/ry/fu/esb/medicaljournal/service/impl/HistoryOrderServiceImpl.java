package com.ry.fu.esb.medicaljournal.service.impl;

import com.ry.fu.esb.common.constants.Constants;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.medicaljournal.mapper.OrderMapper;
import com.ry.fu.esb.medicaljournal.model.response.OrderResponse;
import com.ry.fu.esb.medicaljournal.service.HistoryOrderService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author jane
 * @Date 2018/5/4
 * 历史订单
 */
@Service
public class HistoryOrderServiceImpl implements HistoryOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public ResponseData selectOrderPayHistory(Long accountId) {

        List<OrderResponse> orders = orderMapper.selectOrderPayHistory(accountId);
        if (orders.size() == 0) {
            return new ResponseData("200", "没有已支付订单", orders);
        }
        spiltTime(orders);
        return new ResponseData("200", "请求成功", orders);
    }

    @Override
    public ResponseData selectOrderHistory(Long accountId) {
        String currentDate = DateFormatUtils.format(new Date(), Constants.DATE_YYYY_MM_DD_HH_MM_SS);
        List<OrderResponse> orders = orderMapper.selectOrderHistory(accountId, currentDate);
        if (orders.size() == 0) {
            return new ResponseData("200", "没有已过期订单", orders);
        }
        spiltTime(orders);
        return new ResponseData("200", "请求成功", orders);
    }

    @Override
    public ResponseData selectOrderNoPayHistory(Long accountId) {
        String currentDate = DateFormatUtils.format(new Date(), Constants.DATE_YYYY_MM_DD_HH_MM_SS);
        List<OrderResponse> orders = orderMapper.selectOrderNoPayHistory(accountId, currentDate);
        if (orders.size() == 0) {
            return new ResponseData("200", "没有未支付订单", orders);
        }
        spiltTime(orders);
        return new ResponseData("200", "请求成功", orders);
    }

    private void spiltTime(List<OrderResponse> orders) {
        for (int i = 0; i < orders.size(); i++) {
            //创建时间不为空且长度大于16
            if (StringUtils.isNotBlank(orders.get(i).getCreateTime()) && orders.get(i).getCreateTime().length() > 16) {
                String creatTime = orders.get(i).getCreateTime().substring(0, 16);
                orders.get(i).setCreateTime(creatTime);
            }
        }
    }
}
