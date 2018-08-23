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
 * @Description ：护理三测响应参数
 * @create ： 2018-01-15 16:51
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RESPONSE")
@Data
@EqualsAndHashCode(callSuper = false)
public class NurseMeasureResponse implements Serializable{
    private static final long serialVersionUID = -4851891875125564600L;
    @XmlElement(name="RECORD")
    private List<NurseMeasureResponseRecode> nurseMeasureDetailResponse;


}
