package com.ry.fu.esb.medicaljournal.service.impl;

import com.ry.fu.esb.common.constants.Constants;
import com.ry.fu.esb.common.enumstatic.ESBServiceCode;
import com.ry.fu.esb.common.enumstatic.StatusCode;
import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.common.seq.RedisIncrementGenerator;
import com.ry.fu.esb.common.service.PublicService;
import com.ry.fu.esb.common.utils.BeanMapUtils;
import com.ry.fu.esb.common.utils.JsonUtils;
import com.ry.fu.esb.medicaljournal.enums.PayStatusEnum;
import com.ry.fu.esb.medicaljournal.mapper.*;
import com.ry.fu.esb.medicaljournal.model.*;
import com.ry.fu.esb.medicaljournal.model.request.RegisterCanRefundRequest;
import com.ry.fu.esb.medicaljournal.model.request.RegisterRefundRequest;
import com.ry.fu.esb.medicaljournal.model.response.ResultResponse;
import com.ry.fu.esb.medicaljournal.service.MedicalService;
import com.ry.fu.esb.medicaljournal.service.OrderService;
import com.ry.fu.esb.medicaljournal.service.PayMedNotifyService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xuxu
 * @version V1.0.0
 * @Date date 2018/04/09 16:02
 * @description 医保支付模块的通知Service实现类
 */
@Service
public class PayMedNotifyServiceImpl implements PayMedNotifyService {

    private static Logger logger = LoggerFactory.getLogger(PayMedNotifyServiceImpl.class);

    @Autowired
    private PayMedReqMapper payMedReqMapper;

    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private PayMedNotifyMapper payMedNotifyMapper;

    @Autowired
    private PayMedRefundReqMapper payMedRefundReqMapper;

    @Autowired
    private PayMedAuthNotifyMapper payMedAuthNotifyMapper;

    @Autowired
    private PayMedAuthReqMapper payMedAuthReqMapper;

    @Autowired
    private PublicService publicService;

    @Autowired
    private PayMedRefundNotifyMapper payMedRefundNotifyMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RedisIncrementGenerator redisIncrementGenerator;

    @Autowired
    private MedicalService medicalService;

    @Autowired
    private OrderService orderService;

