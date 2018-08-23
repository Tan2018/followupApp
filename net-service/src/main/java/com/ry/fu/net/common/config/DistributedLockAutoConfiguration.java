package com.ry.fu.net.common.config;

import com.ry.fu.net.common.seq.lock.DistributedLock;
import com.ry.fu.net.common.seq.lock.RedisDistributedLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/3/31 16:52
 * @description Redis锁配置
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class DistributedLockAutoConfiguration {

    @Autowired
    private RedisTemplate redisTemplate;

    @Bean
    public DistributedLock redisDistributedLock(){
        return new RedisDistributedLock(redisTemplate);
    }

}