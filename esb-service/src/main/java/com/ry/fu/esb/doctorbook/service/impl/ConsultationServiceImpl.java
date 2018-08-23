package com.ry.fu.esb.doctorbook.service.impl;

import com.ry.fu.esb.common.enumstatic.ESBServiceCode;
import com.ry.fu.esb.common.enumstatic.StatusCode;
import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.common.service.PublicService;
import com.ry.fu.esb.doctorbook.model.request.*;
import com.ry.fu.esb.doctorbook.model.response.*;
import com.ry.fu.esb.doctorbook.service.ConsultationService;
import com.ry.fu.esb.doctorbook.service.DoctorBookAppSetService;
import com.ry.fu.esb.medicaljournal.model.request.DoctorQueryRequest;
import com.ry.fu.esb.medicaljournal.model.request.DoctorSearchReq;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/6/27 9:35
 **/
@Service
public class ConsultationServiceImpl implements ConsultationService {

    @Autowired
    private PublicService publicService;

    @Autowired
    private DoctorBookAppSetService doctorBookAppSetService;

    /**
     * 会诊科室查询
     * @param request
     * @return
     * @throws ESBException
     * @throws SystemException
     */
    @Override
    public ResponseData findConsultationDeptInfo(ConsultationDeptRequest request) throws ESBException, SystemException {
        ResponseData responseData = publicService.query(ESBServiceCode.CONSULTATIONDEPT, request, ConsultationDeptResponse.class);
        return responseData;
    }


    /**
     * 会诊医生查询
     * @param request
     * @return
     * @throws ESBException
     * @throws SystemException
     */
    @Override
    public ResponseData findConsultationDoctorInfo(DoctorSearchReq request) throws ESBException, SystemException {
        ResponseData responseData = publicService.query(ESBServiceCode.ALLDOCTORINFOLIST, request, DoctorInfoByNameRecord.class);
        DoctorInfoByNameRecord response=(DoctorInfoByNameRecord)responseData.getData();
        List<Object> list=new ArrayList<>();
        String currPage="1";
        if(response.getRecord().size()<Integer.valueOf(request.getPageSize())){
            currPage="0";
        }
            if (response != null && response.getRecord() != null && response.getRecord().size() > 0) {
                for (DoctorInfoByName doctorInfoByName : response.getRecord()
                        ) {
                    if(!doctorInfoByName.getDoctorId().equals(request.getDoctorId())){
                        Map<String, Object> map = new HashMap<>();
                        DoctorQueryRequest doctorQueryRequest = new DoctorQueryRequest();
                        doctorQueryRequest.setDoctorId(doctorInfoByName.getDoctorId());
                        ResponseData responseData1 = doctorBookAppSetService.findDoctorPersonalData(doctorQueryRequest);
                        Map<String, Object> map1 = (Map) responseData1.getData();
                        map.put("doctorId", doctorInfoByName.getDoctorId());
                        map.put("doctorName", doctorInfoByName.getDoctorName());
                        map.put("deptId", doctorInfoByName.getDeptId());
                        map.put("deptName", doctorInfoByName.getDeptName());
                        if (responseData1.getData() != null) {
                            map.put("title", map1.get("processName"));
                        } else {
                            map.put("title", "");
                        }
                        if(!"1".equals(request.getDoctorType())) {
                            if (map != null && map.get("title").toString().contains("主任") ||
                                    map.get("title").toString().contains("主治") ||
                                    map.get("title").toString().contains("教授") ||
                                    map.get("title").toString().contains("副") ||
                                    map.get("title").toString().contains("院长")) {
                                list.add(map);
                            }
                        }else{
                            list.add(map);
                        }
                    }
                }
            }
        Map<String,Object> map=new HashMap<>();
        map.put("record",list);
        map.put("currPage",currPage);
        responseData.setData(map);
        return responseData;
    }

