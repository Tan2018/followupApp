package com.ry.fu.esb.doctorbook.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class DepartmentsLogRequest implements Serializable {
    private static final long serialVersionUID = -8533348378781032687L;

    /*
    * 交班医生id
    * */
    private Long shiftDoctor;

    /*
    * 交班医生姓名
    * */
    private String shiftDoctorName;

    /*
    * 接班医生id
    * */
    private Long takeDoctor;

    /**
     * 接班医生姓名
     */
    private String takeDoctorName;

    /*
    * 图片数组
    * */
    private MultipartFile [] pictures;

    /*
    * 声音数组
    * */
    private MultipartFile [] musics;

    /*
    * 描述
    * */
    private String desc;

   /*
    * 科室ID
    * */
   private Long depId;

    /**
     * 附属交班医生的ID
     */
    private String subShiftDoctorId;

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
