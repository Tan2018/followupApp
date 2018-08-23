package com.ry.fu.esb.doctorbook.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 * @author ：Boven
 * @Description ：患者基本信息
 * @create ： 2018-02-01 17:53
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class InpatientBasicResponseRecode  implements Serializable {


    private static final long serialVersionUID = 4427842754425794608L;

    /**
     *患者姓名
     */
    @XmlElement(name = "PATIENT_NAME",required = true)
    private String patientName;
    /**
     *性别（1男，2女）
     */

    @XmlElement(name = "PATIENT_SEX",required = true)
    private String sex;
    /**
     *民族
     */
    @XmlElement(name = "NATION",required = true)
    private String nation;
    /**
     *年龄
     */
    @XmlElement(name = "AGE",required = true)
    private String age;
    /**
     *婚否（1表示未婚，2表示已婚）
     */
    @XmlElement(name = "MARRIAGE_FLAG",required = true)
    private String marriage;
    /**
     *科室
     */
    @XmlElement(name = "DEPARTMENT")
    private String inpatientCategory;
    /**
     *床号
     */
    @XmlElement(name = "SICK_BED_NO")
    private String sickBedNo;
    /**
     *住院号
     */
    @XmlElement(name = "IPSEQNOTEXT")
    private String ipSeqNoText;
    /**
     *职业
     */
    @XmlElement(name = "PROFESSION",required = true)
    private String profession;
    /**
     *出生地
     */
    @XmlElement(name = "BIRTHPLACE",required = true)
    private String birthPlace;
    /**
     *联系人电话
     */
    @XmlElement(name = "CONTACT_PHONE")
    private String contactPhone;
    /**
     *联系人
     */
    @XmlElement(name = "CONTACT_PERSON")
    private String contactPersion;
    /**
     *移动电话
     */
    @XmlElement(name = "CELL_PHONE")
    private String cellPhone;
    private String patientFlag;
}
