package com.ry.fu.esb.medicaljournal.service.impl;

import com.ry.fu.esb.common.constants.Constants;
import com.ry.fu.esb.common.enumstatic.ESBServiceCode;
import com.ry.fu.esb.common.enumstatic.StatusCode;
import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.common.seq.RedisIncrementGenerator;
import com.ry.fu.esb.common.service.PublicService;
import com.ry.fu.esb.common.service.RequestService;
import com.ry.fu.esb.common.utils.JsonUtils;
import com.ry.fu.esb.common.utils.ResponseUtil;
import com.ry.fu.esb.doctorbook.model.request.PrimaryIndexQueryPatientListRequest;
import com.ry.fu.esb.doctorbook.model.response.PrimaryIndexQueryPatientListResponse;
import com.ry.fu.esb.doctorbook.model.response.PrimaryIndexQueryPatientRecord;
import com.ry.fu.esb.instantmessaging.service.NetEaseCloudLetterService;
import com.ry.fu.esb.medicaljournal.constants.RedisKeyConstants;
import com.ry.fu.esb.medicaljournal.mapper.*;
import com.ry.fu.esb.medicaljournal.model.*;
import com.ry.fu.esb.medicaljournal.model.request.*;
import com.ry.fu.esb.medicaljournal.model.response.*;
import com.ry.fu.esb.medicaljournal.service.OPService;
import com.ry.fu.esb.medicaljournal.util.CheckType;
import com.ry.fu.esb.medicaljournal.util.PrescriptionType;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static com.ry.fu.esb.common.utils.ResponseUtil.*;

@Service
public class OPServiceImpl implements OPService {

    private Logger logger = LoggerFactory.getLogger(OPServiceImpl.class);

    @Autowired
    private PublicService publicService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SymptomMapper symptomMapper;
    @Autowired
    private FavoriteDoctorsMapper favoriteDoctorsMapper;
    @Autowired
    private RequestService requestService;
    @Autowired
    private ContactsMapper contactsMapper;
    @Autowired
    private RedisIncrementGenerator redisIncrementGenerator;
    @Autowired
    private RegistrationMapper registrationMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private PatientMapper patientMapper;
    @Autowired
    private DoctorMapper doctorMapper;
    @Autowired
    private HospitalInfoMapper hospitalInfoMapper;
    @Autowired
    private NetEaseCloudLetterService netEaseCloudLetterService;

    @Value("${fua.SHOWPIC_ADDRESS}")
    public String picPreAdd;


