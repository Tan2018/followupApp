package com.ry.fu.esb.doctorbook.service;

import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.doctorbook.model.request.*;
import com.ry.fu.esb.medicaljournal.model.request.DoctorSearchReq;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/6/27 9:35
 **/
public interface ConsultationService {
    ResponseData findConsultationDeptInfo(ConsultationDeptRequest request) throws ESBException, SystemException;

    ResponseData findConsultationDoctorInfo(DoctorSearchReq request)throws ESBException, SystemException;

    ResponseData addApplicationFormInfo(ApplicationFormRequest request)throws ESBException, SystemException;

    ResponseData findDoctorConsultationFormInfo(DoctorConsultationFormRequest request)throws ESBException, SystemException;

    ResponseData findSuperiorAuditDoctorInfo(DoctorInfomationRequest request)throws ESBException, SystemException;

    ResponseData saveConsultationFormInfo(ConsultationFormAuditRequest request)throws ESBException, SystemException;

    ResponseData saveEntrustDoctorInfo(EntrustDoctorRequest request)throws ESBException, SystemException;

    ResponseData findAuditProcess(ApplicationFormRequest request)throws ESBException,SystemException;

    ResponseData findConsultationId(ConsultationIdRequest request)throws ESBException, SystemException;

    ResponseData findStaffInfo(StaffInfoRequest request)throws  ESBException,SystemException;

}
