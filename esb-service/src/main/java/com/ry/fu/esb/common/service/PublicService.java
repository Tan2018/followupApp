package com.ry.fu.esb.common.service;

import com.ry.fu.esb.common.constants.Constants;
import com.ry.fu.esb.common.enumstatic.ESBServiceCode;
import com.ry.fu.esb.common.enumstatic.StatusCode;
import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.http.HttpRequest;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.common.seq.RedisIncrementGenerator;
import com.ry.fu.esb.common.transformer.Transformer;
import com.ry.fu.esb.common.utils.JsonUtils;
import com.ry.fu.esb.common.utils.VerifyCodeUtil;
import com.ry.fu.esb.medicaljournal.service.OPService;
import com.ry.fu.esb.medicaljournal.util.SMSUtil;
import com.ry.fu.esb.pwp.mapper.AccountMapper;
import com.ry.fu.esb.pwp.model.Account;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;

/**
 * 公共服务类
 */
@Service
public class PublicService {
    protected final Logger logger = LoggerFactory.getLogger(PublicService.class);

    @Autowired
    private RequestService requestService;

    @Value("${fua.SYSTEM_CODE}")
    private String systemCode;

    @Value("${fua.SYSTEM_PASSWORD}")
    private String password;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private Transformer transformer;

    @Autowired
    private PublicService publicService;
    @Autowired
    private OPService opService;
    @Autowired
    private Environment environment;

    @Autowired
    private RedisIncrementGenerator redisIncrementGenerator;

    @Value("${netServerHostAndPort}")
    private String netServerHostAndPort;

    @Transactional(rollbackFor = Throwable.class)
    public String sendData(String srvNo, String requestData) throws ESBException {

        //获取token
        String accessToken = requestService.requestToken(systemCode, password);
        if (accessToken != null) {
            //发送请求
            String responseData = requestService.sendRequest(srvNo, requestData, accessToken);
            if (StringUtils.isNotBlank(responseData)) {
                String msgCode = StringUtils.substringBetween(responseData, "<MSGCODE>", "</MSGCODE>");
                String msgDesc = StringUtils.substringBetween(responseData, "<MSGDESC>", "</MSGDESC>");
                if (Constants.MSG_CODE.equals(msgCode)) {


                    logger.info("========请求ESB成功!==========");
                    return responseData;
                } else if(Constants.AUTH_ERROR_CODE.equals(msgCode)) {
                    //未授权，将Token delete掉 ， 重试一次
                    stringRedisTemplate.delete(Constants.ESB_ACCESS_TOKEN);

                    return reTry(srvNo, requestData);
                } else if(Constants.ARGU_ERROR_CODE.equals(msgCode)){
                    //参数有误
                    throw new ESBException(StatusCode.ESB_ARGU_ERROR.getCode(), StatusCode.ESB_ARGU_ERROR.getMsg());
                } else {
                    logger.error("===========请求ESB返回状态码为:{},描述为:{}", msgCode, msgDesc);
                    throw new ESBException(msgCode, msgDesc);
                }
            } else {
                logger.info("========连接超时!==========");
                throw new ESBException(StatusCode.ESB_OUTTIME.getCode(), StatusCode.ESB_OUTTIME.getMsg());
            }
        }
        return null;
    }

    private String reTry(String srvNo, String requestData) throws ESBException {
        String accessToken = requestService.requestToken(systemCode, password);
        if (accessToken != null) {
            //发送请求
            String responseData = requestService.sendRequest(srvNo, requestData, accessToken);
            if (StringUtils.isNotBlank(responseData)) {
                String msgCode = StringUtils.substringBetween(responseData, "<MSGCODE>", "</MSGCODE>");
                String msgDesc = StringUtils.substringBetween(responseData, "<MSGDESC>", "</MSGDESC>");
                if (Constants.MSG_CODE.equals(msgCode)) {
                    logger.info("======未授权重试==请求ESB成功!==========");
                    return responseData;
                } else {
                    logger.error("=========未授权重试==请求ESB返回状态码为:{},描述为:{}", msgCode, msgDesc);
                    throw new ESBException(StatusCode.ESB_ERROR.getCode(), StatusCode.ESB_ERROR.getCode());
                }
            } else {
                logger.info("====未授权重试====连接超时!==========");
                throw new ESBException(StatusCode.ESB_OUTTIME.getCode(), StatusCode.ESB_OUTTIME.getMsg());
            }
        }
        return null;
    }

