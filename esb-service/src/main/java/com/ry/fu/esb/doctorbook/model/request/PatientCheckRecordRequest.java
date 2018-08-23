package com.ry.fu.esb.doctorbook.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 患者检查记录非文件
 * @author Howard
 * @version V1.0.0
 * @date 2018/3/26 20:51
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PatientCheckRecordRequest implements Serializable{
    private static final long serialVersionUID = 4527163192055765969L;
    /**
     * 患者id
     */
    private Long patientId;

    /**
     * 医生ID
     */
    private Long doctorId;


    /**
     * 文字诊断数据
     */
    private String shiftWord;

    /**
     * 1-本科室可见,2-本组可见
     */
    private String shiftVisible;


    /**
     * 业务类型 1-交接班,2-查房
     */
    private String type;

    /**
     * 医生姓名
     */
    private String doctorName;

    /**
     * 科室ID
     */
    private String deptId;

    /**
     * 科室名称
     */
    private String orgName;

    /**
     * 医生组ID
     */
    private String groupId;

    /**
     * 患者类型
     */
    private String patientType;


    /**
     * 标题类型
     */
    private String pushTitleType;

    /**
     * 床位号
     */
    private String sickBedNo;

    /**
     * 床位号
     */
    private String sex;

    /**
     * 患者姓名
     */
    private String patientName;

    /**
     *责任医师
     */
    private String responsibleDoctor;

    /**
     * 患者年龄
     */
    private String age;

    /**
     * 手术后多少天
     */
    private String postoperativeDay;

    /**
     * 入院诊断
     */
    private String strDiagnosis;

    /**
     * 接班科室ID
     */
    private String appointDeptId;

    /**
     * 接班科室名称
     */
    private String appointDeptName;


    /**
     * 指定医生ID
     */
    private String appointDoctors;


    /**
     * 接班医生名称
     */
    private String doctorTkName;

    /**
     *  1.有选择其他科室   0.没有
     */
    private String isOhterDept;

    /**
     * 附属交班医生的ID
     */
    private Long subShiftDoctorId;

    /**
     * 附属交班医生的名称
     */
    private String subShiftDoctorName;

    /**
     * 接班医生上级的ID
     */
    private String supTakeDoctorId;

    /**
     * 接班医生上级的姓名
     */
    private String supTakeDoctorName;
}
