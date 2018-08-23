package com.ry.fu.followup.pwp.controller;

import com.ry.fu.followup.base.model.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2017/11/23 15:45
 * @description 主要用来测试的Controller，后面将会删掉
 */
@RestController
@RequestMapping("/pwp")
public class PwpController {

    private static Logger logger = LoggerFactory.getLogger(PwpController.class);

    @RequestMapping("/api")
    public ResponseData getApi(@RequestParam(defaultValue = "Test", required = false) String name, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();
        responseData.setMsg("Test");
        String sessionStr = (String) request.getSession().getAttribute("Producer");
        logger.info(sessionStr);
        logger.info(request.getSession().getId());
        return responseData;
    }

    @RequestMapping("/api2")
    public String getApi2(HttpServletRequest request){
        request.getSession().setAttribute("Producer", "Producer");
        logger.info(request.getSession().getId());
        return "This Producer Service Api2";
    }

}
