package com.ry.fu.followup.doctorbook.service.impl;

import com.ry.fu.followup.base.model.ResponseData;
import com.ry.fu.followup.doctorbook.mapper.FlowInstanceMapper;
import com.ry.fu.followup.doctorbook.mapper.SurveyAnswersMapper;
import com.ry.fu.followup.doctorbook.mapper.SurveyMapper;
import com.ry.fu.followup.doctorbook.model.*;
import com.ry.fu.followup.doctorbook.service.SurveyService;
import com.ry.fu.followup.utils.ResponseMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by jackson on 2018/4/27.
 */
@Service
public class SurveyServiceImpl implements SurveyService{

    @Autowired
    private SurveyMapper surveyMapper;

    @Autowired
    private FlowInstanceMapper flowInstanceMapper;

    @Autowired
    private SurveyAnswersMapper surveyAnswersMapper;


    @Override
    public ResponseData querySurveyByFlowProgramInstanceId(ReqInfo reqInfo) {
        System.out.println(reqInfo.getFlowProgramInstanceId());
        Survey survey = surveyMapper.querySurvey(reqInfo.getFlowProgramInstanceId());
        Map<String,Object> innerDataMap = new HashMap<>();
        if(survey!=null) {
            innerDataMap.put("flowProgramInstanceId", reqInfo.getFlowProgramInstanceId());
            innerDataMap.put("title", survey.getTitle());
            innerDataMap.put("surveyId", survey.getId());
            innerDataMap.put("suVerId", survey.getSuVerId());
            innerDataMap.put("description", survey.getDescription());
            List<Map<String, Object>> list = new ArrayList<>();
            for (SurveyQuestion surveyQuestion : survey.getSurveyQuestions()) {
                Map<String, Object> map = new HashMap<>();
                map.put("questionId", surveyQuestion.getId());
                map.put("questionName", surveyQuestion.getSort()+"、"+surveyQuestion.getQuestion());
                map.put("questionType", surveyQuestion.getQuestionType());

                List<SurveyDetail> choiceList = surveyQuestion.getSurveyDetails();

                map.put("choiceList", surveyQuestion.getSurveyDetails());
                list.add(map);
            }
            innerDataMap.put("list", list);
        }
        //restTemplate.postForObject()

        return ResponseMapUtil.getSuccessResultMap(innerDataMap);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData saveSurveyAnswer(ReqInfo reqInfo) {
        List<Answer> answers = reqInfo.getAnswers();
        for(Answer answer : answers){
            answer.setFlowProgramInstanceId(reqInfo.getFlowProgramInstanceId());
        }

        //保存问卷结果
        Integer j = surveyAnswersMapper.insertBatch(answers);
        Integer i = flowInstanceMapper.updateStatus(2,reqInfo.getFlowProgramInstanceId());
        Map<String,Object> innerDataMap = new HashMap<>();
        return ResponseMapUtil.getSuccessResultMap(innerDataMap);
    }
    public ResponseData querySurveylistByPaitentIdAndProjectId(ReqInfo reqInfo) throws ParseException {
        List<Map<String, Object>> surveylist = surveyMapper.querySurveylist(reqInfo.getProjectId(),reqInfo.getPatientId());
        List<Map<String, Object>> flowPrograms = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Set<Long> setDate = new HashSet<>();
        for(Map<String, Object> survery :surveylist){
            Map<String,Object> flowProgram = new HashMap<>();
            flowProgram.put("programName",survery.get("PROGRAMNAME"));
            flowProgram.put("flowProgramInstanceId",survery.get("FLOWPROGRAMINSTANCEID"));
            Object dateObj = survery.get("date");
            if (dateObj !=null){
                Date date = sdf.parse(dateObj.toString());
                setDate.add(date.getTime());
                flowProgram.put("date",date.getTime());
            }else {
                setDate.add(null);
                flowProgram.put("date",null);
            }
            flowPrograms.add(flowProgram);
        }

        List<Map<String, Object>> innerData1 = new ArrayList<>();
        //按时间来处理
        for (Long date:setDate) {
            long parse=0;
            List<Map<String, Object>> flowPrograms2 = new ArrayList<>();
            Map<String,Object> dataMap = new HashMap();
            for (Map<String, Object> map:flowPrograms) {

                if (map.get("date") !=null && date !=null){
                    parse = new Long(map.get("date").toString());
                    if (date==parse){
                        flowPrograms2.add(map);
                    }
                }else {

                    if (map.get("date") ==null && date == null) {
                        flowPrograms2.add(map);
                    }
                }
            }
            if (date !=null){
                dataMap.put("date",sdf.format(date));
            }else {
                dataMap.put("date","未设置开始随访时间");
            }

            dataMap.put("flowPrograms",flowPrograms2);
            innerData1.add(dataMap);
        }
        return  ResponseMapUtil.getSuccessResultList(innerData1);
    }


}
