package com.ry.fu.esb.doctorbook.service;


import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.doctorbook.model.request.*;
import org.json.JSONException;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.ParseException;

public interface IPService {

        ResponseData doctorAdvice(@RequestBody DoctorAdviceRequest request) throws ESBException, SystemException;

        ResponseData inspectionReport(@RequestBody InspectionReportRequest request) throws ESBException, SystemException;

        ResponseData patientDetails(@RequestBody PatientDetailsRequest request) throws ESBException, SystemException;

        ResponseData imageDetails(@RequestBody ImageDetailsRequest request) throws ESBException, SystemException;

        ResponseData otherDetails(@RequestBody OtherDetailsRequest request) throws ESBException, SystemException;

        ResponseData courseOfDiseaseRecord(@RequestBody CourseOfDiseaseRequest request) throws ESBException, SystemException;

        ResponseData operationRecord(@RequestBody OperationRecordRequest request) throws ESBException, SystemException;

        ResponseData hospitalizationRecord(@RequestBody HospitalizationRecordRequest request) throws ESBException, ParseException, SystemException;

        ResponseData inpatientInfo(@RequestBody InpatientInfoRequest request) throws ESBException, SystemException;


        ResponseData getImagePath(@RequestBody GetImageRequest request) throws ESBException, SystemException;

        ResponseData inpatientBasic(@RequestBody InpatientBasicRequest request) throws ESBException, SystemException, ParseException;
        ResponseData inpatientBasicNew(@RequestBody InpatientBasicRequest request) throws ESBException, SystemException, ParseException;


        ResponseData inpatientCase(@RequestBody InpatientCaseRequest request) throws ESBException, SystemException;



        ResponseData nurseMeasure(@RequestBody NurseMeasureRequest request) throws ESBException, SystemException;
        ResponseData systemLogin(@RequestBody SystemLoginRequest request) throws ESBException, SystemException, ParseException;

        ResponseData hospitalPatients(@RequestBody PatientQueryRequest request) throws Exception;
        ResponseData otherQueryOld(@RequestBody OtherQueryRequest request) throws ESBException, SystemException;
        ResponseData otherQuery(@RequestBody HospitalPatientsRequest request) throws ESBException, SystemException;
        ResponseData patientQuery(@RequestBody PatientQueryRequest request) throws ESBException, SystemException;
        ResponseData patientQuery2(@RequestBody PatientQueryRequest request) throws ESBException, SystemException;


        ResponseData inHospitalRecords(InHospitalInfoRequest request) throws ESBException, SystemException;

        //ResponseData hospitalPatientList(@RequestBody InpatientCaseRequest request) throws ESBException, SystemException;


        ResponseData outPatientRecords(InHospitalInfoRequest request) throws SystemException, ESBException;

        ResponseData inspectionRecords(InHospitalInfoRequest request) throws SystemException, ESBException;

        ResponseData showFollowUpDetails(FollowUpDetailQueryRequest request) throws ESBException, SystemException, JSONException, ParseException;

        ResponseData patientInfo(InPatientRequest request) throws SystemException, ESBException;
        ResponseData hospitalStatistics(HospitalStatisticsRequest request) throws SystemException, ESBException;

}

