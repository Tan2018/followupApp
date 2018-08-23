package com.ry.fu.esb.medicaljournal.model.request;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * 挂号请求(普遍情况)
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "REQUEST")
@Data
public class RegisterInfoRequest implements Serializable {
    private static final long serialVersionUID = -3804978379162543699L;

    @XmlElement(name = "ORDERTYPE")
    private String orderType;

    @XmlElement(name = "ORDERID")
    private String orderId;

    @XmlElement(name = "DEPTID")
    private String deptId;

    @XmlElement(name = "DOCTORID")
    private String doctorId;

    @XmlElement(name = "REGDATE")
    private String regDate;

    @XmlElement(name = "TIMEID")
    private String timeId;

    @XmlElement(name = "STARTTIME")
    private String startTime;

    @XmlElement(name = "ENDTIME")
    private String endTime;

    @XmlElement(name = "USERHISPATIENTID")
    private String userHisPatientId;

    @XmlElement(name = "USERIDCARD")
    private String userIdCard;

    @XmlElement(name = "USERJKK")
    private String userJkk;

    @XmlElement(name = "USERSMK")
    private String userSmk;

    @XmlElement(name = "USERYBK")
    private String userYbk;

    @XmlElement(name = "USERPARENTIDCARD")
    private String userParentIdCard;

    @XmlElement(name = "USERNHCARD")
    private String userNhCard;

    @XmlElement(name = "USERNAME")
    private String userName;

    @XmlElement(name = "USERGENDER")
    private String userGender;

    @XmlElement(name = "USERMOBILE")
    private String userMobile;

    @XmlElement(name = "USERBIRTHDAY")
    private String userBirthday;

    @XmlElement(name = "AGENTID")
    private String agentId;

    @XmlElement(name = "ORDERTIME")
    private String orderTime;

    @XmlElement(name = "FEE")
    private String fee;

    @XmlElement(name = "TREATFEE")
    private String treatFee;

    @XmlElement(name = "ADDFLAG")
    private String addFlag;

    private  Long accountId;

    private String districtDeptName;

    /**
     * 随访项目id
     */
    private Integer projectId;

    /**
     * 随访项目名称
     */
    private String projectName;
}
