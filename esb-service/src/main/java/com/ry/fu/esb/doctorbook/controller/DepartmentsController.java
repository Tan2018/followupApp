package com.ry.fu.esb.doctorbook.controller;

import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.doctorbook.model.request.*;
import com.ry.fu.esb.doctorbook.service.DepartmentsService;
import com.ry.fu.esb.medicaljournal.model.request.DoctorSearchReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${prefixPath}/v1/docBook/Departments")
public class DepartmentsController {

    @Autowired
    private DepartmentsService departmentsService;


    /**
     * thankbin
     * 查询其它科室医生
     */

    @PostMapping("/queryDepartmentsDoctor")
    public ResponseData queryDepartmentsDoctor(@RequestBody DoctorSearchReq req)throws Exception{
        return departmentsService.queryDepartmentsDoctor(req);
    }



    /**
     * thankbin
     * 交接班患者统计
     * TODO:to delete
     */

    @PostMapping("/handoverStatistics")
    public ResponseData handoverStatistics(@RequestBody HandoverStatisticsRequest handoverStatisticsRequest){
        //return departmentsService.handoverStatistics(handoverStatisticsRequest.getOrgId(),handoverStatisticsRequest.getDate(),handoverStatisticsRequest.getPageSize(),handoverStatisticsRequest.getPageSum());
        return null;
    }

    /**
     * thankbin
     * 交接班科室统计
     * TODO:to delete
     */
    @PostMapping("/departmentsStatisticsRequest")
    public ResponseData departmentsSStatisticsRequest(@RequestBody DepartmentsStatisticsRequest departmentsStatisticsRequest){
        //return departmentsService.departmentsStatistics(departmentsStatisticsRequest.getDate(),departmentsStatisticsRequest.getPageSize(),departmentsStatisticsRequest.getPageSum());
        return null;
    }


    /**
     * thankbin
     * 新增交接班科室记录
     */
    @PostMapping("/addDepartmentsLog")
    public ResponseData addDepartmentsLog(DepartmentsLogRequest departmentsLogRequest){
        return departmentsService.addDepartmentsLog(departmentsLogRequest);
    }

    /**
     * thankbin
     * 新增其它科室医生可见o
     *
     */
    @PostMapping("/addOtherDep")
    public ResponseData addOtherDep(@RequestBody OtherDepRequest therDepRequest){
        return departmentsService.addOtherDep(therDepRequest);
    }

    /**
     * thankbin
     * 其它科室交接班患者列表
     * TODO:to delete
     */
    @PostMapping(value = "/queryOtherDepPatient")
    public ResponseData queryOtherDepPatient(@RequestBody QueryOtherDepPatientRequest queryOtherDepPatientRequest){
        return departmentsService.queryOtherDepPatient(queryOtherDepPatientRequest);
    }

    /**
     * thankbin
     * 科室交接班历史资料
     */
    @PostMapping("/queryDepLog")
    public ResponseData queryDepLog(@RequestBody QueryOtherDepPatientRequest queryOtherDepPatientRequest){
        return departmentsService.queryDepLog(queryOtherDepPatientRequest);
    }

    /**
     * thankbin
     * 查询科室
     */
    @PostMapping("/queryAllDep")
    public ResponseData queryAllDep(){
        return departmentsService.queryAllDep();
    }

    /**
     * thankbin
     * 接班其它科室患者
     * TODO:to delete
     */
    @PostMapping("/updateState")
    public ResponseData updateState(@RequestBody OtherDepPatientRequest otherDepPatientRequest){
        return departmentsService.updateState(otherDepPatientRequest.getId());
    }

    /**
     * thankbin
     * 其它科室交接班历史资料
     * TODO:to delete
     */
    @PostMapping("/OtherDepInfoLog")
    public ResponseData OtherDepInfoLog(@RequestBody OtherDepInfoLogRequest otherDepInfoLogRequest){
        return departmentsService.otherDepInfoLog(otherDepInfoLogRequest);
    }
}

