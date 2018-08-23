package com.ry.fu.esb.medicaljournal.service;

import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.response.ResponseData;
import org.json.JSONException;

import java.text.ParseException;
import java.util.Map;

/**
 * 随访推送
 * Created by jackson on 2018/5/2.
 */
public interface FollowupService {

    public ResponseData sendSurvey(Map<String, String> retMap);

    public ResponseData sendGroupInfo(Map<String, String> retMap);

    /**
     *  复诊通知推送
     * @param retMap
     * @return
     */
    public ResponseData senfExamination(Map<String, Object> retMap) throws Exception;



    ResponseData queryFollowUpRegistered(Map<String,Object> retMap) throws ESBException, ParseException;

}
