package com.ry.fu.esb.medicaljournal.controller;

import com.ry.fu.esb.common.enumstatic.ESBServiceCode;
import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.common.service.PublicService;
import com.ry.fu.esb.common.utils.JsonUtils;
import com.ry.fu.esb.common.utils.ResponseUtil;
import com.ry.fu.esb.medicaljournal.mapper.HospitalInfoMapper;
import com.ry.fu.esb.medicaljournal.mapper.OrgInfoMapper;
import com.ry.fu.esb.medicaljournal.mapper.SymptomMapper;
import com.ry.fu.esb.medicaljournal.model.FavoriteDoctor;
import com.ry.fu.esb.medicaljournal.model.PartPic;
import com.ry.fu.esb.medicaljournal.model.RegisteredRecord;
import com.ry.fu.esb.medicaljournal.model.request.*;
import com.ry.fu.esb.medicaljournal.model.response.*;
import com.ry.fu.esb.medicaljournal.service.MedicalService;
import com.ry.fu.esb.medicaljournal.service.OPService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 就医日志--门诊接口
 * @author:jam,walker
 */
@RestController
@RequestMapping("${prefixPath}/v1/medicalJournal/op")
public class OpServiceController {
    Logger logger = LoggerFactory.getLogger(OpServiceController.class);

    @Autowired
    private OPService opService;
    @Autowired
    private PublicService publicService;
    @Autowired
    private HospitalInfoMapper hospitalInfoMapper;
    @Autowired
    private SymptomMapper symptomMapper;
    @Autowired
    private MedicalService medicalService;
    @Autowired
    private Environment env;
    @Autowired
    private OrgInfoMapper orgInfoMapper;

    /**
     * 科室信息查询接口
     * @Author:jam
     * @return
     * @throws ESBException
     */
    @RequestMapping(value = "/queryDeptInfo", method = RequestMethod.POST)
    public ResponseData queryDeptInfo(@RequestBody DeptInfoRequest deptInfoRequest) throws ESBException, SystemException {
        logger.info("科室信息查询接口"+JsonUtils.toJSon(deptInfoRequest));
        return opService.queryDeptInfo(deptInfoRequest);
    }

    @RequestMapping(value = "/addOpOrgInfo", method = RequestMethod.POST)
    public void addOpOrgInfo(@RequestBody DeptInfoRequest deptInfoRequest) throws ESBException, SystemException {
        ResponseData orgTree = opService.queryDeptInfo(deptInfoRequest);
        Map<String, Object> resultMap = (Map<String, Object>) orgTree.getData();
        medicalService.addOpOrgInfo(resultMap);
    }

    /**
     * 医生信息查询接口
     * @Author:walker
     * @return
     * @throws ESBException
     */
    @RequestMapping(value = "/getDoctorInfo", method = RequestMethod.POST)
    public ResponseData queryDoctorInfo(@RequestBody DoctorInfoRequest doctorInfoRequest) {
        logger.info("医生信息查询接口入参"+JsonUtils.toJSon(doctorInfoRequest));
        boolean flag = false;
        if(doctorInfoRequest.getDeptId() != null) {
            flag = true;
        }
        if(doctorInfoRequest.getDoctorId() != null) {
            flag = true;
        }
        if(!flag) {
            return new ResponseData("500", "参数缺失，医生ID或者科室ID必须有一个，", null);
        }

        try {
            return opService.queryDoctorInfo(doctorInfoRequest);
        } catch (ESBException e) {
            e.printStackTrace();
            logger.error("ESB系统异常", e.toString());
        } catch (SystemException e) {
            e.printStackTrace();
            logger.error("系统内部异常", e.toString());
        }
        return new ResponseData("500", "获取医生信息失败", null);
    }

