package com.ry.fu.esb.doctorbook.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_DE_DOCTOR")
public class DepartmentsStatisticsInfo extends BaseModel {
    private static final long serialVersionUID = -1142759429652863630L;

    @Column(name = "ID")
    private Long id;

    /*
     * 交班医生id
     */
    @Column(name = "SHIFT_DOCTOR")
    private Long shiftDoctor;

    /*
     * 接班医生id
     */
    @Column(name = "TAKE_DOCTOR")
    private Long takeDoctor;

    /*
     * 交班医生名称
     */
    @Column(name = "CH_NAME")
    private String chName;

    /*
     * 接班医生名称
     */
    @Column(name = "JH_NAME")
    private String jhName;

    /*
     * 科室名称
     */
    @Column(name = "ORG_NAME")
    private String orgName;

    /*
     * 交班时间
     */
    @Column(name = "SHIFT_TIME")
    private String shiftTime;

    /*
     * 接班时间
     */
    @Column(name = "TAKE_TIME")
    private String takeTime;

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
