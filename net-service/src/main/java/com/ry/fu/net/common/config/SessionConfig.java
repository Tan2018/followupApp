package com.ry.fu.net.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2017/12/2 22:06
 * @description 分布式Session，使用spring.session.store-type=redis自动配置
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds= 1800, redisNamespace = "esb-service")
public class SessionConfig {


}
