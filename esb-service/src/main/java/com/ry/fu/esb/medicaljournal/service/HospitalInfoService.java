package com.ry.fu.esb.medicaljournal.service;

import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.medicaljournal.model.HospitalInfo;

import java.util.List;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/4/12 14:51
 **/
public interface HospitalInfoService {

    /**
     * 查询医院简介信息接口
     * @return
     * @throws SystemException
     * @throws ESBException
     */
    ResponseData findHospitalIntroductionInfo()  throws SystemException, ESBException;


    /**
     * 查询所有楼层分布信息和交通指引信息接口
     * @return
     * @throws SystemException
     * @throws ESBException
     */
    ResponseData findFloorDistributionAndTrafficGuidanceInfo()throws SystemException, ESBException;
}
