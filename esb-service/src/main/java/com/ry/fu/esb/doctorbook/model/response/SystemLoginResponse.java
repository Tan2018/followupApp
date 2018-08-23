package com.ry.fu.esb.doctorbook.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * @author ：Boven
 * @Description ：随访系统登录
 * @create ： 2018-02-05 17:09
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RESPONSE")
@Data
@EqualsAndHashCode(callSuper = false)
public class SystemLoginResponse implements Serializable {
    private static final long serialVersionUID = 5434448296422875641L;

    @XmlElement(name="RECORD")
    private List<SystemLoginResponseRecode> systemLoginResponseRecode;

}
