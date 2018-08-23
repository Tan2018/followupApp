package com.ry.fu.esb.medicaljournal.service.impl;

import com.ry.fu.esb.common.enumstatic.StatusCode;
import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.medicaljournal.mapper.HospitalInfoMapper;
import com.ry.fu.esb.medicaljournal.model.HospitalInfo;
import com.ry.fu.esb.medicaljournal.service.HospitalInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @Author:Tom
 * @Description:
 * @create: 2018/4/12 14:58
 **/
@Service
public class HospitalInfoServiceImpl implements HospitalInfoService {

    private static Logger logger =  LoggerFactory.getLogger(HospitalInfoServiceImpl.class);

    @Autowired
    private HospitalInfoMapper hospitalInfoMapper;


    @Override
    public ResponseData findHospitalIntroductionInfo() {
        HospitalInfo hospitalInfo=hospitalInfoMapper.selectHospitalIntroductionInfo();
        if(hospitalInfo==null){
            return new ResponseData(StatusCode.ESB_NOT_QUERY_DATA.getCode(),StatusCode.ESB_NOT_QUERY_DATA.getMsg(),null);
        }
        return new ResponseData(StatusCode.OK.getCode(),StatusCode.OK.getMsg(),hospitalInfo);
    }

    @Override
    public ResponseData findFloorDistributionAndTrafficGuidanceInfo() throws SystemException, ESBException {
        List<HospitalInfo> list=hospitalInfoMapper.selectFloorDistributionAndTrafficGuidanceInfo();
        if(!(list.size()>0)){
            return new ResponseData(StatusCode.ESB_NOT_QUERY_DATA.getCode(),StatusCode.ESB_NOT_QUERY_DATA.getMsg(),null);
        }
        return new ResponseData(StatusCode.OK.getCode(),StatusCode.OK.getMsg(),list);
    }
}
