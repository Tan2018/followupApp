package com.ry.fu.esb.medicaljournal.mapper;

import com.ry.fu.esb.common.mapper.BaseMapper;
import com.ry.fu.esb.medicaljournal.model.Contacts;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author Seaton
 * @Date 2018/3/14
 */
@Mapper
public interface ContactsMapper extends BaseMapper <Contacts>{

    @Select("select mc.account_id from m_contacts mc,m_patient mp where mc.patient_id=mp.id and mp.esb_patient_id=#{patientId}")
    List<String> queryAccountIdListByEsbPatientId(@Param("patientId") String patientId);

    @Select("select account_id from m_contacts where patient_id = #{patientId}")
    List<Contacts> selectByPatientId(@Param("patientId") Long patientId);

    @Select("select patient_id from m_contacts where account_id = #{accountId}")
    List<Long> selectPatientIdById(@Param("accountId") Long accountId);
}
