package com.ry.fu.esb.jpush.mapper;

import com.ry.fu.esb.common.mapper.BaseMapper;
import com.ry.fu.esb.jpush.model.CrisisProject;
import com.ry.fu.esb.jpush.model.PatientInformation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/4/26
 * @description 危急值信息
 */
@Mapper
public interface PatientInformationMapper extends BaseMapper<PatientInformation> {
    /**
     * 查询危急值列表
     * @param personId
     * @return
     */
    @Select("select distinct m.id,m.patient_name,m.sex,m.age,m.inpatient_area,m.seat,m.create_time,m.update_time,\n" +
            "m.sender,m.opend_id,m.is_handler,m.crisis_time,m.patient_id,m.ip_times,m.ip_seq_no_text,\n" +
            "m.inpatient_id,m.lis_lable_no,m.examine_request_id,m.person_id,m.doctor_code,m.person_name,\n" +
            "m.no_televel,m.no_tetype,m.no_tetimeout,m.noticetype,m.patientflag from m_critical_value_list m\n" +
            "left join m_inspection_project mi on m.lis_lable_no = critical_id \n" +
            "where m.person_id = #{personId} and mi.isrelieve = 0 and is_handler = #{whetherDealWith} order by m.crisis_time desc")
    List<PatientInformation> selectByPatientInformation(@Param("personId")String personId,@Param("whetherDealWith") String whetherDealWith);

    /**
     * 根据医生别名统计患者项目条数
     * @param personId
     * @return
     */
    @Select("SELECT COUNT(distinct 1) FROM M_CRITICAL_VALUE_LIST m left join M_INSPECTION_PROJECT mi on m.lis_lable_no = critical_id  WHERE m.PERSON_ID = #{personId} and mi.isrelieve = 0")
    int selectByPatientInformationCount(@Param("personId")String personId);

    /**
     * 根据检验标本号查询
     * @param lisLableNo
     * @return
     */
    @Select("SELECT ID,CRITICAL_ID,ITEMCODE,ITEMNAME,RESULT,COMPANY,PROMPT,RANGE,ITEMID,ISRELIEVE,EXAMINE_REQUEST_ID FROM M_INSPECTION_PROJECT WHERE CRITICAL_ID = #{lisLableNo} AND ISRELIEVE = 0")
    List<CrisisProject> selectByCriticalId(@Param("lisLableNo") Long lisLableNo);

    /**
     * 根据检验标本号修改为已处理
     * @param lisLableNo
     * @return
     */
    @Update("UPDATE M_CRITICAL_VALUE_LIST SET IS_HANDLER = 1 WHERE LIS_LABLE_NO = #{lisLableNo}")
    int updateByLisLableNo(@Param("lisLableNo") Long lisLableNo);

    @Select("SELECT CRISIS_TIME FROM M_CRITICAL_VALUE_LIST WHERE LIS_LABLE_NO = #{lisLableNo}")
    PatientInformation selectByCrisisTime(@Param("lisLableNo") Long lisLableNo);

    /**
     * 根据检验标本号和接收人来查询此记录是否已存在
     * @param lisLableNo
     * @return
     */
    @Select("SELECT COUNT(1) FROM M_CRITICAL_VALUE_LIST WHERE LIS_LABLE_NO = #{lisLableNo} AND PERSON_ID  = #{personId}")
    int selectByLisLableNoCount(@Param("lisLableNo") Long lisLableNo,@Param("personId") String personId);
}
