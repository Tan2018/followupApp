package com.ry.fu.esb.doctorbook.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/5/7 16:11
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_HAND_INFORM")
public class HandInform extends BaseModel{

    /**
     * id
     */
    @Id
    private Long id;

    /**
     * 推送标题类型
     */
    @Column(name = "push_title_type")
    private String pushTitleType;

    /**
     * 床号
     */
    @Column(name = "sick_Bed_No")
    private String sickBedNo;

    /**
     * 性别
     */
    @Column(name = "sex")
    private String sex;

    /**
     * 医生姓名
     */
    @Column(name = "doctor_Name")
    private String doctorName;


    /**
     * 患者id
     */
    @Column(name = "patient_Id")
    private String patientId;

    /**
     * 患者姓名
     */
    @Column(name = "patient_Name")
    private String patientName;

    /**
     * 年龄
     */
    @Column(name = "age")
    private String age;

    /**
     * 手术后天数
     */
    @Column(name = "postoperative_Day")
    private String postoperativeDay;

    /**
     * 入院诊断记录
     */
    @Column(name = "strDiagnosis")
    private String strDiagnosis;


    /**
     * 创建时间
     */
    @Column(name = "create_date")
    private Date createDate;

    /**
     *用来显示的时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @Transient
    private Date outCreateDate;

    /**
     * 附属交班医生的名称
     */
    @Transient
    private String subShiftDoctorName;

    public void setDoctorName(String doctorName) {
       this.doctorName = doctorName;
    }

    public String getDoctorName() {
        if(StringUtils.isBlank(subShiftDoctorName)){
            return doctorName;
        }else {
            return subShiftDoctorName+"/"+doctorName;
        }
    }
}
