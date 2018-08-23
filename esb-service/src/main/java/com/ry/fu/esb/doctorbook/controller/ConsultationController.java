package com.ry.fu.esb.doctorbook.controller;

import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.doctorbook.model.request.*;
import com.ry.fu.esb.doctorbook.service.ConsultationService;
import com.ry.fu.esb.medicaljournal.model.request.DoctorSearchReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/6/27 9:54
 **/
@RestController
@RequestMapping("${prefixPath}/v1/docBook/Consultation")
public class ConsultationController {

    @Autowired
    private ConsultationService consultationService;

    @RequestMapping("/queryConsultationDeptInfo")
    public ResponseData queryConsultationDeptInfo(@RequestBody ConsultationDeptRequest request) throws ESBException, SystemException {

        return consultationService.findConsultationDeptInfo(request);
    }

    @RequestMapping("/queryConsultationDoctorInfo")
    public ResponseData queryConsultationDoctorInfo(@RequestBody DoctorSearchReq request) throws ESBException, SystemException {

        return consultationService.findConsultationDoctorInfo(request);
    }

    @RequestMapping("/saveApplicationFormInfo")
    public ResponseData saveApplicationFormInfo(@RequestBody ApplicationFormRequest request) throws ESBException, SystemException {

        return consultationService.addApplicationFormInfo(request);
    }

    @RequestMapping("/queryDoctorConsultationFormInfo")
    public ResponseData queryDoctorConsultationFormInfo(@RequestBody DoctorConsultationFormRequest request) throws ESBException, SystemException {

        return consultationService.findDoctorConsultationFormInfo(request);
    }

    @RequestMapping("/querySuperiorAuditDoctorInfo")
    public ResponseData querySuperiorAuditDoctorInfo(@RequestBody DoctorInfomationRequest request) throws ESBException, SystemException {
        return consultationService.findSuperiorAuditDoctorInfo(request);
    }

    @RequestMapping("/addConsultationFormInfo")
    public ResponseData addConsultationFormInfo(@RequestBody ConsultationFormAuditRequest request) throws ESBException, SystemException {
        return consultationService.saveConsultationFormInfo(request);
    }

    @RequestMapping("/addEntrustDoctorInfo")
    public ResponseData addEntrustDoctorInfo(@RequestBody EntrustDoctorRequest request) throws ESBException, SystemException {
        return consultationService.saveEntrustDoctorInfo(request);
    }

    @RequestMapping("/queryConsultationId")
    public ResponseData queryConsultationId(@RequestBody ConsultationIdRequest request) throws ESBException, SystemException {
        return consultationService.findConsultationId(request);
    }

    @RequestMapping("/findAuditProcess")
    public ResponseData findAuditProcess(@RequestBody ApplicationFormRequest request)throws ESBException, SystemException{
        return consultationService.findAuditProcess(request);
    }

    @RequestMapping("/findStaffInfo")
    public ResponseData findStaffInfo(@RequestBody StaffInfoRequest request)throws ESBException,SystemException{
        return consultationService.findStaffInfo(request);
    }

}
