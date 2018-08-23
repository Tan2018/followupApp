package com.ry.fu.esb.doctorbook.service.impl;

import com.ry.fu.esb.common.enumstatic.ESBServiceCode;
import com.ry.fu.esb.common.enumstatic.StatusCode;
import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.common.service.PublicService;
import com.ry.fu.esb.doctorbook.model.request.DoctorBookAppSetRequest;
import com.ry.fu.esb.doctorbook.service.DoctorBookAppSetService;
import com.ry.fu.esb.medicaljournal.mapper.DoctorMapper;
import com.ry.fu.esb.medicaljournal.model.DoctorInfo;
import com.ry.fu.esb.medicaljournal.model.request.DeptInfoRequest;
import com.ry.fu.esb.medicaljournal.model.request.DoctorQueryRequest;
import com.ry.fu.esb.medicaljournal.model.response.DeptListResponse;
import com.ry.fu.esb.medicaljournal.model.response.DoctorQueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/5/24 16:35
 **/
@Service
public class DoctorBookAppSetServiceImpl implements DoctorBookAppSetService {

    @Autowired
    private PublicService publicService;

    @Autowired
    private DoctorMapper doctorMapper;

    @Override
    public ResponseData findDoctorPersonalData(DoctorQueryRequest request) throws ESBException, SystemException {
        DoctorInfo doctorInfo = new DoctorInfo();
        doctorInfo.setDoctorId(Long.valueOf(request.getDoctorId()));
        List<DoctorInfo> doctorInfoList = doctorMapper.select(doctorInfo);
        if(doctorInfoList.size()>0){
            Map<String,Object> map=new HashMap<>();
            if(!"未确定".equals(doctorInfoList.get(0).getProcessName())){
                map.put("processName",doctorInfoList.get(0).getProcessName());
            }else{
                map.put("processName","");
            }
            return new ResponseData(StatusCode.OK.getCode(),StatusCode.OK.getMsg(),map);
        }
        return new ResponseData(StatusCode.OK.getCode(),StatusCode.OK.getMsg(),null);
    }
}
