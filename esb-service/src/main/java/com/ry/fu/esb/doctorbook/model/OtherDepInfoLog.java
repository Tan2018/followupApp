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
public class OtherDepInfoLog extends BaseModel {
    private static final long serialVersionUID = -1276669169725603310L;

    @Column(name="ID")
    private Long id;
    /*
    * 交班医生id
    * */
    @Column(name="SHIFT_DOCTOR")
    private String shiftDoctor;

    /*
    * 患者名称
    * */
    @Column(name="DOCTOR_NAME")
    private String doctorName;

    /*
    * 交班时间
    * */
    @Column(name="SHIFT_TIME")
    private String shiftTime;

    /*
    * 文字诊断数据
    * */
    @Column(name="SHIFTWORD")
    private String shiftword;

    /*
    * 图片数组
    * */
    private List<String> pictures;

    /*
    * 声音数组
    * */
    private List<String> musics;
}