    /**
     * 曾挂号医生
     * @param registeredDoctorRequest 曾挂号医生请求报文
     * @return ResponseData
     * @throws ESBException 平台自定义异常
     */
    @RequestMapping(value = "/getRegisteredDoctor", method = RequestMethod.POST)
    public ResponseData getRegisteredDoctor(@RequestBody RegisteredDoctorRequest registeredDoctorRequest) throws ESBException, SystemException {
        logger.info("曾挂号医生入参"+JsonUtils.toJSon(registeredDoctorRequest));
        return opService.getRegisteredDoctor(registeredDoctorRequest);
    }

    /**
     * 医生号源信息查询接口(排班信息)
     * @Author:Boven
     * @param
     * @return
     * @throws DocumentException
     */
    @RequestMapping(value = "/queryRegInfo", method = RequestMethod.POST)
    public ResponseData queryRegInfo(@RequestBody DoctorRegisterSourceRequestExtend doctorRegisterSourceRequestExtend) throws ESBException, SystemException {
        logger.info("医生号源信息查询接口入参"+ JsonUtils.toJSon(doctorRegisterSourceRequestExtend));
        return opService.queryRegInfo(doctorRegisterSourceRequestExtend);
    }

    /**
     * 医生号源分时信息查询接口
     * @throws DocumentException
     */
    @RequestMapping(value = "/queryTimeRegInfo", method = RequestMethod.POST)
    public ResponseData queryTimeRegInfo(@RequestBody DoctorRegisterSourceByTimeRequest doctorRegisterSourceByTimeRequest) throws ESBException, SystemException {
        long t1= System.currentTimeMillis();
        logger.info("----医生号源分时信息查询接口请求时间----"+JsonUtils.toJSon(doctorRegisterSourceByTimeRequest));
        ResponseData responseData= publicService.query(ESBServiceCode.DOCTORREGISTERSOURCEBYTIME,doctorRegisterSourceByTimeRequest, DoctorRegisterSourceByTimeRespone.class);
        long t2= System.currentTimeMillis();
        logger.info("----医生号源分时信息查询ESB响应时间----"+(t2-t1),JsonUtils.toJSon(responseData));
        return responseData;

    }

    /**
     * 科室号源信息查询接口
     * @param deptRegInfoRequest 科室请求报文
     * @return ResponseData
     * @throws ESBException 平台自定义异常
     */
    @RequestMapping(value = "/queryDeptRegInfo", method = RequestMethod.POST)
    public ResponseData queryDeptRegInfo(@RequestBody DeptRegInfoRequest deptRegInfoRequest) throws ESBException, SystemException {
        logger.info("===============科室号源请求:{}",JsonUtils.toJSon(deptRegInfoRequest));
        return opService.queryDeptRegInfo(deptRegInfoRequest);
    }

    /**
     * 医院信息查询接口(获取后台页面对应表数据)
     * @return ResponseData
     */
    @RequestMapping(value = "/getHospitalInfo", method = RequestMethod.POST)
    public ResponseData findHospitalInfo()  {
        return ResponseUtil.getSuccessResultMap(opService.findHospitalInfo());
    }

    /**
     * 普通挂号
     * @param registerInfoRequest 挂号request
     * @return ResponseData
     * @throws ESBException 平台自定义异常
     */
    @RequestMapping(value = "/addNewRegistration", method = RequestMethod.POST)
    public ResponseData addNewRegistration(@RequestBody RegisterInfoRequest registerInfoRequest)  {
        logger.info("普通挂号入参"+JsonUtils.toJSon(registerInfoRequest));
        try {
            return opService.addNewRegistration(registerInfoRequest);
        } catch (SystemException e) {
            e.printStackTrace();
            logger.error(e.toString());

        } catch (ESBException e) {
            e.printStackTrace();
            logger.error("ESB错误",e.toString());
        }
        return new ResponseData("500", "挂号失败", null);
    }

