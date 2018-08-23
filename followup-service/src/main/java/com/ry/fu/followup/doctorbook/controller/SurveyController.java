package com.ry.fu.followup.doctorbook.controller;

import com.ry.fu.followup.base.model.ResponseData;
import com.ry.fu.followup.doctorbook.model.ReqInfo;
import com.ry.fu.followup.doctorbook.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

/**
 * Created by jackson on 2018/4/27.
 */
@RestController
@RequestMapping("${prefixPath}/v1/survey")
public class SurveyController {

    @Autowired
    private SurveyService surveyService;

    //获取随访调查问卷
    @RequestMapping(value = "/survey", method = RequestMethod.POST)
    public ResponseData followSurvey(@RequestBody ReqInfo reqInfo) {
        return surveyService.querySurveyByFlowProgramInstanceId(reqInfo);
    }

    //保存随访调查问卷答题
    @RequestMapping(value = "/saveSurvey", method = RequestMethod.POST)
    public ResponseData followSaveSurvey(@RequestBody ReqInfo reqInfo) {
        System.out.println(reqInfo);
        return surveyService.saveSurveyAnswer(reqInfo);
    }
    //根据病人id和项目id获取问卷调查列表
    @RequestMapping(value = "/surveyList", method = RequestMethod.POST)
    public ResponseData followSurveyList(@RequestBody ReqInfo reqInfo) throws ParseException {
        System.out.println(reqInfo);
        return surveyService.querySurveylistByPaitentIdAndProjectId(reqInfo);
    }

    //根据实例id获取问卷信息以及问卷结果
    @RequestMapping(value = "/surveyResult", method = RequestMethod.POST)
    public ResponseData followSurveyAndResult(@RequestBody ReqInfo reqInfo) {
        return surveyService.querySurveyByFlowProgramInstanceId(reqInfo);
    }


}
