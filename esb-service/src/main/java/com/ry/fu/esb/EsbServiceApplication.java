package com.ry.fu.esb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2017/11/23 15:45
 * @description 生产者，主要是此处用来当做Controller和Service中的Service，真正的服务
 * 此系统用来操作数据库
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableAsync    //开启异步
@EnableTransactionManagement    //开启事务管理
@EnableScheduling   //开启定时器
public class EsbServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EsbServiceApplication.class, args);
    }

}
