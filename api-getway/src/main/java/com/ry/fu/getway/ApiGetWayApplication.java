package com.ry.fu.getway;

import com.ry.fu.getway.base.filter.BaseFilter;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2017/11/22 19:21
 * @description 网关启动器，类似Spring中的AOP切面
 */
@EnableZuulProxy
@EnableHystrix
@SpringCloudApplication
public class ApiGetWayApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ApiGetWayApplication.class).web(true).run(args);
    }

    @Bean
    public BaseFilter accessFilter() {
        return new BaseFilter();
    }

}
