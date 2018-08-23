package com.ry.fu.esb.doctorbook.service.impl;

import com.ry.fu.esb.common.enumstatic.ESBServiceCode;
import com.ry.fu.esb.common.enumstatic.StatusCode;
import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.common.service.PublicService;
import com.ry.fu.esb.common.utils.TimeUtils;
import com.ry.fu.esb.doctorbook.model.request.*;
import com.ry.fu.esb.doctorbook.model.response.*;
import com.ry.fu.esb.doctorbook.service.IPService;
import com.ry.fu.esb.doctorbook.service.PrimeIndexService;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ：Boven
 * @Description ：
 * @create ： 2018-06-26 19:35
 **/
@Service
public class PrimeIndexServicelmpl  implements PrimeIndexService{
    private Logger logger = LoggerFactory.getLogger(PrimeIndexServicelmpl.class);
    @Autowired
    private PublicService publicService;
    @Autowired
    private IPService ip;
    @Override
    public ResponseData primaryIndexRegistion(PrimaryIndexRegistRequest request) throws ESBException, SystemException {
        ResponseData responseData= publicService.query(ESBServiceCode.PRIMARYINDEXREGISTION,request, PrimaryIndexRegistResponse.class);
        if (responseData.getData()==null){
            return new ResponseData(StatusCode.ESB_NOT_QUERY_DATA.getCode(),StatusCode.ESB_NOT_QUERY_DATA.getMsg(),null);
        }
        return responseData;
    }

    @Override
    public ResponseData primaryIndexQuery(PrimaryIndexQueryRequest request) throws ESBException, SystemException {
        ResponseData responseData= publicService.query(ESBServiceCode.PRIMARYINDEXQUERY,request, PrimaryIndexQueryPatientResponse.class);
        if (responseData.getData()==null){
            return new ResponseData(StatusCode.ESB_NOT_QUERY_DATA.getCode(),StatusCode.ESB_NOT_QUERY_DATA.getMsg(),null);
        }
        return responseData;
    }

    @Override
    public ResponseData primaryIndexQueryPatient(PrimaryIndexQueryRequest request) throws ESBException, SystemException {
        ResponseData responseData= publicService.query(ESBServiceCode.PRIMARYINDEXQUERYPATIENT,request, PrimaryIndexQueryPatientResponse.class);
        if (responseData.getData()==null){
            return new ResponseData(StatusCode.ESB_NOT_QUERY_DATA.getCode(),StatusCode.ESB_NOT_QUERY_DATA.getMsg(),null);
        }
        return responseData;
    }

    @Override
    public ResponseData localIndexQuery(LocalIndexQueryRequest request) throws ESBException, SystemException {
        ResponseData responseData= publicService.query(ESBServiceCode.LOCALINDEXQUERY,request, PrimaryIndexQueryPatientResponse.class);
        if (responseData.getData()==null){
            return new ResponseData(StatusCode.ESB_NOT_QUERY_DATA.getCode(),StatusCode.ESB_NOT_QUERY_DATA.getMsg(),null);
        }
        return responseData;
    }

    @Override
    public ResponseData primaryIndexChange(PrimaryIndexQueryRequest request) throws ESBException, SystemException {
        ResponseData responseData= publicService.query(ESBServiceCode.PRIMARYINDEXCHANGE,request, PrimaryIndexQueryPatientResponse.class);
        if (responseData.getData()==null){
            return new ResponseData(StatusCode.ESB_NOT_QUERY_DATA.getCode(),StatusCode.ESB_NOT_QUERY_DATA.getMsg(),null);
        }
        return responseData;
    }

    @Override
    public ResponseData localIndexQueryPatient(LocalIndexQueryPatientRequest  request) throws ESBException, SystemException {
        ResponseData responseData= publicService.query(ESBServiceCode.LOCALINDEXQUERYPATIENT,request, PrimaryIndexQueryPatientResponse.class);
        if (responseData.getData()==null){
            return new ResponseData(StatusCode.ESB_NOT_QUERY_DATA.getCode(),StatusCode.ESB_NOT_QUERY_DATA.getMsg(),null);
        }
        return responseData;
    }

