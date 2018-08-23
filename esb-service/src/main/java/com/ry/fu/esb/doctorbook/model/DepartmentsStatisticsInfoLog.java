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
@Table(name = "M_DE_DOCTOR")
public class DepartmentsStatisticsInfoLog extends BaseModel {
    private static final long serialVersionUID = -6987729488541890053L;

    /*
     * 交班医生id
     */
    @Column(name = "SHIFT_DOCTOR")
    private Long shiftDoctor;

    /*
     * 交班医生名称
     */
    @Column(name = "CH_NAME")
    private String chName;

    /*
     * 交班时间
     */
    @Column(name = "SHIFT_TIME")
    private Date shiftTime;

    /*
    * 文字描述
    * */
    @Column(name = "DESCRIBE")
    private String describe;

    /*
    * 图片数组
    * */
    private List<String> pictures;

    /*
    * 声音数组
    * */
    private List<String> musics;
}
