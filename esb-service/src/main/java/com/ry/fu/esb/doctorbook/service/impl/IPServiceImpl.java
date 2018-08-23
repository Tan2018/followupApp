package com.ry.fu.esb.doctorbook.service.impl;

import com.ry.fu.esb.common.config.UrlConfig;
import com.ry.fu.esb.common.enumstatic.ESBServiceCode;
import com.ry.fu.esb.common.enumstatic.StatusCode;
import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.properties.SystemProperties;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.common.seq.RedisIncrementGenerator;
import com.ry.fu.esb.common.service.PublicService;
import com.ry.fu.esb.common.utils.BeanMapUtils;
import com.ry.fu.esb.common.utils.JsonUtils;
import com.ry.fu.esb.common.utils.RestTemplateMethodParamUtil;
import com.ry.fu.esb.common.utils.TimeUtils;
import com.ry.fu.esb.doctorbook.mapper.DoctorLoginMapper;
import com.ry.fu.esb.doctorbook.model.DoctorLoginInfo;
import com.ry.fu.esb.doctorbook.model.request.*;
import com.ry.fu.esb.doctorbook.model.response.*;
import com.ry.fu.esb.doctorbook.service.DoctorBookAppSetService;
import com.ry.fu.esb.doctorbook.service.IPService;
import com.ry.fu.esb.doctorbook.service.PrimeIndexService;
import com.ry.fu.esb.doctorbook.utils.HospitalStatisticsUtils;
import com.ry.fu.esb.doctorbook.utils.inpatientCaseUtils;
import com.ry.fu.esb.instantmessaging.service.NetEaseCloudLetterService;
import com.ry.fu.esb.medicaljournal.mapper.DoctorMapper;
import com.ry.fu.esb.medicaljournal.model.DoctorInfo;
import com.ry.fu.esb.medicaljournal.model.request.DoctorQueryRequest;
import com.ry.fu.esb.medicaljournal.model.request.InspectReportRequest;
import com.ry.fu.esb.medicaljournal.model.request.TestDetailsRequest;
import com.ry.fu.esb.medicaljournal.service.OPService;
import com.ry.fu.esb.medicaljournal.util.InpatientDayStatus;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class IPServiceImpl implements IPService {
    private Logger logger = LoggerFactory.getLogger(IPServiceImpl.class);
    @Autowired
    private PublicService publicService;
    @Autowired
    private OPService opService;
    @Autowired
    private PrimeIndexService prService;
    @Autowired
    private DoctorLoginMapper doctorLoginMapper;
    @Autowired
    private DoctorMapper doctorMapper;

    @Autowired
    private SystemProperties systemProperties;

    @Autowired
    private RedisIncrementGenerator redisIncrementGenerator;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DoctorBookAppSetService doctorBookAppSetService;

    @Autowired
    private UrlConfig urlConfig;
    @Autowired
    private NetEaseCloudLetterService easeCloudLetterService;


    @Value("${followServerHostAndPort}")
    private String followServerHostAndPort;

    @Autowired
    private RestTemplateMethodParamUtil restTemplateMethodParamUtil;

    @Override
    public ResponseData doctorAdvice(DoctorAdviceRequest request) throws ESBException, SystemException {
        long t1 = System.currentTimeMillis();
        if (StringUtils.isBlank(request.getDaType()) && StringUtils.isBlank(request.getInpatientId())) {
            return new ResponseData(StatusCode.ARGU_EMPTY.getCode(), StatusCode.ARGU_EMPTY.getMsg(), null);
        }
            long t2 = System.currentTimeMillis();
            logger.info("==医嘱接口===响应时间t2=" + (t2) + ",---响应时长---" + (t2 - t1));
        ResponseData   responseData=publicService.query(ESBServiceCode.DOCTORADVICE, request, DoctorAdviceResponse.class);
            if(responseData.getData()==null){
                return new ResponseData(StatusCode.ARGU_EMPTY.getCode(), StatusCode.ARGU_EMPTY.getMsg(), null);
            }
            DoctorAdviceResponse response = (DoctorAdviceResponse) responseData.getData();
            if ( response.getDoctorAdviceResponseRecode() != null) {

                for (DoctorAdviceResponseRecode record : response.getDoctorAdviceResponseRecode()
                        ) {
                    if (record.getDoctorAdviceResponseLongdaList() != null) {
                        record.setCount(record.getDoctorAdviceResponseLongdaList().size());
                        for (DoctorAdviceResponseRecodeLongdaList longList : record.getDoctorAdviceResponseLongdaList()
                                ) {
                            String dastartDateTime = longList.getDastartDateTime();
                            if (StringUtils.isNotBlank(dastartDateTime) & dastartDateTime.length() > 16) {
                                String dastartDate = longList.getDastartDateTime().substring(5, 10);
                                String dastartTime = longList.getDastartDateTime().substring(11, 16);
                                longList.setDastartDate(dastartDate);
                                longList.setDastartTime(dastartTime);
                                String startTime = longList.getDastartDateTime();
                                DateTime start = TimeUtils.getSdfTime(startTime);

                            }
                            String dastopDateTime = longList.getDastopDateTime();
                            if (StringUtils.isNotBlank(dastopDateTime) && dastopDateTime.length() > 16) {
                                String dastopDate = longList.getDastopDateTime().substring(5, 10);
                                String dastopTime = longList.getDastopDateTime().substring(11, 16);
                                longList.setDastopDate(dastopDate);
                                longList.setDastopTime(dastopTime);
                            }
                        }
                    }
                    if (record.getDoctorAdviceResponseTempdaList() != null) {
                        record.setCount(record.getDoctorAdviceResponseTempdaList().size());
                        for (DoctorAdviceResponseRecodeTempdaList tempList : record.getDoctorAdviceResponseTempdaList()
                                ) {
                            String dadateTime = tempList.getDadateTime();
                            if (StringUtils.isNotBlank(dadateTime)&&dadateTime.length()>16) {
                                if (StringUtils.isNotBlank(dadateTime) & dadateTime.length() > 16) {
                                    String daDate = tempList.getDadateTime().substring(5, 10);
                                    String daTime = tempList.getDadateTime().substring(11, 16);
                                    tempList.setDadate(daDate);
                                    tempList.setDaTime(daTime);
                                }
                            }
                        }
                    }

                    if (record.getDoctorAdviceResponseOutdaList() != null) {
                        record.setCount(record.getDoctorAdviceResponseOutdaList().size());
                        for (DoctorAdviceResponseRecodeOutdaList outList : record.getDoctorAdviceResponseOutdaList()
                                ) {
                            if (outList != null) {
                                String dadateTime = outList.getDadateTime();
                                if (StringUtils.isNotBlank(dadateTime) & dadateTime.length() > 16) {
                                    String daDate = outList.getDadateTime().substring(5, 10);
                                    String daTime = outList.getDadateTime().substring(11, 16);
                                    outList.setDadate(daDate);
                                    outList.setDaTime(daTime);
                                }
                            }
                        }
                    }
                }

            }

        return responseData;
    }

    @Override
    public ResponseData inspectionReport(InspectionReportRequest request) throws ESBException, SystemException {
        long t1 = System.currentTimeMillis();
        if(StringUtils.isNotBlank(request.getPageNum())&&"1".equals(request.getPageNum())){
            request.setRptBegin(new DateTime().minusMonths(12).toString().substring(0,10));
            request.setRptBegin(new DateTime().minusMonths(6).toString().substring(0,10));
        }
        ResponseData responseData = publicService.query(ESBServiceCode.INSPECTIONREPORT, request, InspectionReportResponse.class);
        long t2 = System.currentTimeMillis();
        logger.info("=====响应时间t2=" + (t2) + "---ESB响应时长---" + (t2 - t1));
        ArrayList arrayList = new ArrayList();
        String reportCount="";
        InspectionReportResponse response = (InspectionReportResponse) responseData.getData();
        if (response != null & response.getInspectionReportResponseRecode() != null) {
            Map<String, String> map = new HashMap();
            Iterator iterator = response.getInspectionReportResponseRecode().iterator();
            reportCount= String.valueOf (response.getInspectionReportResponseRecode().size());
            while (iterator.hasNext()) {
                InspectionReportResponseRecode record = (InspectionReportResponseRecode) iterator.next();
                String item = "";
                String listNo = "";
                if (record.getInspectionReportResponseResult() != null && record.getInspectionReportResponseResult().size() > 0) {
                    record.setFlagReport("1");
                    record.setItemCount(String.valueOf(record.getInspectionReportResponseResult().size()));
                }
                if (record.getItemSet() != null) {
                    item = record.getItemSet();
                    arrayList.add(record);
                }
                if (record.getListNo() != null) {
                    listNo = record.getListNo();
                }
                map.put(listNo, item);
            }
        } else {
            return  new ResponseData(StatusCode.ESB_NOT_QUERY_DATA.getCode(),StatusCode.ESB_NOT_QUERY_DATA.getMsg(),null);
        }
        response.setReportCount(reportCount);
        long t3 = System.currentTimeMillis();
        logger.info( "---业务逻辑时长---" + (t3 - t2));
        return responseData;
    }

    @Override
    public ResponseData patientDetails(PatientDetailsRequest request) throws ESBException, SystemException {
        //RequestType 1-影像检查 2-其他检查结果
        long t1 = System.currentTimeMillis();
        if (StringUtils.isBlank(request.getPatientId())){
            return  new ResponseData(StatusCode.ARGU_DEFECT.getCode(),StatusCode.ARGU_DEFECT.getMsg(),null);
        }
        if (StringUtils.isNotBlank(request.getRequestType())&&"1".equals(request.getRequestType())){
            request.setFuncType("1,2,3,99");
        }
        if (StringUtils.isNotBlank(request.getRequestType())&&"2".equals(request.getRequestType())){
            request.setFuncType("4,5,6,7,8,9,10");
        }
        if (StringUtils.isNotBlank(request.getPageNum())&&"1".equals(request.getPageNum())){
            request.setStartDate(new DateTime().minusMonths(12).toString().substring(0,10));
            request.setEndDate(new DateTime().minusMonths(6).toString().substring(0,10));
        }
        ResponseData responseData = publicService.query(ESBServiceCode.PATIENTDETAILS, request, PatientDetailsResponse.class);
        long t2 = System.currentTimeMillis();
        logger.info("=====响应时间t2=" + (t2) + "---响应时长---" + (t2 - t1));
        PatientDetailsResponse response = (PatientDetailsResponse) responseData.getData();

        if (response != null) {
            int type1 = 0;
            int type2 = 0;
            int type3 = 0;
            int type4 = 0;
            int type5 = 0;
            int type6 = 0;
            int type7 = 0;
            int type8 = 0;
            int type9 = 0;
            int type10 = 0;
            int type99 = 0;
            String examId="";
            Map<String, Integer> reportCount = new HashMap<String, Integer>();
            ArrayList<Integer> countArry = new ArrayList<>();
            if (response.getPatientDetailsResponseReportList() != null) {
                for (PatientDetailsResponseReportList list : response.getPatientDetailsResponseReportList()
                        ) {
                    if (StringUtils.isNotBlank(list.getReportPath()) && StringUtils.isNotBlank(list.getReportTime())) {
                        list.setFlagReport("1");
                    }
                    if (StringUtils.isNotBlank(list.getLocalReportPath()) && list.getLocalReportPath().length()>0&&"1".equals(request.getRequestType())) {
                        int index= list.getLocalReportPath().lastIndexOf("=");
                        examId=list.getLocalReportPath().substring(index+1,list.getLocalReportPath().length());
                        list.setExamId(list.getFuncReqId());
                        list.setFuncReqId(examId);

                    }
                    if (list != null) {
                        if (list.getFuncReqId().equals("-1")) {
                            list.setFuncReqId("手工单");
                        }

                        String type = list.getFuncType();
                        if (StringUtils.isNotBlank(type)) {
                            switch (type) {
                                case "1":
                                    type1++;
                                    reportCount.put("MR", type1);
                                    countArry.add(type1);
                                    break;
                                case "2":
                                    type2++;
                                    reportCount.put("X", type2);
                                    countArry.add(type2);
                                    break;
                                case "3":
                                    type3++;
                                    reportCount.put("CT", type3);
                                    countArry.add(type3);
                                    break;
                                case "4":
                                    type4++;
                                    reportCount.put("超声", type4);
                                    countArry.add(type4);
                                    break;
                                case "5":
                                    type5++;
                                    reportCount.put("内镜", type5);
                                    countArry.add(type5);
                                    break;
                                case "6":
                                    type6++;
                                    reportCount.put("核医学", type6);
                                    countArry.add(type6);
                                    break;
                                case "7":
                                    type7++;
                                    reportCount.put("心电图", type7);
                                    countArry.add(type7);
                                    break;
                                case "8":
                                    type8++;
                                    reportCount.put("病理资料", type8);
                                    countArry.add(type8);
                                    break;
                                case "9":
                                    type9++;
                                    reportCount.put("ALO", type9);
                                    countArry.add(type9);
                                    break;
                                case "10":
                                    type10++;
                                    reportCount.put("ALO-随医拍", type10);
                                    countArry.add(type10);
                                    break;
                                case "99":
                                    type99++;
                                    reportCount.put("Other", type99);
                                    countArry.add(type99);
                                    break;
                                default:
                            }
                        }
                    }
                    response.setReportConut(reportCount);
                }
            } else {
                responseData.setData(null);
            }
        }
        long t3 = System.currentTimeMillis();
        logger.info("---业务逻辑时长---" + (t3 - t2));
        return responseData;
    }

    @Override
    public ResponseData imageDetails(ImageDetailsRequest request) throws ESBException, SystemException {
        long t1 = System.currentTimeMillis();
        ResponseData responseData = publicService.query(ESBServiceCode.PATIENTDETAILS, request, PatientDetailsResponse.class);
        long t2 = System.currentTimeMillis();
        logger.info("=====响应时间t2=" + (t2) + "---ESB响应时长---" + (t2 - t1));
        PatientDetailsResponse response = (PatientDetailsResponse) responseData.getData();
        if (response != null) {
            int type1 = 0;
            int type2 = 0;
            int type3 = 0;

            int type99 = 0;
            Map<String, Integer> reportCount = new HashMap<String, Integer>();
            ArrayList<Integer> countArry = new ArrayList<>();
            String examId="";

            if (response.getPatientDetailsResponseReportList() != null) {
                for (PatientDetailsResponseReportList list : response.getPatientDetailsResponseReportList()
                        ) {
                    if (StringUtils.isNotBlank(list.getReportPath()) && StringUtils.isNotBlank(list.getReportTime())) {
                        list.setFlagReport("1");
                    }
                    if (StringUtils.isNotBlank(list.getLocalReportPath()) && list.getLocalReportPath().length()>0) {
                        int index= list.getLocalReportPath().lastIndexOf("=");
                        examId=list.getLocalReportPath().substring(index+1,list.getLocalReportPath().length());
                        list.setExamId(list.getFuncReqId());
                        list.setFuncReqId(examId);

                    }
                    if (list != null) {
                        if (list.getFuncReqId().equals("-1")) {
                            list.setFuncReqId("手工单");
                        }
                        String type = list.getFuncType();
                        if (StringUtils.isNotBlank(type)) {
                            switch (type) {
                                case "1":
                                    type1++;
                                    reportCount.put("MR", type1);
                                    countArry.add(type1);
                                    break;
                                case "2":
                                    type2++;
                                    reportCount.put("X", type2);
                                    countArry.add(type2);
                                    break;
                                case "3":
                                    type3++;
                                    reportCount.put("CT", type3);
                                    countArry.add(type3);
                                    break;
                                case "99":
                                    type99++;
                                    reportCount.put("Other", type99);
                                    countArry.add(type99);
                                    break;
                                default:
                            }
                        }
                    }
                    response.setReportConut(reportCount);
                }
            } else {
                responseData.setData(null);
            }
        }
        long t3 = System.currentTimeMillis();
        logger.info("---业务逻辑时长---" + (t3 - t2));
        return responseData;
    }

    @Override
    public ResponseData otherDetails(OtherDetailsRequest request) throws ESBException, SystemException {
        long t1 = System.currentTimeMillis();
        if (StringUtils.isNotBlank(request.getPageNum())&&"1".equals(request.getPageNum())){
            request.setStartDate(new DateTime().minusMonths(12).toString().substring(0,10));
            request.setEndDate(new DateTime().minusMonths(6).toString().substring(0,10));
        }
        ResponseData responseData = publicService.query(ESBServiceCode.PATIENTDETAILS, request, PatientDetailsResponse.class);
        long t2 = System.currentTimeMillis();
        logger.info("=====响应时间t2=" + (t2) + "---响应时长---" + (t2 - t1));

        PatientDetailsResponse response = (PatientDetailsResponse) responseData.getData();
        if (response != null) {
            int type4 = 0;
            int type5 = 0;
            int type6 = 0;
            int type7 = 0;
            int type8 = 0;
            int type9 = 0;
            int type10 = 0;
            ArrayList<Integer> countArry = new ArrayList<>();
            Map<String, Integer> reportCount = new HashMap<String, Integer>();
            if (response.getPatientDetailsResponseReportList() != null) {
                for (PatientDetailsResponseReportList list : response.getPatientDetailsResponseReportList()
                        ) {
                    if (StringUtils.isNotBlank(list.getReportPath()) && StringUtils.isNotBlank(list.getReportTime())) {
                        list.setFlagReport("1");
                    }
                    if (list != null) {
                        if (list.getFuncReqId().equals("-1")) {
                            list.setFuncReqId("手工单");
                        }
                        String type = list.getFuncType();

                        if (StringUtils.isNotBlank(type)) {
                            switch (type) {
                                case "4":
                                    type4++;
                                    //超声波
                                    reportCount.put("Ultrasonic", type4);
                                    break;
                                case "5":
                                    type5++;
                                    reportCount.put("Endoscope", type5);
                                    break;
                                case "6":
                                    type6++;
                                    reportCount.put("Nuclear", type6);
                                    break;
                                case "7":
                                    type7++;
                                    reportCount.put("Electrocardiogram", type7);
                                    break;
                                case "8":
                                    type8++;
                                    reportCount.put("Pathological", type8);
                                    break;
                                case "9":
                                    type9++;
                                    reportCount.put("ALO", type9);
                                    break;
                                case "10":
                                    type10++;
                                    reportCount.put("ALO-Photo", type10);
                                    break;
                                default:
                            }
                        }
                    }

                    response.setReportConut(reportCount);
                }
            } else {
                responseData.setData(null);
            }
        }
        long t3 = System.currentTimeMillis();
        logger.info("=====响应时间t2=" + (t3) + "---业务逻辑时长---" + (t3 - t2));
        return responseData;
    }

    @Override
    public ResponseData courseOfDiseaseRecord(CourseOfDiseaseRequest request) throws ESBException, SystemException {
        long t1 = System.currentTimeMillis();
        ResponseData responseData = publicService.query(ESBServiceCode.COURSEOFDISASERECORD, request, CourseOfDiseaseResponse.class);
        long t2 = System.currentTimeMillis();
        logger.info("=====响应时间t2=" + (t2) + "---响应时长---" + (t2 - t1));
        CourseOfDiseaseResponse response = (CourseOfDiseaseResponse) responseData.getData();
        ArrayList<String> aryDates = new ArrayList<String>();
        if (response != null && response.getCourseOfDiseaseResponseRecode() != null) {
            for (CourseOfDiseaseResponseRecord record : response.getCourseOfDiseaseResponseRecode()) {
                String reportUrl = record.getReportUrl();
                if (reportUrl != null) {
                    record.setReportUrl(reportUrl);
                }
                    String durDate = record.getDurationTime();
                    if (durDate != null & durDate.length() > 10) {
                        String year = durDate.substring(2, 4);
                        String month = durDate.substring(5, 7);
                        String day = durDate.substring(8, 10);
                        durDate = year + "/" + month + "/" + day;
                    }
                    aryDates.add(durDate);
                    record.setAryDate(aryDates);
            }
        } else {
            responseData.setData(null);
        }
        long t3 = System.currentTimeMillis();
        logger.info("=====响应时间t2=" + (t3) + "---业务逻辑时长---" + (t3 - t2));
        return responseData;
    }

    @Override
    public ResponseData operationRecord(OperationRecordRequest request) throws ESBException, SystemException {
        long t1 = System.currentTimeMillis();
        ResponseData responseData = publicService.query(ESBServiceCode.OPERATIONRECORD, request, OperationRecordResponse.class);
        long t2 = System.currentTimeMillis();
        if (responseData==null){
            return  new ResponseData(StatusCode.ESB_NOT_QUERY_DATA.getCode(),StatusCode.ESB_NOT_QUERY_DATA.getMsg(),null);
        }
        logger.info("=====响应时间t2=" + (t2) + "---响应时长---" + (t2 - t1));
        OperationRecordResponse response = (OperationRecordResponse) responseData.getData();
        if (response != null && response.getOperationRecordResponseRecode() != null) {
            for (OperationRecordResponseRecode record : response.getOperationRecordResponseRecode()) {
                ArrayList<String> aryDates = new ArrayList<>();
                ArrayList<String> aryPdfs = new ArrayList<>();
                if (record != null) {
                    String reportUrl = record.getReportUrl();
                    if (reportUrl != null) {
                        record.setReportUrl(reportUrl);
                    }
                    String opeDate = record.getOperationTime();
                    if (opeDate != null && opeDate.length() > 10) {
                        String year = opeDate.substring(2, 4);
                        String month = opeDate.substring(5, 7);
                        String day = opeDate.substring(8, 10);
                        opeDate = year + "/" + month + "/" + day;
                        aryDates.add(opeDate);
                    }
                    aryPdfs.add(reportUrl);
                    record.setAryDate(aryDates);
                    record.setAryPdf(aryPdfs);
                }
            }
        } else {
           return  new ResponseData(StatusCode.ESB_NOT_QUERY_DATA.getCode(),StatusCode.ESB_NOT_QUERY_DATA.getMsg(),null);
        }
        long t3 = System.currentTimeMillis();
        logger.info("=====响应时间t2=" + (t3) + "---业务逻辑时长---" + (t3 - t2));
        return responseData;
    }

    @Override
    public ResponseData hospitalizationRecord(HospitalizationRecordRequest request) throws ESBException, ParseException, SystemException {
        long t1 = System.currentTimeMillis();
        ResponseData responseData = publicService.query(ESBServiceCode.HOSPITALIZATIONRECORD, request, HospitalizationRecordResponse.class);
        long t2 = System.currentTimeMillis();
        logger.info("=====响应时间t2=" + (t2) + "---响应时长---" + (t2 - t1));
        HospitalizationRecordResponse response = (HospitalizationRecordResponse) responseData.getData();
        ArrayList<String> aryDates = new ArrayList<String>();
        ArrayList<String> preliminaryDiagnosis = new ArrayList<String>();
        if (response != null && response.getHospitalizationRecordDetailResponse() != null) {
            for (HospitalizationRecordResponseRecode record : response.getHospitalizationRecordDetailResponse()) {
                if (record != null) {
                    String reportUrl = record.getReportUrl();
                    if (reportUrl != null) {
                        record.setReportUrl(reportUrl);
                    }
                    if (record.getHospitalizationRecordResponseDiagnoseList() != null) {
                        for (int i = 0; i < record.getHospitalizationRecordResponseDiagnoseList().size(); i++) {
                            String disDate = record.getHospitalizationRecordResponseDiagnoseList().get(i).getDiagnoseTime();
                            String type = record.getHospitalizationRecordResponseDiagnoseList().get(i).getType();
                            if (disDate != null) {
                                String year = disDate.substring(2, 4);
                                String month = disDate.substring(5, 7);
                                String day = disDate.substring(8, 10);
                                disDate = year + "/" + month + "/" + day;
                                aryDates.add(disDate);
                            }
                            //入院诊断
                            if (StringUtils.isNoneBlank(type) && type.equals("1")) {
                                String diagnosis = record.getHospitalizationRecordResponseDiagnoseList().get(i).getDiseaseName();

                                preliminaryDiagnosis.add(diagnosis);
                            }
                        }
                    }
                    record.setAryDate(aryDates);
                    record.setPreliminaryDiagnosis(preliminaryDiagnosis);
                }
            }
        } else {
            responseData.setData(null);
        }
        long t3 = System.currentTimeMillis();
        logger.info("=====响应时间t2=" + (t3) + "---业务逻辑时长---" + (t3 - t2));
        return responseData;
    }

    @Override
    public ResponseData inpatientInfo(InpatientInfoRequest request) throws ESBException, SystemException {
        long t1 = System.currentTimeMillis();
        ResponseData responseData = publicService.query(ESBServiceCode.INPATIENTINFO, request, InpatientInfoResponse.class);
        long t2 = System.currentTimeMillis();
        logger.info("=====响应时间t2=" + (t2) + "---响应时长---" + (t2 - t1));
        InpatientInfoResponse response = (InpatientInfoResponse) responseData.getData();
        if (response.getInpatientInfoDetailResponse()==null){
            return  new ResponseData(StatusCode.ESB_NOT_QUERY_DATA.getCode(),StatusCode.OK.getMsg(),null);
        }
        ArrayList<InpatientInfoResponseRecord> patientList=response.getInpatientInfoDetailResponse();
        Iterator<InpatientInfoResponseRecord> iterator=patientList.iterator();
        if (response != null && response.getInpatientInfoDetailResponse() != null) {
            String nurseFlag = "";
            //过滤转科患者和无管床医生患者
            while (iterator.hasNext()){
                InpatientInfoResponseRecord patient=iterator.next();
                if ("1".equals(patient.getChangeDeptFlag())||StringUtils.isBlank(patient.getResponsibleDoctor())){
                    iterator.remove();
                }
            }
            for (InpatientInfoResponseRecord record : response.getInpatientInfoDetailResponse()) {
                DateTime birthday = TimeUtils.getSdfDate(record.getBirthday());
//                Date birthday=TimeUtils.parseDate(record.getBirthday());
                //计算年龄
                if (birthday != null) {
                    DateTime now = new DateTime();
                    String strAge = TimeUtils.getAge(birthday, now);
//                    String strAge=TimeUtils.countAge(birthday);
                    record.setAge(strAge);
                }

                if (StringUtils.isNotBlank(record.getLcljFlag()) && "1".equals(record.getLcljFlag())) {
                    record.setPatientNameFlag("1");
                    record.setPatientName(record.getPatientName());
                    record.setPatientFlag("路径");
                } else if (StringUtils.isNotBlank(record.getChangeDeptFlag()) && "1".equals(record.getChangeDeptFlag())) {
                    record.setPatientNameFlag("2");
                    record.setPatientFlag("转科");
                }
                DateTime operationTime;
                DateTime behospitalTime;
                behospitalTime = TimeUtils.getSdfTime(record.getInDate());
                if (behospitalTime != null) {
                    int inpatientDay = TimeUtils.getDays(behospitalTime.withMillisOfDay(0), TimeUtils.getCurrentTime().withMillisOfDay(0));
                    if (inpatientDay == 0) {
                        inpatientDay = 1;
                    }

                    record.setInpatientDayStatus(InpatientDayStatus.getInpatientDayStatus(inpatientDay));

                    //护理等级一级或者四级
                    if ("1".equals(record.getNurseFlag()) || "4".equals(record.getNurseFlag())) {
                        record.setPatientStatus("0");
                    } else {
                        //新增执行医嘱时间
                        if (StringUtils.isNotBlank(record.getDaInputDateTime())) {
                            DateTime daInputDateTime = TimeUtils.getSdfTime(record.getDaInputDateTime());
                            int dateFlag = TimeUtils.getHours(new DateTime().withMillisOfDay(0), daInputDateTime);
                            if (0 == dateFlag) {
                                record.setDaInputDate(record.getDaInputDateTime().substring(5, 10));
                                record.setPatientStatus("1");
                            }
                        } else {
                            if ("1".equals(String.valueOf(inpatientDay))) {
                                record.setPatientStatus("2");
                            } else {
                                record.setPatientStatus("4");
                            }
                        }
                    }
                    if (record.getOperationTime() != null) {
                        //手术时间
                        DateTime operTime = TimeUtils.getSdfTime(record.getOperationTime());
                    }
                    //术后天数
                    if (record.getOperationTime() != null) {
                        if (record.getOperationTime() != null) {
                            operationTime = TimeUtils.getSdfTime(record.getOperationTime());
                            int count = 1;
                            if (StringUtils.isBlank(record.getOperationTimes())) {
                                count = 1;
                            } else {
                                count = Integer.parseInt(record.getIpTimes());
                            }
                            int postDay = TimeUtils.getDays(operationTime.withMillisOfDay(0), TimeUtils.getCurDate().withMillisOfDay(0));
                            if (postDay == 0) {
                                postDay = 1;
                            }
                            if (postDay > 0) {
                                if ("5".equals(record.getIpFlag())) {
                                    String hospitalDay = "已办理出院";
                                    record.setPostoperativeDay(hospitalDay);
                                } else {
                                    String postoperativeDay = "第" + count + "次手术后：" + postDay + "天";
                                    record.setPostoperativeDay(postoperativeDay);
                                }
                            } else {
                                if (record.getInDate() != null) {
                                    behospitalTime = TimeUtils.getSdfTime(record.getInDate());
                                    inpatientDay = TimeUtils.getDays(behospitalTime.withMillisOfDay(0), TimeUtils.getCurDate().withMillisOfDay(0));
                                    if (inpatientDay == 0) {
                                        inpatientDay = 1;
                                    }
                                    if ("5".equals(record.getIpFlag())) {
                                        String hospitalDay = "已办理出院";
                                        record.setPostoperativeDay(hospitalDay);

                                    } else {
                                        String hospitalDay = "住院第：" + inpatientDay + "天";
                                        record.setPostoperativeDay(hospitalDay);
                                    }
                                }
                            }
                        }
                    } else {
                        //住院天数
                        if (record.getInDate() != null && record.getOperationTime() == null) {
                            behospitalTime = TimeUtils.getSdfTime(record.getInDate());
                            if (behospitalTime != null) {
                                inpatientDay = TimeUtils.getDays(behospitalTime.withMillisOfDay(0), TimeUtils.getCurDate().withMillisOfDay(0));
                                if (inpatientDay == 0) {
                                    inpatientDay = 1;
                                }
                                String hospitalDay = "住院第：" + inpatientDay + "天";
                                record.setPostoperativeDay(hospitalDay);
                            }
                        }
                    }
                }
                if (record.getPatientDiagnos() != null) {
                    ArrayList<String> diagnosisArry = new ArrayList<>();
                    StringBuilder strdiagnosis = new StringBuilder();
                    for (InpatientInfoDiagnose diagnose : record.getPatientDiagnos()
                            ) {
                        //mode:1初步诊断         diagnoseType:1入院诊断
                        if ("1".equals(diagnose.getMode()) && "1".equals(diagnose.getDiagnoseType())) {
                            diagnosisArry.add(diagnose.getDescription());
                        }
                    }
                    if (diagnosisArry != null) {
                        //入院诊断去重
                        ArrayList diagnosisNew = new ArrayList(new HashSet(diagnosisArry));
                        record.setPreliminaryDiagnosis(diagnosisNew);
                        //入院诊断拼接字符串
                        for (Object sb : diagnosisNew
                                ) {
                            strdiagnosis.append(sb);
                            strdiagnosis.append(",");
                        }
                        if (StringUtils.isBlank(strdiagnosis)){
                            record.setStrDiagnosis("暂无数据");
                            continue;
                        }
                        record.setStrDiagnosis(strdiagnosis.substring(0, strdiagnosis.length()-1));
                    }
                }
            }
            response.setPatientCount(String.valueOf(patientList.size()));
        } else {
            return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), null);
        }
        long t3 = System.currentTimeMillis();
        logger.info("=====响应时间t3=" + (t3) + "---业务处理时长---" + (t3 - t2));
        return responseData;
    }
    @Override
    public ResponseData getImagePath(GetImageRequest request) throws ESBException, SystemException {
        long t1 = System.currentTimeMillis();
        ResponseData responseData=new ResponseData();
        if (StringUtils.isBlank(request.getVerNo())){
            long t2 = System.currentTimeMillis();

             responseData = publicService.query(ESBServiceCode.MEDIACALIMAGE, request, GetImageOldRespone.class);
            logger.info("=====响应时间t2=" + (t2) + "---响应时长---" + (t2 - t1));
            return responseData;
        }else if ("1".equals(request.getVerNo())){
             responseData = publicService.query(ESBServiceCode.MEDIACALIMAGE, request, GetImageRespone.class);
            long t2 = System.currentTimeMillis();
            logger.info("=====响应时间t2=" + (t2) + "---响应时长---" + (t2 - t1));
            long t3 = System.currentTimeMillis();
            logger.info("=====响应时间t2=" + (t3) + "---业务逻辑时长---" + (t3 - t2));
        }else {
            return  new ResponseData(StatusCode.ARGU_ERROR.getCode(),StatusCode.ARGU_ERROR.getMsg(),null);
        }
        if (responseData.getData()==null){
            return  new ResponseData(StatusCode.ESB_NOT_QUERY_DATA.getCode(),StatusCode.ESB_NOT_QUERY_DATA.getMsg(),null);
        }

        return responseData;
    }

    @Override
    public ResponseData inpatientBasic(InpatientBasicRequest request) throws ESBException, SystemException {
        long t1 = System.currentTimeMillis();
        long t2=0;
        if (StringUtils.isBlank(request.getIpseqNoText())&&StringUtils.isBlank(request.getPatientId())){
            return new ResponseData(StatusCode.ARGU_EMPTY.getCode(),StatusCode.ARGU_EMPTY.getMsg(),null);
        }
        ResponseData esbResponseData =new ResponseData();

        //通过住院号查询
        if (StringUtils.isNotBlank(request.getIpseqNoText())){
            if (StringUtils.isNotBlank(request.getIpseqNoText())&&StringUtils.isBlank(request.getIpTimes())){
                InpatientCaseRequest caseRequest= new InpatientCaseRequest();
                caseRequest.setIpseqNoText(request.getIpseqNoText());
                ResponseData caseData = publicService.query(ESBServiceCode.INPATIENTCASE, caseRequest, InpatientCaseResponse.class);
                if (caseData.getData()==null){
                    return  new ResponseData(StatusCode.ESB_NOT_QUERY_DATA.getCode(),StatusCode.ESB_NOT_QUERY_DATA.getMsg(),null);
                }
                InpatientCaseResponse caseResponse= (InpatientCaseResponse) caseData.getData();
                List<InpatientCaseResponseRecord> caseList=new ArrayList<>();
                InpatientCaseResponseRecord  patientCase=  caseResponse.getInpatientCaseDetailResponse().get(0);
                caseList.add(patientCase);
                request.setIpTimes(patientCase.getIpTimes());
            }
             esbResponseData = publicService.query(ESBServiceCode.INPATIENTBASIC, request, InpatientBasicResponse.class);
             t2 = System.currentTimeMillis();
            logger.info( "---住院患者信息响应时长---" + (t2 - t1));
            InpatientBasicResponse response = (InpatientBasicResponse) esbResponseData.getData();
            String strAge=null;
            if(response.getInpatientBasicResponse()==null){
               return  new ResponseData(StatusCode.ESB_NOT_QUERY_DATA.getCode(),StatusCode.ESB_NOT_QUERY_DATA.getMsg(),null);
            }
            for (InpatientBasicResponseRecode record : response.getInpatientBasicResponse()
                 ) {
                if (record!=null){

                    if (StringUtils.isNotBlank(record.getBirthPlace())){
                        String address=record.getBirthPlace().replaceAll("-","");
                        record.setBirthPlace(address);
                    }
                    if (StringUtils.isNumeric(record.getBirthPlace())){
                        record.setBirthPlace("");
                    }
                    if (StringUtils.isNotBlank(record.getAge())&&record.getAge().length()>1){

                        String last = record.getAge().substring(record.getAge().length()-1,record.getAge().length());
                        if (last.equals("Y")) {
                            strAge=record.getAge();
                            strAge = strAge.replace(strAge.charAt(strAge.length() - 1) + "", "岁");
                        } else if (last.equals("M")) {
                            strAge = strAge.replace(strAge.charAt(strAge.length() - 1) + "", "个月");
                        } else {
                            strAge = strAge.replace(strAge.charAt(strAge.length() - 1) + "", "天");
                        }
                        record.setAge(strAge);
                    }
                }
            }
        }else {
            InpatientBasicResponseRecode record =new InpatientBasicResponseRecode();
            OtherQueryRequest otherQueryRequest= new OtherQueryRequest();
            otherQueryRequest.setPatientId(request.getPatientId());
            otherQueryRequest.setPatientFlag("1");
            esbResponseData =publicService.query(ESBServiceCode.OTHERQUERY,otherQueryRequest,OtherQueryResponse.class);
            if(esbResponseData==null){
                return  new ResponseData(StatusCode.ESB_NOT_QUERY_DATA.getCode(),StatusCode.ESB_NOT_QUERY_DATA.getMsg(),null);
            }
            OtherQueryResponse response= (OtherQueryResponse) esbResponseData.getData();
            InpatientBasicResponse basicResponse=new InpatientBasicResponse();
            InpatientBasicResponseRecode  basicRecode= (InpatientBasicResponseRecode) basicResponse.getInpatientBasicResponse();
            if(response.getHospitalPatientsResponseRecord()==null){
                return  new ResponseData(StatusCode.ESB_NOT_QUERY_DATA.getCode(),StatusCode.ESB_NOT_QUERY_DATA.getMsg(),null);
            }
            String strAge="";
            List<InpatientBasicResponseRecode> list= new ArrayList<>();
            for (OtherqueryRecord OtherRecord : response.getHospitalPatientsResponseRecord()
                    ) {
                if (OtherRecord!=null){
                    if (StringUtils.isNotBlank(OtherRecord.getBirthday())&&OtherRecord.getBirthday().length()>1){
                        if (OtherRecord.getBirthday() != null) {
                            DateTime now = new DateTime();

                                strAge = TimeUtils.getAge(TimeUtils.getSdfDate(OtherRecord.getBirthday()), now);


                            OtherRecord.setAge(strAge);
                        }
                        OtherRecord.setAge(strAge);
                        record.setAge(OtherRecord.getAge());
                        record.setSex(OtherRecord.getSexFlag());
                        record.setPatientName(OtherRecord.getPatientName());
                        record.setProfession(OtherRecord.getProfession());
                        record.setCellPhone(OtherRecord.getCellPhone());
                        record.setContactPersion(OtherRecord.getContactPersion());
                        record.setContactPhone(OtherRecord.getContactPhone());
                        record.setMarriage(inpatientCaseUtils.getMaritalType(OtherRecord.getMarriageFlag()));
                        if (StringUtils.isNumeric(OtherRecord.getAddress())){
                            record.setBirthPlace("");
                        }else {
                            record.setBirthPlace(OtherRecord.getAddress());
                        }
                        list.add(record);
                        basicResponse.setInpatientBasicResponse(list);
                    }
                }
            }
            esbResponseData.setData(basicResponse);
        }
        long t3 = System.currentTimeMillis();
        logger.info("=====响应时间t2=" + (t3) + "---业务逻辑时长---" + (t3 - t2));
        return esbResponseData;
    }

    @Override
    public ResponseData inpatientBasicNew(InpatientBasicRequest request) throws ESBException, SystemException, ParseException {
        if (StringUtils.isBlank(request.getIpseqNoText())&&StringUtils.isBlank(request.getPatientId())){
            return  new ResponseData(StatusCode.ARGU_EMPTY.getCode(),StatusCode.ARGU_EMPTY.getMsg(),null);
        }
        ResponseData responseData= new ResponseData();
        // 主索引拿患者信息开始
        PrimaryIndexQueryPatientListRequest priRequest= new PrimaryIndexQueryPatientListRequest();
        priRequest.setRequestFlag("2");
        priRequest.setPatientId(request.getPatientId());
        priRequest.setIpSeqNoText(request.getIpseqNoText());
        responseData= prService.primaryIndexQueryPatientList(priRequest);
        PrimaryIndexQueryPatientListResponse priResponse= (PrimaryIndexQueryPatientListResponse) responseData.getData();
        if(responseData.getData()==null) {
            return new ResponseData(StatusCode.ESB_NOT_QUERY_DATA.getCode(), StatusCode.ESB_NOT_QUERY_DATA.getMsg(), null);
        }
        if(priResponse.getPatientRecord()==null){
            return  new ResponseData(StatusCode.ESB_NOT_QUERY_DATA.getCode(),StatusCode.ESB_NOT_QUERY_DATA.getMsg(),null);
        }else {
            InpatientBasicResponse basicResponse= new InpatientBasicResponse();
            List<InpatientBasicResponseRecode> recodeList=new ArrayList<>();
            InpatientBasicResponseRecode basicRecord=new InpatientBasicResponseRecode();
            basicRecord.setPatientName(priResponse.getPatientRecord().get(0).getPatientName());
            basicRecord.setProfession(priResponse.getPatientRecord().get(0).getProfessionName());
            basicRecord.setContactPhone(priResponse.getPatientRecord().get(0).getPhone());
            basicRecord.setContactPersion(priResponse.getPatientRecord().get(0).getContactPerson());
            if (StringUtils.isNotBlank(priResponse.getPatientRecord().get(0).getCellPhone())){
                basicRecord.setCellPhone(priResponse.getPatientRecord().get(0).getCellPhone());
            }else  if (StringUtils.isNotBlank(priResponse.getPatientRecord().get(0).getPhone())){
                basicRecord.setCellPhone(priResponse.getPatientRecord().get(0).getPhone());
            }
            basicRecord.setAge(priResponse.getPatientRecord().get(0).getAge());
            if (StringUtils.isNotBlank(priResponse.getPatientRecord().get(0).getMarriageFlag())){
                basicRecord.setMarriage(priResponse.getPatientRecord().get(0).getMarriageFlag());
            }else {
                basicRecord.setMarriage("9");
            }
            basicRecord.setNation(priResponse.getPatientRecord().get(0).getRaceName());
            basicRecord.setBirthPlace(priResponse.getPatientRecord().get(0).getNativePlace());
            basicRecord.setProfession(priResponse.getPatientRecord().get(0).getProfessionName());
            basicRecord.setSex(priResponse.getPatientRecord().get(0).getSexFlag());
            basicRecord.setPatientFlag(priResponse.getPatientRecord().get(0).getPatientFlag());
            basicRecord.setIpSeqNoText(priResponse.getPatientRecord().get(0).getIpSeqNoText());
            // 主索引拿患者信息结束
            // 住院患者床号和科室
            if ("0".equals(priResponse.getPatientRecord().get(0).getPatientFlag())||"1".equals(priResponse.getPatientRecord().get(0).getPatientFlag())||"2".equals(priResponse.getPatientRecord().get(0).getPatientFlag())||"3".equals(priResponse.getPatientRecord().get(0).getPatientFlag())||"5".equals(priResponse.getPatientRecord().get(0).getPatientFlag())){
                InPatientRequest inpRequest= new InPatientRequest();
                inpRequest.setPatientId(priResponse.getPatientRecord().get(0).getPatientId());
                ResponseData inpData=patientInfo(inpRequest);
                InPatientResponse inpResponse= (InPatientResponse) inpData.getData();
                if (inpData.getData()==null){
                    basicRecord.setSickBedNo("");
                    basicRecord.setInpatientCategory("");
                }else {
                    InPatientResponseRecord inpRecord=inpResponse.getPatientInfo().get(0);
                    basicRecord.setSickBedNo(inpRecord.getSickBedNo());
                    basicRecord.setInpatientCategory(inpRecord.getDepartmentName());
                    if (StringUtils.isBlank(priResponse.getPatientRecord().get(0).getSexFlag())){
                        basicRecord.setSex(inpRecord.getSexFlag());
                    }
                }

            }
            recodeList.add(basicRecord);
            basicResponse.setInpatientBasicResponse(recodeList);
            responseData.setData(basicResponse);
        }
        return responseData;
    }

    @Override
    public ResponseData inpatientCase(InpatientCaseRequest request) throws ESBException, SystemException {
        long t1 = System.currentTimeMillis();
        ResponseData esbResponseData = publicService.query(ESBServiceCode.INPATIENTCASE, request, InpatientCaseResponse.class);
        long t2 = System.currentTimeMillis();
        logger.info("=====响应时间t2=" + (t2) + "---ESB响应时长---" + (t2 - t1));
        InpatientCaseResponse response = (InpatientCaseResponse) esbResponseData.getData();

        List<InpatientCaseResponseRecord> caseList=new ArrayList<>();
        if (response.getInpatientCaseDetailResponse()!=null){
            InpatientCaseResponseRecord  patientCase=  response.getInpatientCaseDetailResponse().get(0);
            caseList.add(patientCase);
            try {
                  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                  SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
                  Date date=sdf1.parse(patientCase.getOutpatientDate());
                  String date2=sdf1.format(date);
                  long betweenDays=0;
                  Date inpatientDate = sdf.parse(patientCase.getInpatientDate());
                  Date outpatientDate = sdf.parse(patientCase.getOutpatientDate());
                  Calendar cal = Calendar.getInstance();
                  cal.setTime(inpatientDate);
                  long time1 = cal.getTimeInMillis();
                  cal.setTime(outpatientDate);
                  long time2 = cal.getTimeInMillis();

                  Calendar ca2 = Calendar.getInstance();
                  long time3=ca2.getTimeInMillis();
                  ca2.setTime(new Date());
                  if(StringUtils.isNotBlank(patientCase.getInpatientDate())&&StringUtils.isNotBlank(patientCase.getOutpatientDate())&&!("1900".equals(date2))){
                      betweenDays = (time2 - time1) / (1000 * 3600 * 24);
                  }else{
                      betweenDays=(time3 - time1) / (1000 * 3600 * 24);
                      patientCase.setOutpatientDate("");
                  }
                  if(betweenDays==0){
                      betweenDays=1;
                  }
                  patientCase.setInpatientTimes(String.valueOf(betweenDays));
                  patientCase.setMarriage(inpatientCaseUtils.getMaritalType(patientCase.getMarriage()));
                  patientCase.setInpatientWay(inpatientCaseUtils.getInpatientWayType(patientCase.getInpatientWay()));
                  patientCase.setIpFlag(inpatientCaseUtils.getipFlagType(patientCase.getIpFlag()));
                  patientCase.setResidencePostCode(patientCase.getCurrPostCode());
                  patientCase.setWorkPhone(patientCase.getPhone());
                  if("0".equals(patientCase.getBirthWeight())){
                      patientCase.setBirthWeight("/");
                  }
                  if("0".equals(patientCase.getCheckinWeight())){
                      patientCase.setCheckinWeight("/");
                  }
            } catch (Exception e) {
                 return new ResponseData("500", "日期格式转换错误", null);
             }
            esbResponseData.setData(caseList);
            long t3 = System.currentTimeMillis();
            logger.info("=====响应时间t3=" + (t3) + "---业务逻辑时长---" + (t3 - t2));
            return esbResponseData;
        }else {
            return new ResponseData(StatusCode.ESB_NOT_QUERY_DATA.getCode(),StatusCode.ESB_NOT_QUERY_DATA.getMsg(),null);
        }
    }

    @Override
    public ResponseData nurseMeasure(NurseMeasureRequest nurseMeasureRequest) throws ESBException, SystemException {
        long t1 = System.currentTimeMillis();
        ResponseData esbResponseData = publicService.query(ESBServiceCode.NURSEMEASURE, nurseMeasureRequest, NurseMeasureResponse.class);
        long t2 = System.currentTimeMillis();
        logger.info("---ESB响应时长---" + (t2 - t1));
        if (esbResponseData.getData() == null) {
            return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), null);
        }
        long t3 = System.currentTimeMillis();
        logger.info("=====响应时间t3=" + (t3) + "---业务逻辑时长---" + (t3 - t2));
        return esbResponseData;
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData systemLogin(SystemLoginRequest request) throws ESBException, SystemException, ParseException {
        long t1 = System.currentTimeMillis();
        //附属账号登录
        if (request.getDoctorFlag()==null|| StringUtils.isBlank(request.getUserPwd())||StringUtils.isBlank(request.getUserName())) {
           return  new ResponseData(StatusCode.ARGU_DEFECT.getCode(),StatusCode.ARGU_DEFECT.getMsg(),null);
        }
        if ( "1".equals(request.getDoctorFlag())&&StringUtils.isBlank(request.getSubUserName())) {
            return  new ResponseData(StatusCode.ARGU_DEFECT.getCode(),StatusCode.ARGU_DEFECT.getMsg(),null);
        }
        ResponseData esbResponseData = new ResponseData();
        String uuid="";
        String netEaseId="";
        if ("0".equals(request.getDoctorFlag())){
            request.setSubUserName("");
            esbResponseData = publicService.query(ESBServiceCode.SYSTEMLOGIN, request, SystemLoginResponse.class);
        }
        if ("1".equals(request.getDoctorFlag())){
            esbResponseData = publicService.query(ESBServiceCode.SYSTEMLOGIN, request, SystemLoginResponse.class);
        }
        SystemLoginResponse doctorResponse= (SystemLoginResponse) esbResponseData.getData();
        long t2 = System.currentTimeMillis();
        logger.info("---ESB响应时长---" + (t2 - t1));
        if (doctorResponse.getSystemLoginResponseRecode() == null) {
            return  new ResponseData(StatusCode.ESB_USER_NOT_LOGIN.getCode(),StatusCode.ESB_USER_NOT_LOGIN.getMsg(),null);
        }
        long t3 = System.currentTimeMillis();
        logger.info("---业务逻辑时长---" + (t3 - t2));
       List<SystemLoginResponseRecode> doctorRecord=doctorResponse.getSystemLoginResponseRecode();
        DoctorLoginInfo doctorLoginInfo = new DoctorLoginInfo();
        List<DoctorLoginInfo> doctorLoginList=new ArrayList<>();
        String orgId="";
        //通过登录的doctorCode 查询医生信息
        if ("1".equals(request.getDoctorFlag())){
            doctorLoginInfo.setSubDctorCode(doctorRecord.get(0).getSubDoctorCode());
            /*doctorLoginInfo.setSubDctorCode(request.getSubUserName());*/
            doctorLoginInfo.setDoctorFlag(request.getDoctorFlag());
            doctorLoginList= doctorLoginMapper.select(doctorLoginInfo);
        }else {
            doctorLoginInfo.setDoctorCode(doctorRecord.get(0).getDoctorCode());
            doctorLoginInfo.setDoctorFlag(request.getDoctorFlag());
            doctorLoginList= doctorLoginMapper.select(doctorLoginInfo);
            DoctorInfo doctorInfo= new DoctorInfo();
            doctorInfo.setDoctorId(Long.valueOf(doctorRecord.get(0).getDoctorId()));
            List<DoctorInfo> doctorInfoList=doctorMapper.select(doctorInfo);
            if (doctorInfoList.size()==0){
                doctorRecord.get(0).setCountType("0");
            }else {
                if ("1".equals(doctorInfoList.get(0).getCountType())){
                    doctorRecord.get(0).setCountType("1");
                }else {
                    doctorRecord.get(0).setCountType("0");
                }
            }
        }
        //如果职称未空，查询职称
        if (StringUtils.isBlank(doctorRecord.get(0).getJobTitle())&&"0".equals(request.getDoctorFlag())){
            doctorRecord.get(0).setJobTitle(getProcessName(doctorRecord.get(0).getDoctorId()));
            String processName=getProcessName(doctorRecord.get(0).getDoctorId());
            logger.info("医生Id:"+doctorRecord.get(0).getDoctorId()+"，职称:"+processName);
            doctorRecord.get(0).setJobTitle(processName);
            doctorLoginInfo.setProcessName(processName);
        }
        //获取医生所在默认科室
        if (doctorLoginList.size()>0){
            doctorLoginInfo=doctorLoginList.get(0);
            if (doctorRecord.get(0).getSystemLoginResponseRecodeOrgList().size()==0){

            }else {
                //获取判断医生是否属于医务科
                for (SystemLoginResponseRecodeOrgList orgInfo:doctorRecord.get(0).getSystemLoginResponseRecodeOrgList()
                     ) {
                    String departMent=orgInfo.getOrgId();
                    if (StringUtils.isBlank(departMent)){
                        continue;
                    }else {
                        if ("112".equals(departMent)||"2013".equals(departMent)||"2919".equals(departMent)){
                            doctorRecord.get(0).setMedicalDepartment("1");
                            break;
                        }else {
                            doctorRecord.get(0).setMedicalDepartment("0");
                        }
                    }
                }
                orgId = doctorRecord.get(0).getSystemLoginResponseRecodeOrgList().get(0).getOrgId();
            }
            //注册网易云信
            if ("0".equals(request.getDoctorFlag())){
                doctorRecord.get(0).setNetEaseId("d"+doctorRecord.get(0).getDoctorCode());
            }else {
                doctorRecord.get(0).setNetEaseId("d"+doctorRecord.get(0).getSubDoctorCode());
            }

            //TODO 查询到医生
            if (doctorLoginInfo.getNeteaseToken()==null){
                 uuid = UUID.randomUUID().toString().replaceAll("-", "");
                doctorLoginInfo.setNeteaseToken(uuid);
                doctorRecord.get(0).setNetEaseToken(uuid);
                //设置网易云信Id 0-正式医生 1-附属账号
                if ("0".equals(request.getDoctorFlag())){
                    doctorRecord.get(0).setNetEaseId("d"+doctorRecord.get(0).getDoctorCode());
                    try {
                        easeCloudLetterService.registerNetEaseCloudCommunicationId("d"+doctorRecord.get(0).getDoctorCode(),uuid,doctorRecord.get(0).getDoctorName());
                        logger.info("正式账号:"+doctorRecord.get(0).getDoctorCode()+","+doctorRecord.get(0).getDoctorName());
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("网易云信正式账号失败:"+doctorRecord.get(0).getDoctorCode()+","+doctorRecord.get(0).getDoctorName());
                    }
                }else {
                    doctorRecord.get(0).setNetEaseId("d"+doctorRecord.get(0).getSubDoctorCode());
                    try {
                        easeCloudLetterService.registerNetEaseCloudCommunicationId("d"+doctorRecord.get(0).getSubDoctorCode(),uuid,doctorRecord.get(0).getSubDoctorName());
                        logger.info("网易云信附属账号:"+doctorRecord.get(0).getSubDoctorCode()+","+doctorRecord.get(0).getSubDoctorName());
                    } catch (Exception e) {

                        logger.error("网易云信附属账号失败:"+doctorRecord.get(0).getSubDoctorCode()+","+doctorRecord.get(0).getSubDoctorName());
                    }
                }
            }
            doctorRecord.get(0).setNetEaseToken(doctorLoginList.get(0).getNeteaseToken());
            //设置医生信息，然后更新到数据库
            doctorLoginInfo.setSex(doctorRecord.get(0).getGender());
            doctorLoginInfo.setDoctorName(doctorRecord.get(0).getDoctorName());
            doctorLoginInfo.setDoctorId(Long.valueOf(doctorRecord.get(0).getDoctorId()));
            doctorLoginInfo.setDoctorCode(doctorRecord.get(0).getDoctorCode());
            doctorLoginInfo.setDoctorFlag("0");
            doctorRecord.get(0).setDoctorFlag("0");
            doctorLoginInfo.setNeteaseToken(doctorRecord.get(0).getNetEaseToken());
            if ("1".equals(request.getDoctorFlag())){
                doctorLoginInfo.setSubDctorCode(doctorRecord.get(0).getSubDoctorCode());
                doctorLoginInfo.setSubDoctorName(doctorRecord.get(0).getSubDoctorName());
                doctorLoginInfo.setDoctorFlag("1");
                doctorRecord.get(0).setDoctorFlag("1");
                doctorLoginInfo.setProcessName("附属账号");
                doctorRecord.get(0).setJobTitle("附属账号");
            }
            doctorLoginInfo.setMobile(doctorRecord.get(0).getMobile());
            doctorLoginInfo.setTelePhone(doctorRecord.get(0).getTelePhone());
            //时间戳转date
            doctorRecord.get(0).setFirstLoginTime(doctorLoginInfo.getFirstLoginTime());
            doctorRecord.get(0).setLastLoginTime(new Date(request.getTimeStamp()));
            doctorLoginInfo.setAppId(request.getAppId());
           //第一次登陆时间为空
            if (doctorLoginInfo.getFirstLoginTime()==null){
                doctorLoginInfo.setFirstLoginTime(new Date(request.getTimeStamp()));
                doctorLoginInfo.setLastLoginTime(new Date(request.getTimeStamp()));
                doctorRecord.get(0).setFirstLoginTime(new Date(request.getTimeStamp()));
                doctorRecord.get(0).setLastLoginTime(new Date(request.getTimeStamp()));
            }else {
                doctorLoginInfo.setLastLoginTime(new Date(request.getTimeStamp()));
                doctorRecord.get(0).setLastLoginTime(new Date(request.getTimeStamp()));
            }
            doctorRecord.get(0).setAppId(request.getAppId());
            doctorLoginInfo.setAppId(request.getAppId());
            doctorLoginInfo.setOrgId(orgId);
            doctorRecord.get(0).setOrgId(orgId);
            doctorLoginMapper.updateByPrimaryKeySelective(doctorLoginInfo);
        }else {      //TODO  未查询到医生
            try {
                 uuid = UUID.randomUUID().toString().replaceAll("-", "");
                doctorRecord.get(0).setNetEaseToken(uuid);
                doctorLoginInfo.setNeteaseToken(uuid);
                doctorRecord.get(0).setNetEaseToken(netEaseId);
                if ("0".equals(request.getDoctorFlag())){
                    doctorRecord.get(0).setNetEaseId("d"+doctorRecord.get(0).getDoctorCode());
                    try {
                        easeCloudLetterService.registerNetEaseCloudCommunicationId("d"+doctorRecord.get(0).getDoctorCode(),uuid,doctorRecord.get(0).getDoctorName());
                        logger.info("网易云信注册正式账号:"+doctorRecord.get(0).getDoctorCode()+","+doctorRecord.get(0).getDoctorName());
                    } catch (Exception e) {
                        logger.error("网易云信正式账号注册失败:"+doctorRecord.get(0).getDoctorCode()+","+doctorRecord.get(0).getDoctorName());
                    }
                }else {
                    doctorRecord.get(0).setNetEaseId("d"+doctorRecord.get(0).getSubDoctorCode());
                    try {
                        easeCloudLetterService.registerNetEaseCloudCommunicationId("d"+doctorRecord.get(0).getSubDoctorCode(),uuid,doctorRecord.get(0).getSubDoctorName());
                        logger.info("网易云信注册附属账号:"+doctorRecord.get(0).getSubDoctorCode()+","+doctorRecord.get(0).getSubDoctorName());
                    } catch (Exception e) {
                        logger.error("网易云信注册附属账号失败:"+doctorRecord.get(0).getSubDoctorCode()+","+doctorRecord.get(0).getSubDoctorName());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            String doctorInfoKey = BeanMapUtils.getTableSeqKey(doctorLoginInfo);
            Long tableId = redisIncrementGenerator.increment(doctorInfoKey, 1);
            doctorLoginInfo.setId(tableId);
            if (StringUtils.isNotBlank(doctorRecord.get(0).getDoctorId())){
                doctorLoginInfo.setDoctorId(Long.valueOf(doctorRecord.get(0).getDoctorId()));
            }
            if ("1".equals(request.getDoctorFlag())){
                doctorLoginInfo.setSubDctorCode(doctorRecord.get(0).getSubDoctorCode());
                doctorLoginInfo.setSubDoctorName(doctorRecord.get(0).getSubDoctorName());
                doctorLoginInfo.setDoctorFlag("1");
                doctorRecord.get(0).setDoctorFlag("1");
                doctorLoginInfo.setProcessName("附属账号");
                doctorRecord.get(0).setJobTitle("附属账号");
            }else {
                doctorLoginInfo.setDoctorFlag("0");
                doctorRecord.get(0).setDoctorFlag("0");
            }
            if (doctorRecord.get(0).getSystemLoginResponseRecodeOrgList().size()==0){
                doctorRecord.get(0).setOrgId("");
                doctorLoginInfo.setOrgId("");
            }else {
                doctorRecord.get(0).setOrgId(orgId);
                doctorLoginInfo.setOrgId(orgId);
            }
            //更新到数据库
            doctorLoginInfo.setSex(doctorRecord.get(0).getGender());
            doctorLoginInfo.setDoctorCode(doctorRecord.get(0).getDoctorCode());
            doctorLoginInfo.setDoctorId(Long.valueOf(doctorRecord.get(0).getDoctorId()));
            doctorLoginInfo.setDoctorName(doctorRecord.get(0).getDoctorName());
            doctorLoginInfo.setMobile(doctorRecord.get(0).getMobile());
            doctorLoginInfo.setProcessName( doctorRecord.get(0).getJobTitle());
            doctorLoginInfo.setProcessName(getProcessName(doctorRecord.get(0).getDoctorId()));
            doctorLoginInfo.setFirstLoginTime(new Date(request.getTimeStamp()));
            doctorLoginInfo.setAppId(request.getAppId());
            doctorRecord.get(0).setAppId(request.getAppId());
            doctorRecord.get(0).setFirstLoginTime(new Date(request.getTimeStamp()));
            doctorLoginInfo.setLastLoginTime(new Date(request.getTimeStamp()));
            doctorRecord.get(0).setLastLoginTime(new Date(request.getTimeStamp()));
            doctorLoginMapper.insertSelective(doctorLoginInfo);
        }
            doctorResponse.setSystemLoginResponseRecode(doctorRecord);
        return esbResponseData;
    }
/**
*通过doctorId获取职称
*/
    private String getProcessName(String doctorId) throws ESBException, SystemException {
            DoctorQueryRequest doctorRequest=new DoctorQueryRequest();
            if (StringUtils.isBlank(doctorId)){

            }else {
                doctorRequest.setDoctorId(doctorId);
                ResponseData doctorData = doctorBookAppSetService.findDoctorPersonalData(doctorRequest);
                if (doctorData.getData() == null) {
                    return "";
                } else{
                    Map map = (Map) doctorData.getData();
                    return map.get("processName").toString();
                 }
            }
            return "";
        }

    @Override
    public ResponseData hospitalPatients(PatientQueryRequest request) throws ESBException,SystemException {
        long t1 = System.currentTimeMillis();
        String queryValue=request.getQueryValue();
        request.getQueryValue();
        ArrayList<PatientQueryRecord> patientList=new ArrayList<>();
        PatientQueryRequest  patientrequest = new PatientQueryRequest();
        String patientCardNo = StringUtils.leftPad(request.getQueryValue(),20,"0");
        ResponseData responseData = patientQuery2(request);
        long t2 = System.currentTimeMillis();
        logger.info("---ESB响应时长---" + (t2 - t1));
        PatientQueryResponse response = (PatientQueryResponse) responseData.getData();
        if (response!=null&& response.getPatientQueryRecord()!=null){
            for ( PatientQueryRecord record: response.getPatientQueryRecord()
                 ) {
                if (StringUtils.isNotBlank(record.getBirthday())){
                    DateTime birthday = TimeUtils.getSdfDate(record.getBirthday());
                    String age=TimeUtils.getAge(birthday,new DateTime());
                    record.setAge(age);
                }
                if (StringUtils.isNumeric(record.getAddress())){
                    record.setAddress("");
                }
                if (StringUtils.isNotBlank(record.getAddress())){
                    String address = record.getAddress().replaceAll("-", "");
                    record.setAddress(address);
                }
                patientList.add(record);
            }
            for (int i = 0; i < patientList.size()-1; i++) {
                for (int j = patientList.size()-1; j > i; j--) {
                    if (patientList.get(j).getPatientId().equals(patientList.get(i).getPatientId())) {
                        patientList.remove(j);
                    }
                }
            }
        }else {
            return  new ResponseData(StatusCode.ESB_NOT_QUERY_DATA.getCode(),StatusCode.ESB_NOT_QUERY_DATA.getMsg(),null);
        }
            response.setPatientCount(patientList.size());
        long t3 = System.currentTimeMillis();
        logger.info("---业务逻辑时长---" + (t3 - t2));
        ListSort(patientList);
        return  new ResponseData(StatusCode.OK.getCode(),StatusCode.OK.getMsg(),patientList);

    }

    @Override
    public ResponseData otherQueryOld(OtherQueryRequest request) throws ESBException, SystemException {
        long t1 = System.currentTimeMillis();
        if (StringUtils.isNotBlank(request.getPatientName()) && StringUtils.isBlank(request.getIpSeqNoText())) {
            //门诊（只输入姓名查询，住院号为空）
            request.setPatientFlag("1");
            ResponseData responseData = publicService.query(ESBServiceCode.OTHERQUERY, request, OtherQueryResponse.class);
            long t2 = System.currentTimeMillis();
            logger.info("---ESB响应时长---" + (t2 - t1));
            OtherQueryResponse response = (OtherQueryResponse) responseData.getData();
            if (response != null&&response.getHospitalPatientsResponseRecord()!=null) {
                ArrayList<OtherqueryRecord> patientList = response.getHospitalPatientsResponseRecord();
                Iterator<OtherqueryRecord> it = patientList.iterator();
                while (it.hasNext()) {
                    OtherqueryRecord record = it.next();
                    if (record.getPatientCardNo() == null) {
                        it.remove();
                    }
                    if (StringUtils.isNotBlank(record.getBirthday())) {
                        DateTime birthday = TimeUtils.getSdfDate(record.getBirthday());
                        DateTime now = new DateTime();
                        String strAge = TimeUtils.getAge(birthday, now);
                        record.setAge(strAge);
                    }
                }
                long t3 = System.currentTimeMillis();
                logger.info("---业务逻辑时长---" + (t3 - t2));
                return responseData;
            } else {
                //通过名字未查到患者
                return new ResponseData(StatusCode.ESB_NOT_QUERY_DATA.getCode(), StatusCode.ESB_NOT_QUERY_DATA.getMsg(), null);
            }

        } else if (StringUtils.isNotBlank(request.getPatientName()) && StringUtils.isNotBlank(request.getIpSeqNoText())) {
            //住院患者，姓名和住院号查询
            HospitalPatientsRequest patientsRequest = new HospitalPatientsRequest();
            patientsRequest.setIpSeqNoText(request.getIpSeqNoText());
            patientsRequest.setPatientName(request.getPatientName());
            ResponseData patientRespone = publicService.query(ESBServiceCode.HOSPITALPATIENTS, patientsRequest, HospitalPatientsResponse.class);

            long t2 = System.currentTimeMillis();
            logger.info("=====响应时间t2=" + (t2) + "---响应时长---" + (t2 - t1));
            HospitalPatientsResponse response = (HospitalPatientsResponse) patientRespone.getData();

            if (patientRespone.getData() == null) {
                return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), null);
            } else {
                HospitalPatientsResponse hospitalResponse= (HospitalPatientsResponse) patientRespone.getData();

               List<HospitalPatientsResponseRecord> patientList=response.getHospitalPatientsResponseRecord();
                List<HospitalPatientsResponseRecord> newList=new ArrayList<>();
               Iterator<HospitalPatientsResponseRecord> iterator=patientList.iterator();
               while (iterator.hasNext()){
                   HospitalPatientsResponseRecord patient=iterator.next();
                   if ("1900".equals(patient.getOutDate().substring(0,4))){
                       newList.add(patient);
                   }
               }
                hospitalResponse.setHospitalPatientsResponseRecord(newList);
                for (HospitalPatientsResponseRecord record : patientList
                        ) {
                    if (request.getPatientName().equals(record.getPatientName())) {
                        DateTime operationTime;
                        DateTime behospitalTime;
                        DateTime outTime;
                        behospitalTime = TimeUtils.getSdfTime(record.getAdmissionTime());

                        if (behospitalTime != null) {
                            int inpatientDay = TimeUtils.getDays(behospitalTime.withMillisOfDay(0), TimeUtils.getCurrentTime().withMillisOfDay(0));
                            if (inpatientDay == 0) {
                                inpatientDay = 1;
                            }
                            record.setInpatientDayStatus(InpatientDayStatus.getInpatientDayStatus(inpatientDay));
                            if (record.getLastOperationTime() != null) {
                                //手术时间
                                DateTime operTime = TimeUtils.getSdfTime(record.getLastOperationTime());
                            }
                            //计算年龄
                            DateTime birthday = TimeUtils.getSdfDate(record.getBirthday());
                            if (birthday != null) {
                                DateTime now = new DateTime();
                                String strAge = TimeUtils.getAge(birthday, now);
                                record.setAge(strAge);
                            }
                            //术后天数
                            if (record.getLastOperationTime() != null) {
                                if (record.getLastOperationTime() != null) {
                                    operationTime = TimeUtils.getSdfTime(record.getLastOperationTime());
                                    int count;
                                    if (record.getIpTimes() == null) {
                                        count = 1;
                                    } else {
                                        count = Integer.parseInt(record.getIpTimes());
                                    }
                                    int postDay = TimeUtils.getDays(operationTime.withMillisOfDay(0), TimeUtils.getCurDate().withMillisOfDay(0));
                                    if (postDay == 0) {
                                        postDay = 1;
                                    }
                                    if (postDay > 0) {
                                        String postoperativeDay = "第" + count + "次手术后：" + postDay + "天";
                                        record.setPostoperativeDay(postoperativeDay);
                                    } else {
                                        if (record.getAdmissionTime() != null) {
                                            behospitalTime = TimeUtils.getSdfTime(record.getAdmissionTime());
                                            outTime = TimeUtils.getSdfTime(record.getOutDate());

                                            inpatientDay = TimeUtils.getDays(behospitalTime.withMillisOfDay(0), outTime.withMillisOfDay(0));
                                            if (inpatientDay == 0) {
                                                inpatientDay = 1;
                                            }
                                            if (inpatientDay<0){
                                                inpatientDay = TimeUtils.getDays(behospitalTime.withMillisOfDay(0), TimeUtils.getCurDate().withMillisOfDay(0));
                                            }
                                            String hospitalDay = "住院第：" + inpatientDay + "天";
                                            record.setPostoperativeDay(hospitalDay);
                                        }
                                    }
                                }
                            } else {
                                //住院天数
                                if (record.getAdmissionTime() != null && record.getLastOperationTime() == null) {
                                    behospitalTime = TimeUtils.getSdfTime(record.getAdmissionTime());
                                    outTime=TimeUtils.getSdfTime(record.getOutDate());
                                    if (behospitalTime != null&&outTime!=null) {
                                        inpatientDay = TimeUtils.getDays(behospitalTime.withMillisOfDay(0), outTime.withMillisOfDay(0));
                                        if (inpatientDay == 0) {
                                            inpatientDay = 1;
                                        }
                                        if (inpatientDay<0){
                                            inpatientDay = TimeUtils.getDays(behospitalTime.withMillisOfDay(0), TimeUtils.getCurDate().withMillisOfDay(0));
                                        }
                                        String hospitalDay = "住院第：" + inpatientDay + "天";
                                        record.setPostoperativeDay(hospitalDay);
                                    }
                                }
                            }
                        }
                        //入院诊断
                        String ipSeqNoText = record.getIpSeqNoText();
                        String ipTimes = record.getIpTimes();
                        if (StringUtils.isNotBlank(ipSeqNoText) && StringUtils.isNotBlank(record.getIpTimes())) {
                            HospitalizationRecordRequest recordRequest = new HospitalizationRecordRequest();
                            recordRequest.setIpseqNoText(ipSeqNoText);
                            recordRequest.setIpTimes(ipTimes);
                            //入院记录
                            ResponseData recordData = publicService.query(ESBServiceCode.HOSPITALIZATIONRECORD, recordRequest, HospitalizationRecordResponse.class);
//                            ResponseData recordData= ipService.hospitalizationRecord(recordRequest);
                            HospitalizationRecordResponse recordResponse = (HospitalizationRecordResponse) recordData.getData();

                            ArrayList<String> diagnosisArray = new ArrayList<>();
                            if (recordResponse == null) {
                                return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), null);
                            } else {

                                for (HospitalizationRecordResponseRecode hospitalRecord : recordResponse.getHospitalizationRecordDetailResponse()
                                        ) {
                                    if (hospitalRecord.getHospitalizationRecordResponseDiagnoseList() != null) {

                                        for (int i = 0; i < hospitalRecord.getHospitalizationRecordResponseDiagnoseList().size(); i++) {

                                            String type = hospitalRecord.getHospitalizationRecordResponseDiagnoseList().get(i).getType();
                                            if (StringUtils.isNotBlank(type) && type.equals("1")) {

                                                String diagnosis = hospitalRecord.getHospitalizationRecordResponseDiagnoseList().get(i).getDiseaseName();
                                                diagnosisArray.add(diagnosis);
                                            }

                                            if (diagnosisArray != null) {
                                                StringBuffer strDiagnosis = new StringBuffer();
                                                record.setPreliminaryDiagnosis(diagnosisArray);
                                                for (String diagnosis : diagnosisArray
                                                        ) {
                                                    strDiagnosis.append(diagnosis);
                                                    strDiagnosis.append(",");

                                                    record.setStrDiagnosis(strDiagnosis.substring(0, strDiagnosis.length() - 1).toString());
                                                }
                                            }
                                        }
                                    }
                                }
                                record.setPreliminaryDiagnosis(diagnosisArray);
                                long t3 = System.currentTimeMillis();
                                logger.info("=====响应时间t2=" + (t3) + "---业务逻辑时长---" + (t3 - t2));
                                patientRespone.setData(hospitalResponse);
                                return patientRespone;
                            }
                        } else {
                            return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), null);
                        }
                    } else {
                        return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), null);
                    }
                }
            }

        } else {

            return new ResponseData(StatusCode.ARGU_ERROR.getCode(), StatusCode.ARGU_ERROR.getMsg(), null);
        }
        return null;

    }

    @Override
    public ResponseData otherQuery(HospitalPatientsRequest request) throws ESBException, SystemException {
        long t1=System.currentTimeMillis();
        //住院号去查
        ResponseData responseData= new ResponseData();
        List<HospitalPatientsResponseRecord> patientsList=new ArrayList<>();

        if (StringUtils.isNotBlank(request.getIpSeqNoText())&&StringUtils.isBlank(request.getPatientName())){
            //通过住院号去查
             responseData = publicService.query(ESBServiceCode.HOSPITALPATIENTS, request, HospitalPatientsResponse.class);

        }
        if (StringUtils.isNotBlank(request.getPatientName())&&StringUtils.isBlank(request.getIpSeqNoText())){
            //通过姓名去查
            responseData = publicService.query(ESBServiceCode.HOSPITALPATIENTS, request, HospitalPatientsResponse.class);

        }
        if (StringUtils.isNotBlank(request.getPatientName())&&StringUtils.isNotBlank(request.getIpSeqNoText())){
            //通过住院号和姓名去查
            responseData = publicService.query(ESBServiceCode.HOSPITALPATIENTS, request, HospitalPatientsResponse.class);

        }
        responseData = publicService.query(ESBServiceCode.HOSPITALPATIENTS, request, HospitalPatientsResponse.class);
        HospitalPatientsResponse response= (HospitalPatientsResponse) responseData.getData();
            //查询结果为空
        if (response.getHospitalPatientsResponseRecord()==null){
            return  new ResponseData( StatusCode.ESB_NOT_QUERY_DATA.getCode(),StatusCode.ESB_NOT_QUERY_DATA.getMsg(),null);
        }

        response= (HospitalPatientsResponse) responseData.getData();
        List<HospitalPatientsResponseRecord> patientList=response.getHospitalPatientsResponseRecord();
        List<HospitalPatientsResponseRecord> newList=new ArrayList<>();
        Iterator<HospitalPatientsResponseRecord> iterator=patientList.iterator();
        while (iterator.hasNext()){
            HospitalPatientsResponseRecord patient=iterator.next();
            //过滤只查询在院患者
            if ("1900".equals(patient.getOutDate().substring(0,4))){
                newList.add(patient);
            }
        }
        if (newList.size()==0){
            return  new ResponseData(StatusCode.ESB_NOT_QUERY_DATA.getCode(),StatusCode.ESB_NOT_QUERY_DATA.getMsg(),null);
        }
        response.setHospitalPatientsResponseRecord(newList);;
        for (HospitalPatientsResponseRecord record:newList
             ) {
            DateTime operationTime;
            DateTime behospitalTime;
            String hospitalDay="";
            int inpatientDay=0;
            StringBuffer strDiagnosis = new StringBuffer();
            int count=0;
            //##手术天数或住院天数
            //手术患者
            if (StringUtils.isNotBlank(record.getLastOperationTime())){

                if (StringUtils.isBlank(record.getOperationTimes())){
                   count=1;
                }
                count=Integer.valueOf(record.getOperationTimes());
                operationTime=TimeUtils.getSdfTime(record.getLastOperationTime());

                int postDay = TimeUtils.getDays(operationTime.withMillisOfDay(0), TimeUtils.getCurDate().withMillisOfDay(0));
                if (postDay == 0) {
                    postDay = 1;
                }
                //手术天数，大于0， 预期手术时间
                if (postDay > 0) {
                    String postoperativeDay = "第" + count + "次手术后：" + postDay + "天";
                    record.setPostoperativeDay(postoperativeDay);
                } else {
                    if (record.getAdmissionTime() != null) {
                        behospitalTime = TimeUtils.getSdfTime(record.getAdmissionTime());
                        inpatientDay = TimeUtils.getDays(behospitalTime.withMillisOfDay(0), new DateTime().withMillisOfDay(0));
                        if (inpatientDay == 0) {
                            inpatientDay = 1;
                        }
                        if (inpatientDay<0){
                            inpatientDay = TimeUtils.getDays(behospitalTime.withMillisOfDay(0), TimeUtils.getCurDate().withMillisOfDay(0));
                        }
                         hospitalDay = "住院第：" + inpatientDay + "天";
                        record.setBed(record.getSickBedNo());
                        record.setPostoperativeDay(hospitalDay);
                    }
                }
            }else {
            //患者未手术
                if (record.getAdmissionTime() != null) {
                    behospitalTime = TimeUtils.getSdfTime(record.getAdmissionTime());
                    inpatientDay = TimeUtils.getDays(behospitalTime.withMillisOfDay(0), new DateTime().withMillisOfDay(0));
                    if (inpatientDay == 0) {
                        inpatientDay = 1;
                    }
                    if (inpatientDay<0){
                        inpatientDay = TimeUtils.getDays(behospitalTime.withMillisOfDay(0), TimeUtils.getCurDate().withMillisOfDay(0));
                    }
                    hospitalDay = "住院第：" + inpatientDay + "天";
                    record.setPostoperativeDay(hospitalDay);
                }

            }
            //##手术天数或住院天数结束
            //##入院诊断开始
            String ipSeqNoText = record.getIpSeqNoText();
            String ipTimes = record.getIpTimes();
            if (StringUtils.isNotBlank(ipSeqNoText) && StringUtils.isNotBlank(record.getIpTimes())) {
                HospitalizationRecordRequest recordRequest = new HospitalizationRecordRequest();
                recordRequest.setIpseqNoText(ipSeqNoText);
                recordRequest.setIpTimes(ipTimes);
                //入院记录
                ResponseData recordData = publicService.query(ESBServiceCode.HOSPITALIZATIONRECORD, recordRequest, HospitalizationRecordResponse.class);

                HospitalizationRecordResponse recordResponse = (HospitalizationRecordResponse) recordData.getData();
                ArrayList<String> diagnosisArray = new ArrayList<>();
                if (recordResponse==null){
                    if (recordResponse.getHospitalizationRecordDetailResponse()==null){
                    return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), null);
                    }
                }else {
                    //入院记录不为空
                    if (recordResponse.getHospitalizationRecordDetailResponse() == null) {

                    }else {
                        for (HospitalizationRecordResponseRecode hospitalRecord : recordResponse.getHospitalizationRecordDetailResponse()
                                ) {
                            if (hospitalRecord.getHospitalizationRecordResponseDiagnoseList() != null) {

                                for (int i = 0; i < hospitalRecord.getHospitalizationRecordResponseDiagnoseList().size(); i++) {

                                    String type = hospitalRecord.getHospitalizationRecordResponseDiagnoseList().get(i).getType();
                                    if (StringUtils.isNotBlank(type) && type.equals("1")) {

                                        String diagnosis = hospitalRecord.getHospitalizationRecordResponseDiagnoseList().get(i).getDiseaseName();
                                        diagnosisArray.add(diagnosis);
                                    }

                                    if (diagnosisArray != null) {
                                        strDiagnosis = new StringBuffer();
                                        record.setPreliminaryDiagnosis(diagnosisArray);
                                        for (String diagnosis : diagnosisArray
                                                ) {
                                            strDiagnosis.append(diagnosis);
                                            strDiagnosis.append(",");

                                            record.setStrDiagnosis(strDiagnosis.substring(0, strDiagnosis.length() - 1).toString());
                                        }
                                        record.setBed(record.getSickBedNo());
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }

        return  responseData;
    }

    @Override
    public ResponseData patientQuery(PatientQueryRequest request) throws ESBException, SystemException {
        long t1 = System.currentTimeMillis();
        String queryVaule = StringUtils.stripToNull(request.getQueryValue());
        //非空校验
        if (StringUtils.isBlank(queryVaule)&&!StringUtils.isNumeric(request.getQueryValue())) {
            return new ResponseData(StatusCode.ARGU_EMPTY.getCode(),StatusCode.ARGU_EMPTY.getMsg(),null);
        }
        ResponseData responseData=new ResponseData();
        String strRex = "^[\\u4e00-\\u9fa5]+$";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(strRex);
        Matcher matcher = pattern.matcher(queryVaule);
       // 字符串是否与正则表达式相匹配
        boolean rs = matcher.matches();
        String Flag="0";

        ArrayList<PatientQueryRecord> patientList=new ArrayList<>();
        // queryVaule是否是数字

        if (StringUtils.isNumeric(queryVaule)) {

            //判断是否为住院号
            request.setIpSeqNoText(queryVaule);
            ResponseData IpSeqNoTextData = publicService.query(ESBServiceCode.OTHERQUERY, request, PatientQueryResponse.class);
            PatientQueryResponse IpSeqNoTextRespone= (PatientQueryResponse) IpSeqNoTextData.getData();
            if(IpSeqNoTextRespone==null){
                //判断是否诊疗卡号
                request.setPatientCardNo(StringUtils.leftPad(queryVaule,20,"0"));
                request.setPatientFlag("2");
                ResponseData inpatientCardData = publicService.query(ESBServiceCode.OTHERQUERY, request, PatientQueryResponse.class);
                PatientQueryResponse inpatientCardRespone= (PatientQueryResponse) inpatientCardData.getData();
                //查询结果不等空,为诊疗卡号
                if (inpatientCardRespone!=null){
                    patientList.add(inpatientCardRespone.getPatientQueryRecord().get(0));
                    request.setPatientFlag("1");
                    inpatientCardData = publicService.query(ESBServiceCode.OTHERQUERY, request, PatientQueryResponse.class);
                     inpatientCardRespone= (PatientQueryResponse) inpatientCardData.getData();
                     if (inpatientCardRespone==null){
                         //门诊没有数据
                         request.setPatientFlag("1");
                         inpatientCardData = publicService.query(ESBServiceCode.OTHERQUERY, request, PatientQueryResponse.class);
                         inpatientCardRespone.getPatientQueryRecord().get(0).setPatientFlag("3");
                         patientList.add(inpatientCardRespone.getPatientQueryRecord().get(0));
                     }else {
                         inpatientCardRespone.getPatientQueryRecord().get(0).setPatientFlag("1");
                         patientList.add(inpatientCardRespone.getPatientQueryRecord().get(0));
                     }

                   responseData.setData(inpatientCardData);

                }else {
                    //诊疗疗卡号
                    request.setPatientFlag("1");
                    request.setPatientCardNo(StringUtils.leftPad(queryVaule,20,"0"));
                    inpatientCardData = publicService.query(ESBServiceCode.OTHERQUERY, request, PatientQueryResponse.class);
                    Flag="1";
                    inpatientCardRespone= (PatientQueryResponse) inpatientCardData.getData();
                    inpatientCardRespone.getPatientQueryRecord().get(0).setPatientFlag("1");
                    patientList.add(inpatientCardRespone.getPatientQueryRecord().get(0));
                    responseData.setData(patientList);
                }

            }else {
               //是住院号
                IpSeqNoTextRespone.getPatientQueryRecord().get(0).setPatientFlag("2");
                patientList.add(IpSeqNoTextRespone.getPatientQueryRecord().get(0));
                 }

        }else if(rs) {
           //输入名字
           /*
              ************* 诊疗卡号
            */
           //补足20位
           /*
             * patientFlag 1门诊
             * outpatienCardtData 门诊data
             * outpatientCardRespone 门诊respone
            */
           request.setPatientName(queryVaule);
           request.setPatientFlag("1");
           ResponseData outpatienNametData = publicService.query(ESBServiceCode.OTHERQUERY, request, PatientQueryResponse.class);
            long t2 = System.currentTimeMillis();
            logger.info("=====响应时间t2=" + (t2) + "---业务逻辑时长---" + (t2 - t1));
           PatientQueryResponse outpatientNameRespone= (PatientQueryResponse) outpatienNametData.getData();
           if (outpatientNameRespone!=null&&outpatientNameRespone.getPatientQueryRecord()!=null) {
               for (PatientQueryRecord record : outpatientNameRespone.getPatientQueryRecord()
                       ) {
                   //门诊患者
                   Flag="1";
                   record.setPatientFlag("1");
                   patientList.add(record);
               }
           /*
              * patientFlag 2住院
              * outpatienCardtData 住院data
              * outpatientCardRespone 住院respone
            */
           }
//
           request.setPatientFlag("2");
           ResponseData inpatientNameCardData = publicService.query(ESBServiceCode.OTHERQUERY, request, PatientQueryResponse.class);
           PatientQueryResponse inpatientNameRespone= (PatientQueryResponse) inpatientNameCardData.getData();
           if (inpatientNameRespone!=null&&inpatientNameRespone.getPatientQueryRecord()!=null){
               for (PatientQueryRecord record:inpatientNameRespone.getPatientQueryRecord()
                       ) {

                   patientList.add(record);
                   request.setPatientFlag("1");
                   outpatienNametData = publicService.query(ESBServiceCode.OTHERQUERY, request, PatientQueryResponse.class);
                   outpatientNameRespone= (PatientQueryResponse) outpatienNametData.getData();
                   if (outpatientNameRespone!=null&&outpatientNameRespone.getPatientQueryRecord()!=null) {
                       for (PatientQueryRecord outRecord : outpatientNameRespone.getPatientQueryRecord()) {
                           {
                               if ("1".equals(Flag)) {
                                   record.setPatientFlag("3");
                               }
                           }
                           outRecord.setPatientFlag("2");
                           patientList.add(outRecord);

                       }
                   }

               }
           }
            request.setPatientName(queryVaule);
//            request.setPatientFlag("1");
//            ResponseData outpatienNametData = publicService.query(ESBServiceCode.OTHERQUERY, request, PatientQueryResponse.class);
//            PatientQueryResponse outpatientNameRespone= (PatientQueryResponse) outpatienNametData.getData();
            request.setPatientName(queryVaule);
//            request.setPatientFlag("1");
//            ResponseData outpatienNametData = publicService.query(ESBServiceCode.OTHERQUERY, request, PatientQueryResponse.class);
//            PatientQueryResponse outpatientNameRespone= (PatientQueryResponse) outpatienNametData.getData();

        }else {
            return  new ResponseData(StatusCode.ARGU_ERROR.getCode(),StatusCode.ARGU_ERROR.getMsg(),null);
        }

        //List 去重

        for (int i = 0; i < patientList.size()-1; i++) {
            for (int j = patientList.size()-1; j > i; j--) {
                if (patientList.get(j).getPatientId().equals(patientList.get(i).getPatientId())) {
                    patientList.remove(j);
                }
            }
        }
        if (patientList.size()==0){
            return  new ResponseData(StatusCode.ESB_NOT_QUERY_DATA.getCode(),StatusCode.ESB_NOT_QUERY_DATA.getMsg(),null);
        }
        for (PatientQueryRecord record :patientList
             ) {
            if (StringUtils.isNotBlank(record.getPatientId())) {
                InpatientCaseRequest inpatientCaseRequest= new InpatientCaseRequest();
                    inpatientCaseRequest.setInpatientId(record.getPatientId());
                    logger.info("========"+record.getPatientId()+"=========");
                    ResponseData inpatientCaseData = publicService.query(ESBServiceCode.INPATIENTCASE, inpatientCaseRequest, InpatientCaseResponse.class);

                logger.info("========"+inpatientCaseData+"=========");
                    InpatientCaseResponse inpatientCaseResponse= (InpatientCaseResponse) inpatientCaseData.getData();
//                    if (inpatientCaseResponse!=null){
//                        record.setIpSeqNoText(inpatientCaseResponse.getInpatientCaseDetailResponse().get(0).getIpSeqNoText());
//                        patientList.add(record);
//                    }
            }
        }


        return new ResponseData(StatusCode.OK.getCode(),StatusCode.OK.getMsg(),patientList);
    }

    @Override
    public ResponseData patientQuery2(PatientQueryRequest request) throws ESBException, SystemException {
        long t1 = System.currentTimeMillis();
        String queryVaule = request.getQueryValue();
        PatientQueryResponse p4 = new PatientQueryResponse();
        //非空校验
        if (StringUtils.isBlank(queryVaule)) {
            return new ResponseData(StatusCode.ARGU_EMPTY.getCode(), StatusCode.ARGU_EMPTY.getMsg(), null);
        }
        ResponseData responseData = new ResponseData();
        String strRex = "^[\\u4e00-\\u9fa5]+$";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(strRex);
        Matcher matcher = pattern.matcher(queryVaule);
        // 字符串是否与正则表达式相匹配
        boolean rs = matcher.matches();
        String Flag = "0";

        ArrayList<PatientQueryRecord> patientList = new ArrayList<>();
            String patientCardNo = null;
            String ipSeqNoText = null;
            String patientName = null;
        // queryVaule是否是数字
            PatientQueryResponse inpatientCardRespone = new PatientQueryResponse();
        if (StringUtils.isNumeric(queryVaule)) {
            //判断是否为住院号
            request.setIpSeqNoText(queryVaule);
            request.setPatientFlag("2");
            ResponseData IpSeqNoTextData = publicService.query(ESBServiceCode.OTHERQUERY, request, PatientQueryResponse.class);
            PatientQueryResponse IpSeqNoTextRespone = (PatientQueryResponse) IpSeqNoTextData.getData();
            if (IpSeqNoTextRespone == null) {
                //判断是否诊疗卡号
                request.setPatientCardNo(StringUtils.leftPad(queryVaule, 20, "0"));
                request.setPatientFlag("1");
                ResponseData inpatientCardData = publicService.query(ESBServiceCode.OTHERQUERY, request, PatientQueryResponse.class);
                inpatientCardRespone = (PatientQueryResponse) inpatientCardData.getData();

                //查询结果不为空,为诊疗卡号
                if (inpatientCardRespone != null) {
                    //inpatientCardRespone.getPatientQueryRecord().get(0).setPatientFlag("2");
                    patientCardNo = queryVaule;
                    return  inpatientCardData;
                } else {
                    //什么都不是
                   return new ResponseData(StatusCode.OK.getCode(), "您输入的有误，或者没有此患者", null);
                }
            } else {
                //是住院号
                ipSeqNoText = queryVaule;
            }
            return new ResponseData(StatusCode.OK.getCode(), "查询成功", IpSeqNoTextRespone);
        }
        //不是数字
        if (!StringUtils.isNumeric(queryVaule)) {
            patientName = queryVaule;
            //调用接口去查询患者信息
             p4 =  queryPatientInfo(patientCardNo,ipSeqNoText,patientName);
        }

        return   new ResponseData(StatusCode.OK.getCode(), "查询成功", p4);
    }

    private PatientQueryResponse queryPatientInfo(String patientCardNo, String ipSeqNoText, String patientName) throws ESBException, SystemException {
        long t1 = System.currentTimeMillis();
        PatientQueryRequest request = new PatientQueryRequest();
       /* request.setPatientCardNo(StringUtils.leftPad(patientCardNo,20,"0"));
        request.setIpSeqNoText(ipSeqNoText);*/
        request.setPatientName(patientName);

        ArrayList<PatientQueryRecord> patientList=new ArrayList<>();
        PatientQueryResponse patientQueryResp = new PatientQueryResponse();
        for(int i=1; i<3; i++){
            request.setPatientFlag(String.valueOf(i));
            ResponseData inpatientCardData =  publicService.query(ESBServiceCode.OTHERQUERY, request, PatientQueryResponse.class);
            PatientQueryResponse patientQueryResponse = (PatientQueryResponse) inpatientCardData.getData();
            if(patientQueryResponse != null){

                if (patientQueryResponse !=null && patientQueryResponse.getPatientQueryRecord() != null) {
                    for (PatientQueryRecord patientInfo : patientQueryResponse.getPatientQueryRecord()
                            ) {
                        if (StringUtils.isBlank(patientInfo.getPatientFlag())){
                            patientInfo.setPatientFlag("99");
                        }
                        if (StringUtils.isNumeric(patientInfo.getAddress())){
                            patientInfo.setAddress("");
                        }
                       /*if(i==1){
                           patientInfo.setIsOutPatient(true);
                           patientInfo.setPatientFlag("1");
                       }else {
                          patientInfo.setIsInHostpital(true);
                          if ("1".equals(patientInfo.getPatientFlag())){
                              patientInfo.setPatientFlag("3");
                          }
                       }*/
                       patientList.add(patientInfo);
                    }
                }
                patientQueryResp.setPatientQueryRecord(patientList);


            }

        }
        patientQueryResp.setPatientCount(patientList.size());

        return patientQueryResp;
    }
    @Override
    public ResponseData inHospitalRecords(InHospitalInfoRequest request) throws ESBException, SystemException {
        long t1 = System.currentTimeMillis();
        //住院病人病案首页基本信息查询
        InpatientCaseRequest inpatientCaseRequest = new InpatientCaseRequest();
        inpatientCaseRequest.setPatientId(request.getPatientId());
        ResponseData caseData = publicService.query(ESBServiceCode.INPATIENTCASE, inpatientCaseRequest, InpatientCaseResponse.class);
        InpatientCaseResponse caseResponse= (InpatientCaseResponse) caseData.getData();
        ArrayList<InpatientCaseResponseRecord> recordList= (ArrayList<InpatientCaseResponseRecord>) caseResponse.getInpatientCaseDetailResponse();
        List list = new ArrayList();
        if (recordList != null) {
            for (InpatientCaseResponseRecord record : caseResponse.getInpatientCaseDetailResponse()
                    ) {
                String times = record.getIpTimes();
                String startDate = record.getInpatientDate().substring(0, 10);
                Date start = null;
                try {
                    start = DateUtils.parseDate(startDate, "yyyy-MM-dd");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
               String endDate = record.getOutpatientDate().substring(0, 10);
                endDate = "1900-01-01".equals(endDate) ? null : endDate;
                if ("2".equals(request.getPageNo())) {
                    //半年前
                    if (start.compareTo(DateUtils.addMonths(new Date(), -6)) == -1) {
                        list.add(getInspectionRecordByDate(request, times, startDate, endDate));
                    }
                }else {
                    //半年内
                    if (start.compareTo(DateUtils.addMonths(new Date(), -6)) == -1) {
                        break;
                    }
                    list.add(getInspectionRecordByDate(request, times, startDate, endDate));
                }
            }
        }
        return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), list);
    }

    private Map getInspectionRecordByDate(InHospitalInfoRequest request,String times, String startDate, String endDate) throws ESBException, SystemException {
        long t1 = System.currentTimeMillis();
        Map map = new HashMap(4);
        switch (request.getInspectionType()) {
            case "2":
                //影像结果查询
                ImageDetailsRequest imageDetailsRequest = new ImageDetailsRequest();
                imageDetailsRequest.setStartDate(startDate);
                imageDetailsRequest.setEndDate(endDate);
                imageDetailsRequest.setPatientId(request.getPatientId());
                ResponseData imageResponseData = imageDetails(imageDetailsRequest);
                Object imgObj = imageResponseData.getData();
                map.put("imageDetails", imgObj);
                break;

            case "3":
                //其他结果查询
                OtherDetailsRequest otherDetailsRequest = new OtherDetailsRequest();
                otherDetailsRequest.setStartDate(startDate);
                otherDetailsRequest.setEndDate(endDate);
                otherDetailsRequest.setPatientId(request.getPatientId());
                ResponseData otherResponseData = otherDetails(otherDetailsRequest);
                Object otherObj = otherResponseData.getData();
                map.put("otherDetails", otherObj);
                break;

            default:
                //检验报告查询（第三方查询）
                InspectionReportRequest inspectionReportRequest = new InspectionReportRequest();
                inspectionReportRequest.setPatientId(request.getPatientId());
                //inspectionReportRequest.setIpTimes(times);
                inspectionReportRequest.setRptBegin(startDate);
                inspectionReportRequest.setRptEnd(endDate);
                ResponseData inspectionReportData = inspectionReport(inspectionReportRequest);
                Object inspectionReport = inspectionReportData.getData();
                map.put("inspectionReport", inspectionReport);
                break;
        }
        //List list = new ArrayList();
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("times", times);
        //list.add(map);
        System.out.println("第" + times + "次住院,入院日期:" + startDate + " 出院日期:" + endDate);

        return map;
    }

    @Override
    public ResponseData outPatientRecords(InHospitalInfoRequest request) throws SystemException, ESBException {
        long t1 = System.currentTimeMillis();
        //门诊检查报告
        TestDetailsRequest testDetailsRequest = new TestDetailsRequest();
        testDetailsRequest.setPatientId(request.getPatientId());
        testDetailsRequest.setStartDate(request.getStartDate());
        testDetailsRequest.setEndDate(request.getEndDate());
        ResponseData testDetailsData = opService.patientDetails(testDetailsRequest);
        Object  testDetailsObj = testDetailsData.getData();

        //门诊检验报告
        InspectReportRequest inspectReportRequest = new InspectReportRequest();
        inspectReportRequest.setPatientId(request.getPatientId());
        inspectReportRequest.setRptBegin(request.getStartDate());
        inspectReportRequest.setRptEnd(request.getEndDate());
        ResponseData inspectReportData = opService.inspectionReport(inspectReportRequest);
        Object inspectReportObj = inspectReportData.getData();

        Map map = new HashMap(2);
        map.put("testDetails",testDetailsObj);
        map.put("inspectReport",inspectReportObj);

        return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), map);
    }

    @Override
    public ResponseData inspectionRecords(InHospitalInfoRequest request) throws SystemException, ESBException {
        long t1 = System.currentTimeMillis();
        Map map = new HashMap(4);

        switch (request.getInspectionType()) {
            case "2":
                //影像结果查询
                ImageDetailsRequest imageDetailsRequest = new ImageDetailsRequest();
                imageDetailsRequest.setStartDate(request.getStartDate());
                imageDetailsRequest.setEndDate(request.getEndDate());
                imageDetailsRequest.setPatientId(request.getPatientId());
                ResponseData imageResponseData = imageDetails(imageDetailsRequest);
                Object imgObj = imageResponseData.getData();
                map.put("imageDetails", imgObj);
                break;

            case "3":
                //其他结果查询
                OtherDetailsRequest otherDetailsRequest = new OtherDetailsRequest();
                otherDetailsRequest.setStartDate(request.getStartDate());
                otherDetailsRequest.setEndDate(request.getEndDate());
                otherDetailsRequest.setPatientId(request.getPatientId());
                ResponseData otherResponseData = otherDetails(otherDetailsRequest);
                Object otherObj = otherResponseData.getData();
                map.put("otherDetails", otherObj);
                break;

            default:
                //检验报告查询（第三方查询）
                InspectionReportRequest inspectionReportRequest = new InspectionReportRequest();
                inspectionReportRequest.setPatientId(request.getPatientId());
                //inspectionReportRequest.setIpTimes(times);
                inspectionReportRequest.setRptBegin(request.getStartDate());
                inspectionReportRequest.setRptEnd(request.getEndDate());
                ResponseData inspectionReportData = inspectionReport(inspectionReportRequest);
                Object inspectionReport = inspectionReportData.getData();
                map.put("inspectionReport", inspectionReport);
                break;
        }
        return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), map);
    }

    /**
     * 把16进制字符串转换成字节数组
     *
     * @param hex
     * @return byte[]
     */
    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    private static int toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }


    /**
     * 调用随访详情接口
     * @param request
     * @return
     */
    @Override
    public ResponseData showFollowUpDetails(FollowUpDetailQueryRequest request) throws ESBException, SystemException, ParseException {

        Map<String,Object> innerDate = new HashMap<>();
        List<Map<String,Object>> innerDateList = new ArrayList<>();
        //住院信息
        List<Map<String,Object>> inHospitalMsg = new ArrayList<>();
        if (request.getCdrPatientId() == null || StringUtils.isBlank(request.getCdrPatientId())){
            //-------------
            sortAndFormateTimeResult(request, innerDate, innerDateList, inHospitalMsg);
            return  new ResponseData("200","请求成功",innerDate);
        }
        long t1 = System.currentTimeMillis();
        ResponseData esbResponseData = publicService.query(ESBServiceCode.FOLLOWUPDETAIL, request, FollowUpDetailQueryResponse.class);
        FollowUpDetailQueryResponse followUpDetailQueryResponse = (FollowUpDetailQueryResponse)esbResponseData.getData();
        long t2 = System.currentTimeMillis();
        logger.info("=====响应时间t2=" + (t2) + "---响应时长---" + (t2 - t1));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date =null;
         if(followUpDetailQueryResponse != null){
         List<FollowUpDetailQuery> inHospitalDetaiList = followUpDetailQueryResponse.getFollowUpDetailQueryResponse();
         for (FollowUpDetailQuery list:inHospitalDetaiList){
         Map<String,Object> inHospital = new HashMap<>();
         String dateString = null;
         if (list.getIndateTime()!=null){

             dateString = sdf.format(sdf.parse(list.getIndateTime()));
         }
         inHospital.put("inpatientId", list.getInpatientId());//住院信息id
         inHospital.put("ipTimes", list.getIpTimes());//住院次数
         inHospital.put("ipseqnoText", list.getIpseqNoText());//住院号
         inHospital.put("time", dateString);//入院时间
         inHospital.put("selectStatus","2");//类型标识
         inHospital.put("patientId",list.getPatientId());//患者主索引
         inHospital.put("rownums","第"+list.getIpTimes()+"次住院");//住院顺序,esb获取的记录是按照
         inHospitalMsg.add(inHospital);
             }
         }
        //整合数据并排序
        return sortAndFormateTimeResult(request, innerDate, innerDateList, inHospitalMsg);

        }

    private ResponseData sortAndFormateTimeResult(FollowUpDetailQueryRequest request,
                                                  Map<String, Object> innerDate,
                                                  List<Map<String, Object>> innerDateList,
                                                  List<Map<String, Object>> inHospitalMsg)
                                                  throws ESBException, ParseException {

        List<Map<String, Object>> flowInstanceList = getFlowInstanceList(request.getPatientId());

        if (flowInstanceList!=null){
            innerDateList.addAll(flowInstanceList);
        }

        if (inHospitalMsg!=null){
            innerDateList.addAll(inHospitalMsg);
        }

        String sort = request.getSort();
        if (StringUtils.isBlank(sort)){
            sort="1";
        }
        if (sort.equals("1")){
            compareAsc(innerDateList);
        }
        if (sort.equals("2")){
            compareDesc(innerDateList);
        }
        //再把集合的时间格式化
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(innerDateList == null){
            return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), innerDate);
        }
        for(Map<String,Object> responseDate : innerDateList){
            logger.info("{}",innerDateList);
            if (responseDate.get("time")!=null){
                String time = simpleDateFormat.format(simpleDateFormat.parse(responseDate.get("time").toString()));
                responseDate.put("time",time);
            }else{
                responseDate.put("time","");
            }
        }
        innerDate.put("innerDateList",innerDateList);
        return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), innerDate);
    }


    @Override
    public ResponseData patientInfo(InPatientRequest request) throws SystemException, ESBException {
        ResponseData esbResponseData = publicService.query(ESBServiceCode.FOLLOWUPDETAIL, request, InPatientResponse.class);
       if (esbResponseData.getData()==null){
           return  new ResponseData(StatusCode.ESB_NOT_QUERY_DATA.getCode(),StatusCode.ESB_NOT_QUERY_DATA.getMsg(),null);
       }

        return esbResponseData;
    }

    @Override
    public ResponseData hospitalStatistics(HospitalStatisticsRequest request) throws SystemException, ESBException {

        if (StringUtils.isNotBlank(request.getDoctorId())&&"3".equals(request.getCollectType())){
            request.setStartDate(new DateTime().withDayOfMonth(1).toString().substring(0,10));
        }
        ResponseData esbResponseData = publicService.query(ESBServiceCode.HOSPITALSTATISTICS, request, HospitalStatisticsResponse.class);
        if (esbResponseData.getData()==null){
            return new ResponseData(StatusCode.ESB_NOT_QUERY_DATA.getCode(), StatusCode.ESB_NOT_QUERY_DATA.getMsg(), null);
        }
        HospitalStatisticsResponse response= (HospitalStatisticsResponse) esbResponseData.getData();
         //当前日期
         String currentDate=new DateTime().toString().substring(0,10);
         //前一天
         String preDate=new DateTime().minusDays(1).toString().substring(0,10);
        Integer statisticsNum=0;
        List<HospitalStatisticsRecord> statistics= new ArrayList<>();

        for (HospitalStatisticsRecord record:response.getHospitalStatisticsRecord()
             ) {
            String dataTypeDesc = HospitalStatisticsUtils.getDataDesc(record.getDataType());
            record.setDataTypeDesc(dataTypeDesc);
            // 1-高级统计按院区统计
            if ("1".equals(request.getCollectType())) {
                //当天数据
                if (currentDate.equals(record.getDataDate()) && "1".equals(record.getDataType()) || (currentDate.equals(record.getDataDate()) && "2".equals(record.getDataType()))) {
                    if ("1699".equals(request.getHospitalArea())) {
                        if ("1699".equals(record.getDepartmentId())) {

                            statistics.add(record);
                        }
                    } else if ("1106".equals(request.getHospitalArea())) {
                        if ("1106".equals(record.getDepartmentId())) {
                            statistics.add(record);
                        }
                    } else {
                        statistics.add(record);
                    }

                }
                //前一天
                if ((preDate.equals(record.getDataDate()) && "1".equals(record.getDataType())) || (preDate.equals(record.getDataDate()) && "3".equals(record.getDataType()))) {
                    if ("1699".equals(request.getHospitalArea())) {
                        if ("1699".equals(record.getDepartmentId())) {
                            statistics.add(record);
                        }
                    } else if ("1106".equals(request.getHospitalArea())) {
                        if ("1106".equals(record.getDepartmentId())) {
                            statistics.add(record);
                        }
                    } else {
                        statistics.add(record);
                    }
                }
                if ("1".equals(record.getDataType())&&currentDate.equals(record.getDataDate())){
                    record.setStatisticsIndex("1");
                }
                if ("1".equals(record.getDataType())&&preDate.equals(record.getDataDate())){
                    record.setStatisticsIndex("2");
                }
                if ("2".equals(record.getDataType())&&currentDate.equals(record.getDataDate())){
                    record.setStatisticsIndex("3");
                }
                if ("3".equals(record.getDataType())&&preDate.equals(record.getDataDate())){
                    record.setStatisticsIndex("4");
                }
                if (currentDate.equals(record.getDataDate())){
                    record.setIsToday("1");
                }else {
                    record.setIsToday("-1");
                }

            }
            //0-医生统计
            if ("2".equals(request.getDataType()) && StringUtils.isNotBlank(request.getDoctorId())) {
                statisticsNum=Integer.parseInt(record.getDataNum())+statisticsNum;
                statistics.add(record);
            }
            response.setStatisticsNum(statisticsNum.toString());
            response.setHospitalStatisticsRecord(statistics);
        }
        return esbResponseData;
    }

    /**

    /**

         *根据患者年龄排序
         */
        private static void ListSort(List<PatientQueryRecord> list) {
            Collections.sort(list, new Comparator<PatientQueryRecord>() {
                @Override
                public int compare(PatientQueryRecord o1, PatientQueryRecord o2) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    if (StringUtils.isNotBlank(o1.getBirthday())&&StringUtils.isNotBlank(o1.getBirthday())){
                        return 0;
                    }
                    Date dt1 = format.parse(o1.getBirthday());
                    Date dt2 = format.parse(o2.getBirthday());

                    if (dt1.getTime() > dt2.getTime()) {
                        return 1;
                    } else if (dt1.getTime() < dt2.getTime()) {
                        return -1;
                    }  else if((o1.getBirthday()).compareTo(o2.getBirthday())==0){
                        return 0;//此处需要返回0；
                    }
                    else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
                }
            });
        }

    /**
     * @author  leon_zhang
     * @param patientId 随访患者id
     * @return
     * @throws
     * @Desc 调用随访flowup接口获取随访信息
     */
    public List<Map<String, Object>> getFlowInstanceList(Long patientId){

        Map<String,Object> postData = new HashMap<>();
        String timeStamp = System.currentTimeMillis()+"";
        postData.put("patientId",patientId);
        postData.put("timeStamp",timeStamp);

        String postDatas = JsonUtils.toJSon(postData);

        HttpEntity<String> formEntitys = restTemplateMethodParamUtil.getPostRequestParams(postDatas);

        if(formEntitys == null){
            return null;
        }

        HashMap results = restTemplate.postForObject(followServerHostAndPort + urlConfig.getFOLLOW_SHOW_FOLLOWDETAIL_URL(), formEntitys, HashMap.class);
        //随访集合列表
        if(results == null){
            return null;
        }
        HashMap<String, Object> data = (HashMap<String, Object>) results.get("data");
        if(data == null){
            return null;
        }        logger.info("{}", results);

        ArrayList<Map<String, Object>> flowInstanceList =
                (ArrayList<Map<String, Object>>) data.get("flowInstanceList");
        logger.info("{}", flowInstanceList);
        if (flowInstanceList ==null){
            return  null;
        }
        return flowInstanceList;
    }

    private static void compareDesc(List<Map<String, Object>> innerDateList) {
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //生序排序
        Collections.sort(innerDateList, new Comparator<Map<String,Object>>() {
            @Override
            public int compare(Map<String,Object> o1, Map<String,Object> o2) {
                int ret = 0;
                try {
                    //比较两个对象的顺序，如果前者小于、等于或者大于后者，则分别返回-1/0/1

                    if (o1.get("time") == null && o2.get("time") == null)
                    {return 0;}
                    if (o1.get("time") == null||"null".equals(o1.get("time")))
                    {return -1;}
                    if (o2.get("time") == null||"null".equals(o2.get("time")))
                    {return 1;}


                    if(o1.get("time") != null && o2.get("time") != null){
                        ret = df.parse(o2.get("time").toString()).compareTo(df.parse(o1.get("time").toString()));
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return ret;
            }

        });
    }
    //升序排序
    private static void compareAsc(List<Map<String, Object>> innerDateList) {
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //生序排序
        Collections.sort(innerDateList, new Comparator<Map<String,Object>>() {
            @Override
            public int compare(Map<String,Object> o1, Map<String,Object> o2) {
                int ret = 0;
                try {
                    //比较两个对象的顺序，如果前者小于、等于或者大于后者，则分别返回-1/0/1

                    if (o1.get("time") == null && o2.get("time") == null)
                    {return 0;}

                    if (o1.get("time") == null||"null".equals(o1.get("time")))
                    {return -1;}

                    if (o2.get("time") == null||"null".equals(o2.get("time")))
                    { return 1;}

                    if(o1.get("time") != null && o2.get("time") != null){

                        ret = df.parse(o1.get("time").toString()).compareTo(df.parse(o2.get("time").toString()));
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return ret;
            }

        });
    }

}
