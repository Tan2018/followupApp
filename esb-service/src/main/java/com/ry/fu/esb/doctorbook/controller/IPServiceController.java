package com.ry.fu.esb.doctorbook.controller;

import com.ry.fu.esb.common.enumstatic .StatusCode;
import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.common.service.PublicService;
import com.ry.fu.esb.common.service.RequestService;
import com.ry.fu.esb.doctorbook.model.request.*;
import com.ry.fu.esb.doctorbook.service.IPService;
import com.ry.fu.esb.doctorbook.service.PrimeIndexService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;


@RestController
@RequestMapping("${prefixPath}/v1/docBook/ip")
public class IPServiceController {

    @Autowired
    private PublicService publicService;

    @Autowired
    private IPService ipService;
    @Autowired
    private PrimeIndexService prService;

    @Autowired
    private RequestService requestService;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * telly
     * 住院患者医嘱查询
     */
    @PostMapping("/doctorAdvice")
    public ResponseData doctorAdvice(@RequestBody DoctorAdviceRequest request) {
        try{
            return ipService.doctorAdvice(request);
        }catch (ESBException e){
            logger.error("病程记录接口", e);
            return new ResponseData(e.getStatus(), e.getMsg(), null);
        }catch (SystemException e){
            logger.error("病程记录接口", e);
            return new ResponseData(StatusCode.OK.getCode(), e.getMsg(), null);
        }

    }

    /**
     * telly
     * 检验报告查询（第三方查询）
     */
    @PostMapping("/inspectionReport")
    public ResponseData inspectionReport(@RequestBody InspectionReportRequest request)  {
        try{
            return ipService.inspectionReport(request);
        }catch (ESBException e){
            logger.error("检验报告接口", e);
            return new ResponseData(e.getStatus(), e.getMsg(), null);
        }catch (SystemException e){
            logger.error("检验报告接口", e);
            return new ResponseData(StatusCode.OK.getCode(), e.getMsg(), null);
        }

    }

    /**
     * Boven
     * 影像结果查询
     */
    @PostMapping("/imageDetails")
    public ResponseData imageDetails(@RequestBody ImageDetailsRequest request) throws ESBException, SystemException {
        try{
            return ipService.imageDetails(request);
        }catch (ESBException e){
            logger.error("影像结果接口", e);
            return new ResponseData(e.getStatus(), e.getMsg(), null);
        }catch (SystemException e){
            logger.error("影像结果接口", e);
            return new ResponseData(StatusCode.OK.getCode(), e.getMsg(), null);
        }

    }

    /**
     * Boven
     * 其他结果查询
     */
    @PostMapping("/otherDetails")
    public ResponseData otherDetails(@RequestBody OtherDetailsRequest request) throws ESBException, SystemException {
        try{
            return ipService.otherDetails(request);
        }catch (ESBException e){
            logger.error("影像结果接口", e);
            return new ResponseData(e.getStatus(), e.getMsg(), null);
        }catch (SystemException e){
            logger.error("影像结果接口", e);
            return new ResponseData(StatusCode.OK.getCode(), e.getMsg(), null);
        }
    }

    /**
     * Boven
     * 检查结果查询
     */
    @PostMapping("/patientDetails")
    public ResponseData patientDetails(@RequestBody PatientDetailsRequest request) throws ESBException, SystemException {
        return ipService.patientDetails(request);
    }


    /**
     * Jane
     * 住院患者病程记录信息查询
     */
    @PostMapping("/courseOfDisease")
    public ResponseData courseOfDiseaseRecord(@RequestBody CourseOfDiseaseRequest request) throws ESBException, SystemException {
      try{
          return ipService.courseOfDiseaseRecord(request);
      }catch (ESBException e){
          logger.error("病程记录接口", e);
          return new ResponseData(e.getStatus(), e.getMsg(), null);
      }catch (SystemException e){
          logger.error("病程记录接口", e);
          return new ResponseData(StatusCode.OK.getCode(), e.getMsg(), null);
      }

    }

    /**
     * Jane
     * 住院患者手术记录信息查询
     */
    @PostMapping("/operationRecord")
    public ResponseData operationRecord(@RequestBody OperationRecordRequest request) throws ESBException, SystemException {
        try{
            return ipService.operationRecord(request);
        }catch (ESBException e){
            return new ResponseData(StatusCode.ARGU_ERROR.getCode(),StatusCode.ARGU_ERROR.getMsg(),null);
        }catch (SystemException e) {
            return new ResponseData(StatusCode.INSIDE_ERROR.getCode(), StatusCode.INSIDE_ERROR.getMsg(), null);

        }
    }

