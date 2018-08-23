package com.ry.fu.esb.doctorbook.service.impl;

import com.ry.fu.esb.common.enumstatic.ESBServiceCode;
import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.common.service.PublicService;
import com.ry.fu.esb.doctorbook.model.request.DoctorConsultationFormRequest;
import com.ry.fu.esb.doctorbook.model.response.DoctorConsultationFormResponse;
import com.ry.fu.esb.doctorbook.model.response.DoctorConsultationFormResponseRecord;
import com.ry.fu.esb.doctorbook.service.ConsultationDoctorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Joker
 * @Date 2018/7/4 16:33
 * @description
 */
@Service
public class ConsultationDoctorServiceImpl implements ConsultationDoctorService {

    private Logger logger = LoggerFactory.getLogger(ConsultationDoctorServiceImpl.class);

    @Autowired
    private PublicService publicService;

    /**
     * 会诊患者列表
     */
    @Override
    public ResponseData consultationPatient(String consDoctorId, String orderMode) throws ESBException, SystemException {

        DoctorConsultationFormRequest consultationRequest = new DoctorConsultationFormRequest();
        consultationRequest.setConsDoctorId(consDoctorId);
        consultationRequest.setOrderMode(orderMode);
        ResponseData responseData = publicService.query(ESBServiceCode.DOCTORCONSULTATIONFORM, consultationRequest,
                DoctorConsultationFormResponse.class);
        DoctorConsultationFormResponse Response = (DoctorConsultationFormResponse) responseData.getData();
        DoctorConsultationFormResponse Responses = new DoctorConsultationFormResponse();
        List<DoctorConsultationFormResponseRecord> records = Response.getDoctorConsultationFormResponseRecord();
        List<DoctorConsultationFormResponseRecord> recordList = new ArrayList<>();

        //logger.info("原有的数据：" + records.size());
        if (records != null && records.size() > 0) {
            for (DoctorConsultationFormResponseRecord record : records) {
                if ("6".equals(record.getCurrentNode()) || "7".equals(record.getCurrentNode())) {
                    String applyConsultationTime = record.getApplyConsultationTime();
                    if (applyConsultationTime != null && !StringUtils.isBlank(applyConsultationTime)) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date parse = null;
                        try {
                            parse = simpleDateFormat.parse(record.getApplyConsultationTime());
                            record.setDatelyConsultationTime(parse.getTime());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        recordList.add(record);
                    }
                }
            }
        }
        Responses.setDoctorConsultationFormResponseRecord(recordList);
        responseData.setData(Responses);
        logger.info("除去后的数据条数：" + recordList.size());
        return responseData;
    }
}