    @Override
    public ResponseData primaryIndexQueryPatientList(PrimaryIndexQueryPatientListRequest request) throws ESBException, SystemException, ParseException {
        if(StringUtils.isBlank(request.getRequestFlag())){
            return  new ResponseData(StatusCode.ARGU_DEFECT.getCode(),StatusCode.ARGU_DEFECT.getMsg(),null);
        }
        ResponseData responseData= new ResponseData();
        if ("1".equals(request.getRequestFlag())){
            if(StringUtils.isBlank(request.getQueryValue())){
                return  new ResponseData(StatusCode.ARGU_DEFECT.getCode(),StatusCode.ARGU_DEFECT.getMsg(),null);
            }
            if (StringUtils.isNumeric(request.getQueryValue())){
                if (request.getQueryValue().length()<=4){
                    return  new ResponseData(StatusCode.ARGU_ERROR.getCode(),StatusCode.ARGU_ERROR.getMsg(),null);
                }else  if (request.getQueryValue().length()>=5&&request.getQueryValue().length()<9){
                    request.setIpSeqNoText(request.getQueryValue());
                }else {

                    request.setPatientCardNo(StringUtils.leftPad(request.getQueryValue(), 20, "0"));
                }
            }else {
                //中文姓名
                String regEx="^[\\u4e00-\\u9fa5]+$";
                Pattern pattern = Pattern.compile(regEx);
                Matcher matcher = pattern.matcher(request.getQueryValue());
                boolean rs = matcher.matches();
                if (rs){
                    request.setPatientName(request.getQueryValue());
                }else {
                    return  new ResponseData(StatusCode.ARGU_ERROR.getCode(),StatusCode.ARGU_ERROR.getMsg(),null);
                }
            }
            responseData= publicService.query(ESBServiceCode.PRIMARYINDEXQUERYPATIENTLIST,request, PrimaryIndexQueryPatientListResponse.class);
        }else if ("2".equals(request.getRequestFlag())){
            responseData= publicService.query(ESBServiceCode.PRIMARYINDEXQUERYPATIENTLIST,request, PrimaryIndexQueryPatientListResponse.class);
        }

        PrimaryIndexQueryPatientListResponse response= (PrimaryIndexQueryPatientListResponse) responseData.getData();
        if (response.getPatientRecord()==null){
            return new ResponseData(StatusCode.ESB_NOT_QUERY_DATA.getCode(),StatusCode.ESB_NOT_QUERY_DATA.getMsg(),null);
        }
        Integer totalRecord=response.getReCount() != null ? Integer.parseInt(response.getReCount()) : 0;
        Integer pageSize=request.getPageSize() != null ? Integer.parseInt(request.getPageSize()) : 10;
        Integer totalPage=(totalRecord%pageSize==0)?totalRecord/pageSize:totalRecord/pageSize+1;
        response.setRePageSize(totalPage.toString());
        List<PrimaryIndexQueryPatientRecord> patientRecord=response.getPatientRecord();
        List<PrimaryIndexQueryPatientListRecordList> patientList=new ArrayList<>();
        List<PrimaryIndexQueryPatientListRecordPatient> patientsGroup= new LinkedList<>();
        ListSort(patientRecord);
        String  ipSeqNoTex="";
        String  patientCardNo="";
        //遍历每个患者
        for (PrimaryIndexQueryPatientRecord record:patientRecord
             ) {
            List<String> patientIdList=new ArrayList<>();
            List<String> ipSeqNoTextList=new ArrayList<>();
            List<String> patientCardNoList=new ArrayList<>();
            List<HashMap<String,String>> mapList=new ArrayList<>();
            if (StringUtils.isNotBlank(request.getPatientId())){
                record.setPatientId(request.getPatientId());
            }
            //出生日期计算年龄
            if (StringUtils.isNotBlank(record.getBirthday())){
                DateTime birthday= TimeUtils.getSdfDate(record.getBirthday());
                String age=TimeUtils.getAge(birthday,new DateTime());
                record.setAge(age);
            }
            patientList=record.getPatientList();
            patientsGroup=patientList.get(0).getPatientsGroup();
            //遍历每个患者多条记录
            for (PrimaryIndexQueryPatientListRecordPatient patient:patientsGroup
                 ) {
                HashMap<String,String> patientMap=new HashMap<>();
                //每次条记录封装map
                patientIdList.add(patient.getPatientId());
                patientMap.put("patientName",patient.getPatientName());
                if (StringUtils.isNotBlank(patient.getPatientCardNo())){
                    patientMap.put("patientCardNo",Long.valueOf(patient.getPatientCardNo()).toString());
                    patient.setPatientCardNo(Long.valueOf(patient.getPatientCardNo()).toString());
                    patientCardNoList.add(Long.valueOf(patient.getPatientCardNo()).toString());
                }else {
                    patientMap.put("patientCardNo","");
                    patientCardNoList.add("");
                }
                if (StringUtils.isNotBlank(patient.getIpSeqNoText())){
                    ipSeqNoTextList.add(patient.getIpSeqNoText());
                    patientMap.put("ipSeqNoText",patient.getIpSeqNoText());
                }else {
                    patientMap.put("ipSeqNoText","");
                    ipSeqNoTextList.add("");
                }
                mapList.add(patientMap);
            }
            record.setCount(String.valueOf(patientsGroup.size()));
            record.setPatientId(patientIdList.get(0));
            record.setPatientIdList(patientIdList);
            record.setPatientCardNoList(patientCardNoList);
            record.setIpSeqNoTextList(ipSeqNoTextList);

            record.setMapList(mapList);
            InPatientRequest inPatientRequest= new InPatientRequest();
            inPatientRequest.setPatientId(record.getPatientId());
            String ipFlag ="";
            ResponseData inPatientData=ip.patientInfo(inPatientRequest);
            if (inPatientData.getData()==null){
                 ipFlag ="";
            }else {
                InPatientResponse inPatientResponse = (InPatientResponse) inPatientData.getData();
                List<InPatientResponseRecord> inPatientList = inPatientResponse.getPatientInfo();
                 ipFlag = inPatientList.get(0).getIpFlag();
            }
            record.setPatientFlag(ipFlag);
            //取第一个非空诊疗卡号
            for (int i=0;i<patientCardNoList.size();i++){
                String num= patientCardNoList.get(i);
                if (StringUtils.isNotBlank(num)){
                    record.setPatientCardNo(num);
                    break;
                }
            }
            //取第一个非空住院号
            for (int i=0;i<ipSeqNoTextList.size();i++){
                String num= ipSeqNoTextList.get(i);
                if (StringUtils.isNotBlank(num)){
                    record.setIpSeqNoText(num);
                    break;
                }
            }
            //前端不用序列化
            record.setPatientIdList(null);
            record.setIpSeqNoTextList(null);
            record.setPatientCardNoList(null);
            record.setPatientList(null);
        }
            response.setPatientCount(String.valueOf(patientRecord.size()));
        return responseData;
    }
    /**
     *根据患者年龄排序
     */
    private static void ListSort(List<PrimaryIndexQueryPatientRecord> list) {
        Collections.sort(list, new Comparator<PrimaryIndexQueryPatientRecord>() {
            @Override
            public int compare(PrimaryIndexQueryPatientRecord o1, PrimaryIndexQueryPatientRecord o2) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    if (StringUtils.isBlank(o1.getBirthday())&&StringUtils.isBlank(o1.getBirthday())){
                        return 0;
                    }else {
                        Date dt1 = format.parse(o1.getBirthday());
                        Date dt2 = format.parse(o2.getBirthday());
                        if (dt1.getTime() > dt2.getTime()) {
                            return 1;
                        } else if (dt1.getTime() < dt2.getTime()) {
                            return -1;
                        }
                        else if((o1.getBirthday()).compareTo(o2.getBirthday())==0){
                            return 0;//此处需要返回0；
                        }else {
                            return 0;
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }

}