    /**
     * 保存申请单
     * @param request
     * @return
     * @throws ESBException
     * @throws SystemException
     */
    @Override
    public ResponseData addApplicationFormInfo(ApplicationFormRequest request) throws ESBException, SystemException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date=sdf.format(new Date());
        request.setSubmitTime(date);
        ResponseData responseData = publicService.query(ESBServiceCode.APPLICATIONFORM, request, ApplicationFormResponse.class);
        return responseData;
    }

    /**
     * 医生会诊单查询
     * @param request
     * @return
     * @throws ESBException
     * @throws SystemException
     */
    @Override
    public ResponseData findDoctorConsultationFormInfo(DoctorConsultationFormRequest request) throws ESBException, SystemException {
            ResponseData responseData = publicService.query(ESBServiceCode.DOCTORCONSULTATIONFORM, request, DoctorConsultationFormResponse.class);
            DoctorConsultationFormResponse response=(DoctorConsultationFormResponse)responseData.getData();
            Map<String,Object> map=new HashMap<>();
            ResponseData responseData2=new ResponseData();
            List<DoctorConsultationFormResponseRecord> list=new ArrayList<>();
            List<DoctorConsultationFormResponseRecord> list1=new ArrayList<>();
            if(response!=null&&response.getDoctorConsultationFormResponseRecord()!=null
                    &&response.getDoctorConsultationFormResponseRecord().size()>0){
                List<DoctorConsultationFormResponseRecord> checkDoctorList=response.getDoctorConsultationFormResponseRecord();
                for (int i=0;i<checkDoctorList.size();i++){
                    list.add(checkDoctorList.get(i));
                }
                if (StringUtils.isNotEmpty(request.getCheckDoctorId())) {
                    DoctorConsultationFormRequest request1=new DoctorConsultationFormRequest();
                    request1.setConsDoctorId(request.getCheckDoctorId());
                    ResponseData responseData1 = publicService.query(ESBServiceCode.DOCTORCONSULTATIONFORM, request1, DoctorConsultationFormResponse.class);
                    DoctorConsultationFormResponse response1=(DoctorConsultationFormResponse)responseData1.getData();
                    if(response1!=null&&response1.getDoctorConsultationFormResponseRecord()!=null
                            &&response1.getDoctorConsultationFormResponseRecord().size()>0){
                        List<DoctorConsultationFormResponseRecord> consDoctorList=response1.getDoctorConsultationFormResponseRecord();
                        for (int i = 0; i < consDoctorList.size(); i++) {
                            list.add(consDoctorList.get(i));
                        }
                    }
                    for (DoctorConsultationFormResponseRecord record:list
                            ) {
                        if("1".equals(record.getConsultationType())||"2".equals(record.getConsultationType())
                                ||"3".equals(record.getConsultationType())){
                            if("2".equals(record.getCurrentNode())||"3".equals(record.getCurrentNode())
                                    ||"6".equals(record.getCurrentNode())){
                                list1.add(record);
                            }
                        }else if("4".equals(record.getConsultationType())||"5".equals(record.getConsultationType())){
                            if("2".equals(record.getCurrentNode())||"5".equals(record.getCurrentNode())
                                    ||"6".equals(record.getCurrentNode())){
                                list1.add(record);
                            }
                        }
                    }
                }else{
                    for (DoctorConsultationFormResponseRecord record:list){
                        list1.add(record);
                    }
                }

            }
            for (DoctorConsultationFormResponseRecord record:list1
                 ) {
                if(!"/n".equals(record.getConsultationProfile())){
                    int index=record.getConsultationProfile().indexOf("/n");
                    if(index>0){
                        record.setConsultationGoal(record.getConsultationProfile().substring(index+2,record.getConsultationProfile().length()));
                        record.setConsultationBrief(record.getConsultationProfile().substring(0,index));
                    }
                }
            }
            Collections.sort(list1, new Comparator<DoctorConsultationFormResponseRecord>() {
                @Override
                public int compare(DoctorConsultationFormResponseRecord o1, DoctorConsultationFormResponseRecord o2) {
                    DoctorConsultationFormResponseRecord record=(DoctorConsultationFormResponseRecord)o1;
                    DoctorConsultationFormResponseRecord record1=(DoctorConsultationFormResponseRecord)o2;
                    int flag=0;
                    if(StringUtils.isNotBlank(record.getSubmitTime())){
                        flag = record.getSubmitTime().compareTo(record1.getSubmitTime());
                    }
//                    int flag = record.getApplyConsultationTime().compareTo(record1.getApplyConsultationTime());
                    return -flag;
                }
            });
            map.put("doctorConsultationFormResponseRecord",list1);
            responseData2.setStatus(StatusCode.OK.getCode());
            responseData2.setMsg(StatusCode.OK.getMsg());
            responseData2.setData(map);
            return responseData2;
    }

    /**
     * 上级审核医生
     * @param request
     * @return
     * @throws ESBException
     * @throws SystemException
     */
    @Override
    public ResponseData findSuperiorAuditDoctorInfo(DoctorInfomationRequest request) throws ESBException, SystemException {
        DoctorInfomationRequest request1=new DoctorInfomationRequest();
        request1.setDeptId(request.getDeptId());
        ResponseData responseData = publicService.query(ESBServiceCode.DOCTORINFOQUERY, request1, DoctorInfoResponse.class);
        DoctorInfoResponse response=(DoctorInfoResponse)responseData.getData();
        List<DoctorInfoResponseRecord> list=new ArrayList<>();
        if(response!=null&&response.getList()!=null&&response.getList().size()>0){
            if(request.getDoctorId()==null){
                request.setDoctorId(Integer.valueOf("0"));
            }
            for (DoctorInfoResponseRecord record:response.getList()
                 ) {
                    if(StringUtils.isBlank(record.getPDoctorId())){
                        if(!(request.getDoctorId().toString().equals(record.getDoctorId()))){
                            list.add(record);
                        }
                    }
            }
            Collections.sort(((DoctorInfoResponse) responseData.getData()).getList(), new Comparator<DoctorInfoResponseRecord>() {
                @Override
                public int compare(DoctorInfoResponseRecord o1, DoctorInfoResponseRecord o2) {
                    DoctorInfoResponseRecord record1=(DoctorInfoResponseRecord)o1;
                    DoctorInfoResponseRecord record2=(DoctorInfoResponseRecord)o2;
                    int flag=record1.getDoctorName().compareTo(record2.getDoctorName());
                    return flag;
                }
            });
        }
        Map<String,List<DoctorInfoResponseRecord>> map=new HashMap<>();
        map.put("medicalInfoResponseRecord",list);
        responseData.setData(map);
        return responseData;
    }

    /**
     * 添加会诊单审核和确认
     * @param request
     * @return
     * @throws ESBException
     * @throws SystemException
     */
    @Override
    public ResponseData saveConsultationFormInfo(ConsultationFormAuditRequest request) throws ESBException, SystemException {
        ResponseData responseData = publicService.query(ESBServiceCode.CONSULTATIONFORMAUDIT, request, ConsultationFormAuditResponse.class);
        return responseData;
    }

    @Transient
    public ResponseData saveEntrustDoctorInfo(EntrustDoctorRequest request) throws ESBException, SystemException {
        if(request.getDoctorList()!=null&&request.getDoctorList().size()>0){
            EntrustDoctorRequest request1=new EntrustDoctorRequest();
            request1.setConsultationId(request.getConsultationId());
            request1.setDepList(request.getDoctorList());
            ResponseData responseData = publicService.query(ESBServiceCode.ENTRUSTDOCTORSAVE, request1, ApplicationFormResponse.class);
            ApplicationFormResponse response=(ApplicationFormResponse)responseData.getData();
            System.out.print(response.getApplicationFormResponseRecord().get(0).getResultCode());
            if("0".equals(response.getApplicationFormResponseRecord().get(0).getResultCode())){
                request1.setDepList(request.getDepList());
                ResponseData responseData1 = publicService.query(ESBServiceCode.ENTRUSTDOCTORSAVE, request1, ApplicationFormResponse.class);
                return responseData1;
            }
            return responseData;
        }else{
            ResponseData responseData = publicService.query(ESBServiceCode.ENTRUSTDOCTORSAVE, request, ApplicationFormResponse.class);
            return responseData;
        }
    }

    @Override
    public ResponseData findConsultationId(ConsultationIdRequest request) throws ESBException, SystemException {
        ResponseData responseData = publicService.query(ESBServiceCode.CONSULTATIONID, request, ConsultationIdRsponse.class);
        return responseData;
    }

    /**
     * 医护人员信息查询
     * @param request
     * @return
     * @throws ESBException
     * @throws SystemException
     */
    @Override
    public ResponseData findStaffInfo(StaffInfoRequest request) throws ESBException, SystemException {
        ResponseData responseData = publicService.query(ESBServiceCode.STAFFINFO, request, DoctorInfoResponse.class);
        return responseData;
    }

    /**
     * 会诊审核流程信息查询
     * @param request
     * @return
     * @throws ESBException
     * @throws SystemException
     */
    @Override
    public ResponseData findAuditProcess(ApplicationFormRequest request) throws ESBException, SystemException {
       ResponseData responseData=publicService.query(ESBServiceCode.AUDITPROCESS,request,AuditProcessResponse.class);
        return responseData;
    }
}