    /**
     * Boven
     * 患者入院记录信息
     */
    @PostMapping("/hospitalizationRecord")
    public Object hospitalizationRecord(@RequestBody HospitalizationRecordRequest request) throws ESBException, ParseException, SystemException {

        return ipService.hospitalizationRecord(request);
    }
    /**
     * Boven
     * 住院患者信息信息列表
     */
    @PostMapping("/inpatientInfo")
    public ResponseData inpatientInfo(@RequestBody InpatientInfoRequest request) {
        try{
        return ipService.inpatientInfo(request);
        }catch (SystemException e){
            logger.error("inpatientInfo", e);
            return  new ResponseData( StatusCode.INSIDE_ERROR.getCode(),StatusCode.INSIDE_ERROR.getMsg(),null);
        } catch (ESBException e){
            logger.error("inpatientInfo", e);
            return  new ResponseData( StatusCode.INSIDE_ERROR.getCode(),StatusCode.INSIDE_ERROR.getMsg(),null);
        }
    }

    /**
     * Boven
     * 患者基础信息
     */
    @PostMapping("/inpatientBasicNew")
    public ResponseData inpatientBasicNew(@RequestBody InpatientBasicRequest request)  {
        try{
            return ipService.inpatientBasicNew(request);
            } catch (ParseException e) {
                e.printStackTrace();
                return new ResponseData("500", "转换异常", null);
            }catch (ESBException e){
                logger.error("inpatientBasic接口", e);
                return new ResponseData(e.getStatus(), e.getMsg(), null);
            }catch (SystemException e){
                logger.error("inpatientBasic接口", e);
                return new ResponseData(StatusCode.OK.getCode(), e.getMsg(), null);
        }
    }

    @PostMapping("/inpatientBasic")
    public ResponseData inpatientBasic(@RequestBody InpatientBasicRequest request) {
        try {
            return ipService.inpatientBasicNew(request);
        } catch (ParseException e) {
            e.printStackTrace();
            return new ResponseData("500", "转换异常", null);
        }catch (ESBException e){
            logger.error("inpatientBasic接口", e);
            return new ResponseData(e.getStatus(), e.getMsg(), null);
        }catch (SystemException e){
            logger.error("inpatientBasic接口", e);
            return new ResponseData(StatusCode.OK.getCode(), e.getMsg(), null);
        }
    }

    /**
     * Boven
     * 住院患者病案首页
     */
    @PostMapping("/inpatientCase")
    public ResponseData inpatientCase(@RequestBody InpatientCaseRequest request)  {
        try{
            return ipService.inpatientCase(request);
        }catch (ESBException e){
            return new ResponseData(StatusCode.OK.getCode(),StatusCode.OK.getMsg(),null);
        }catch (SystemException e){
            return new ResponseData(StatusCode.OK.getCode(),StatusCode.OK.getMsg(),null);
        }
    }


    /**
     * Boven
     * 护理三测单报告查询
     */
    @PostMapping("/nurseMeasure")
    public ResponseData nurseMeasure(@RequestBody NurseMeasureRequest request)  {
        try{
            return ipService.nurseMeasure(request);
        }catch (ESBException e){
            return new ResponseData(StatusCode.OK.getCode(),StatusCode.OK.getMsg(),null);
        }catch (SystemException e){
            return new ResponseData(StatusCode.OK.getCode(),StatusCode.OK.getMsg(),null);
        }
    }

    /**
     * Boven
     * 随访系统登录认证
     */
    @PostMapping("/systemLogin")
    public ResponseData systemLogin(@RequestBody SystemLoginRequest request) {
        try {
            return ipService.systemLogin(request);
            } catch (ParseException e) {
                return new ResponseData("500", "时间转换错误", null);
            } catch (ESBException e) {

            if("000100".equals(e.getStatus())) {
                return new ResponseData(StatusCode.ESB_USER_NOT_FOUND.getCode(), e.getMsg(), null);
            }if("000000".equals(e.getStatus())) {
                return new ResponseData(StatusCode.ESB_USER_NOT_FOUND.getCode(), e.getMsg(), null);
            }
            return new ResponseData("500", "用户登录失败", null);
            } catch (SystemException e) {
            e.printStackTrace();
                return new ResponseData("500", "用户登录失败", null);
            }
    }