    /**
     * 取消挂号预约
     * @param cancelRegisterRequest 取消挂号request
     * @return ResponseData
     * @throws ESBException 平台自定义异常
     */
    @RequestMapping(value = "/cancelRegistration", method = RequestMethod.POST)
    public ResponseData cancelRegistration(@RequestBody CancelRegisterRequest cancelRegisterRequest) throws ESBException, SystemException {
        //cancelRegisterRequest.setCancelTime(FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
        cancelRegisterRequest.setCancelTime(DateFormatUtils.format((new Date()),"yyyy-MM-dd HH:mm:ss"));
        cancelRegisterRequest.setOrderType("40");
        return opService.cancelRegistration(cancelRegisterRequest);
    }

    /**
     * 挂号(号源锁定)
     * @param registerLockedInfoRequest 号源锁定request
     * @return ResponseData
     * @throws ESBException 平台自定义异常
     */
    @RequestMapping(value = "/addNewRegistrationLocked", method = RequestMethod.POST)
    public ResponseData addNewRegistrationLocked(@RequestBody RegisterLockedInfoRequest registerLockedInfoRequest) throws ESBException, SystemException {
        return publicService.query(ESBServiceCode.REGISTERLOCKED,registerLockedInfoRequest, RegisterListResponse.class);
    }

    /**
     * 号源锁定(S001008)
     * @param registerSourceLockedRequest 号源锁定报文
     * @return ResponseData
     * @throws ESBException 平台自定义异常
     */
    @RequestMapping(value = "/getRegistrationSourceLock", method = RequestMethod.POST)
    public ResponseData getRegistrationSourceLock(@RequestBody RegisterSourceLockedRequest registerSourceLockedRequest) throws ESBException, SystemException {

        return publicService.query(ESBServiceCode.REGISTERSOURCELOCKED,registerSourceLockedRequest, RegisteredDoctorListResponse.class);

    }

    /**
     * 号源解除锁定(S001009)
     * @param registerSourceLockedRequest 号源解除锁定报文
     * @return ResponseData
     * @throws ESBException 平台自定义异常
     */
    @RequestMapping(value = "/cancelRegistrationSourceLock", method = RequestMethod.POST)
    public ResponseData cancelRegistrationSourceLock(@RequestBody RegisterSourceLockedRequest registerSourceLockedRequest) throws ESBException, SystemException {
        return publicService.query(ESBServiceCode.REGISTERSOURCEUNLOCKED,registerSourceLockedRequest, RegisterListResponse.class);
    }

    /**
    * @Author:Boven
    * @Description:检验报告
    * @Date: Created in 18:35 2018/3/14
    */
    @RequestMapping(value = "/inspectionReport", method = RequestMethod.POST)
    public ResponseData inspectionReport(@RequestBody InspectReportRequest inspectionReportRequest) throws ESBException, SystemException {

        return opService.inspectionReport(inspectionReportRequest);

    }

    /**
    * @Author:Boven
    * @Description:检查报告
    * @Date: Created in 18:35 2018/3/14
    */
    @RequestMapping(value = "/patientDetails", method = RequestMethod.POST)
    public ResponseData patientDetails(@RequestBody TestDetailsRequest testDetailsRequest) throws ESBException, SystemException {
        return opService.patientDetails(testDetailsRequest);
    }
    /**
    * @Author:Tom
    * @Description:门诊查询
    * @Date: Created in 18:35 2018/3/14
    */
    @RequestMapping(value = "/clinicalQuery", method = RequestMethod.POST)
    public ResponseData clinicalQuery(@RequestBody ClinicalQueryRequest request) throws ESBException, SystemException {
        if(request.getWeek()==null||StringUtils.isBlank(request.getWeek())){
            return new ResponseData("500","week不能为空",null);
        }
        if(request.getType()==null||StringUtils.isBlank(request.getType())){
            return new ResponseData("500","type不能为空",null);
        }
        try{
            ResponseData responseData=opService.clinicalQuery(request);
            return responseData;
        }catch (Exception e){
            return new ResponseData("200","未查到数据",null);
        }

    }
    /**
     * @Author:Tom
     * @Description:停诊查询
     * @Date: Created in 18:35 2018/3/14
     */
    @RequestMapping(value = "/stopConsultation", method = RequestMethod.POST)
    public ResponseData stopConsultation(@RequestBody StopConsultationRequest request) throws ESBException, SystemException {
        if(request.getType()==null||StringUtils.isBlank(request.getType())){
            return new ResponseData("500","type不能为空",null);
        }
        try{
            ResponseData responseData=opService.stopConsultation(request);
            return responseData;
        }catch (Exception e){
            return new ResponseData("200","未查到数据",null);
        }
    }

