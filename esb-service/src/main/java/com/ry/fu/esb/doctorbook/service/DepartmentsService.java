package com.ry.fu.esb.doctorbook.service;

import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.doctorbook.model.request.DepartmentsLogRequest;
import com.ry.fu.esb.doctorbook.model.request.OtherDepInfoLogRequest;
import com.ry.fu.esb.doctorbook.model.request.OtherDepRequest;
import com.ry.fu.esb.doctorbook.model.request.QueryOtherDepPatientRequest;
import com.ry.fu.esb.medicaljournal.model.request.DoctorSearchReq;

public interface DepartmentsService {
    public ResponseData queryDepartmentsDoctor(DoctorSearchReq req)throws Exception;

    public ResponseData handoverStatistics(Integer orgId,String date,Integer pageSize,Integer pageSum);

    public Integer getCount(Integer orgId,String date);


    public ResponseData departmentsStatistics(String date,Integer pageSize,Integer pageSum);

    public Integer getDepCount(String date);

    public ResponseData addDepartmentsLog(DepartmentsLogRequest departmentsLogRequest);

    public ResponseData addOtherDep(OtherDepRequest otherDepRequest);

    public ResponseData queryOtherDepPatient(QueryOtherDepPatientRequest queryOtherDepPatientRequest);

    public ResponseData queryDepLog(QueryOtherDepPatientRequest queryOtherDepPatientRequest);

    public ResponseData queryAllDep();

    public ResponseData updateState(Long id);

    public ResponseData otherDepInfoLog(OtherDepInfoLogRequest otherDepInfoLogRequest);
}