    @Override
    public ResponseData queryDeptInfo(DeptInfoRequest deptInfoRequest) throws ESBException, SystemException {
        long t1=System.currentTimeMillis();
        logger.info("请求时间------"+t1);
        ResponseData deptInfos = (ResponseData) redisTemplate.opsForValue().get(RedisKeyConstants.DEPT_LIST);
        long t2=0L;
        if(deptInfos == null) {
            deptInfos = publicService.query(ESBServiceCode.DEPTLIST,deptInfoRequest, DeptListResponse.class);
             t2=System.currentTimeMillis();
            logger.info("ESB响应时间------"+(t2-t1));
            if(deptInfos != null) {
                redisTemplate.opsForValue().set(RedisKeyConstants.DEPT_LIST, deptInfos, 2, TimeUnit.DAYS);  //两天缓存时间

            }
        }

        DeptListResponse deptListResponse =  (DeptListResponse) deptInfos.getData();
        t2=System.currentTimeMillis();
        logger.info("redis响应时间------"+(t2-t1));
        List<DeptItemResponse> deptItemResponses = deptListResponse.getDeptItemResponses();
        List<DeptItemResponse> sortedDept = new ArrayList<DeptItemResponse>();
        Map<String, DeptItemResponse> sortParentMap = new HashMap();
        Map<String, DeptItemResponse> secondDeptMap = new HashMap();
        Map<String, DeptItemResponse> thirdDeptMap = new HashMap();

        Long hospFlag = deptInfoRequest.getFlag();

        for (int i = 0; i < deptItemResponses.size(); i++) {
            DeptItemResponse dept = deptItemResponses.get(i);
            dept.setDeptName(dept.getDeptName().replace("分诊台",""));
            if (-1 == dept.getParentId()) {

                if (hospFlag == null) {
                    sortParentMap.put(dept.getDeptId().toString(),dept);
                } else {
                    if (hospFlag == dept.getDistrictDeptId().longValue()) {
                        sortParentMap.put(dept.getDeptId().toString(),dept);
                    }
                }
            } else if (-1 != dept.getParentId() && -1 != dept.getSecondParentId()) {

                if (!secondDeptMap.containsKey(dept.getSecondParentId().toString())) {
                    DeptItemResponse secondDept = new DeptItemResponse();
                    secondDept.setDeptId(dept.getSecondParentId());
                    secondDept.setDeptName(dept.getSecondParentDeptName());
                    secondDept.setParentId(dept.getParentId());
                    secondDeptMap.put(dept.getSecondParentId().toString(),secondDept);

                }

                if (!thirdDeptMap.containsKey(dept.getDeptId().toString())) {
                    DeptItemResponse thirdDept = new DeptItemResponse();
                    thirdDept.setDeptId(dept.getDeptId());
                    thirdDept.setDeptName(dept.getDeptName());
                    thirdDept.setParentId(dept.getSecondParentId());
                    thirdDeptMap.put(dept.getDeptId().toString(),thirdDept);
                }
            }
        }
        for (Map.Entry<String, DeptItemResponse> thirdDeptEntry : thirdDeptMap.entrySet()) {
            DeptItemResponse thirdDept = thirdDeptEntry.getValue();
            if (secondDeptMap.containsKey(thirdDept.getParentId().toString())) {
                DeptItemResponse secondDept = secondDeptMap.get(thirdDept.getParentId().toString());
                secondDept.addDepts(thirdDept);
            }
        }

        for (Map.Entry<String, DeptItemResponse> secondDeptEntry : secondDeptMap.entrySet()) {
            DeptItemResponse secondDept = secondDeptEntry.getValue();
            if (sortParentMap.containsKey(secondDept.getParentId().toString())) {
                DeptItemResponse parentDept = sortParentMap.get(secondDept.getParentId().toString());
                parentDept.addDepts(secondDept);
            }
        }

        List<DeptItemResponse> mapValuesList = new ArrayList<DeptItemResponse>(sortParentMap.values());
        Collections.reverse(mapValuesList);
        Map resultMap = new HashMap();
        resultMap.put("depts", mapValuesList);
        long t3=System.currentTimeMillis();
        logger.info("业务处理时间------"+(t3-t2));
        logger.info("科室信息查询接口"+JsonUtils.toJSon(resultMap));
        return getSuccessResultMap(resultMap);

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData queryDoctorInfo(DoctorInfoRequest doctorInfoRequest) throws ESBException, SystemException {
        long t1=System.currentTimeMillis();
        logger.info("请求时间------"+t1);
        String doctorKey = String.format(RedisKeyConstants.DOCTOR_DEPT_DOCTORID, doctorInfoRequest.getDeptId(), doctorInfoRequest.getDoctorId());
        DoctorListResponse doctorListResponse = (DoctorListResponse) redisTemplate.opsForValue().get(doctorKey);
        ResponseData responseData = null;
        if(doctorListResponse == null) {
            responseData = publicService.query(ESBServiceCode.DOCTORINFOLIST, doctorInfoRequest, DoctorListResponse.class);
            long t2=System.currentTimeMillis();
            logger.info("ESB响应时间------"+(t2-t1));
            redisTemplate.opsForValue().set(doctorKey,responseData.getData(), 30, TimeUnit.MINUTES);
            //保存到本数据库
            if(responseData.getData() != null){
                doctorListResponse = (DoctorListResponse) responseData.getData();
                if(doctorListResponse != null && !doctorListResponse.getDoctorItemResponses().isEmpty()) {
                    List<DoctorItemResponse> doctorItemResponseList = doctorListResponse.getDoctorItemResponses();
                    DoctorItemResponse doctorItemResponse = doctorItemResponseList.get(0);
                    DoctorInfo doctorInfo = new DoctorInfo();
                    Long id = redisIncrementGenerator.increment(Constants.M_DOCTOR_SEQ, 1);
                    doctorInfo.setId(id);
                    doctorInfo.setDoctorId(Long.valueOf(doctorItemResponse.getDoctorId()));
                    doctorInfo.setChName(doctorItemResponse.getDoctorName());
                    doctorInfo.setGoodAt(doctorItemResponse.getDesc());
                    doctorInfo.setHeadImg(doctorItemResponse.getImg());
                    doctorInfo.setProcessName(doctorItemResponse.getTitle());
                    doctorInfo.setSex(doctorItemResponse.getGender().equals("F-女")? "2":"1");
                    doctorInfo.setStatus("0");
                    doctorMapper.saveOrUpdate(doctorInfo);
                }
            }
        } else {
            responseData = new ResponseData();
            responseData.setMsg(StatusCode.OK.getMsg());
            responseData.setStatus(StatusCode.OK.getCode());
            responseData.setData(doctorListResponse);
        }
        long t3=System.currentTimeMillis();
        logger.info("ESB响应时间------"+(t3-t1));
        logger.info("医生信息查询接口出参"+JsonUtils.toJSon(responseData));
        return responseData;
    }

    @Override
    public ResponseData getRegisteredDoctor(RegisteredDoctorRequest registeredDoctorRequest) throws ESBException,SystemException {
        long t1=System.currentTimeMillis();
        logger.info("请求时间------"+t1);
        ResponseData responseData= publicService.query(ESBServiceCode.REGISTEREDDOCTOR,registeredDoctorRequest, RegisteredDoctorListResponse.class);
        long t2=System.currentTimeMillis();
        logger.info("ESB响应时间------"+(t2-t1));
        logger.info("曾挂号医生getRegisteredDoctor出参,responseData={}",JsonUtils.toJSon(responseData));
        return  responseData;

    }


    @Override
    public ResponseData queryDeptRegInfo(DeptRegInfoRequest deptRegInfoRequest) throws ESBException, SystemException {
        long t1=System.currentTimeMillis();
        logger.info("请求时间------"+t1);
        List<String> deptIdList = deptRegInfoRequest.getDeptIdList();
        ResponseData result = new ResponseData();
        result.setMsg(StatusCode.OK.getMsg());
        result.setStatus(StatusCode.OK.getCode());
        List<DeptRegInfoResponse> deptRegInfoResponseList = new ArrayList<DeptRegInfoResponse>(100);
        for (String deptId : deptIdList) {
            //先根据deptId查询科室号源
            //获取医生号源
            DoctorRegisterSourceRequest doctorRegisterSourceRequest = new DoctorRegisterSourceRequest();
            doctorRegisterSourceRequest.setDeptId(deptId);
            doctorRegisterSourceRequest.setStartDate(deptRegInfoRequest.getStartDate());
            doctorRegisterSourceRequest.setEndDate(deptRegInfoRequest.getEndDate());
            long t2=0L;
            String key = String.format(RedisKeyConstants.DOCTOR_SOURCE, deptId, deptRegInfoRequest.getStartDate(), deptRegInfoRequest.getEndDate());
            DoctorRegisterSourceRespone doctorRegisterSourceRespone = (DoctorRegisterSourceRespone) redisTemplate.opsForValue().get(key);
            ResponseData doctorRegisterInfo = null;
            if(doctorRegisterSourceRespone == null) {
                doctorRegisterInfo = publicService.query(ESBServiceCode.DOCTORREGISTERSOURCE, doctorRegisterSourceRequest, DoctorRegisterSourceRespone.class);
                t2=System.currentTimeMillis();
                logger.info("ESB医生号源响应时间------"+(t2-t1));
                redisTemplate.opsForValue().set(key,
                        doctorRegisterInfo.getData(), 30, TimeUnit.MINUTES);
                doctorRegisterSourceRespone = (DoctorRegisterSourceRespone)doctorRegisterInfo.getData();
            }
            //根据每一个号源信息查询医生基本信息
            if (doctorRegisterSourceRespone != null) {
                //返回的科室号源
                List<DeptRegInfoResponseRecord> deptRegInfoResponseRecordList = new ArrayList<DeptRegInfoResponseRecord>(50);
                DeptRegInfoResponse deptRegInfoResponse = new DeptRegInfoResponse();
//                DoctorRegisterSourceRespone doctorRegisterSourceRespone = (DoctorRegisterSourceRespone)doctorRegisterInfo.getData();
                if (!CollectionUtils.isEmpty(doctorRegisterSourceRespone.getDoctorRegisterSourceResponeRecodeRegInfos())) {
                    for (DoctorRegisterSourceResponeRecodeRegInfo regInfoElement :doctorRegisterSourceRespone.getDoctorRegisterSourceResponeRecodeRegInfos()) {
                        if (regInfoElement != null) {
                            //科室号源响应实体
                            DeptRegInfoResponseRecord registerInfoItem = new DeptRegInfoResponseRecord();
                            List<TimeRegInfoList> timeRegInfoListElementListResult = new ArrayList<TimeRegInfoList>(50);
                            //<REGINFO>节点
                            if (regInfoElement != null) {
                                //--------设置医生基本信息开始------//
                                registerInfoItem.setDoctorId(regInfoElement.getDoctorId());
                                registerInfoItem.setDoctorName(regInfoElement.getDoctorName());
                                registerInfoItem.setTitle(regInfoElement.getDoctorTitle());
                                registerInfoItem.setDeptId(regInfoElement.getDeptId());
                                registerInfoItem.setDeptName(regInfoElement.getDeptName());

                                //门诊专科医生信息
                                DoctorInfoRequest doctorInfoRequest = new DoctorInfoRequest();
                                doctorInfoRequest.setDeptId(Integer.parseInt(deptId));
                                doctorInfoRequest.setDoctorId(Integer.parseInt(regInfoElement.getDoctorId()));
                                String doctorKey = String.format(RedisKeyConstants.DOCTOR_DEPT_DOCTORID, deptId, regInfoElement.getDoctorId());
                                DoctorListResponse doctorListResponse = (DoctorListResponse) redisTemplate.opsForValue().get(doctorKey);
                                ResponseData dcotorInfo = null;
                                if(doctorListResponse == null) {
                                    dcotorInfo = publicService.query(ESBServiceCode.DOCTORINFOLIST, doctorInfoRequest, DoctorListResponse.class);
                                    long t3=System.currentTimeMillis();
                                    logger.info("ESB医生列表响应时间------"+(t3-t1));
                                    redisTemplate.opsForValue().set(doctorKey,
                                            dcotorInfo.getData(), 30, TimeUnit.MINUTES);
                                    doctorListResponse = (DoctorListResponse)dcotorInfo.getData();
                                }

                                if (doctorListResponse != null) {
                                    for(DoctorItemResponse doctorItemResponse : doctorListResponse.getDoctorItemResponses()){
                                        registerInfoItem.setImg(doctorItemResponse.getImg());
                                        registerInfoItem.setDesc(doctorItemResponse.getDesc());
                                    }
                                }

                                deptRegInfoResponse.setDeptName(regInfoElement.getDeptName());
                                //--------设置医生基本信息结束------//

                                //--------设置医生号源list开始------//
                                List<DoctorRegisterSourceResponeRecodeTimeRegInfoList> timeRegInfoListElementList = regInfoElement.getDoctorRegisterSourceResponeRecodeTimeRegInfoList();
                                //<TIMEREGINFOLIST>集合
                                if (timeRegInfoListElementList != null) {
                                    for (DoctorRegisterSourceResponeRecodeTimeRegInfoList item : timeRegInfoListElementList) {
                                        TimeRegInfoList timeRegInfoList = new TimeRegInfoList();
                                        timeRegInfoList.setRegDate(item.getRegDate());

                                        //<TIMEREGINFO>集合
                                        if (!CollectionUtils.isEmpty(item.getTimeRegInfoList())) {
                                            //挂号信息
                                            List<TimeRegInfoItem> timeRegInfoItemList = new ArrayList<TimeRegInfoItem>(2);
                                            List <DoctorRegisterSourceResponeRecodeTimeRegInfoListTimeRegInfo> list = item.getTimeRegInfoList();
                                            for (DoctorRegisterSourceResponeRecodeTimeRegInfoListTimeRegInfo timeRegInfo : list) {
                                                TimeRegInfoItem timeRegInfoItem = new TimeRegInfoItem();
                                                timeRegInfoItem.setTimeId(timeRegInfo.getTimeId());
                                                timeRegInfoItem.setTimeName(timeRegInfo.getTimeName());
                                                timeRegInfoItem.setRegType(timeRegInfo.getRegType());
                                                timeRegInfoItem.setRegFee(timeRegInfo.getRegFee());
                                                timeRegInfoItem.setTreatFee(timeRegInfo.getTreatFee());
                                                timeRegInfoItem.setRegLeaveCount(timeRegInfo.getRegLeaveCount());
                                                timeRegInfoItem.setStatusType(timeRegInfo.getStatusType());
                                                timeRegInfoItem.setIsTimeReg(timeRegInfo.getIsTimeReg());
                                                timeRegInfoItemList.add(timeRegInfoItem);
                                            }
                                            timeRegInfoList.setTimeRegInfoItemList(timeRegInfoItemList);
                                            timeRegInfoListElementListResult.add(timeRegInfoList);
                                        }
                                    }
                                }
                                registerInfoItem.setTimeRegInfoList(timeRegInfoListElementListResult);
                                deptRegInfoResponseRecordList.add(registerInfoItem);
                                //--------设置医生号源list结束------//
                            }
                        }
                    }
                }
                if (!CollectionUtils.isEmpty(deptRegInfoResponseRecordList)) {
                    deptRegInfoResponse.setDeptRegInfoResponseRecode(deptRegInfoResponseRecordList);
                    deptRegInfoResponse.setDeptId(deptId);
                    deptRegInfoResponseList.add(deptRegInfoResponse);
                }
            }
        }
        Map<String,List<DeptRegInfoResponse>> resultMap = new ConcurrentHashMap<String, List<DeptRegInfoResponse>>(1);
        resultMap.put("deptsRegInfo",deptRegInfoResponseList);
        result.setData(resultMap);
        long t4=System.currentTimeMillis();
        logger.info("业务处理时长------"+(t4-t1));
        logger.info("科室号源请求"+JsonUtils.toJSon(result));
        return result;
    }

    @Override
    public List<HospitalInfo> findHospitalInfo() {
        long t1=System.currentTimeMillis();
        logger.info("请求时间------"+t1);
        List<HospitalInfo> hospitalInfos = (List<HospitalInfo>) redisTemplate.opsForValue().get(RedisKeyConstants.HOSPITALINFO);
        long t2=System.currentTimeMillis();
        logger.info("redis响应时间------"+t2);
        if(hospitalInfos == null) {
            hospitalInfos = hospitalInfoMapper.findHospitalInfo();
            redisTemplate.opsForValue().set(RedisKeyConstants.HOSPITALINFO, hospitalInfos, 1, TimeUnit.DAYS);
        }
        long t3=System.currentTimeMillis();
        logger.info("业务处理时间------"+t3);
        return hospitalInfos;
    }

    @Override
    public ResponseData queryRegInfo(DoctorRegisterSourceRequestExtend doctorRegisterSourceRequestExtend) throws ESBException, SystemException {

        long t1=System.currentTimeMillis();
        logger.info("请求时间------"+t1);
        ResponseData responseData= publicService.query(ESBServiceCode.DOCTORREGISTERSOURCE,doctorRegisterSourceRequestExtend, DoctorRegisterSourceRespone.class);
        long t2=System.currentTimeMillis();
        logger.info("ESB响应时间------"+(t2-t1));
        logger.info("医生号源信息查询接口出参,responseData={}",JsonUtils.toJSon(responseData));
        return  responseData;
    }

    @Transactional(rollbackFor = Exception.class)
    //新增挂号
    @Override
    public ResponseData addNewRegistration(RegisterInfoRequest registerInfoRequest) throws ESBException, SystemException {
        long t1=System.currentTimeMillis();
        logger.info("请求时间------"+t1);
        Long orderTableId = redisIncrementGenerator.increment(Constants.M_ORDER_SEQ, 1);
        String sendHisOrderId = "RL" + orderTableId;    //传递给HIS的订单ID

        registerInfoRequest.setOrderId(sendHisOrderId);
        registerInfoRequest.setOrderTime(FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
        ResponseData result = publicService.query(ESBServiceCode.REGISTER,registerInfoRequest, RegisterListResponse.class);
        long t2=System.currentTimeMillis();
        logger.info("ESB响应时间------"+(t2-t1));
        if(result.getData() != null ) {
            RegisterListResponse registerListResponse = (RegisterListResponse) result.getData();
            if (registerListResponse != null && registerListResponse.getDeptItemResponses() != null){
                List<RegisterItemResponse> registerItemResponseList = registerListResponse.getDeptItemResponses();
                if(registerItemResponseList != null){
                    RegisterItemResponse registerItemResponse = registerItemResponseList.get(0);
                    //挂号成功

                    if(registerItemResponse.getResultCode().equals("0")){
                        registerItemResponse.setOrderId(sendHisOrderId);
                        //插入M_ORDER表 orderType=GHF id=288
                        Long registrationId = redisIncrementGenerator.increment(Constants.M_REGISTRATION_SEQ, 1);
                        orderMapper.insertOrder(sendHisOrderId, registrationId, "诊查费", registerInfoRequest.getTreatFee(),
                                registerInfoRequest.getDoctorId(), "诊查费", new Date());
                        Registration registration = new Registration();
                        registration.setId(registrationId);
                        registration.setHisOrderId(registerItemResponse.getOrderidHis());
                        registration.setDeptId(registerItemResponse.getDiagnoseRoomName());
                        registration.setPatientId(registerInfoRequest.getUserHisPatientId());
                        registration.setAccountId(registerInfoRequest.getAccountId());
                        registration.setDeptId(registerInfoRequest.getDeptId());
                        registration.setDoctorId(registerInfoRequest.getDoctorId());
                        registration.setMedicalNo(registerInfoRequest.getUserJkk());
                        registration.setVisitTime(registerInfoRequest.getStartTime()+"-"+registerInfoRequest.getEndTime());
                        try {
                            registration.setVisitDate(DateUtils.parseDate(registerInfoRequest.getRegDate(),"yyyy-MM-dd"));
                            registration.setWaitTime(DateUtils.parseDate(registerItemResponse.getWaitTime(), "HH:mm"));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        registration.setCreateTime(new Date());
                        registration.setDiagnoseRoom(registerItemResponse.getDiagnoseRoomName());
                        registration.setDistrictDeptName(registerInfoRequest.getDistrictDeptName());
                        registration.setProjectId(registerInfoRequest.getProjectId());
                        registration.setProjectName(registerInfoRequest.getProjectName());
                        //插入M_REGISTRATION表
                        registrationMapper.insert(registration);
                        logger.info("挂号成功======================="+registration);
                    }else {
                        logger.info("挂号失败========================="+registerItemResponse.getResultDesc());
                        result.setStatus("400");
                        result.setMsg("挂号失败"+registerItemResponse.getResultDesc());
                    }
                }
            }
        }
        long t3=System.currentTimeMillis();
        logger.info("业务处理时间------"+(t3-t2));
        logger.info("普通挂号出参"+JsonUtils.toJSon(result));
        return result;
    }

    @Override
    public ResponseData inspectionReport(InspectReportRequest request) throws ESBException, SystemException {
        //校验入参patientId
        long t1=System.currentTimeMillis();
        logger.info("请求时间------"+t1);
        if (StringUtils.isBlank(request.getPatientId())){
            return new ResponseData(StatusCode.ARGU_EMPTY.getCode(),StatusCode.ARGU_EMPTY.getMsg(),null);
        }
        ArrayList<InspectReportResponseRecord> reportArray = new ArrayList();
        ResponseData responseData = this.publicService.query(ESBServiceCode.INSPECTIONREPORT, request, InspectResponse.class);
        long t2=System.currentTimeMillis();
        logger.info("ESB响应时间------"+(t2-t1));
        InspectResponse response = (InspectResponse)responseData.getData();
        if (response != null & response.getInspectionReportResponseRecord() != null) {
            ArrayList<String> listArray = new ArrayList();
            ArrayList<String> itemArray = new ArrayList();
            Map<String, String> map = new HashMap();
            Iterator var7 = response.getInspectionReportResponseRecord().iterator();
            while(var7.hasNext()) {
                InspectReportResponseRecord record = (InspectReportResponseRecord)var7.next();
                String item = "";
                String list = "";
                if (StringUtils.isNotBlank(record.getRequestTime())){
                    record.setRequestTime(record.getRequestTime().substring(0,10));
                }
                if (record.getItemSet() != null) {
                    item = record.getItemSet();
                    itemArray.add(item);
                }
                if (record.getListNo() != null) {
                    list = record.getListNo();
                    listArray.add(list);
                }
                    map.put(list, item);
                    record.setMap(map);
                    record.setListArry(listArray);
                    record.setItemArry(itemArray);
                //检验结果
                if (record.getInspectReportResponseRecordResult()!=null&&record.getInspectReportResponseRecordResult().size()>0){
                    record.setFlagReport("1");
                    reportArray.add(record);
                    }
                }

        }else {
            return new ResponseData(StatusCode.OK.getCode(),StatusCode.OK.getMsg(),null);
        }
    responseData.setData(reportArray);
        long t3=System.currentTimeMillis();
        logger.info("业务处理时间------"+(t3-t2));
        return responseData;
    }

    @Override
    public ResponseData patientDetails(TestDetailsRequest testDetailsRequest) throws ESBException, SystemException {
        long t1=System.currentTimeMillis();
        logger.info("请求时间------"+t1);
        if(StringUtils.isBlank(testDetailsRequest.getPatientId())){
            return  new ResponseData(StatusCode.ARGU_EMPTY.getCode(),StatusCode.ARGU_EMPTY.getMsg(),null);
        }
        ResponseData patientDetails = this.publicService.query(ESBServiceCode.PATIENTDETAILS, testDetailsRequest, TestDetailsResponse.class);
        long t2=System.currentTimeMillis();
        logger.info("ESB响应时间------"+(t2-t1));
            TestDetailsResponse response = (TestDetailsResponse)patientDetails.getData();
            if (response != null &&response.getPatientDetailsResponseReportList()!=null) {
                ArrayList<String> listArray = new ArrayList();
                ArrayList<String> typeArray = new ArrayList();
                ArrayList<String> checkPointArray = new ArrayList();
                ArrayList<String> pdfArray = new ArrayList();
                List<TestDetailsResponseReportList> var8 = response.getPatientDetailsResponseReportList();

                for(TestDetailsResponseReportList list : var8) {
                    if (list != null && StringUtils.isNoneBlank(list.getFuncReqId())) {

                        if (StringUtils.isNotBlank(list.getReportPath())||!"-1".equals(list.getReportPath())){
                            list.setFlagReport("1");
                        }
                        if ( StringUtils.isNotBlank(list.getFuncReqId()) &&list.getFuncReqId().equals("-1")) {
                            list.setFuncReqId("手工单");
                        }

                        if (list.getProcedurestepName() == null) {
                            list.setProcedurestepName("");
                        }
                        listArray.add(list.getFuncReqId());
                        checkPointArray.add(list.getProcedurestepName());
                        if (list.getFuncType() != null) {
                            typeArray.add(CheckType.getCheckType(list.getFuncType()));
                        }
                        if (StringUtils.isNotBlank(list.getReportPath()) &&list.getReportPath().equals("-1") ) {
                            list.setReportPath("手工单");
                        }
                        pdfArray.add(list.getReportPath());
                    }

                    response.setListArray(listArray);
                    response.setCheckPointArray(checkPointArray);
                    response.setTypeArray(typeArray);
                    response.setPdfArray(pdfArray);
                }
            }else {
                return  new ResponseData(StatusCode.OK.getCode(),StatusCode.OK.getMsg(),null);
            }
        long t3=System.currentTimeMillis();
        logger.info("业务处理时间------"+(t3-t2));
        return patientDetails;
    }

    @Override
    public ResponseData stopConsultation(StopConsultationRequest request) throws ESBException, SystemException {
        long t1=System.currentTimeMillis();
        logger.info("请求时间------"+t1);
        String doctorDeptKey = String.format(RedisKeyConstants.STOPCONSULTATION_TYPE, request.getType());
        StopConsultationResponse stopConsultationResponse = (StopConsultationResponse) redisTemplate.opsForValue().get(doctorDeptKey);
        ResponseData responseData = null;
        if (stopConsultationResponse == null || stopConsultationResponse.getStopConsultationRecord() == null
                || stopConsultationResponse.getStopConsultationRecord().size() <= 0) {
            responseData = this.publicService.query(ESBServiceCode.STOPCONSULTATION, request, StopConsultationResponse.class);

            stopConsultationResponse = (StopConsultationResponse) responseData.getData();
            redisTemplate.opsForValue().set(doctorDeptKey, stopConsultationResponse, 3, TimeUnit.HOURS);
        } else {
            responseData = new ResponseData();
            responseData.setMsg(StatusCode.OK.getMsg());
            responseData.setStatus(StatusCode.OK.getCode());
            responseData.setData(stopConsultationResponse);
        }
        long t2=System.currentTimeMillis();
        logger.info("ESB响应时间------"+(t2-t1));
        StopConsultationResponse response = (StopConsultationResponse) responseData.getData();
        if (null != response || response.getStopConsultationRecord() != null
                || response.getStopConsultationRecord().size() > 0) {
            Set<String> deptIds = new HashSet<>();
            Set<String> regTypes = new HashSet<>();
            for (StopConsultationRecord doctor : response.getStopConsultationRecord()) {
                deptIds.add(doctor.getDeptId());
            }
            for (StopConsultationRecord doctor : response.getStopConsultationRecord()) {
                regTypes.add(doctor.getRegType());
            }
            Map<String, Object> listMap = new HashMap<>();
            List<Map<String, Object>> doctors = new ArrayList<Map<String, Object>>();
            List<Object> deptList = new ArrayList<>();
            for (String deptId : deptIds) {
                Map<String, Object> depts = new HashMap<>();
                depts.put("deptId", deptId);
                List<Object> typeList = new ArrayList<Object>();
                for (String regType : regTypes) {
                    Map<String, Object> typeMap = new HashMap<>();
                    Map<String, Object> amMap = new HashMap<>();
                    Map<String, Object> pmMap = new HashMap<>();
                    amMap.put("timeName", "上午");
                    pmMap.put("timeName", "下午");
                    List<Object> timesList = new ArrayList<>();
                    for (StopConsultationRecord doctor : response.getStopConsultationRecord()) {//找出同一科室的医生
                        if (deptId.equals(doctor.getDeptId())) {//相同科室
                            if (regType.equals(doctor.getRegType())) {
                                depts.put("deptName", doctor.getDeptName());
                                if ("上午".equals(doctor.getTimeName())) {
                                    List<Object> doctorList = new ArrayList<>();
                                    Map<String, Object> doctorMap = new HashMap<>();
                                    doctorMap.put("doctorId", doctor.getDoctorId());
                                    doctorMap.put("doctorName", doctor.getDoctorName());
                                    doctorList.add(doctorMap);
                                    List<Object> tempList = (List<Object>) amMap.get("doctors");
                                    if (tempList != null) {
                                        tempList.addAll(doctorList);
                                        amMap.put("doctors", tempList);
                                    } else {
                                        amMap.put("doctors", doctorList);
                                    }
                                } else {
                                    List<Object> doctorList = new ArrayList<>();
                                    Map<String, Object> doctorMap = new HashMap<>();
                                    doctorMap.put("doctorId", doctor.getDoctorId());
                                    doctorMap.put("doctorName", doctor.getDoctorName());
                                    doctorList.add(doctorMap);
                                    List<Object> tempList = (List<Object>) pmMap.get("doctors");
                                    if (tempList != null) {
                                        tempList.addAll(doctorList);
                                        pmMap.put("doctors", tempList);
                                    } else {
                                        pmMap.put("doctors", doctorList);
                                    }
                                }
                            }
                        }
                    }
                    if (amMap.size() > 1) {
                        timesList.add(amMap);
                    }
                    if (pmMap.size() > 1) {
                        timesList.add(pmMap);
                    }
                    if (timesList.size() > 0) {
                        typeMap.put("type", regType);
                        typeMap.put("times", timesList);
                        typeList.add(typeMap);
                    }
                }
                depts.put("types", typeList);
                deptList.add(depts);
            }
            listMap.put("list", deptList);
            responseData.setData(listMap);
        }
        long t3=System.currentTimeMillis();
        logger.info("业务处理时间------"+(t3-t2));
        return responseData;
    }

    @Override
    public ResponseData prescription(PrescriptionRequest request) throws ESBException, SystemException {
        long t1=System.currentTimeMillis();
        logger.info("请求时间t1------"+t1);
        if (StringUtils.isBlank(request.getPatientId())&&StringUtils.isBlank(request.getPatientCardNo())){
            return new ResponseData(StatusCode.ARGU_EMPTY.getCode(),StatusCode.ARGU_EMPTY.getMsg(),null);
        }
        if (StringUtils.isNotBlank(request.getRequestFlag())&&"1".equals(request.getRequestFlag())){
            request.setRegisterStartDate(new DateTime().minusMonths(6).toString().substring(0,10));
        }
        ResponseData responseData =publicService.query(ESBServiceCode.PRESCRIPTION,request,PrescriptionResponse.class);
        if (responseData.getData()==null){
            return new ResponseData(StatusCode.ESB_NOT_QUERY_DATA.getCode(),StatusCode.ESB_NOT_QUERY_DATA.getMsg(),null);
        }
        long t2=System.currentTimeMillis();
        logger.info("ESB响应时间------"+(t2-t1));
        PrescriptionResponse response = (PrescriptionResponse) responseData.getData();
        Map<String,List<String>> presMap=new HashMap<>();
        ArrayList<String> dateList = new ArrayList();
        ArrayList<String> rxNoList = new ArrayList();
        ArrayList<String> rxNoArray = new ArrayList();
        ArrayList<PrescriptionRecord> recordList = new ArrayList();
        if (response.getPrescriptionRecord()==null){
            return  new ResponseData(StatusCode.ESB_NOT_QUERY_DATA.getCode(),StatusCode.ESB_NOT_QUERY_DATA.getMsg(),null);
        }
        List<PrescriptionRecord>  prescriptionList= response.getPrescriptionRecord();
        Iterator<PrescriptionRecord> iterator=prescriptionList.iterator();
        while (iterator.hasNext()){
            PrescriptionRecord prescription=iterator.next();
            if ("0".equals(prescription.getPayFlag())){
                iterator.remove();
            }
        }
        if (response!=null){
            for ( PrescriptionRecord record:response.getPrescriptionRecord()
                    ) {
                // payFlag是否缴费 0-未付费,1-已付费
                //statusFlag 0-未打印,1-打印,2-配药,3-挂起,4-发药,5-退药,6-申请发药

                if (StringUtils.isNotBlank(record.getPayFlag())&&"1".equals(record.getPayFlag())){
                    NumberFormat nt = NumberFormat.getPercentInstance();
                    nt.setMaximumFractionDigits(2);
                    Integer num=Integer.parseInt(record.getPayProportion());
                    String payProportion=nt.format( (float)num/10000);
                    record.setPayProportion(payProportion);
                    String specialControlFlag= PrescriptionType.getSpecialControlFlag(record.getSpecialControlFlag());
                    if (StringUtils.isNotBlank(record.getInputDateTime()) && record.getInputDateTime().length() > 10) {
                        dateList.add(record.getInputDateTime().substring(5,16));
                    }
                    if (StringUtils.isNotBlank(record.getRxNo())) {
                        rxNoList.add(record.getRxNo());
                    }
                    if (StringUtils.isNotBlank(record.getAmount())) {
                        Double amount=Double.parseDouble(record.getAmount())*100;
                        String price=(BigDecimal.valueOf(amount)).toString();
                        int index=price.indexOf(".");
                        record.setAmount(price.substring(0,index));
                    }
                    record.setSpecialControlFlag(specialControlFlag);
                    record.setDosage(getPrettyNumber(record.getDosage()));
                    record.setDosageperTime(getPrettyNumber(record.getDosageperTime()));
                    recordList.add(record);
                }
            }
            response.setRxList(new ArrayList(new HashSet(rxNoList)));
            response.setDateList(new ArrayList(new HashSet(dateList)));
            response.setPrescriptionRecord(recordList);

        }else {
            return new ResponseData(StatusCode.OK.getCode(),StatusCode.OK.getMsg(),null);
        }
        long t3=System.currentTimeMillis();
        logger.info("业务处理时间------"+(t3-t2));
        return responseData;
    }

    @Override
    public ResponseData newPrescription(PrescriptionRequest request) throws ESBException, SystemException {

        return null;
    }

    @Override
    public ResponseData registerExamin(RegisterExaminRequest request) throws ESBException, SystemException {
        long t1=System.currentTimeMillis();
        logger.info("请求时间------"+t1);
        if (StringUtils.isBlank(request.getRegistrId())){
            return new ResponseData(StatusCode.ARGU_EMPTY.getCode(),StatusCode.ARGU_EMPTY.getMsg(),null);
        }
        ResponseData responseData = this.publicService.query(ESBServiceCode.CLINICALQUERY, request, RegistrExaminRespone.class);
        long t2=System.currentTimeMillis();
        logger.info("ESB响应时间------"+(t2-t1));
        if (responseData==null){
            responseData.setData(null);
        }

        return responseData;
    }

    @Override
    public ResponseData registerInspect(RegisteInspectRequest request) throws ESBException, SystemException {

        return null;
    }

    @Override
    public ResponseData clinicalQuery(ClinicalQueryRequest request) throws ESBException, SystemException {
        long t1=System.currentTimeMillis();
        logger.info("请求时间------"+t1);
        //加入缓存
        String doctorDeptKey = String.format(RedisKeyConstants.CLINICALQUERY_WEEK_TYPE,request.getWeek(), request.getType());
        ClinicalQueryRespone clinicalQueryRespone = (ClinicalQueryRespone) redisTemplate.opsForValue().get(doctorDeptKey);
        long t2=System.currentTimeMillis();
        logger.info("ESB响应时间------"+(t2-t1));
        ResponseData responseData = null;
        if (clinicalQueryRespone == null || clinicalQueryRespone.getClinicalQueryRecord() == null
                || clinicalQueryRespone.getClinicalQueryRecord().size() <= 0) {
            responseData = this.publicService.query(ESBServiceCode.CLINICALQUERY, request, ClinicalQueryRespone.class);
            t2=System.currentTimeMillis();
            logger.info("---ESB门诊查询响应时长---" + (t2 - t1));
            clinicalQueryRespone = (ClinicalQueryRespone) responseData.getData();
            redisTemplate.opsForValue().set(doctorDeptKey, clinicalQueryRespone, 7, TimeUnit.DAYS);
        } else {
            responseData = new ResponseData();
            responseData.setMsg(StatusCode.OK.getMsg());
            responseData.setStatus(StatusCode.OK.getCode());
            responseData.setData(clinicalQueryRespone);
        }
        ClinicalQueryRespone response = (ClinicalQueryRespone) responseData.getData();
        if (response != null) {
            List<ClinicalQueryRecord> clinicalQueryRecords = response.getClinicalQueryRecord();
            List<ClinicalQueryRecord> am = new ArrayList<>();
            List<ClinicalQueryRecord> pm = new ArrayList<>();
            for (ClinicalQueryRecord clinicalQueryRecord : clinicalQueryRecords) {
                if ("上午".equals(clinicalQueryRecord.getTimeName())) {
                    am.add(clinicalQueryRecord);
                } else {
                    pm.add(clinicalQueryRecord);
                }
            }
            if ("1".equals(request.getType()) || "3".equals(request.getType())) {
                Set<String> amDeptIds = new HashSet<>();
                for (ClinicalQueryRecord clinicalQueryRecord : am) {
                    amDeptIds.add(clinicalQueryRecord.getDeptId());
                }
                List<Map<String, Object>> amDepts = new ArrayList<>();
                for (String deptId : amDeptIds) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("deptId", deptId);
                    List<Map<String, Object>> row = new ArrayList<>();
                    for (ClinicalQueryRecord clinicalQueryRecord : am) {
                        if (deptId.equals(clinicalQueryRecord.getDeptId())) {
                            map.put("deptName", clinicalQueryRecord.getDeptName());
                            Map<String, Object> clinicalQueryRecordMap = new HashMap<String, Object>();
                            clinicalQueryRecordMap.put("doctorId", clinicalQueryRecord.getDoctorId());
                            clinicalQueryRecordMap.put("doctorName", clinicalQueryRecord.getDoctorName());
                            clinicalQueryRecordMap.put("regType", clinicalQueryRecord.getRegType());
                            row.add(clinicalQueryRecordMap);
                        }
                    }
                    map.put("doctors", row);
                    amDepts.add(map);
                }
                Set<String> pmDeptIds = new HashSet<>();
                for (ClinicalQueryRecord clinicalQueryRecord : pm) {
                    pmDeptIds.add(clinicalQueryRecord.getDeptId());
                }
                List<Map<String, Object>> pmDepts = new ArrayList<>();
                for (String deptId : pmDeptIds) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("deptId", deptId);
                    List<Map<String, Object>> row = new ArrayList<>();
                    for (ClinicalQueryRecord clinicalQueryRecord : pm) {
                        if (deptId.equals(clinicalQueryRecord.getDeptId())) {
                            map.put("deptName", clinicalQueryRecord.getDeptName());
                            Map<String, Object> clinicalQueryRecordMap = new HashMap<>();
                            clinicalQueryRecordMap.put("doctorId", clinicalQueryRecord.getDoctorId());
                            clinicalQueryRecordMap.put("doctorName", clinicalQueryRecord.getDoctorName());
                            clinicalQueryRecordMap.put("regType", clinicalQueryRecord.getRegType());
                            row.add(clinicalQueryRecordMap);
                        }
                    }
                    map.put("doctors", row);
                    pmDepts.add(map);
                }
                List<Map<String, Object>> list = new ArrayList<>();
                Map<String, Object> amMap = new HashMap<>();
                Map<String, Object> pmMap = new HashMap<>();
                amMap.put("timeId", "2");
                amMap.put("timeName", "上午");
                amMap.put("depts", amDepts);
                pmMap.put("timeId", "4");
                pmMap.put("timeName", "下午");
                pmMap.put("depts", pmDepts);
                list.add(amMap);
                list.add(pmMap);
                Map<String, List> jsonMap = new HashMap<>();
                jsonMap.put("list", list);
                responseData.setData(jsonMap);
            } else {
                Set<String> amParentIds = new HashSet<>();
                for (ClinicalQueryRecord clinicalQueryRecord : am) {
                    amParentIds.add(clinicalQueryRecord.getParentId());
                }
                List<Map<String, Object>> amParents = new ArrayList<>();
                for (String parentId : amParentIds) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("parentId", parentId);
                    List<Map<String, Object>> row = new ArrayList<>();
                    String deptId = "";
                    for (ClinicalQueryRecord clinicalQueryRecord : am) {
                        if (parentId.equals(clinicalQueryRecord.getParentId())) {
                            map.put("parentName", clinicalQueryRecord.getParentName());
                            if ("".equals(deptId) || !(deptId.equals(clinicalQueryRecord.getDeptId()))) {
                                Map<String, Object> clinicalQueryRecordMap = new HashMap<String, Object>();
                                clinicalQueryRecordMap.put("deptId", clinicalQueryRecord.getDeptId());
                                clinicalQueryRecordMap.put("deptName", clinicalQueryRecord.getDeptName());
                                clinicalQueryRecordMap.put("regType", clinicalQueryRecord.getRegType());
                                row.add(clinicalQueryRecordMap);
                            }
                        }
                        deptId = clinicalQueryRecord.getDeptId();
                    }
                    map.put("depts", row);
                    amParents.add(map);
                }
                Set<String> pmParentIds = new HashSet<>();
                for (ClinicalQueryRecord clinicalQueryRecord : pm) {
                    amParentIds.add(clinicalQueryRecord.getParentId());
                }
                List<Map<String, Object>> pmParents = new ArrayList<>();
                for (String parentId : pmParentIds) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("parentId", parentId);
                    List<Map<String, Object>> row = new ArrayList<>();
                    String deptId = "";
                    for (ClinicalQueryRecord clinicalQueryRecord : pm) {
                        if (parentId.equals(clinicalQueryRecord.getParentId())) {
                            map.put("parentName", clinicalQueryRecord.getParentName());
                            if ("".equals(deptId) || !(deptId.equals(clinicalQueryRecord.getDeptId()))) {
                                Map<String, Object> clinicalQueryRecordMap = new HashMap<String, Object>();
                                clinicalQueryRecordMap.put("deptId", clinicalQueryRecord.getDeptId());
                                clinicalQueryRecordMap.put("deptName", clinicalQueryRecord.getDeptName());
                                clinicalQueryRecordMap.put("regType", clinicalQueryRecord.getRegType());
                                row.add(clinicalQueryRecordMap);
                            }
                        }
                        deptId = clinicalQueryRecord.getDeptId();
                    }
                    map.put("depts", row);
                    pmParents.add(map);
                }
                List<Map<String, Object>> list = new ArrayList<>();
                Map<String, Object> amMap = new HashMap<>();
                Map<String, Object> pmMap = new HashMap<>();
                amMap.put("timeId", "2");
                amMap.put("timeName", "上午");
                amMap.put("parents", amParents);
                pmMap.put("timeId", "4");
                pmMap.put("timeName", "下午");
                pmMap.put("parents", pmParents);
                list.add(amMap);
                list.add(pmMap);
                Map<String, List> jsonMap = new HashMap<>();
                jsonMap.put("list", list);
                long t3=System.currentTimeMillis();
                logger.info("---业务处理时间---" + (t2 - t1));
                responseData.setData(jsonMap);
            }
        }
        return responseData;
    }

    @Override
    public ResponseData findDoctorInfo(DoctorQueryRequest request) throws ESBException, SystemException {
        long t1=System.currentTimeMillis();
        logger.info("---请求时间---" + t1);
        //加入缓存
        String doctorDeptKey = String.format(RedisKeyConstants.DOCTORQUERY_DEPT_DOCTORID, request.getDeptId(), request.getDoctorId());
        DoctorQueryResponse doctorInfoResponse = (DoctorQueryResponse) redisTemplate.opsForValue().get(doctorDeptKey);
        ResponseData doctorRepsonseData = null;
        if(doctorInfoResponse == null || doctorInfoResponse.getDoctorQueryRecord() == null
                || doctorInfoResponse.getDoctorQueryRecord().size() <= 0) {
            doctorRepsonseData = publicService.query(ESBServiceCode.DOCTORINFOQUERY, request, DoctorQueryResponse.class);

            doctorInfoResponse = (DoctorQueryResponse) doctorRepsonseData.getData();

            redisTemplate.opsForValue().set(doctorDeptKey, doctorInfoResponse, 2, TimeUnit.HOURS);
        } else {
            doctorRepsonseData = new ResponseData();
            doctorRepsonseData.setData(doctorInfoResponse);
            doctorRepsonseData.setStatus(StatusCode.OK.getCode());
            doctorRepsonseData.setMsg(StatusCode.OK.getMsg());
        }
        long t2=System.currentTimeMillis();;
        logger.info("----ESB响应时间---" + (t2 - t1));
        DoctorQueryResponse doctorQueryResponse = (DoctorQueryResponse) doctorRepsonseData.getData();
        if (doctorQueryResponse!=null) {
            Set<String> titles = new HashSet<>();
            for (DoctorQueryRecord doctorQueryRecord : doctorQueryResponse.getDoctorQueryRecord()
                    ) {
                titles.add(doctorQueryRecord.getTitle());
            }
            List<DoctorQueryRecord> doctorQueryRecordList = new ArrayList<>();
            DoctorInfo doctorInfo = new DoctorInfo();
            for (DoctorQueryRecord doctorQueryRecord : doctorQueryResponse.getDoctorQueryRecord()
                    ) {
                if(checkIsShow(doctorQueryRecord.getTechPostNo())){
                    DoctorQueryRecord doctor = new DoctorQueryRecord();
                    doctorInfo.setDoctorId(Long.valueOf(doctorQueryRecord.getDoctorId()));
                    List<DoctorInfo> doctorInfoList = doctorMapper.select(doctorInfo);
                    if(doctorInfoList.size()>0){
                        doctor.setDeptId(doctorQueryRecord.getDeptId());
                        doctor.setDoctorId(doctorQueryRecord.getDoctorId());
                        doctor.setDoctorName(doctorQueryRecord.getDoctorName());
                        doctor.setTitle(doctorQueryRecord.getTitle());
                        doctor.setTechPostNo(doctorQueryRecord.getTechPostNo());
                        doctor.setImg(doctorInfoList.get(0).getHeadImg());
                        doctorQueryRecordList.add(doctor);
                    }
                }
            }

            List<Map<String, Object>> titlesList = new ArrayList<>();
            for (String title : titles
                    ) {
                Map<String, Object> titleMap = new HashMap<>();
                titleMap.put("title", title);
                List<Map<String, Object>> doctorList = new ArrayList<>();

                for (DoctorQueryRecord doctorQueryRecord : doctorQueryRecordList
                        ) {
                    if (title.equals(doctorQueryRecord.getTitle())) {
                        Map<String, Object> doctorMap = new HashMap<>();
                        doctorMap.put("doctorId", doctorQueryRecord.getDoctorId());
                        doctorMap.put("doctorName", doctorQueryRecord.getDoctorName());
                        doctorMap.put("techPostNo",doctorQueryRecord.getTechPostNo());
                        doctorMap.put("img", doctorQueryRecord.getImg());
                        doctorList.add(doctorMap);
                    }
                }
                titleMap.put("doctors", doctorList);
                titlesList.add(titleMap);
            }
            Map<String, Object> map = new HashMap<>();
            map.put("list", titlesList);
            doctorRepsonseData.setData(map);
        }
        long t3=System.currentTimeMillis();;
        logger.info("---业务逻辑时间---" + (t3 - t2));
        return doctorRepsonseData;
    }

    public boolean checkIsShow(String techPostNo) {
        if ("000".equals(techPostNo)) {
            return true;
        } else if ("001".equals(techPostNo)) {
            return true;
        } else if ("002".equals(techPostNo)) {
            return true;
        } else if ("011".equals(techPostNo)) {
            return true;
        } else if ("012".equals(techPostNo)) {
            return true;
        } else if ("013".equals(techPostNo)) {
            return true;
        } else if ("022".equals(techPostNo)) {
            return true;
        } else if ("023".equals(techPostNo)) {
            return true;
        } else if ("024".equals(techPostNo)) {
            return true;
        } else if ("095".equals(techPostNo)) {
            return true;
        } else if ("096".equals(techPostNo)) {
            return true;
        } else if ("097".equals(techPostNo)) {
            return true;
        } else if ("098".equals(techPostNo)) {
            return true;
        } else if ("099".equals(techPostNo)) {
            return true;
        } else if ("100".equals(techPostNo)) {
            return true;
        } else if ("110".equals(techPostNo)) {
            return true;
        } else if ("111".equals(techPostNo)) {
            return true;
        } else if ("112".equals(techPostNo)) {
            return true;
        } else if ("121".equals(techPostNo)) {
            return true;
        } else if ("122".equals(techPostNo)) {
            return true;
        } else if ("123".equals(techPostNo)) {
            return true;
        } else if ("132".equals(techPostNo)) {
            return true;
        } else if ("133".equals(techPostNo)) {
            return true;
        } else if ("134".equals(techPostNo)) {
            return true;
        } else if ("191".equals(techPostNo)) {
            return true;
        } else if ("192".equals(techPostNo)) {
            return true;
        }
        return false;
    }

    @Override
    public String symptomsList() {
        long t1=System.currentTimeMillis();
        logger.info("----请求时间----"+t1);
        String symptomsListJson = stringRedisTemplate.opsForValue().get(RedisKeyConstants.SYMPTOMSLISTJSON);
        if(symptomsListJson ==null) {
            List list = new ArrayList();
            //1为男性,2为女性
            for (int i = 1; i <= 2; i++) {
                List<Part> symptomsList = symptomMapper.findSymptom(Integer.toString(i));
                for (Part p:symptomsList) {
                    p.setSelectedUrl(picPreAdd + p.getSelectedUrl());
                    p.setUnSelectedUrl(picPreAdd + p.getUnSelectedUrl());
                }
                list.add(symptomsList);
            }
            symptomsListJson = JsonUtils.toJSon(ResponseUtil.getSuccessResultMap(list));
            stringRedisTemplate.opsForValue().set(RedisKeyConstants.SYMPTOMSLISTJSON, symptomsListJson,24, TimeUnit.HOURS);
        }
        long t2=System.currentTimeMillis();
        logger.info("----ESB响应时间----"+(t2-t1));
        return symptomsListJson;
    }

    @Override
    public ResponseData recheckReservation(RecheckReservationRequest recheckReservationRequest) throws ESBException, SystemException {
        ResponseData docInfos = null;
        long t1=System.currentTimeMillis();
        logger.info("---请求时间---" + t1);
        try {
            docInfos = publicService.query(ESBServiceCode.RECHECKRESERVATION,recheckReservationRequest, RecheckDocListResponse.class);


        } catch (SystemException e) {
            e.printStackTrace();
            logger.error("复诊预约接口系统错误",e);
            return getFailResultMapMap();
        } catch (ESBException e) {
            e.printStackTrace();
            logger.error("复诊预约接口ESB错误",e);
            return getFailResultMapMap();
        }
        long t2=System.currentTimeMillis();
        logger.info("---ESB响应时间---" + (t2-t1));
        Map resultMap = new HashMap();
        resultMap.put("doctors", null);
        if(docInfos != null && docInfos.getData() != null){
        RecheckDocListResponse recheckDocListResponse = (RecheckDocListResponse) docInfos.getData();
            if(recheckDocListResponse != null && recheckDocListResponse.getRecheckDocItemResponse() != null){
            List<RecheckDocItemResponse> recheckDocItemResponse = recheckDocListResponse.getRecheckDocItemResponse();
            DoctorInfo doctorInfo = new DoctorInfo();
            for(RecheckDocItemResponse r: recheckDocItemResponse){
                doctorInfo.setDoctorId(Long.valueOf(r.getDoctorId()));
                List<DoctorInfo> doctorInfoList = doctorMapper.select(doctorInfo);
                if(doctorInfoList != null && doctorInfoList.size() != 0){
                        r.setImg(doctorInfoList.get(0).getHeadImg());
                }
            }
            resultMap.put("doctors", recheckDocItemResponse);
            }
        }
        long t3=System.currentTimeMillis();
        logger.info("---业务处理时间---" + (t3-t2));
        return getSuccessResultMap(resultMap);
    }

    @Override
    public ResponseData favoriteDoctors(Long accountId) {
        long t1=System.currentTimeMillis();
       // FavoriteDoctor favoriteDoctor = new FavoriteDoctor();
        List<DoctorInfo> favoriteDoctorList = favoriteDoctorsMapper.findFavoriteDoctor(accountId);
        //过段时间注释该行以检查我的医生不显示的Bug
        favoriteDoctorList.remove(null);
        Map resultMap = new HashMap(8);
        resultMap.put("list", favoriteDoctorList);
        long t3=System.currentTimeMillis();
        logger.info("---业务处理时间---" + (t3-t1));
        return getSuccessResultMap(resultMap);
    }

    @Override
    @Transactional(rollbackFor =Exception.class)
    public ResponseData collectDoctor(FavoriteDoctor favoriteDoctor) {
        long t1=System.currentTimeMillis();
        logger.info("---请求时间---" + t1);
        //Map resultMap = new HashMap(4);
        Map colectMap = new HashMap(8);
        List<FavoriteDoctor> favoriteDoctorList = favoriteDoctorsMapper.select(favoriteDoctor);
        if (favoriteDoctorList != null && favoriteDoctorList.size() >0) {
            //取消关注
            if (favoriteDoctorsMapper.delete(favoriteDoctor) == 1) {
                colectMap.put("collected", false);
                return getSuccessResultMap(colectMap);
            }
        } else {
            //加入关注
            Long id = redisIncrementGenerator.increment(Constants.M_FAVORITEDOCTOR_SEQ, 1);
            favoriteDoctor.setId(id);
            favoriteDoctor.setCreateTime(new Date());
            if (favoriteDoctorsMapper.insert(favoriteDoctor)==1) {
                colectMap.put("collected", true);
                return getSuccessResultMap(colectMap);
            }
        }
        long t2=System.currentTimeMillis();
        logger.info("---业务处理时间---" + (t2-t1));
        return getFailResultMapMap();
    }

    @Override
    public ResponseData isCollection(FavoriteDoctor favoriteDoctor) {
        long t1=System.currentTimeMillis();
        logger.info("---请求时间---" + t1);
        List<FavoriteDoctor> favoriteDoctorList = favoriteDoctorsMapper.select(favoriteDoctor);
        Map resultMap = new HashMap(4);
        if (favoriteDoctorList != null && favoriteDoctorList.size() >0){
            resultMap.put("isCollection", true);
        }else {
            resultMap.put("isCollection", false);
        }
        long t2=System.currentTimeMillis();
        logger.info("---业务处理时间---" + (t2-t1));
        return getSuccessResultMap(resultMap);
    }


    @Override
    @Transactional(rollbackFor =Exception.class)
    public ResponseData addPatient(PatientInfoRequest patientInfoRequest, PatientInfoResponse patientInfoResponse)  {
        long t1=System.currentTimeMillis();
        logger.info("---请求时间---" + t1);
        ResponseData responseData = new ResponseData();
        responseData = getSuccessResultBean(null);
        PatientInfoRecord patientInfo = patientInfoResponse.getPatientInfo();
        Patient p = new Patient();
        p.setIdCard(patientInfo.getUserIdCard());
        List<Patient> patientList = patientMapper.select(p);
        Contacts contacts = new Contacts();

        Long pId = redisIncrementGenerator.increment(Constants.M_PATIENT_SEQ, 1);
        Patient patient = new Patient();

        patient.setId(pId);
        patient.setEsbPatientId(Long.valueOf(patientInfo.getUserHisPatientId()));
        patient.setName(patientInfo.getUserName());
        patient.setSex((patientInfo.getUserGender()+"").contains("M-男") ?"M":"F");
        patient.setIdCard(patientInfo.getUserIdCard());
        patient.setHealthCardNo(patientInfo.getUserJkk());
        patient.setAddress(patientInfo.getUserAddress());
        patient.setCreateTime(new Date());
        patient.setPhone(patientInfo.getUserMobile());
        patient.setPrimaryIndex(patientInfo.getPrimaryIndex());
        //patient.setNeteaseId("p"+patientInfo.getPrimaryIndex());
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            patient.setBirthday(sf.parse(patientInfo.getUserBirthDay()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Long cId = redisIncrementGenerator.increment(Constants.M_CONTACTS_SEQ, 1);
        contacts.setId(cId);
        contacts.setAccountId(Long.valueOf(patientInfoRequest.getAccountId()));
        contacts.setRelationship(patientInfoRequest.getRelationship());
        contacts.setCreateTime(new Date());

        String token =  ("p"+ StringUtils.rightPad(String.valueOf(patient.getEsbPatientId()),6,"0"));
        try {
            if(StringUtils.isNotBlank(patient.getNeteaseId())){
                ResponseData neteaseResponse = netEaseCloudLetterService.registerOrUpdateToken(patient.getNeteaseId(), token, patient.getName());
                if("200".equals(neteaseResponse.getStatus())){
                    patient.setNeteaseToken(token);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //病人表无则插入病人表以及Contacts表,有则去Contacts表看是否需要绑定
        if (patientList == null || patientList.size() == 0) {
            //String token = UUID.randomUUID().toString().replace("-", "");
            patientMapper.insertSelective(patient);
            contacts.setPatientId(pId);
            contactsMapper.insertSelective(contacts);
            responseData.setData(patient);
        }else {
            //当前用户没有绑定当前病人则绑定,否则只更新病人表
            Long patientId = patientList.get(0).getId();
            Contacts c1 = new Contacts();
            c1.setAccountId(Long.valueOf(patientInfoRequest.getAccountId()));
            c1.setPatientId(patientId);
            List<Contacts> contactsList = contactsMapper.select(c1);
            Example example = new Example(Patient.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("esbPatientId",patient.getEsbPatientId());
            if (contactsList != null && contactsList.size() != 0){
                for (Contacts c : contactsList) {
                    if (c.getPatientId().equals(patientId)){
                        //更新病人表
                        patientMapper.updateByExampleSelective(patient,example);
                        patient.setId(c.getPatientId());
                        break;
                    }
                }
                responseData.setStatus("400");
                responseData.setMsg("该患者已添加");
            }else {
                contacts.setPatientId( patientId);
                contactsMapper.insertSelective(contacts);
                patient.setId(patientId);
                responseData.setData(patient);
            }
        }
        long t2=System.currentTimeMillis();
        logger.info("---业务处理时间---" + (t2-t1));
        return responseData;
    }

    @Override
    public String getPatientPrimaryIndex(PatientInfoRequest patientInfoRequest) throws ESBException, SystemException {
        PrimaryIndexQueryPatientListRequest request = new PrimaryIndexQueryPatientListRequest();
        request.setIdentityCardNo(patientInfoRequest.getUserIdCard());
        //request.setPatientCardNo(patientInfoRequest.getUserCardId());
        //request.setPatientName(patientInfoRequest.getUserName());
        //request.setPatientId(patientInfoRequest.getEsbPatientId());
        ResponseData  responseData= publicService.query(ESBServiceCode.PRIMARYINDEXQUERYPATIENTLIST,request, PrimaryIndexQueryPatientListResponse.class);
        PrimaryIndexQueryPatientListResponse response= (PrimaryIndexQueryPatientListResponse) responseData.getData();
        List<PrimaryIndexQueryPatientRecord> patientRecordList=response.getPatientRecord();
        if(patientRecordList != null && patientRecordList.size() >0){
            PrimaryIndexQueryPatientRecord patientInfo = patientRecordList.get(0);
           return patientInfo.getMpiId();
        }
        return null;
    }

    @Override
    public ResponseData selectPatient(Long accountId) {
        long t1=System.currentTimeMillis();
        logger.info("---请求时间---" + t1);
        Contacts contacts = new Contacts();
        contacts.setAccountId(accountId);
        Map resultMap = new HashMap();
        //List<Contacts> contactsList = contactsMapper.select(contacts);
        List<Long> patientIdList = contactsMapper.selectPatientIdById(accountId);
        if(patientIdList != null && patientIdList.size()>0){
            Example example = new Example(Patient.class);
            example.createCriteria().andIn("id",patientIdList);
            List<Patient> patientList = patientMapper.selectByExample(example);
            resultMap.put("list",patientList);
        }else {
            resultMap.put("list",null);
        }
       /* if(contactsList != null && contactsList.size() != 0){
            List<Patient> patientList = new ArrayList<>();
            Patient patient = new Patient();
            for (Contacts c:contactsList ) {
                patient.setId(c.getPatientId());
                List<Patient> pList = patientMapper.select(patient);
                if(pList != null ){
                    patientList.add(pList.get(0));
                }
            }
            resultMap.put("list",patientList);
        }else {
            resultMap.put("list",null);
        }*/
        long t2=System.currentTimeMillis();
        logger.info("---业务处理时间---" + (t2-t1));
        return getSuccessResultMap(resultMap);
    }

    @Override
    @Transactional(rollbackFor =Exception.class)
    public ResponseData removePatient(Long accountId, Long id) {
        long t1=System.currentTimeMillis();
        logger.info("---请求时间---" + t1);
        Contacts contacts = new Contacts();
        contacts.setAccountId(accountId);
        contacts.setPatientId(id);
        int row = contactsMapper.delete(contacts);
        long t2=System.currentTimeMillis();
        logger.info("---业务处理时间---" + (t2-t1));
        if(row == 1){
            return getSuccessResultBean("删除成功");
        }else {
            return  getFailResultBean("删除失败");
        }
    }

    @Override
    public ResponseData registrationDetail(RegistrationDetailRequest request) throws ESBException, SystemException {
        long t1=System.currentTimeMillis();
        logger.info("---请求时间---" + t1);
        ResponseData responseData=publicService.query(ESBServiceCode.REGISTEREDLIST,request, RegistrationDetailRespone.class);
        long t2=System.currentTimeMillis();
        logger.info("---ESB响应时间---" + (t2-t1));
        if (responseData.getData()==null){
            return new ResponseData(StatusCode.OK.getCode(),StatusCode.OK.getMsg(),null);
        }
        long t3=System.currentTimeMillis();
        logger.info("---业务处理时间---" + (t3-t2));
        return responseData ;
    }

    @Override
    public ResponseData lineUp(String patientId) throws ESBException, SystemException {

        long t1=System.currentTimeMillis();
        logger.info("---请求时间---" + t1);
        List<RegisteredRecord> registeredRecordList = registrationMapper.selectLineUpList(Long.valueOf(patientId));
        if(registeredRecordList != null && registeredRecordList.size() != 0 ){
            for(RegisteredRecord registeredRecord : registeredRecordList){
                if(registeredRecord != null && registeredRecord.getOrderId()!= null && registeredRecord.getHisOrderId() != null ){
                    PayMentRequest request = new PayMentRequest();
                    request.setOrderId(registeredRecord.getOrderId());
                    request.setOrderIdHis(registeredRecord.getHisOrderId());
                    request.setOrderType("40");
                    ResponseData responseData=publicService.query(ESBServiceCode.PAYMENT,request, PayMentRespone.class);
                    long t2=System.currentTimeMillis();
                    logger.info("---ESB响应时间---" + (t2-t1));
                    PayMentRespone respone= (PayMentRespone) responseData.getData();
                    if (respone!=null && respone.getResultInfo() != null) {
                        PayMentRecord record = respone.getResultInfo();
                        //取号成功
                        if(record != null && "0".equals(record.getResultCode())){
                            String infoSeq = record.getInfoSeq();
                            String serialNumber = StringUtils.substringBetween(infoSeq,"-","(");
                            String lineUpNumber = StringUtils.substringBetween(infoSeq,"(",")");
                            registeredRecord.setInfoSeq(infoSeq);
                            registeredRecord.setSerialNumber(serialNumber);
                            registeredRecord.setLineUpNumber(lineUpNumber);
                        }
                    }
                }
            }
        }
        long t3=System.currentTimeMillis();
        logger.info("---业务处理时间---" + (t3-t1));
        return ResponseUtil.getSuccessResultBean(registeredRecordList);
    }

    @Override
    public ResponseData registerOrUpdateNeteaseToken(Map<String, String> map) {
        return null;
    }


    @Override
    public ResponseData cancelRegistration(CancelRegisterRequest cancelRegisterRequest) throws ESBException, SystemException {
        long t1=System.currentTimeMillis();
        logger.info("---请求时间---" + t1);
        ResponseData responseData = publicService.query(ESBServiceCode.CANCELREGISTER, cancelRegisterRequest, ResultResponse.class);
        long t2=System.currentTimeMillis();
        logger.info("---ESB响应时间---" + t2);
        if(responseData != null && responseData.getData() != null){
            ResultResponse resultResponse = (ResultResponse) responseData.getData();
            ResultRecord record = resultResponse.getRecord();
            if(record != null && record.getResultCode().equals("0") ){
                //ESB端取消成功,更改Order表状态
                Order order = new Order();
                order.setId(cancelRegisterRequest.getOrderId());
                order.setUpdateTime(new Date());
                order.setOrderStatus("3");
                order.setStatusRemark("主动取消");
                orderMapper.updateByPrimaryKeySelective(order);
            }
        }
        long t3=System.currentTimeMillis();
        logger.info("---业务处理时间---" + (t3-t2));
        return responseData;

    }

    @Override
    public ResponseData inspectionCost(InspectionCostRequest request) throws ESBException, SystemException {
        long t1=System.currentTimeMillis();
        logger.info("---请求时间---" + t1);
        if (StringUtils.isBlank(request.getPatientCardNo())){
            return  new ResponseData(StatusCode.ARGU_EMPTY.getCode(),StatusCode.ARGU_EMPTY.getMsg(),null);
        }
            //根据诊疗卡号查询患者
//            Patient patient=patientMapper.selectByUserJkk(request.getPatientCardNo());
//            if (patient==null){
//                return new ResponseData(StatusCode.ARGU_ERROR.getCode(),StatusCode.ARGU_ERROR.getMsg(),null);
//            }
//            Order order=orderMapper.selectByPrimaryKey(patient.getPatientId());

        if (request.getPatientCardNo().length()<20){
           request.setPatientCardNo( StringUtils.leftPad(request.getPatientCardNo(),20,"0"));

        }
        ResponseData responseData=publicService.query(ESBServiceCode.INSPECTIONCOST,request, InspectionCostRespone.class);
        long t2=System.currentTimeMillis();
        logger.info("---ESB响应时间---" + (t2-t1));
        ArrayList costList=new ArrayList();
        ArrayList itemIdList=new ArrayList();
        ArrayList itemNameList=new ArrayList();
        ArrayList<InspectionCostRecord> itemList=new ArrayList();
        InspectionCostRespone respone= (InspectionCostRespone) responseData.getData();
        if (respone!=null){
            if (respone.getInspectionCostRecord()!=null){
                for (InspectionCostRecord record :respone.getInspectionCostRecord()
                     ) {
                    if (StringUtils.isNotBlank(record.getSourceTypeFlag())&&"6".equals(record.getSourceTypeFlag())&&"0".equals(record.getEmergencyFlag())&&!"3".equals(record.getStatus())||!"4".equals(record.getStatus())){
                        String sourceType = PrescriptionType.getPrescriptionType(record.getSourceTypeFlag());
                        record.setSourceFlag(sourceType);

                        if (StringUtils.isNotBlank(record.getAmount())) {
                            Double amount = Double.parseDouble(record.getAmount()) * 100;
                            BigDecimal prcie = BigDecimal.valueOf(amount);
                            int index = prcie.toString().indexOf(".");
                            costList.add(prcie.toString().substring(0,index));
                            record.setAmount(prcie.toString().substring(0,index));
                        }

                        if ("4".equals(request.getSourceType())){
                            //4 检验报告
                            record.setFuncName(record.getFuncName());
                        }else  if ("5".equals(request.getSourceType())){
                            //4 检验报告 检查报告
                            record.setFuncName(CheckType.getCheckType(record.getFuncType()));
                        }

                        itemIdList.add(record.getItemId());
                        itemNameList.add(record.getItemName());
                        itemList.add(record);
                    }
                }
                respone.setInspectionCostRecord(itemList);
                respone.setItemIdList(itemIdList);
                respone.setItemNameList(itemNameList);
            }

        }
        long t3=System.currentTimeMillis();
        logger.info("---业务处理时间---" + (t3-t2));
        return responseData;
    }

    @Override
    public ResponseData registeredQueue(RegisteredQueueRequest request) throws ESBException, SystemException {
        long t1=System.currentTimeMillis();
        logger.info("---请求时间---" + t1);
        if (StringUtils.isBlank(request.getPatientIds())){
            return new ResponseData(StatusCode.ARGU_ERROR.getCode(), StatusCode.ARGU_ERROR.getMsg(), null);
        }
        ResponseData responseData =publicService.query(ESBServiceCode.REGISTERERECORD,request, RegisteredRecordResponse.class);
        long t2=System.currentTimeMillis();
        logger.info("---ESB响应时间---" + t2);
        RegisteredRecordResponse response= (RegisteredRecordResponse) responseData.getData();
        ArrayList<RegisterdResponseRecord> registList= new ArrayList<>();
        if (response!=null&&response.getDeptRegInfoResponseRecode()!=null){
            for ( RegisterdResponseRecord record : response.getDeptRegInfoResponseRecode()  ) {
                if (StringUtils.isNotBlank(record.getOrderIdHis())&&StringUtils.isNotBlank(record.getOrderId())){
                    String orderIdHis = record.getOrderIdHis();
                    String orderId = record.getOrderId();
                    RegistrationDetailRequest registrRequest=new RegistrationDetailRequest();
                    registrRequest.setOrderId(orderId);
                    registrRequest.setOrderType(String.valueOf(40));
                    Order order=new Order();
                    order.setId(orderId);
                    order=orderMapper.selectByPrimaryKey(order);
                    ResponseData registeredInfo=publicService.query(ESBServiceCode.REGISTEREDLIST,registrRequest, RegistrationDetailRespone.class);
                    if (registeredInfo.getData()!=null) {
                        RegistrationDetailRespone registeredRespone = (RegistrationDetailRespone) registeredInfo.getData();
                        RegistrationDetailRecord registrationrecord=registeredRespone.getRecord();
                        String ESBStatus=  registrationrecord.getOrderStatus();
                        record.setOrderESBStatus(registrationrecord.getOrderStatus());
                        if (StringUtils.isNotBlank(ESBStatus)&&"2".equals(ESBStatus)||"3".equals(ESBStatus)){

                            record.setOrderFlag(order.getOrderStatus());
                            registList.add(record);
                            response.setDeptRegInfoResponseRecode(registList);
                        }
                    }
                }

            }
        }else {
            responseData.setData(null);
        }
        long t3=System.currentTimeMillis();
        logger.info("---业务处理时间---" + (t3-t2));
        return  responseData;
    }

    @Override
    public List<RegisteredRecord> registeredRecord(Map<String,Long> map) {
        Integer pageSize = map.get("pageSize").intValue();
        Integer start = (map.get("pageNum").intValue() -1)*pageSize;
        Integer end = start + pageSize;
        return registrationMapper.selectRegisteredRecordList(map.get("accountId"),start,end);
    }



    @Override
    public ResponseData payMent(PayMentRequest request) throws ESBException, SystemException {
        long t1=System.currentTimeMillis();
        logger.info("---请求时间---" + t1);
        ResponseData responseData=publicService.query(ESBServiceCode.PAYMENT,request, PayMentRespone.class);
        long t2=System.currentTimeMillis();
        logger.info("---ESB响应时间---" + t2);
        PayMentRespone respone= (PayMentRespone) responseData.getData();
        if (respone!=null){
            if (respone.getResultInfo()!=null){
                Integer queueNumber;
                PayMentRecord record=respone.getResultInfo();
                if (StringUtils.isNotBlank(respone.getResultInfo().getInfoSeq())){
                    int registStart=respone.getResultInfo().getInfoSeq().indexOf("(");
                    int registEnd=respone.getResultInfo().getInfoSeq().indexOf(")");
                    if (registStart<registEnd){
                        String num=respone.getResultInfo().getInfoSeq().substring(registStart+1,registEnd);
                        record.setNumber(num);
                        if (record.getQueueSeqNo()!=null){
                            if ("1".equals(record.getQueueSeqNo())){
                                record.setQueueNumber("0");
                            }else {
                                queueNumber = Integer.parseInt(record.getNumber())-Integer.parseInt(record.getQueueSeqNo());
                                record.setQueueNumber(queueNumber.toString());
                            }
                        }
                    }
                    int serialStart=respone.getResultInfo().getInfoSeq().indexOf("-");
                    int serialEnd=respone.getResultInfo().getInfoSeq().indexOf("(");
                    if (serialStart<serialEnd){
                        String serialNumber=  respone.getResultInfo().getInfoSeq().substring(serialStart+1,serialEnd);;
                        record.setSerialNumber(serialNumber);
                    }
                }
            }else {
                return  new ResponseData(StatusCode.OK.getCode(),StatusCode.OK.getMsg(),null);
            }
        }
        long t3=System.currentTimeMillis();
        logger.info("---业务处理时间---" + (t3-t2));
        return responseData;

    }



    public static <T> List<List<T>> averageAssign(List<T> source, int n) {
        List<List<T>> result = new ArrayList<List<T>>();
        int remaider = source.size() % n;  //先计算出余数
        int number = source.size() / n;  //然后是商
        int offset = 0;//偏移量
        for (int i = 0; i < n; i++) {
            List<T> value = null;
            if (remaider > 0) {
                value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
                remaider--;
                offset++;
            } else {
                value = source.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
    }


    /**
     * 把16进制字符串转换成字节数组
     * @param hex
     * @return byte[]
     */
    public byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    private int toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }
    //去除去除无效0
    public static String getPrettyNumber(String number) {
        return BigDecimal.valueOf(Double.parseDouble(number))
                .stripTrailingZeros().toPlainString();
    }
}
