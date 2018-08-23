package com.ry.fu.esb.medicaljournal.scheduler;

import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.medicaljournal.service.MedicalService;
import com.ry.fu.esb.medicaljournal.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/4/2 10:12
 * @description 订单管理
 */
@Profile(value = {"pro", "test"})
@Component
public class OrderTask {

    private Logger logger = LoggerFactory.getLogger(OrderTask.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private MedicalService medicalService;


    /*
     * 定时检查订单状态，取消已经过时挂号(通过ESB取消预约)以及通知支付
     * 每五分钟执行一次
     */
   @Scheduled(cron = "0 0/5 * * * ? ")
    public void cancleReg() {
        orderService.payInform();
        try {
            orderService.cancleReg();
        } catch (ESBException e) {
            logger.error("ESBException异常", e);
            e.printStackTrace();
        } catch (SystemException e) {
            logger.error("SystemException异常", e);
            e.printStackTrace();
        }

    }

    /**
     * 定时同步已支付订单，主动查询 待支付订单，同步医程通状态，如果是已支付，则改为已支付
     */
    @Scheduled(cron = "0 0/5 * * * ? ")
    public void autoPayOrder() {
        try {
            orderService.autoPayOrder();
        } catch (Exception e) {
            logger.error("Exception", e);
            e.printStackTrace();
        }
    }

    /**
     * 晚上9点通知次日上午就诊
     */
    @Scheduled(cron = "0 0 21 * * ? ")
    public void seeDoctorTomorrowInform(){
        orderService.seeDoctorTomorrowInform();
    }

    /**
     * 下午就诊当天9点通知
     */
   @Scheduled(cron = "0 0 9 * * ? ")
    public void seeDoctorTodayInform(){
       orderService.seeDoctorTodayInform();
   }


    /**
     * 凌晨3点更新m_patient的token
     */
    @Scheduled(cron = "0 0 9 * * ? ")
    public void updatePatientToken(){
        medicalService.autoUpdatePatientToken();
    }


}
