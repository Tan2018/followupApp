package com.ry.fu.esb.doctorbook.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_OTHER_DEP")
public class OtherDepInfo extends BaseModel {
    private static final long serialVersionUID = 4621513931606766197L;


    @Column(name="ID")
    private Long id;

    /*
    * 科室id
    * */
    @Column(name="ORG_ID")
    private String orgId;

    /*
    * 科室名称
    * */
    @Column(name="ORG_NAME")
    private String orgName;

    /*
    * 患者id
    * */
    @Column(name="PATIENT_ID")
    private String patientId;

    /*
    * 接班医生id
    * */
    @Column(name="TAKE_DOCTOR")
    private String takeDoctor;

    /*
    * 接班医生名称
    * */
    @Column(name="DOCTOR_NAME")
    private String doctorName;

    /*
    * 床号
    * */
    @Column(name="BED_NO")
    private String bedNo;

    /*
    * 住院天数
    * */
    @Column(name="HOSPITAL_DAY")
    private String hospitalDay;

    /*
    * 患者年龄
    * */
    @Column(name="P_AGE")
    private String pAge;

    /*
    * 患者性别
    * */
    @Column(name="P_SEX")
    private String pSex;

    /*
    * 入院诊断
    * */
    @Column(name="STR_DIAGNOSIS")
    private String strDiagnosis;

    /*
    * 交接状态
    * */
    @Column(name="ST_STATE")
    private Integer stState;

    /*
    * 交班时间
    * */
    @Column(name="SHIFT_TIME")
    private Date shiftTime;

    /*
    * 接班时间
    * */
    @Column(name="TAKE_TIME")
    private Date takeTime;

    /*
    * 患者名称
    * */
    @Column(name="PATIENT_NAME")
    private String patientName;

    /*
    * 1.有选择其他科室   0.没有
    * */
    @Column(name="ISO_HTER_DEPT")
    private String isoHterDept;
}