    /**
     * 获取短信验证码
     * walker
     * @param phoneNumber 手机号码
     */
    public ResponseData getVerifyCode(String phoneNumber){
        String key = Constants.VERIFY_CODE_PREFIX + phoneNumber;
        String verifyCode = VerifyCodeUtil.generateVerifyCode(Boolean.TRUE, Constants.VERIFY_CODE_LENTH);
        logger.info("================生成的验证码为:{}", verifyCode);
        //设置验证码过期时间
        stringRedisTemplate.opsForValue().set(key, verifyCode, Constants.VERIFY_CODE_VALID_TIME, TimeUnit.SECONDS);
        logger.info("================设置验证码redis缓存key为:{}", key);

        //正式环境使用正式的
        if("pro".equals(environment.getActiveProfiles()[0]) || "pre".equals(environment.getActiveProfiles()[0])) {
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("phoneNumber", phoneNumber);
            paramMap.put("verifyCode", verifyCode);
            String retData = HttpRequest.post(netServerHostAndPort + Constants.SMS_YUNPIAN_URL, JsonUtils.toJSon(paramMap), Constants.APPLICATION_JAON);
            Map<String, Object> retMap = JsonUtils.readValue(retData, HashMap.class);
            Integer yunpianStatus = retMap.get("code") != null ? Integer.parseInt(retMap.get("code").toString()) : 1;
            if(yunpianStatus != null && yunpianStatus == 0) {
                return new ResponseData(StatusCode.OK.getCode(), "获取验证码成功", null);
            } else {
                //使用原生的短信接口
                logger.info("use old send SMS!phone:{},code:{}", phoneNumber, verifyCode);
                retData = SMSUtil.sendVerifyCode(phoneNumber, verifyCode);
                retMap = JsonUtils.readValue(retData, HashMap.class);
                Integer statusInt = retMap.get("state") != null ? Integer.parseInt(retMap.get("state").toString()) : 0;
                if (retMap != null && statusInt == 1) {
                    return new ResponseData(StatusCode.OK.getCode(), "获取验证码成功", null);
                }else {
                    return new ResponseData(StatusCode.ESB_ERROR.getCode(), "发送短信失败", "发送短信失败");
                }
            }
        } else {
            //除了正式环境，其他的都用老的验证码方式
            String retData = SMSUtil.sendVerifyCode(phoneNumber, verifyCode);
            Map<String, String> retMap = JsonUtils.readValue(retData, HashMap.class);
            Integer statusInt = retMap.get("state") != null ? Integer.parseInt(retMap.get("state").toString()) : 0;
            if (retMap != null && statusInt == 1) {
                return new ResponseData(StatusCode.OK.getCode(), "获取验证码成功", null);
            }else {
                return new ResponseData(StatusCode.ESB_ERROR.getCode(), "发送短信失败", "发送短信失败");
            }
        }

    }

    /**
     * 注册
     * @param params accountMobile:手机号码，verifyCode:验证码，accountPassword:密码
     */
    @Transactional(rollbackFor = Throwable.class)
    public Map<String,Boolean> handleRegistration(Map<String, Object> params) {
        Account account = accountMapper.findByAccountMobile((String)params.get("accountMobile"));
        Map<String,Boolean> resultMap = new HashMap<String, Boolean>();
        if (account != null) {//已注册
            resultMap.put("hasRegistration", true);
            return resultMap;
        } else {//未注册
            if (StringUtils.equals((String) params.get("verifyCode"), stringRedisTemplate.opsForValue().get(Constants.VERIFY_CODE_PREFIX + params.get("accountMobile")))) {
                Long no = redisIncrementGenerator.increment(Constants.M_ACCOUNT_SEQ, 1);
                if (no != 0L) {
                    String accountCode = (String) (params.get("accountCode") == null ? params.get("accountMobile") : params.get("accountCode"));
                    accountMapper.insertAccount(no, (String) params.get("accountPassword"), new Date(), (String) params.get("accountMobile"), accountCode);
                    resultMap.put("registerSuccess", true);
                    return resultMap;
                }
            } else {
                resultMap.put("verifyCodeExpired",true);
                return resultMap;
            }
        }
        return null;
    }


    /**
     * 登录
     * @param params accountMobile:手机号码  accountPassword:密码
     */
    public ResponseData handleLogin(Map<String, Object> params) {
        ResponseData responseData = new ResponseData();
        //先判断号码是否注册，进行手机号码密码校验
        Account account = accountMapper.findByAccountMobile((String)params.get("accountMobile"));
        Map resultMap = new HashMap();
        if (account == null) {
            responseData.setStatus("400");
            responseData.setMsg("该号码未注册，请先注册！");
        } else {
            if (StringUtils.equals(account.getAccountPassword(),(String)params.get("accountPassword"))) {
                account.setAccountPassword(null);
                resultMap.put("loginSuccess",true);
                resultMap.put("account",account);
                ResponseData patientResponseData = opService.selectPatient(account.getAccountId());
                if(patientResponseData != null && patientResponseData.getData() != null){
                    Map PatientMap = (Map) patientResponseData.getData();
                    resultMap.put("list",PatientMap.get("list"));
                }else {
                    resultMap.put("list",null);
                }
                responseData.setStatus("200");
                responseData.setMsg("登录成功");
            }else {
                responseData.setStatus("400");
                responseData.setMsg("账户或者密码错误");
            }
        }
        responseData.setData(resultMap);
        return responseData;
    }


