package com.ry.fu.net;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/5/11 9:44
 * @description description
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableAsync    //开启异步
@EnableTransactionManagement    //开启事务管理
@EnableScheduling   //开启定时器
public class NetServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NetServiceApplication.class, args);
    }

}
