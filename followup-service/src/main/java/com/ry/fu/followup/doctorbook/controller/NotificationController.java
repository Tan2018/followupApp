package com.ry.fu.followup.doctorbook.controller;

import com.ry.fu.followup.base.model.ResponseData;
import com.ry.fu.followup.config.UrlConfig;
import com.ry.fu.followup.doctorbook.model.ReqInfo;
import com.ry.fu.followup.doctorbook.service.NotificationService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.util.Map;

/**
 * 通知相关的接口
 * Created by jackson on 2018/5/5.
 */
@RestController
@RequestMapping("${prefixPath}/v1/notification")
public class NotificationController {
    @Autowired
    private UrlConfig urlConfig;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private RestTemplate restTemplate;

    // Restful服务对应的ip地址
    @Value("${esbServerHostAndPort}")
    private String esbServerHostAndPort;

    //获取随访调查问卷
    @RequestMapping(value = "/ApplicationList", method = RequestMethod.POST)
    public ResponseData followSurvey(@RequestBody ReqInfo reqInfo) throws ParseException {
        return notificationService.queryApplicationList(reqInfo);
    }


}
