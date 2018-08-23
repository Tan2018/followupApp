package com.ry.fu.esb.doctorbook.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/7/5 9:15
 **/
@XmlAccessorType(XmlAccessType.NONE)
@Data
@EqualsAndHashCode(callSuper = false)
public class ProCrecResponse {

    /**
     * 操作类型
     */
    @XmlElement(required = true, name = "DEPREC")
    private ProCrecRecord depRec;
}
