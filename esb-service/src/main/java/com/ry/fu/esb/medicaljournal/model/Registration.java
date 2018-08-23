package com.ry.fu.esb.medicaljournal.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Walker
 * @version V1.0.0
 * @Date 2018/3/26 20:02
 * @description 挂号表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_REGISTRATION")
public class Registration extends BaseModel {

    @Id
    @Column(name = "ID")
    private Long id;

    /**
     * HIS订单号
     */
    @Column(name = "HIS_ORDER_ID")
    private String hisOrderId;

    /**
     * 科室ID
     */
    @Column(name = "DEPT_ID")
    private String deptId;

    /**
     * 院区id
     */
    @Column(name = "AREA_ID")
    private Long areaId;

    /**
     * 就诊号
     */
    @Column(name = "MEDICAL_NO")
    private String medicalNo;

    /**
     * 挂号单CODE
     */
    @Column(name = "OUTPATIENT_CODE")
    private String outpatientCode;

    /**
     * 用户ID
     */
    @Column(name = "ACCOUNT_ID")
    private Long accountId;

    /**
     * HIS患者ID
     */
    @Column(name = "PATIENT_ID")
    private String patientId;

    /**
     * 医生ID
     */
    @Column(name = "DOCTOR_ID")
    private String doctorId;

    /**
     * 挂号类型
     */
    @Column(name = "REG_TYPE")
    private String regType;

    /**
     * 挂号来源：0-微信、1-安卓、2-IOS
     */
    @Column(name = "APP_ID")
    private String appId;

    /**
     * 二维码
     */
    @Column(name = "QR_CODE")
    private String qrCode;

    /**
     * 预约时间
     */
    @Column(name = "APPOINT_TIME")
    private Date appointTime;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /**
     * 候诊时间
     */
    @Column(name = "WAITTIME")
    private Date waitTime;

    /**
     * 就诊时间
     */
    @Column(name = "VISIT_TIME")
    private String visitTime;

    /**
     * 就诊日期
     */
    @Column(name = "VISIT_DATE")
    private Date visitDate;

    /**
     * 就诊科室
     */
    @Column(name = "DIAGNOSEROOM")
    private String diagnoseRoom;

    /**
     * 院区
     */
    @Column(name = "HOSPITAL_NAME")
    private String districtDeptName;

    /**
     * 随访项目
     */
    @Column(name = "PROJECT_ID")
    private Integer projectId;

    /**
     * 是否为随访患者
     */
    @Column(name = "PROJECT_NAME")
    private String projectName;



}
