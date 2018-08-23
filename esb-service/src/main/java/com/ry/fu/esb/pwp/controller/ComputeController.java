package com.ry.fu.esb.pwp.controller;

import com.ry.fu.esb.common.utils.JsonUtils;
import com.ry.fu.esb.medicaljournal.controller.PayController;
import com.ry.fu.esb.medicaljournal.model.RechargeParamVo;
import com.ry.fu.esb.pwp.api.FeignClientDemo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2017/11/23 16:13
 * @description 测试Controller
 */
@Controller
@RequestMapping("/test")
public class ComputeController {

    private static Logger logger = LoggerFactory.getLogger(ComputeController.class);

    @RequestMapping("/returnURL")
    public String returnURL(HttpServletRequest request, RechargeParamVo vo) {
        Map<String, String[]> map = request.getParameterMap();
        logger.info("参数：{}", JsonUtils.toJSon(map));
        logger.info("RechargeParamVo：{}", JsonUtils.toJSon(vo));
        return "/success.html";
    }

}
