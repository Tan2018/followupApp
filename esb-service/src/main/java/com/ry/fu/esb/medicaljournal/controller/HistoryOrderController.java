package com.ry.fu.esb.medicaljournal.controller;

import com.ry.fu.esb.common.enumstatic.StatusCode;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.medicaljournal.service.HistoryOrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author jane
 * @Date 2018/5/4
 * 历史订单
 */
@RestController
@RequestMapping("${prefixPath}/v1/medicalJournal/historyOrder")
public class HistoryOrderController {

    @Autowired
    private HistoryOrderService historyOrderService;

    /**
     * 已过期历史订单
     * @param map
     * @return
     */
    @RequestMapping(value = "/selectOrderHistory", method = RequestMethod.POST)
    public ResponseData selectOrderHistory(@RequestBody Map<String, Long> map) {
        if(map.get("accountId") == null || StringUtils.isBlank(map.get("accountId").toString())){
            return new ResponseData(StatusCode.ARGU_EMPTY.getCode(),StatusCode.ARGU_EMPTY.getMsg(),null);
        }
        Long accountId = map.get("accountId");

        return historyOrderService.selectOrderHistory(accountId);
    }

    /**
     * 已支付历史订单
     * @param map
     * @return
     */
    @RequestMapping(value = "/selectOrderPayHistory", method = RequestMethod.POST)
    public ResponseData selectOrderPayHistory(@RequestBody Map<String, Long> map) {
        if(map.get("accountId") == null || StringUtils.isBlank(map.get("accountId").toString())){
            return new ResponseData(StatusCode.ARGU_EMPTY.getCode(),StatusCode.ARGU_EMPTY.getMsg(),null);
        }
        Long accountId = map.get("accountId");
        return historyOrderService.selectOrderPayHistory(accountId);
    }

    /**
     * 未支付订单列表
     * @param map
     * @return
     */
    @RequestMapping(value = "/selectOrderNoPayHistory", method = RequestMethod.POST)
    public ResponseData selectOrderNoPayHistory(@RequestBody Map<String, Long> map){

        if(map.get("accountId") == null || StringUtils.isBlank(map.get("accountId").toString())){
            return new ResponseData(StatusCode.ARGU_EMPTY.getCode(),StatusCode.ARGU_EMPTY.getMsg(),null);
        }
        Long accountId = map.get("accountId");
        return historyOrderService.selectOrderNoPayHistory(accountId);
    }
}