    /**
     * 支付异步回调
     *
     * @param params
     * @return
     * @throws SystemException
     * @throws ESBException
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String payMedNotify(Map<String, String> params) throws ESBException, SystemException {
        String retFlag = "fail";
        Order order = orderMapper.selectByPrimaryKey(params.get("mchOrderNo"));
        order.setAgtTradeNo(params.get("orderNo"));
        orderMapper.updateByPrimaryKeySelective(order);
        Long payMedReqId = payMedReqMapper.selectByMchOrderNo(params.get("mchOrderNo"));
        if (order != null && PayStatusEnum.CREADED.getCode().equals(order.getOrderStatus())) {
            //修改支付请求
            if (payMedReqId != null && payMedReqId != 0) {
                PayMedReq payMedReq = new PayMedReq();
                payMedReq.setId(payMedReqId);
                payMedReq.setRespData(JsonUtils.toJSon(params));
                payMedReqMapper.updateByPrimaryKeySelective(payMedReq);
            }
            //增加支付通知
            PayMedNotify notify = new PayMedNotify();
            String key = BeanMapUtils.getTableSeqKey(notify);
            Long id = redisIncrementGenerator.increment(key, 1);
            notify.setId(id);
            notify.setReqId(payMedReqId);
            notify.setNotifyData(JsonUtils.toJSon(params));
            notify.setOrderPayTime(params.get("orderPayTime"));
            notify.setOrderStatus(params.get("orderStatus"));
            payMedNotifyMapper.insertSelective(notify);
            //修改订单状态，his业务
            /*------------------------------start----bySeaton------------------------------------*/
            String attach = null;
            try {
                attach = URLDecoder.decode(params.get("attach"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Map<String, String> retMap = JsonUtils.readValue(attach, HashMap.class);
//            retMap.put("tradeTotalFee", params.get("tradeTotalFee"));
//            retMap.put("tradeNo",params.get("tradeNo"));
            retMap.put("outOrderNo", params.get("mchOrderNo"));
            //retMap.put("isMedPay","YES");
            /*------------------------------end----------------------------------------*/
            if (orderService.paySuccess(retMap)) {
                retFlag = "success";
            }
        } else {
            logger.info("支付 order == null, orderId:{}", params.get("outOrderNo"));
        }
        return retFlag;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String authMedNotify(Map<String, String> params) {
        logger.info("支付 pay == null, payId:{}", JsonUtils.toJSon(params));

        //查找授权请求对象
        Long authReqId = payMedAuthReqMapper.selectByAttach(params.get("attach"));
        //更新patient.authCode
        Patient patient = new Patient();
        patient.setEsbPatientId(Long.valueOf(params.get("mchUserId")));
        patient.setAuthCode(params.get("authCode"));
        patient.setAuthStatus(params.get("authStatus"));
        Example example = new Example(Patient.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("esbPatientId", patient.getEsbPatientId());
        patientMapper.updateByExampleSelective(patient, example);
        //保存通知对象
        PayMedAuthNotify payAuthMedNotify = new PayMedAuthNotify();
        String key = BeanMapUtils.getTableSeqKey(payAuthMedNotify);
        Long id = redisIncrementGenerator.increment(key, 1);
        payAuthMedNotify.setId(id);
        payAuthMedNotify.setReqId(authReqId);
        payAuthMedNotify.setAuthCode(params.get("phoneNumber"));
        payAuthMedNotify.setAuthStatus(params.get("authStatus"));
        payAuthMedNotify.setUserPhone(params.get("phoneNumber"));
        payAuthMedNotify.setAttach(params.get("attach"));
        payAuthMedNotify.setMchId(params.get("mchid"));
        payAuthMedNotify.setMchUserId(params.get("mchUserId"));
        payAuthMedNotify.setNotifyData(JsonUtils.toJSon(params));
        payAuthMedNotify.setResultCode(params.get("resultCode"));
        payAuthMedNotify.setResultMessage(params.get("resultMessage"));
        payAuthMedNotify.setTimestamp(params.get("timestamp"));
        payMedAuthNotifyMapper.insertSelective(payAuthMedNotify);
        return "success";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData payMedRefundNotify(Map<String, String> params) throws ESBException, SystemException {
        String orderId = params.get("mchOrderNo");//实际是payid，order中successPayId
        Map<String, String> retMap = new HashMap<String, String>();
        retMap.put("mchOrderNo", orderId);
        try {
            String reqId = params.get("mchRefundOrderNo");
            String mchRefundOrderNo = params.get("mchRefundOrderNo");//实际是 医保退费请求id,可以找到order
            logger.info("mchRefundOrderNo:" + mchRefundOrderNo);
            PayMedRefundReq payMedRefundReq = payMedRefundReqMapper.selectByPrimaryKey(mchRefundOrderNo);
            Order order = orderMapper.selectByPrimaryKey(orderId);
            if (StringUtils.isNotBlank(order.getOrderStatus()) && !PayStatusEnum.REFUNDED.getCode().equalsIgnoreCase(order.getOrderStatus())) {
                //todo his退费
                //先判断是否允许退费
                ResultResponse resultResponse = null;
                if("挂号费".equals(order.getFeeType()) || "诊查费".equals(order.getFeeType())) {
                    RegisterCanRefundRequest registerCanRefundRequest = new RegisterCanRefundRequest();
                    registerCanRefundRequest.setOrderId(String.valueOf(order.getId()));
                    registerCanRefundRequest.setOrderType(Constants.SILVER_MEDICINE_TYPE_NO);
                    registerCanRefundRequest.setReturnAmout(order.getTotalFee());

                    ResponseData responseData = null;
                    try {
                        responseData = publicService.query(ESBServiceCode.REGISTERCANREFUND, registerCanRefundRequest, ResultResponse.class);
                    } catch (SystemException e) {
                        e.printStackTrace();
                    } catch (ESBException e) {
                        e.printStackTrace();
                    }
                    resultResponse = (ResultResponse) responseData.getData();
                }
                if(resultResponse == null || resultResponse.getRecord() == null) {
                    //未退费成功，未响应数据
                    return new ResponseData("601", "HIS申请挂号退费接口无响应", null);
                }
                if(!"0".equals(resultResponse.getRecord().getResultCode())) {
                    return new ResponseData("601", "HIS系统不允许退费", resultResponse.getRecord().getResultDesc());
                }

                //取消挂号，退费
                //调用ESB退费接口
                if("挂号费".equals(order.getFeeType()) || "诊查费".equals(order.getFeeType())) {
                    RegisterRefundRequest registerRefundRequest = new RegisterRefundRequest();
                    registerRefundRequest.setOrderId(String.valueOf(order.getId()));
                    registerRefundRequest.setOrderType(Constants.SILVER_MEDICINE_TYPE_NO);
                    registerRefundRequest.setReturnNum(String.valueOf(reqId));
                    registerRefundRequest.setReturnTime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    registerRefundRequest.setReturnAmout(order.getTotalFee());
                    ResponseData responseData = null;
                    try {
                        responseData = publicService.query(ESBServiceCode.REGISTERREFUND, registerRefundRequest, ResultResponse.class);
                        resultResponse = (ResultResponse) responseData.getData();
                    } catch (SystemException e) {
                        e.printStackTrace();
                    } catch (ESBException e) {
                        e.printStackTrace();
                    }


                } else {
                    //检验检查缴费，待调用
                }

                if(resultResponse == null || resultResponse.getRecord() == null) {
                    //未退费成功，未响应数据
                    return new ResponseData(StatusCode.REFUND_FAILD.getCode(), StatusCode.REFUND_FAILD.getMsg(), null);
                }

                if(!"0".equals(resultResponse.getRecord().getResultCode()) && !"true".equals(params.get("ignore"))) {
                    //未退费成功，未响应数据
                    return new ResponseData(StatusCode.REFUND_FAILD.getCode(), "HIS系统退费失败", resultResponse.getRecord().getResultDesc());
                }

                // his 退费结束


                //保存退费相应数据
                PayMedRefundNotify payMedRefundNotify = new PayMedRefundNotify();
                String payMedRefundNotify_id = BeanMapUtils.getTableSeqKey(payMedRefundNotify);
                Long reundId = redisIncrementGenerator.increment(payMedRefundNotify_id, 1);
                payMedRefundNotify.setId(reundId);
                payMedRefundNotify.setMchOrderNo(params.get("orderNo"));
                payMedRefundNotify.setReqId(payMedRefundReq.getId());
                payMedRefundNotify.setMchOrderNo(params.get("mchOrderNo"));
                payMedRefundNotify.setMchRefundOrderNo(params.get("refundOrderNo"));
                payMedRefundNotify.setOrderRefundTime(params.get("orderRefundTime"));
                payMedRefundNotify.setMchRefundOrderNo(params.get("mchRefundOrderNo"));
                payMedRefundNotify.setOrderStatus(params.get("orderStatus"));
                payMedRefundNotifyMapper.insertSelective(payMedRefundNotify);
                //修改订单状态
                order.setRefundDate(new Date());
                order.setOrderStatus(PayStatusEnum.REFUNDED.getCode());  //退费
                orderMapper.updateByPrimaryKeySelective(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
            new ResponseData(StatusCode.REFUND_FAILD.getCode(), "退费失败", retMap);
        }
        return new ResponseData(StatusCode.OK.getCode(), "申请退费成功", retMap);
    }
}
