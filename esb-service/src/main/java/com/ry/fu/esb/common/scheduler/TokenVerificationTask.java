package com.ry.fu.esb.common.scheduler;

import com.ry.fu.esb.common.constants.Constants;
import com.ry.fu.esb.common.service.RequestService;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author : walker
 * 定时检查redis缓存是否与ESB保持一致
 */
@Component
public class TokenVerificationTask {

    Logger logger = LoggerFactory.getLogger(TokenVerificationTask.class);

    @Resource
    private RequestService requestService;

    @Value("${fua.SYSTEM_CODE}")
    private String systemCode;

    @Value("${fua.SYSTEM_PASSWORD}")
    private String password;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //10min检查一次
    @Scheduled(fixedDelay = 10 * 1000 * 1000L)
    public void verifyEsbToken(){
        if (StringUtils.isNotEmpty(stringRedisTemplate.opsForValue().get(Constants.ESB_ACCESS_TOKEN))){
            String accessToken = requestService.requestToken(systemCode, password);
            if (StringUtils.equals(stringRedisTemplate.opsForValue().get(Constants.ESB_ACCESS_TOKEN), accessToken)) {
                logger.info("===========当前时间:{}经过验证token与ESB保持一致", FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
            } else {
                //更新token
                stringRedisTemplate.opsForValue().set(Constants.ESB_ACCESS_TOKEN, accessToken, Constants.ESB_TOKEN_CHECK_TIME, TimeUnit.SECONDS);
                logger.info("===========定时检查token完毕，更新后token为:{}",accessToken);
            }
        }
    }
}
