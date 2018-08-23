package com.ry.fu.esb.pwp.api;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.ry.fu.esb.pwp.api.fallback.FeignFallBack;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 声明式服务调用
 */
@FeignClient(value = "producer-server",fallback = FeignFallBack.class)
public interface FeignClientDemo {

    @HystrixCommand //熔断器命令
    @CacheResult    //缓存，添加了该缓存，有缓存后将不会请求远程，而是从本地直接获取缓存出去，更新方法必须使用@CacheRemove，该命令必须和HystrixCommand结合使用
    @RequestMapping(value = "/pwp/api")
    public String getApi(@RequestParam("name") String name);

    @RequestMapping(value = "/pwp/api2")
    public String getApi2();
}
