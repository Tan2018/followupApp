package com.ry.fu.esb.doctorbook.model.response;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/3/19 11:09
 * @description description
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RESPONSE")
@Data
@EqualsAndHashCode(callSuper = false)
public class DoctorInfoByNameRecord extends BaseModel {

    @XmlElement(name="RECORD")
    private List<DoctorInfoByName> record;


}
