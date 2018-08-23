package com.ry.fu.esb.medicaljournal.mapper;

import com.ry.fu.esb.common.mapper.BaseMapper;
import com.ry.fu.esb.medicaljournal.model.ContentArticle;
import com.ry.fu.esb.medicaljournal.model.HospitalInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author jane
 * @Date 2018/2/24
 */
@Mapper
public interface HospitalInfoMapper extends BaseMapper<HospitalInfo> {

    List<HospitalInfo>  findHospitalInfo();


    /**
     * 查询医院简介信息接口
     * @return
     */
    @Select("SELECT DESCRIPTION FROM M_HOSPITAL_INFO WHERE ID=1")
    HospitalInfo selectHospitalIntroductionInfo();


    /**
     * 查询所有医院楼层分布信息以及所有医院的交通指引信息接口
     * @return
     */
    @Select("SELECT HOSPITALAREA,BUILDING,TRANSPORT_GUIDE FROM M_HOSPITAL_INFO")
    List<HospitalInfo> selectFloorDistributionAndTrafficGuidanceInfo();
}
