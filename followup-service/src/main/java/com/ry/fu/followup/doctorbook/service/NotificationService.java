package com.ry.fu.followup.doctorbook.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ry.fu.followup.base.model.ResponseData;
import com.ry.fu.followup.doctorbook.model.ReqInfo;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Map;

/**
 * 通知相关的service
 * Created by jackson on 2018/5/5.
 */
public interface NotificationService {

    /**
     * 根据医生id获取已经推送给该医生的项目审核列表
     * @param reqInfo
     * @return
     */
    public ResponseData queryApplicationList(ReqInfo reqInfo) throws ParseException;

    public ResponseData sendGroupAppliction( Map<String, Object> retMap) throws JsonProcessingException;
}
