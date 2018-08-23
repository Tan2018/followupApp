package com.ry.fu.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2017/11/22 20:06
 * @description SpringCloud的断路器监控-监控中心
 *   可以在此服务里面查看到各服务的请求响应时间, 请求成功率等数据
 */
@EnableHystrixDashboard
@SpringCloudApplication
public class TurbineApplication {

    public static void main(String[] args) {
        SpringApplication.run(TurbineApplication.class, args);
    }

}