    /**
     * 影像接口
     */
    @PostMapping("/getImagePath")
    public ResponseData getImagePath(@RequestBody GetImageRequest request) {
        try {
            return ipService.getImagePath(request);
        } catch (ESBException e) {
            if("000701".equals(e.getStatus())) {
                return new ResponseData(StatusCode.ESB_USER_NOT_FOUND.getCode(), e.getMsg(), null);
            }
        } catch (SystemException e) {
            return new ResponseData(StatusCode.ESB_USER_NOT_FOUND.getCode(), e.getMsg(), null);
        }

        return new ResponseData("500", "获取影像失败", null);

    }
    /**
     * 报告查询
     */
    @PostMapping("/hospitalPatients")
    public ResponseData hospitalPatients(@RequestBody PatientQueryRequest request)  {
      try {
          return ipService.hospitalPatients(request);
      }catch (ESBException e){
          return new ResponseData(StatusCode.ESB_ERROR.getCode(), StatusCode.ESB_ERROR.getMsg(), null);
      }catch (Exception e){
          return new ResponseData(StatusCode.ESB_ERROR.getCode(), StatusCode.ESB_ERROR.getMsg(), null);
      }
    }
    /**
     * 其他科室查询
     */
    @PostMapping("/otherQueryOld")
    public ResponseData otherQueryOld(@RequestBody OtherQueryRequest request) {

        try{
            return  ipService.otherQueryOld(request);
        }catch (ESBException e){
            return new ResponseData(StatusCode.OK.getCode(),StatusCode.OK.getMsg(),null);
        }catch (SystemException e){
            return new ResponseData(StatusCode.OK.getCode(),StatusCode.OK.getMsg(),null);
        }
    }
    @PostMapping("/otherQuery")
    public ResponseData otherQueryNew(@RequestBody HospitalPatientsRequest request) {
        try{
            return  ipService.otherQuery(request);
        }catch (ESBException e){
            return new ResponseData(StatusCode.OK.getCode(),StatusCode.OK.getMsg(),null);
        }catch (SystemException e){
            return new ResponseData(StatusCode.OK.getCode(),StatusCode.OK.getMsg(),null);
        }
    }

    @PostMapping("/patientQuery")
    public ResponseData patientQuery(@RequestBody PatientQueryRequest request)  {
        try{
            return  ipService.patientQuery(request);
        }catch (ESBException e){
            return new ResponseData(StatusCode.OK.getCode(),StatusCode.OK.getMsg(),null);
        }catch (SystemException e){
            return new ResponseData(StatusCode.OK.getCode(),StatusCode.OK.getMsg(),null);
        }

    }

    /**
     * jackson
     * 根据住院号查询住院患者列表(ipTimes,ipseqnoText,inpatientId)
     * @param request
     * @return
     * @throws ESBException
     * @throws SystemException
     * todo
     */
    @PostMapping("/hospitalPatientList")
    public ResponseData hospitalNo(@RequestBody InpatientCaseRequest request) throws ESBException, SystemException {
        if(request.getIpseqNoText()!=null){
            if(StringUtils.isBlank(request.getIpseqNoText())){

            }
        }
        try{

            return  ipService.inpatientCase(request);
        }catch (ESBException e){

            return new ResponseData(StatusCode.OK.getCode(),StatusCode.OK.getMsg(),null);
        }
    }

    /**
     * 住院检查检验记录查询
     */
    @PostMapping("/inHospitalRecords")
    public ResponseData inHospitalRecords(@RequestBody InHospitalInfoRequest request) throws ESBException, SystemException {
        return  ipService.inHospitalRecords(request);
    }

    /**
     * 门诊检查检验记录查询
     */
    @PostMapping("/outPatientRecords")
    public ResponseData outPatientRecords(@RequestBody InHospitalInfoRequest request) throws ESBException, SystemException {
        return  ipService.outPatientRecords(request);
    }

    /**
     * 检查检验记录查询
     */
    @PostMapping("/inspectionRecords")
    public ResponseData inspectionRecords(@RequestBody InHospitalInfoRequest request) throws ESBException, SystemException {
        return  ipService.inspectionRecords(request);
    }

    /**
     *
     * @param request
     * @return
     * @throws ESBException
     * @throws SystemException
     */
    @PostMapping("/showFollowUpDetails")
    public ResponseData showFollowUpDetails(@RequestBody FollowUpDetailQueryRequest request) {
        try {
            return ipService.showFollowUpDetails(request);
        } catch (ESBException e) {
            e.printStackTrace();
            return new ResponseData(StatusCode.ESB_ERROR.getCode(), e.getMsg(), null);
        } catch (SystemException e) {
            e.printStackTrace();
            return  new ResponseData(StatusCode.INSIDE_ERROR.getCode(), e.getMsg(), null);
        } catch (ParseException e) {
            e.printStackTrace();
           return new ResponseData(StatusCode.INSIDE_ERROR.getCode(), e.getMessage(), null);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(StatusCode.INSIDE_ERROR.getCode(), e.getMessage(), null);
        }

    }
    @PostMapping("/patientInfo")
    public ResponseData patientInfo(@RequestBody InPatientRequest request) {
        try {
            return ipService.patientInfo(request);
        } catch (ESBException e) {
            return new ResponseData(StatusCode.ESB_ERROR.getCode(), e.getMsg(), null);
        } catch (SystemException e) {
            return new ResponseData(StatusCode.INSIDE_ERROR.getCode(), e.getMessage(), null);
        }
    }
    @PostMapping("/hospitalStatistics")
    public ResponseData hospitalStatistics(@RequestBody HospitalStatisticsRequest request) {
        try {
            return ipService.hospitalStatistics(request);
        } catch (ESBException e) {
            return new ResponseData(StatusCode.ESB_ERROR.getCode(), e.getMsg(), null);
        } catch (SystemException e) {
            return new ResponseData(StatusCode.INSIDE_ERROR.getCode(), e.getMessage(), null);
        }
    }


}
