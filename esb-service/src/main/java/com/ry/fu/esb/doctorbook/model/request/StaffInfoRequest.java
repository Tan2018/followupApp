package com.ry.fu.esb.doctorbook.model.request;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * 医护人员信息查询
 * @Author jane
 * @Date 2018/7/10
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "REQUEST")
@Data
public class StaffInfoRequest implements Serializable {

    private static final long serialVersionUID = 9165005099751076220L;

    /**
     * 平台科室编码序列，多个科室编码之间用半角逗号分隔。
     */
    @XmlElement(name = "HIP_DEPT_CODES")
    private String hipDeptCodes;

    /**
     * 平台医疗服务人员编码序列，多个医疗服务人员编码之间用半角逗号分隔。
     */
    @XmlElement(name = "HIP_STAFF_CODES")
    private String hipStaffCodes;

    /**
     * 身份证编码序列，多个身份证编码之间用半角逗号分隔。
     */
    @XmlElement(name = "IDCARDS")
    private String idcards;

    /**
     * 医疗服务人员姓名关键字
     */
    @XmlElement(name = "NAME_KEY")
    private String nameKey;

    /**
     * 医疗服务人员ID
     */
    @XmlElement(name = "ACCOUNT_ID")
    private String accountId;
}
