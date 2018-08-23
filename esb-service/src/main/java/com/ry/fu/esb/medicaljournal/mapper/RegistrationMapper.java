package com.ry.fu.esb.medicaljournal.mapper;

import com.ry.fu.esb.common.mapper.BaseMapper;
import com.ry.fu.esb.medicaljournal.model.RegisteredRecord;
import com.ry.fu.esb.medicaljournal.model.Registration;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Walker
 * @version V1.0.0
 * @Date 2018/3/19 11:54
 * @description 挂号信息
 */
@Mapper
public interface RegistrationMapper extends BaseMapper<Registration> {
    @Insert("INSERT INTO M_REGISTRATION(id,his_order_id,dept_id,medical_no,patient_id,doctor_id,create_time)values(#{id},#{hisOrderId}," +
            "#{deptId},#{medicalNo},#{patientId},#{doctorId},#{createTime})")
    void insertRegistration(@Param("id") Long id, @Param("hisOrderId") String hisOrderId, @Param("deptId") String deptId, @Param
            ("medicalNo") String medicalNo, @Param("patientId") String patientId, @Param("doctorId") String doctorId, @Param
                                    ("createTime") Date createTime);


    @Select("select DISTINCT m.* from (select * from (select * from M_REGISTRATION m where m.CREATE_TIME> (select to_date((select to_char" +
            "(sysdate-(+21600/24/60), 'yyyy-MM-dd HH24:mi:ss') new_date from dual),'yyyy-MM-dd HH24:mi:ss') from dual) and m.medical_no =" +
            " #{medicalNo}) m where m.his_order_id>-1  order by CREATE_TIME desc) m,M_ORDER o where m.id=o.reg_id and (o.fee_type='挂号费' or " +
            "o.fee_type='诊查费') and (o.order_status<=1 or o.order_status=8) order by m.CREATE_TIME desc")
    public List<Registration> selectAndPatientId(@Param("medicalNo") String PatientId);

    /**
     * 查询挂号记录
     *
     * @param accountId
     * @param start
     * @param end
     * @return
     */
    List<RegisteredRecord> selectRegisteredRecordList(@Param("accountId") Long accountId, @Param("start") Integer start, @Param("end")
            Integer end);


    /**
     * 取药查询；
     */
    @Select("SELECT DISTINCT M.* FROM (SELECT * FROM M_REGISTRATION M WHERE M .CREATE_TIME > ( SELECT TO_DATE (( SELECT  TO_CHAR (SYSDATE - (+ " +
            "21600/ 24 / 60),'yyyy-MM-dd HH24:mi:ss') new_date FROM dual),'yyyy-MM-dd HH24:mi:ss') FROM dual) AND M .medical_no = " +
            "#{medicalNo}) M JOIN  M_ORDER o ON o.reg_id = M . ID  AND o.order_name = '总费用'  AND o.order_status = '1'  AND M " +
            ".his_order_id >- 1 ORDER BY visit_date DESC,visit_time DESC")
    List<Registration> selectRegistration(@Param("medicalNo") String PatientId);


    /**
     * 报到排号
     */
    List<RegisteredRecord> selectLineUpList(@Param("patientId") Long patientId);

    /**
     * 根据订单号查询挂号记录
     */
    RegisteredRecord selectRegisteredRecordByOrderId(@Param("orderId") String orderId);

    /**
     * 根据医生id和随访标识(fullowupid不为空)查询挂号信息
     * @param accountId
     * @return
     */

    List<Map<String,Object>> selectFollowUpRegistration(@Param("accountId") Long accountId);

}
