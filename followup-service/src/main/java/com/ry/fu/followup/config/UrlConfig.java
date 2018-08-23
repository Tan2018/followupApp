package com.ry.fu.followup.config;

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
     * esb推送条数接口
     */
    @Value("/esb/followupApp/v1/jpush/dm/savePushBarNumber")
    private String ESB_SAVE_PUSH_BARNUM_URL;

    /**
     * esb
     */
    @Value("/esb/followupApp/v1/medicalJournal/followup/")
    private String ESB_FOLLOWUP_SERVICEPATH_URL;


    /**
     * esb的查询患者列表主索引的url
     */
    @Value("/esb/followupApp/v1/docBook/pr/primaryIndexQueryPatientList")
    private String ESB_FOLLOWUP_PRIMARYINDEXQUERYPATIENTLIST;

    @Value("/mip/platform/workFlow/review.do")
    private  String FOLLOWUP_PC_REVIEW;


    public String getFOLLOWUP_PC_REVIEW() {
        return FOLLOWUP_PC_REVIEW;
    }

    public void setFOLLOWUP_PC_REVIEW(String FOLLOWUP_PC_REVIEW) {
        this.FOLLOWUP_PC_REVIEW = FOLLOWUP_PC_REVIEW;
    }


    public String getNET_SEND_PUSH_URL() {
        return NET_SEND_PUSH_URL;
    }

    public void setNET_SEND_PUSH_URL(String NET_SEND_PUSH_URL) {
        this.NET_SEND_PUSH_URL = NET_SEND_PUSH_URL;
    }

    public String getESB_SAVE_PUSH_BARNUM_URL() {
        return ESB_SAVE_PUSH_BARNUM_URL;
    }

    public void setESB_SAVE_PUSH_BARNUM_URL(String ESB_SAVE_PUSH_BARNUM_URL) {
        this.ESB_SAVE_PUSH_BARNUM_URL = ESB_SAVE_PUSH_BARNUM_URL;
    }

    public String getESB_FOLLOWUP_SERVICEPATH_URL() {
        return ESB_FOLLOWUP_SERVICEPATH_URL;
    }

    public void setESB_FOLLOWUP_SERVICEPATH_URL(String ESB_FOLLOWUP_SERVICEPATH_URL) {
        this.ESB_FOLLOWUP_SERVICEPATH_URL = ESB_FOLLOWUP_SERVICEPATH_URL;
    }

    public String getESB_FOLLOWUP_PRIMARYINDEXQUERYPATIENTLIST() {
        return ESB_FOLLOWUP_PRIMARYINDEXQUERYPATIENTLIST;
    }

    public void setESB_FOLLOWUP_PRIMARYINDEXQUERYPATIENTLIST(String ESB_FOLLOWUP_PRIMARYINDEXQUERYPATIENTLIST) {
        this.ESB_FOLLOWUP_PRIMARYINDEXQUERYPATIENTLIST = ESB_FOLLOWUP_PRIMARYINDEXQUERYPATIENTLIST;
    }



}
