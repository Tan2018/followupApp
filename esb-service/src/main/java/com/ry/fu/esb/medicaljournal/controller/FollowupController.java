package com.ry.fu.esb.medicaljournal.controller;

import com.ry.fu.esb.common.enumstatic.StatusCode;
import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.medicaljournal.service.FollowupService;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jackson on 2018/5/2.
 */
@RestController
@RequestMapping("${prefixPath}/v1/medicalJournal/followup")
public class FollowupController {

    Logger logger = LoggerFactory.getLogger(FollowupController.class);

    @Autowired
    private FollowupService followupService;

    /**
     *
     * @param retMap
     * @return
     * @throws ESBException
     * @throws SystemException
     */
    @RequestMapping(value = "/surveySend", method = RequestMethod.POST)
    public ResponseData sendSurvey(@RequestBody Map<String, String> retMap) throws ESBException, SystemException {
        return followupService.sendSurvey(retMap);
    }

    @RequestMapping(value = "/groupApplictionSend", method = RequestMethod.POST)
    public ResponseData sendGroupAppliction(@RequestBody Map<String, String> retMap) throws ESBException, SystemException {
        return followupService.sendGroupInfo(retMap);
    }

    /*
     * 复诊推送接口
     * @param retMap
     * @return
     */
    @RequestMapping(value = "examinationSend",method = RequestMethod.POST)
    public ResponseData senfExamination(@RequestBody Map<String,Object> retMap ) {
        try {
            return followupService.senfExamination(retMap);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseData("500","后台异常","null");
        }
    }

    /**
     * 随访挂复诊提醒列表
     *
     */
    @PostMapping("/followUpRegistered")
    public ResponseData followUpRegistered(@RequestBody Map<String,Object> retMap) {
        try {
            try {
              return followupService.queryFollowUpRegistered(retMap);
            } catch (ParseException e) {
                e.printStackTrace();
                return  new ResponseData(StatusCode.INSIDE_ERROR.getCode(), e.getMessage(), null);

            }
        } catch (ESBException e) {
            e.printStackTrace();
            return  new ResponseData(StatusCode.INSIDE_ERROR.getCode(), e.getMessage(), null);
        }

    }




}
