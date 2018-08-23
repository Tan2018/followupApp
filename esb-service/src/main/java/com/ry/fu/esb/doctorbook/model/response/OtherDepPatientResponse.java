package com.ry.fu.esb.doctorbook.model.response;

import com.ry.fu.esb.doctorbook.model.OtherDepInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * @Author:thankbin
 * @Description:其它科室交接班
 * @Date: Created in 20:01 2018/5/17
 */
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@XmlRootElement(name = "RESPONSE")
@EqualsAndHashCode(callSuper = false)
public class OtherDepPatientResponse implements Serializable{
    private static final long serialVersionUID = 3260563458035271074L;

    @XmlElement(name="RECORD")
    private List<OtherDepInfo> otherDepInfoList;
}
