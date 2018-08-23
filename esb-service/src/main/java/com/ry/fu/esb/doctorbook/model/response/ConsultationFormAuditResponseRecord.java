package com.ry.fu.esb.doctorbook.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/7/3 14:51
 **/
@XmlAccessorType(XmlAccessType.NONE)
@Data
@EqualsAndHashCode(callSuper = false)
public class ConsultationFormAuditResponseRecord {

    /**
     *处理结果代码：0-成功，1失败
     */
    @XmlElement(name = "resultCode")
    private String resultCode;

    /**
     *处理结果描述
     */
    @XmlElement(name = "resultDesc")
    private String resultDesc;
}
