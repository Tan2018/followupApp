package com.ry.fu.esb.medicaljournal.service.impl;

import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.properties.RSAKeyProperties;
import com.ry.fu.esb.common.seq.RedisIncrementGenerator;
import com.ry.fu.esb.common.utils.BeanMapUtils;
import com.ry.fu.esb.common.utils.JsonUtils;
import com.ry.fu.esb.medicaljournal.mapper.OrderMapper;
import com.ry.fu.esb.medicaljournal.mapper.PayCashNotifyMapper;
import com.ry.fu.esb.medicaljournal.mapper.PayCashRefundNotifyMapper;
import com.ry.fu.esb.medicaljournal.mapper.PayCashReqMapper;
import com.ry.fu.esb.medicaljournal.model.Order;
import com.ry.fu.esb.medicaljournal.model.PayCashNotify;
import com.ry.fu.esb.medicaljournal.model.PayCashRefundNotify;
import com.ry.fu.esb.medicaljournal.service.OrderService;
import com.ry.fu.esb.medicaljournal.service.PayNotifyService;
import com.ry.fu.esb.medicaljournal.util.SignatureUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xuxu
 * @version V1.0.0
 * @Date date 2018/04/09 16:03
 * @description 支付模块的通知Service实现类
 */
@Service
public class PayNotifyServiceImpl implements PayNotifyService {

    private static Logger logger = LoggerFactory.getLogger(PayNotifyServiceImpl.class);

    @Autowired
    private PayCashReqMapper payCashReqMapper;

    @Autowired
    private PayCashNotifyMapper payCashNotifyMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RedisIncrementGenerator redisIncrementGenerator;

    @Autowired
    private OrderService orderService;
    @Autowired
    private RSAKeyProperties rsaKeyProperties;
    @Autowired
    private PayCashRefundNotifyMapper payCashRefundNotifyMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String payCashNotify(Map<String, String> params) {
        String retFlag = "fail";
        Long payCashReqId = payCashReqMapper.selectByNoncestrAndOutOrderId(params.get("outOrderNo"));
        Order order = orderMapper.selectByPrimaryKey(params.get("outOrderNo"));

        //每次通知都要新增一条记录
        PayCashNotify notify = new PayCashNotify();
        String key = BeanMapUtils.getTableSeqKey(notify);
        Long id = redisIncrementGenerator.increment(key, 1);
        notify.setId(id);
        notify.setReqId(payCashReqId);
        notify.setMerchantNo(params.get("merchantNo"));
        notify.setNoncestr(params.get("nonceStr"));
        notify.setOutOrderNo(params.get("outOrderNo"));
        notify.setAttach(params.get("attach"));
        notify.setNotifyType("0");
        notify.setNotifyData(JsonUtils.toJSon(params));
        notify.setCreateTime(new Date());
        payCashNotifyMapper.insertSelective(notify);

        //Todo :插入订单明细表
        if(order != null && "0".equals(order.getOrderStatus())) {
            order.setUpdateTime(new Date());
            order.setStatusRemark("回调支付成功");
            order.setTradeNo(params.get("tradeNo"));
            order.setAgtTradeNo(params.get("agtTradeNo"));
            order.setPayType(params.get("channelCode"));
            order.setOrderStatus("1");
            order.setPaySuccessTime(new Date());
            orderMapper.updateByPrimaryKeySelective(order);

             /*------------------------------start----bySeaton------------------------------------*/
           /* String attach = "";
            try {
                attach = URLDecoder.decode(params.get("attach"),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Map<String, String> retMap = JsonUtils.readValue(attach, HashMap.class);*/
            Map<String, String> retMap = new HashMap<>(4);
            retMap.put("tradeNo",params.get("tradeNo"));
            retMap.put("tradeTotalFee",params.get("tradeTotalFee"));
            retMap.put("outOrderNo",params.get("outOrderNo"));
            /*------------------------------end----------------------------------------*/
            try {
                if(orderService.paySuccess(retMap)) {
                    retFlag = "success";
                }
            } catch (SystemException e) {
                e.printStackTrace();
            } catch (ESBException e) {
                e.printStackTrace();
            }
        } else {
            logger.info("支付 order == null, outOrderNo:{}", params.get("outOrderNo"));
        }
        return retFlag;
    }

    /**
     * @param params
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String payCashRefundNotify(Map<String, String> params) {
        String outOrderNo = params.get("outOrderNo");   //Order表订单号
        //验签
        String encryptStr = params.remove("sign"); //秘钥
        logger.info("sign:{}", encryptStr);
        String password = SignatureUtils.getSortContent(params);
        logger.info("SortContent:{}", password);
        if(StringUtils.isBlank(password)) {
            return "fail";
        }
        boolean signFlag = SignatureUtils.rsa256CheckContent(password, encryptStr, rsaKeyProperties.getCasherPublicKey());
        if(!signFlag) {
            //验签失败
            return "fail";
        }
        if(params.get("tradeState") != null && "SUCCESS".equals(params.get("tradeState"))) {
            //退费成功
            Order order = orderMapper.selectByPrimaryKey(outOrderNo);
            if(order != null) {
                if(order.getOrderStatus() != null && !"5".equals(order.getOrderStatus())) {
                    order.setOrderStatus("5");
                    order.setStatusRemark("异步通知退款");
                    order.setUpdateTime(new Date());
                    order.setRefundDate(new Date());
                    orderMapper.updateByPrimaryKeySelective(order);
                    //通知成功，只需要修改订单状态就可以了，不需要调用HIS系统
                }

                PayCashRefundNotify payCashRefundNotify = new PayCashRefundNotify();
                String key = BeanMapUtils.getTableSeqKey(payCashRefundNotify);
                Long id = redisIncrementGenerator.increment(key, 1);
                payCashRefundNotify.setId(id);
                payCashRefundNotify.setNoncestr(params.get("nonceStr"));
                payCashRefundNotify.setOutOrderNo(outOrderNo);
                payCashRefundNotify.setRefundNo(params.get("refundNo"));
                payCashRefundNotify.setAttach(params.get("attach"));
                payCashRefundNotify.setTradeTotalFee(order.getTotalFee());
                payCashRefundNotify.setRefundFee(params.get("totalFee"));
                payCashRefundNotify.setRespData(JsonUtils.toJSon(params));
                payCashRefundNotify.setCreateTime(new Date());
                payCashRefundNotifyMapper.insertSelective(payCashRefundNotify);

                return "success";
            } else {
                logger.info("payCashRefundNotify 未查询到order:{}", outOrderNo);
            }
        }
        return "success";
    }
}