    /**
     * 重置密码
     * @param params accountMobile:手机号码，verifyCode:验证码，accountPassword:密码
     */
    public Map<String, Boolean> resetPassword(Map<String, Object> params) {
        Account account = accountMapper.findByAccountMobile((String)params.get("accountMobile"));
        Map<String,Boolean> resultMap = new HashMap<String, Boolean>();
        if (account == null) {
            resultMap.put("notRegistered", true);
            return resultMap;
        } else {
            if (StringUtils.isEmpty(stringRedisTemplate.opsForValue().get(Constants.VERIFY_CODE_PREFIX + params.get("accountMobile")))) {//验证码已过期
                resultMap.put("verifyCodeExpired",true);

                return resultMap;
            } else if (StringUtils.equals((String) params.get("verifyCode"), stringRedisTemplate.opsForValue().get(Constants.VERIFY_CODE_PREFIX + params.get("accountMobile")))) {
                accountMapper.updatePassword((String)params.get("accountMobile"),(String)params.get("newPassword"));
                resultMap.put("resetSuccess",true);
                return resultMap;
            }
        }
        return null;
    }


    public ResponseData query(String srvNo, Object object, Class objectClass) throws SystemException, ESBException {
        String responseData = null;
        try {
            String requestData = "<REQUEST>" + StringUtils.substringBetween(transformer.writeAsXML(object),"<REQUEST>","</REQUEST>") + "</REQUEST>";
            logger.info("======requestData======"+requestData);
            responseData = publicService.sendData(srvNo, requestData);
            logger.info("======requestData======"+responseData);
            String msgCode = StringUtils.substringBetween(responseData, "<MSGCODE>", "</MSGCODE>");
            String msgDesc = StringUtils.substringBetween(responseData, "<MSGDESC>", "</MSGDESC>");

            String xml = StringEscapeUtils.unescapeHtml4(StringUtils.substringBetween(responseData, "<RESPONSEDATA>", "</RESPONSEDATA>"));
            //解决His的Bug
            xml = StringUtils.replaceOnce(xml, "XML:SPACE=\"PRESERVE\"", "");
            ResponseData esbResponseData = new ResponseData();
            if (StringUtils.isNotEmpty(xml) && !StringUtils.equals(Constants.ESB_FIND_NO_DATA, StringUtils.strip(msgDesc))) {
                if (srvNo == ESBServiceCode.DOCTORINFOLIST || srvNo == ESBServiceCode.REGISTEREDDOCTOR ||
                        srvNo == ESBServiceCode.DEPTLIST ||
                        srvNo == ESBServiceCode.DOCTORINFOBYNAME) {
                    //base64解密
                    byte[] decodedXml = decodeDoctorInfo(xml);
                    byte[] uncompressXml = uncompress(decodedXml);
                    logger.info(new String(uncompressXml));
                    object = transformer.readFromXML(new String(uncompressXml, "UTF-8"), objectClass);
                } else {
                    object = transformer.readFromXML(xml, objectClass);
                }
                esbResponseData.setData(object);

            } else {
                esbResponseData.setData(null);
            }
            esbResponseData.setStatus(msgCode);
            esbResponseData.setMsg(msgDesc);
            if (Constants.MSG_CODE.equals(msgCode)) {
                esbResponseData.setStatus(StatusCode.OK.getCode());
                esbResponseData.setMsg(StatusCode.OK.getMsg());
            }
            return esbResponseData;
        } catch (IOException e) {
            logger.error("===========================Object-->XML：内部数据转换错误", e);
            throw new SystemException(StatusCode.XML_FORMAT_ERROR.getCode(), StatusCode.XML_FORMAT_ERROR.getMsg());
        } catch (JAXBException e) {
            logger.error("===========================XML-->Object：内部数据转换错误", e);
            throw new SystemException(StatusCode.XML_FORMAT_ERROR.getCode(), StatusCode.XML_FORMAT_ERROR.getMsg());
        }
    }


    private byte[] decodeDoctorInfo(String xml) {
        byte[] bt = null;
        String decodeXml = null;
        try {
            sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
            bt = decoder.decodeBuffer(xml);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bt;
    }

    public static byte[] uncompress(byte[] b) throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(b);
        GZIPInputStream gunzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n;
        while ((n = gunzip.read(buffer)) >= 0) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }
}
