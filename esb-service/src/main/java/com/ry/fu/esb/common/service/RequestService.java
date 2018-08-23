package com.ry.fu.esb.common.service;

import com.ry.fu.esb.common.constants.Constants;
import com.ry.fu.esb.common.http.HttpRequest;
import com.ry.fu.esb.common.request.BaseRequest;
import com.ry.fu.esb.common.request.GetTokenRequest;
import com.ry.fu.esb.common.transformer.Transformer;
import com.ry.fu.esb.common.utils.StringFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * common request service
 * @author : walker
 */
@Component
public class RequestService {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${fua.REQUEST_TOKEN_ADDRESS}")
    private String requestTokenAddress;

    @Value("${fua.SYSTEM_IP}")
    private String systemIp;

    @Value("${fua.SRV_VERSION}")
    private String srvVersion;

    @Value("${fua.SHOWPIC_ADDRESS}")
    public  String picPreAdd;

    @Autowired
    private StringRedisTemplate stringRedisTemplate ;

    @Resource
    private Transformer transformer;

    private String getToken(String systemcode, String password) {
        String uuid = StringUtils.replace(UUID.randomUUID().toString(), "-", "");
        GetTokenRequest getTokenRequest = new GetTokenRequest();
        getTokenRequest.setRequestId(uuid);
        getTokenRequest.setRequestIp(systemIp);
        getTokenRequest.setSystemCode(systemcode);
        getTokenRequest.setSystemPassword(password);
        try {
            String tokenBody = transformer.writeAsXML(getTokenRequest);
            logger.info("=========request token body is:{}",tokenBody);
            String tokenContent = HttpRequest.post(requestTokenAddress + Constants.ESB_TOKEN_ADDRESS, tokenBody);
            String accessToken = StringUtils.substringBetween(tokenContent, "<ACCESSTOKEN>", "</ACCESSTOKEN>");
            return accessToken;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(ExceptionUtils.getStackTrace(e));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(ExceptionUtils.getStackTrace(e));
        }
        return StringUtils.EMPTY;
    }

    public String sendRequest(String srvNo, String requestData, String accessToken) {
        return send(srvNo, requestData, accessToken, requestTokenAddress + Constants.ESB_SEND_CONTENT_ADDRESS);
    }

    private String send(String srvNo, String requestData, String accessToken, String sendAddress) {
        String uuid = StringUtils.replace(UUID.randomUUID().toString(), "-", "");
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setRequestId("1000000");
        baseRequest.setRequestIp(systemIp);
        baseRequest.setAccessToken(accessToken);
        baseRequest.setServiceCode(srvNo);
        baseRequest.setServiceVersion(srvVersion);
        baseRequest.setRequestData(requestData);

        try {
            String commonPortBody = StringFormatUtils.formatXMLEscapeToCharacter(transformer.writeAsXML(baseRequest));
            logger.info("commonPortBody:=========================:{}",commonPortBody);
            String commonPortContent = HttpRequest.post(sendAddress, commonPortBody);


            return commonPortContent;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(ExceptionUtils.getStackTrace(e));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(ExceptionUtils.getStackTrace(e));
        }
        return StringUtils.EMPTY;
    }

    private String send(String requestData, String sendAddress) {
        try {
            logger.info("commonPortBody:=========================:{}",requestData);
            String commonPortContent = HttpRequest.post(sendAddress, requestData);

            return commonPortContent;
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }
        return StringUtils.EMPTY;
    }

    public String requestToken(String systemCode, String password) {
        String accessToken = getToken(systemCode, password);
        //1小时有效
        stringRedisTemplate.opsForValue().set(Constants.ESB_ACCESS_TOKEN, accessToken, Constants.ESB_TOKEN_CHECK_TIME, TimeUnit.SECONDS);
        return accessToken;
    }

}