    /**
     * @Author:Tom
     * @Description:医生查询
     * @Date: Created in 15:41 2018/4/11
     */
    @RequestMapping(value = "/queryDoctorInfo", method = RequestMethod.POST)
    public ResponseData queryDoctorInfo(@RequestBody DoctorQueryRequest request) throws ESBException, SystemException {
            if(request.getDeptId()==null||StringUtils.isBlank(request.getDeptId())){
                return new ResponseData("500","deptId不能为空",null);
            }

            try{
                ResponseData responseData=opService.findDoctorInfo(request);
                return responseData;
            }catch (Exception e){
                return new ResponseData("200","未查到数据",null);
            }
    }
     /**
    * @Author:Boven
    * @Description:门诊处方
    * @Date: Created in 18:35 2018/3/14
    */
    @RequestMapping(value = "/prescription", method = RequestMethod.POST)
    public ResponseData prescription(@RequestBody PrescriptionRequest request) throws ESBException, SystemException {

        return opService.prescription(request);
    }

    /**
     * 科室切换
     * @param deptSwitchRequest 切换科室报文
     * @return ResponseData
     * @throws ESBException 平台自定义异常
     */
    @RequestMapping(value = "/deptSwitch", method = RequestMethod.POST)
    public ResponseData deptSwitch(@RequestBody DeptSwitchRequest deptSwitchRequest) throws ESBException, SystemException {
        long t1= System.currentTimeMillis();
        logger.info("----请求时间---"+t1);
        if(StringUtils.isBlank(deptSwitchRequest.getDoctorId())) {
            return new ResponseData("500", "参数缺失", "医生ID不能为空！");
        }

        ResponseData responseData= publicService.query(ESBServiceCode.DEPTSWITCH,deptSwitchRequest, DeptSwitchRespone.class);
        long t2= System.currentTimeMillis();
        logger.info("----ESB响应时间---"+(t2-t1));
        return responseData;
    }

    /**
     * 挂号记录查询
     * @return ResponseData
     * @throws ESBException 平台自定义异常
     */
    @RequestMapping(value = "/registeredRecord", method = RequestMethod.POST)
    public ResponseData registeredRecord(@RequestBody Map<String,Long> map) {
        long t1=System.currentTimeMillis();

        logger.info("----请求时间----"+t1);
        logger.info("----请求map----"+map);
        List<RegisteredRecord> registeredRecords = opService.registeredRecord(map);

        Map responseData = new HashMap(2);
        responseData.put("list",registeredRecords);
        long t2=System.currentTimeMillis();

        logger.info("----业务处理时间----"+(t2-t1));
        return ResponseUtil.getSuccessResultBean(responseData);
    }
/*
    @RequestMapping(value = "/registeredRecord", method = RequestMethod.POST)
    public ResponseData registeredRecord(@RequestBody RegisteredRecordRequest request) throws ESBException, SystemException {
        if (StringUtils.isBlank(request.getPatientIds())){
            return new ResponseData(StatusCode.ARGU_ERROR.getCode(), StatusCode.ARGU_ERROR.getMsg(), null);
        }
        return  opService.registeredRecord(request);

    }
*/
    @RequestMapping(value = "/registeredQueue", method = RequestMethod.POST)
    public ResponseData registeredQueue(@RequestBody RegisteredQueueRequest request) throws ESBException, SystemException {

       return opService.registeredQueue(request);
    }
    /**
     * 获取症状列表
     * @param
     * @return String
     * @throws
     */
    @RequestMapping(value = "/symptomsList", method = RequestMethod.POST)
    public String symptomsList() {
        return opService.symptomsList();
    }

