package com.ry.fu.esb.doctorbook.model.response;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
* @Author:Boven
* @Description:主索引注册
* @Date: Created in 11:21 2018/1/24
*/

@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class PrimaryIndexQueryRecord implements Serializable {

    /**
     *主索引ID
     */
    @XmlElement(required = true, name = "MPI_ID")
    private String mpiId;
    /**
    *患者姓名
    */
    @XmlElement(required = true, name = "PATIENT_NAME")
    private String patientName;
    /**
     *性别：0-未知,1-男,2-女
     */
    @XmlElement(required = true, name = "SEX")
    private String sex;
    /**
    *电话
    */
    @XmlElement(required = true, name = "PHONE")
    private String phone;
    /**
    *出生日期
    */
    @XmlElement(required = true, name = "BIRTHDAY")
    private String birthday;
    /**
    *婚姻状况：0-未知,1-未婚,2-已婚,3-离婚,4-丧偶,100-HIS30使用
    */
    @XmlElement(required = true, name = "MARRIAGE_FLAG")
    private String marriageFlag;
    /**
    *职业
    */
    @XmlElement(required = true, name = "PROFESSION_NAME")
    private String professionName;
    /**
     *证件号
     */
    @XmlElement(required = true, name = "IDENTITY_CARD_NO")
    private String IdCardNo;
    /**
     *联系人
     */
    @XmlElement(required = true, name = "CONTACT_PERSON")
    private String contactPerson;
    /**
     *家庭住址
     */
    @XmlElement(required = true, name = "ADDRESS")
    private String address;
    /**
     *移动电话
     */
    @XmlElement(required = true, name = "CELL_PHONE")
    private String cellPhone;
    /**
     *国籍
     */
    @XmlElement(required = true, name = "NATIONALITY_NAME")
    private String nationalame;
    /**
     *籍贯
     */
    @XmlElement(required = true, name = "NATIVE_PLACE")
    private String nativePlace;
    /**
     *民族
     */
    @XmlElement(required = true, name = "RACE_NAME")
    private String nationName;





}
