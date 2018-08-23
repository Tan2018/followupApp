package com.ry.fu.esb.medicaljournal.service;

import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.medicaljournal.model.FavoriteDoctor;
import com.ry.fu.esb.medicaljournal.model.HospitalInfo;
import com.ry.fu.esb.medicaljournal.model.RegisteredRecord;
import com.ry.fu.esb.medicaljournal.model.request.*;
import com.ry.fu.esb.medicaljournal.model.response.PatientInfoResponse;

import java.util.List;
import java.util.Map;

public interface OPService {

    public ResponseData queryDeptInfo(DeptInfoRequest deptInfoRequest) throws ESBException, SystemException;

    public ResponseData queryDoctorInfo(DoctorInfoRequest doctorInfoRequest) throws ESBException, SystemException;

    public ResponseData getRegisteredDoctor(RegisteredDoctorRequest registeredDoctorRequest) throws ESBException, SystemException;

    /**
     * 科室号源信息查询
     * @param deptRegInfoRequest
     * @return
     * @throws ESBException
     */
    ResponseData queryDeptRegInfo(DeptRegInfoRequest deptRegInfoRequest) throws ESBException, SystemException;

    List<HospitalInfo> findHospitalInfo();

    /**
     * 医生号源信息查询
     * @param doctorRegisterSourceRequestExtend
     * @return ESBResponseData
     * @throws ESBException 平台自定义异常
     */
    ResponseData queryRegInfo(DoctorRegisterSourceRequestExtend doctorRegisterSourceRequestExtend) throws ESBException, SystemException;

    /**
     * 新增挂号
     * @param registerInfoRequest
     * @return
     * @throws SystemException
     * @throws ESBException
     */
    ResponseData addNewRegistration(RegisterInfoRequest registerInfoRequest) throws SystemException, ESBException;

    /**
    * @Author:Boven
    * @Description: 检验报告
    * @Date: Created in 18:21 2018/3/14
    */
    ResponseData inspectionReport(InspectReportRequest inspectionReportRequest) throws ESBException, SystemException;
    /**
    * @Author:Boven
    * @Description: 检查报告
    * @Date: Created in 18:21 2018/3/14
    */
    ResponseData patientDetails(TestDetailsRequest patientDetailsRequest) throws ESBException, SystemException;
    /**
    * @Author:Boven
    * @Description: 今日停诊查询
    * @Date: Created in 18:21 2018/3/14
    */
    ResponseData stopConsultation(StopConsultationRequest request) throws ESBException, SystemException; /**
    * @Author:Boven
    * @Description: 门诊处方
    * @Date: Created in 18:21 2018/3/14
    */
    ResponseData prescription(PrescriptionRequest request) throws ESBException, SystemException;

    ResponseData newPrescription(PrescriptionRequest request) throws ESBException, SystemException;
    /** @Author:Boven
    * @Description: 单次挂号检验报告
    * @Date: Created in 18:21 2018/3/14
    */
    ResponseData registerExamin(RegisterExaminRequest request) throws ESBException, SystemException;
    /** @Author:Boven
    * @Description: 单次挂号检查报告
    * @Date: Created in 18:21 2018/3/14
    */
    ResponseData registerInspect(RegisteInspectRequest request) throws ESBException, SystemException;

    /**
     * @Author:Boven
     * @Description: 门诊查询
     * @Date: Created in 18:21 2018/3/14
     */
    ResponseData clinicalQuery(ClinicalQueryRequest request) throws ESBException, SystemException;


    /**
     * @Author:Tom
     * @Description: 医生查询
     */
    ResponseData findDoctorInfo(DoctorQueryRequest request) throws ESBException, SystemException;

    /**
     * 获取症状列表
     * @param
     * @return String
     * @throws
     */
    public String symptomsList();

    /**
     * 预约复诊
     */
    public ResponseData recheckReservation(RecheckReservationRequest recheckReservationRequest) throws ESBException, SystemException;


    /**
     * 我的医生
     */
    ResponseData favoriteDoctors(Long accountId);


    /**
     * 关注/取消关注
     */
    ResponseData collectDoctor(FavoriteDoctor favoriteDoctor);

    /**
     * 查询医生是否关注
     */
    ResponseData isCollection(FavoriteDoctor favoriteDoctor);


    /**
     * 添加就诊对象
     */
    ResponseData addPatient(PatientInfoRequest patientInfoRequest,PatientInfoResponse patientInfoResponse);
    String getPatientPrimaryIndex(PatientInfoRequest patientInfoRequest) throws ESBException, SystemException;

    /**
     * 查询就诊对象
     * @param accountId
     * @return
     */
    ResponseData selectPatient(Long accountId);
    /**
    *检查检验费用
    */
    public ResponseData inspectionCost(InspectionCostRequest request) throws ESBException, SystemException;
    /**
    *排队报到
    */
    public ResponseData registeredQueue(RegisteredQueueRequest request) throws ESBException, SystemException;
    /**
    *挂号记录
    */
    List<RegisteredRecord> registeredRecord(Map<String,Long> map);
    //public ResponseData registeredRecord(RegisteredRecordRequest request) throws ESBException, SystemException;
    /**
    *订单支付
    */
    public ResponseData payMent(PayMentRequest request) throws ESBException, SystemException;

    /**
     * 解绑就诊对象
     */
    ResponseData removePatient(Long accountId, Long id);

    /**
     * 取消挂号预约
     */
    ResponseData cancelRegistration(CancelRegisterRequest cancelRegisterRequest) throws ESBException, SystemException;

    /**
     *挂号详情
     */
    public ResponseData registrationDetail(RegistrationDetailRequest request) throws ESBException, SystemException;


    /**
     * 报到排号
     * @param patientId
     * @return
     */
    ResponseData lineUp(String patientId) throws ESBException, SystemException;


    ResponseData registerOrUpdateNeteaseToken(Map<String, String> map);
}