    /**
     * 返回图片
     * @param PicId
     * @param request
     * @param response
     */
    @RequestMapping(value = "show", method = RequestMethod.GET)
    public void show(@RequestParam(value = "picId", required = true) String PicId,
                     HttpServletRequest request, HttpServletResponse response) {
        ServletOutputStream out = null;
        try {
            response.reset();
            response.setContentType("image/png; charset=utf-8");
            PartPic partPic = symptomMapper.getPartPic(PicId);
            byte[] contents = partPic.getBytearray();
            out = response.getOutputStream();
            out.write(contents);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 复诊预约
     */
    @RequestMapping(value = "/recheckReservation", method = RequestMethod.POST)
    public ResponseData recheckReservation(@RequestBody RecheckReservationRequest recheckReservationRequest) throws ESBException, SystemException {
        return opService.recheckReservation(recheckReservationRequest);
    }

    /**
     * 我的医生
     */
    @RequestMapping(value = "/favoriteDoctors", method = RequestMethod.POST)
    public ResponseData favoriteDoctors(@RequestBody Map<String,Long> map) {
        Long accountId = map.get("accountId");
        return opService.favoriteDoctors(accountId);
    }

    /**
     * 关注/取消关注
     */
    @RequestMapping(value = "/collectDoctor", method = RequestMethod.POST)
    public ResponseData collectDoctor(@RequestBody FavoriteDoctor favoriteDoctor)  {
     return opService.collectDoctor(favoriteDoctor);
    }

    /**
     * 查询医生是否关注
     */
    @RequestMapping(value = "/isCollection", method = RequestMethod.POST)
    public ResponseData isCollection(@RequestBody FavoriteDoctor favoriteDoctor)  {
        return opService.isCollection(favoriteDoctor);
    }


    /**
     * 患者信息查询
     * @param patientInfoRequest 请求报文
     * @return ResponseData
     */
    @RequestMapping(value = "/patientInfo", method = RequestMethod.POST)
    public ResponseData patientInfo(@RequestBody PatientInfoRequest patientInfoRequest) throws ESBException, SystemException {
        return publicService.query(ESBServiceCode.PATIENT_INFO,patientInfoRequest, PatientInfoResponse.class);
    }
    /**
     * 检查检验费用
     *
     * @return ResponseData
     */
    @RequestMapping(value = "/inspectionCost", method = RequestMethod.POST)
    public ResponseData inspectionCost(@RequestBody InspectionCostRequest request) throws ESBException, SystemException {
//        if(StringUtils.isBlank(request.getPatientId())&&StringUtils.isBlank(request.getPatientCardNo())){
//            return  ResponseData（StatusCode.ARGU_EMPTY.getCode(),StatusCode.ARGU_EMPTY.getMsg(),null);
//        }
        return opService.inspectionCost(request);
    }
    /**
     * 单次挂号检查报告
     *
     * @return ResponseData
     */
    @RequestMapping(value = "/registerExamin", method = RequestMethod.POST)
    public ResponseData registerExamin(@RequestBody RegisterExaminRequest request) throws ESBException, SystemException {

        return opService.registerExamin(request);
    }
    /**
     * 单次挂号检验报告
     *
     * @return ResponseData
     */
    @RequestMapping(value = "/registerInspect", method = RequestMethod.POST)
    public ResponseData registerInspect(@RequestBody RegisteInspectRequest request) throws ESBException, SystemException {

        return opService.registerInspect(request);
    } /**
     * 订单详情
     *
     * @return ResponseData
     */
    @RequestMapping(value = "/registrationDetail", method = RequestMethod.POST)
    public ResponseData registrationDetail(@RequestBody RegistrationDetailRequest request) throws ESBException, SystemException {

        return opService.registrationDetail(request);
    }

    /**
     * 取号
     * @param
     * @return ResponseData
     */
    @RequestMapping(value = "/payMent", method = RequestMethod.POST)
    public ResponseData payMent(@RequestBody PayMentRequest request) throws ESBException, SystemException {

        return  opService.payMent(request);
    }


    /**
     * 添加就诊对象
     * @return ResponseData
     */
    @RequestMapping(value = "/addPatient", method = RequestMethod.POST)
    public ResponseData addPatient(@RequestBody PatientInfoRequest patientInfoRequest) {
        ResponseData patientInfo = new ResponseData();
        try {
            patientInfo = publicService.query(ESBServiceCode.PATIENT_INFO,patientInfoRequest, PatientInfoResponse.class);
            if(patientInfo != null && patientInfo.getData() != null){
                PatientInfoResponse patientInfoResponse = (PatientInfoResponse) patientInfo.getData();
                if( patientInfoResponse.getPatientInfo() != null){
                    String patientPrimaryIndex = opService.getPatientPrimaryIndex(patientInfoRequest);
                    patientInfoResponse.getPatientInfo().setPrimaryIndex(patientPrimaryIndex);
                    patientInfo = opService.addPatient(patientInfoRequest, patientInfoResponse);
                }else {
                    patientInfo.setStatus("552");
                    patientInfo.setMsg("查不到该患者信息");
                    patientInfo.setData(null);
                }
            }
        } catch (SystemException e) {
            e.printStackTrace();
        } catch (ESBException e) {
            e.printStackTrace();
            logger.error("ESB错误",e.toString());
        }
        return patientInfo;
    }

    @RequestMapping(value = "/registerOrUpdateNeteaseToken", method = RequestMethod.POST)
    public ResponseData registerOrUpdateNeteaseToken( Map<String,String> map) {
        opService.registerOrUpdateNeteaseToken(map);
        return new ResponseData();
    }

    /**
     * 添加就诊对象
     * 引入主索引
     * @date 2018-07-23
     * @return ResponseData
     */
  /*  @RequestMapping(value = "/addPatient", method = RequestMethod.POST)
    public ResponseData addPatient(@RequestBody PatientInfoQueryRequest patientInfoRequest) {
        ResponseData patientInfo = new ResponseData();
        try {
            patientInfo = opService.addPatient(patientInfoRequest);
        } catch (ESBException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        }
        return patientInfo;
    }*/

    /**
     * 查询就诊对象
     * @return ResponseData
     */
    @RequestMapping(value = "/selectPatient", method = RequestMethod.POST)
    public ResponseData selectPatient(@RequestBody Map<String,Long> map) {
        Long accountId = map.get("accountId");
        return opService.selectPatient(accountId);
    }

    /**
     * 解绑就诊对象
     */
    @RequestMapping(value = "/removePatient", method = RequestMethod.POST)
    public ResponseData removePatient(@RequestBody Map<String,Long> map)  {
        Long accountId = map.get("accountId");
        Long id = map.get("id");
        return opService.removePatient(accountId,id);
    }

    /*@RequestMapping(value = "/getImagePath", method = RequestMethod.POST)
    public ResponseData getImagePath(@RequestBody QueryReportRequest request)  {
        try {
            return publicService.getImagePath(request);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("===========================Object-->XML：内部数据转换错误", e.toString());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("WebService没有返回数据==========", e.toString());
        }
        return new ResponseData("500", "获取影像失败", null);
    }*/

    /**
     * 报到排号
     */
    @RequestMapping(value = "/lineUp", method = RequestMethod.POST)
    public ResponseData lineUp(@RequestBody Map<String,String>map)  {
        try {
            return opService.lineUp(map.get("patientId"));
        } catch (ESBException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        }
        return new ResponseData("500", "获取数据失败", null);
    }



}
