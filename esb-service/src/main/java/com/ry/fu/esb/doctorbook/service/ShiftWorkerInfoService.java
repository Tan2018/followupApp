package com.ry.fu.esb.doctorbook.service;

import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.doctorbook.model.ShiftWorkInfo;
import com.ry.fu.esb.doctorbook.model.request.*;

import java.util.Map;

/**
 * @author Howard
 * @version V1.0.0
 * @date 2018/3/26 16:17
 * @description 交接班service接口
 */
public interface ShiftWorkerInfoService {
    ResponseData findCheckRecordInfoByPager(PatientRecordPagerRequest patientRecordPagerRequest);

    ResponseData saveShiftInfo(ShiftWorkInfo shiftWorkInfo);

    ResponseData updateShiftInfo(ShiftWorkInfo shiftWorkInfo);

    ResponseData saveShiftDutyRecord(PatientCheckRecordRequest patientCheckRecordRequest);

    ResponseData findPatientInfo(InpatientInfoRequest patientInformationRequest);

    ResponseData findDoctorInfo(Integer deptId);

    ResponseData uploadFile(PatientCheckRecordFileRequest patientCheckRecordFileRequest);

    ResponseData findDoctorInfoByName(DoctorInfoByPageRequest doctocInfo) throws ESBException, SystemException;

    ResponseData patientStatistics(Map<String, String> map);

    ResponseData departmentStatistics(Map<String, String> map);

    ResponseData findHospital();

    ResponseData findOrgList(String hospitalId);

    ResponseData cancelShift(Map<String, String> map);

    ResponseData findSuperiorOrg(String deptId);

    ResponseData delRoundRecord(Map<String,String> map);
}
