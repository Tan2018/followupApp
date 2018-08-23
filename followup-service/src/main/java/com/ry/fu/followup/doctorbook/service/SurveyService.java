package com.ry.fu.followup.doctorbook.service;

import com.ry.fu.followup.base.model.ResponseData;
import com.ry.fu.followup.doctorbook.model.ReqInfo;

import java.text.ParseException;

/**
 * Created by jackson on 2018/4/27.
 */
public interface SurveyService {
    ResponseData querySurveyByFlowProgramInstanceId(ReqInfo reqInfo);

    ResponseData saveSurveyAnswer(ReqInfo reqInfo);
    ResponseData querySurveylistByPaitentIdAndProjectId(ReqInfo reqInfo) throws ParseException;
}
