package com.ry.fu.esb.pwp.api.fallback;

import com.ry.fu.esb.pwp.api.FeignClientDemo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2017/11/23 16:14
 * @description 服务降级
 */
@Component
public class FeignFallBack implements FeignClientDemo {

    @Override
    public String getApi(@RequestParam("name") String name) {
        System.out.println("feign fallback1...");
        return "faild";
    }

    @Override
    public String getApi2() {
        System.out.println("feign fallback2...");
        return "faild";
    }
}
