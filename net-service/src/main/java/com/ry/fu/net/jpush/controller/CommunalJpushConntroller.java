package com.ry.fu.net.jpush.controller;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;
import com.ry.fu.net.common.enumstatic.StatusCode;
import com.ry.fu.net.common.response.ResponseData;
import com.ry.fu.net.common.seq.RedisIncrementGenerator;
import com.ry.fu.net.common.utils.BeanMapUtils;
import com.ry.fu.net.common.utils.JsonUtils;
import com.ry.fu.net.jpush.model.JpushHistoricalRecords;
import com.ry.fu.net.jpush.service.JpushHistoricalRecordsService;
import com.ry.fu.net.jpush.utils.JPushUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/6/4
 * @description 极光推送controller
 */
@RestController
@RequestMapping("/netService/v1/jpush/communalJpush")
public class CommunalJpushConntroller {

    private static Logger logger = LoggerFactory.getLogger(CommunalJpushConntroller.class);
    //医生手册（正式）
    @Value("${pro.doctorbookAppKey}")
    private String doctorbookAppKey;

    @Value("${pro.doctorbookMasterSecret}")
    private String doctorbookMasterSecret;
    //就医日志（正式）
    @Value("${pro.medicaljournalAppKey}")
    private String medicaljournalAppKey;

    @Value("${pro.medicaljournalMasterSecret}")
    private String medicaljournalMasterSecret;
    //医生手册（测试）
    @Value("${test.doctorbookAppKey}")
    private String testDoctorbookAppKey;

    @Value("${test.doctorbookMasterSecret}")
    private String testDoctorbookMasterSecret;
    //就医日志（测试）
    @Value("${test.medicaljournalAppKey}")
    private String testMedicaljournalAppKey;

    @Value("${test.medicaljournalMasterSecret}")
    private String testMedicaljournalMasterSecret;
    //医生手册（预发布）
    @Value("${pre.doctorbookAppKey}")
    private String preDoctorbookAppKey;

    @Value("${pre.doctorbookMasterSecret}")
    private String preDoctorbookMasterSecret;
    //就医日志（预发布）
    @Value("${pre.medicaljournalAppKey}")
    private String preMedicaljournalAppKey;

    @Value("${pre.medicaljournalMasterSecret}")
    private String preMedicaljournalMasterSecret;
    @Autowired
    private JpushHistoricalRecordsService jpushHistoricalRecordsService;

    @Autowired
    private RedisIncrementGenerator redisIncrementGenerator;

