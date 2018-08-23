package com.ry.fu.esb.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


/**
 * 接口url配置类
 */
@Configuration
public class UrlConfig {

    /**
     * net推送接口
     */
    @Value("/net/netService/v1/jpush/communalJpush/publicSendPush")
    private  String  NET_SEND_PUSH_URL;

    /**
     * followup随访详情的接口
     */
    @Value("/followup/followupApp/v1/followup/showFollowDetails")
    private String FOLLOW_SHOW_FOLLOWDETAIL_URL;


    public String getNET_SEND_PUSH_URL() {
        return NET_SEND_PUSH_URL;
    }

    public void setNET_SEND_PUSH_URL(String NET_SEND_PUSH_URL) {
        this.NET_SEND_PUSH_URL = NET_SEND_PUSH_URL;
    }

    public String getFOLLOW_SHOW_FOLLOWDETAIL_URL() {
        return FOLLOW_SHOW_FOLLOWDETAIL_URL;
    }

    public void setFOLLOW_SHOW_FOLLOWDETAIL_URL(String FOLLOW_SHOW_FOLLOWDETAIL_URL) {
        this.FOLLOW_SHOW_FOLLOWDETAIL_URL = FOLLOW_SHOW_FOLLOWDETAIL_URL;
    }
}
