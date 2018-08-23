package com.ry.fu.followup.doctorbook.controller;

import com.ry.fu.followup.base.model.ResponseData;
import com.ry.fu.followup.doctorbook.model.ReqInfo;
import com.ry.fu.followup.doctorbook.service.ActHiTaskinstService;
import com.ry.fu.followup.doctorbook.service.GroupService;
import com.ry.fu.followup.doctorbook.service.PatientService;
import com.ry.fu.followup.doctorbook.service.ProgramService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Jam on 2017/12/6.
 *
 */
@RestController
@RequestMapping("${prefixPath}/v1/project")
public class MyProjectController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private ProgramService programService;

    @Autowired
    private ActHiTaskinstService actHiTaskinstService;


    @RequestMapping(value = "/projectDetail", method = RequestMethod.POST)
    public ResponseData projectDetail(@RequestBody ReqInfo reqInfo) {
        return groupService.queryProjectDetail(reqInfo);
    }

    @RequestMapping(value = "/projectMember", method = RequestMethod.POST)
    public ResponseData projectMember(@RequestBody ReqInfo reqInfo) {
        return groupService.queryProjectMember(reqInfo);
    }

    @RequestMapping(value = "/getField", method = RequestMethod.POST)
    public ResponseData getField(@RequestBody ReqInfo reqInfo) {
        return groupService.queryField(reqInfo);
    }

    @RequestMapping(value = "/getCondition", method = RequestMethod.POST)
    public ResponseData getCondition(@RequestBody ReqInfo reqInfo) {
        return groupService.queryExpression(reqInfo);
    }

    @RequestMapping(value = "/patientsList", method = RequestMethod.POST)
    public ResponseData patientsList(@RequestBody ReqInfo reqInfo) {
        return patientService.queryPatientsList(reqInfo);
    }

    @RequestMapping(value = "/searchPatients", method = RequestMethod.POST)
    public ResponseData searchPatients(@RequestBody ReqInfo reqInfo)  {
        return patientService.queryPatientsList(reqInfo);
    }

    @RequestMapping(value = "/projectPlan", method = RequestMethod.POST)
    public ResponseData projectPlan(@RequestBody ReqInfo reqInfo) {
        return programService.queryProgramDetailByGroupId(reqInfo);
    }

    @RequestMapping(value = "/projectProgram",method = RequestMethod.POST)
    public ResponseData projectProgram(@RequestBody ReqInfo reqInfo) throws Exception {
        return programService.queryProgramByGroupId(reqInfo);
    }

    /**
     *
     * Creat by Tasher
     * @param reqInfo
     * @return
     */
    @RequestMapping(value = "/getActHiTaskin",method = RequestMethod.POST)
    public ResponseData getActHiTaskin(@RequestBody ReqInfo reqInfo){
        return actHiTaskinstService.queryActHiTaskinstctByProsId(reqInfo);
    }

    /**
     * Creat byleon
     * @param reqInfo  doctorId医生id
     * @return
     */
    @RequestMapping(value = "/projectContacts",method = RequestMethod.POST)
    public ResponseData projectContacts(@RequestBody ReqInfo reqInfo){


        return groupService.queryDoctoryContactById(reqInfo);
    }

    /**
     * Creat by Leon
     * 判断是否是随访患者
     */
    @PostMapping("/patientStatus")
    public ResponseData patientStatus(@RequestBody ReqInfo reqInfo){

        return patientService.queryPatientStatus(reqInfo);
    }


}