    /**
     * 推送公共接口
     *
     * @param params {doctorbook:1}等数据格式
     * @return
     */
    @RequestMapping(value = "/publicSendPush", method = RequestMethod.POST)
    public ResponseData publicSendPush(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            logger.info("调用人ip：{}", request.getRemoteAddr());
            logger.info("推送请求参数：{}", params);
            //参数校验
            if (params == null || params.size() == 0) {
                return new ResponseData(StatusCode.ARGU_EMPTY.getCode(), StatusCode.ARGU_EMPTY.getMsg(), null);
            }
            if (params.get("doctorbook") == null || StringUtils.isBlank(params.get("doctorbook").toString())) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "doctorbook不能为空");
            }
            if (params.get("title") == null || StringUtils.isBlank(params.get("title").toString())) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "title不能为空");
            }
            if (params.get("notification_title") == null || StringUtils.isBlank(params.get("notification_title").toString())) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "notification_title不能为空");
            }
            if (params.get("alias") == null || StringUtils.isBlank(params.get("alias").toString())) {
                if (params.get("aliaes") == null || StringUtils.isBlank(params.get("aliaes").toString())) {
                    return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "aliaes和alias必须有一个不为空");
                }
            }
            PushPayload pushPayload = null;
            JpushHistoricalRecords jpushHistoricalRecords = new JpushHistoricalRecords();
            String alias = (String) params.get("alias");
            List<String> aliaes = (List<String>) params.get("aliaes");
            String title = (String) params.get("title");
            String notification_title = (String) params.get("notification_title");
            Integer pushType = (Integer) params.get("pushType");
            Map<String, String> extras = null;
            //设置要保存的推送记录
            String key = BeanMapUtils.getTableSeqKey(jpushHistoricalRecords);
            Long id = redisIncrementGenerator.increment(key, 1);
            jpushHistoricalRecords.setId(id);
            jpushHistoricalRecords.setAcceptancePlatform(params.get("doctorbook").toString());
            jpushHistoricalRecords.setTitle(title);
            jpushHistoricalRecords.setContent(notification_title);
            jpushHistoricalRecords.setSendingTime(new Date());
            if (params.get("extras") != null) {
                extras = (Map<String, String>) params.get("extras");
                jpushHistoricalRecords.setExtras(JsonUtils.toJSon(extras));
            }
            if (aliaes != null) {
                pushPayload = JPushUtil.buildPushObject_android_ios_alias(aliaes, title, notification_title, extras);
                jpushHistoricalRecords.setRecipient(JsonUtils.toJSon(aliaes));
            } else {
                pushPayload = JPushUtil.buildPushObject_android_ios_alias(alias, notification_title, title, extras, pushType);
                jpushHistoricalRecords.setRecipient(alias);
            }
            JPushClient jPushClient = null;
            if (params.get("pushTo") == null || StringUtils.isBlank(params.get("pushTo").toString())) {//推正式
                if (params.get("doctorbook").equals("1")) {
                    logger.info("推送至医生手册(正式环境)..");
                    jPushClient = new JPushClient(doctorbookMasterSecret, doctorbookAppKey);
                } else {
                    logger.info("推送至就医日志(正式环境)..");
                    jPushClient = new JPushClient(medicaljournalMasterSecret, medicaljournalAppKey);
                }
            } else if("2".equals(params.get("pushTo"))){//推预发布
                if (params.get("doctorbook").equals("1")) {
                    logger.info("推送至医生手册(预发布环境)..");
                    jPushClient = new JPushClient(preDoctorbookMasterSecret, preDoctorbookAppKey);
                } else {
                    logger.info("推送至就医日志(预发布环境)..");
                    jPushClient = new JPushClient(preMedicaljournalMasterSecret, preMedicaljournalAppKey);
                }
            } else{//推测试
                if (params.get("doctorbook").equals("1")) {
                    logger.info("推送至医生手册(测试环境)..");
                    jPushClient = new JPushClient(testDoctorbookMasterSecret, testDoctorbookAppKey);
                } else {
                    logger.info("推送至就医日志(测试环境)..");
                    jPushClient = new JPushClient(testMedicaljournalMasterSecret, testMedicaljournalAppKey);
                }
            }
            if (jPushClient != null) {
                PushResult pushResult = null;
                pushResult = jPushClient.sendPush(pushPayload);
                if (null != pushResult && 200 == pushResult.getResponseCode()) {
                    jpushHistoricalRecordsService.saveHistoricalRecordsService(jpushHistoricalRecords);
                    logger.info("net-service...publicSendPush()方法推送成功!");
                    return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), 1);
                }
            }
        } catch (ClassCastException e) {
            logger.error("publicSendPush -> ClassCastException", e);
            e.printStackTrace();
            return new ResponseData(StatusCode.ARGU_ERROR.getCode(), StatusCode.ARGU_ERROR.getMsg(), 0);
        } catch (APIConnectionException e) {
            logger.error("publicSendPush -> APIConnectionException", e);
            e.printStackTrace();
            return new ResponseData("1022", "连接错误", 0);
        } catch (APIRequestException e) {
            if (e.getStatus() == 400) {
                if (e.getErrorCode() == 1011) {
                    logger.error("没有推送目标：{}" + e.getErrorCode());
                    return new ResponseData("1011", "没有推送目标", 0);
                } else if (e.getErrorCode() == 1002) {
                    logger.error("参数缺少：{}" + e.getErrorCode());
                    return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), 0);
                } else if (e.getErrorCode() == 1003) {
                    logger.error("参数不合法：{}" + e.getErrorCode());
                    return new ResponseData(StatusCode.ARGU_ERROR.getCode(), StatusCode.ARGU_ERROR.getMsg(), 0);
                }
            }
            logger.error("publicSendPush -> APIRequestException", e);
            e.printStackTrace();
            logger.error("HTTP Status: " + e.getStatus() + "Error Code: " + e.getErrorCode() + "Error Message: " + e.getErrorMessage());
            return new ResponseData(String.valueOf(e.getStatus()), e.getErrorMessage().toString(), 0);
        }
        return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), 0);
    }
}
