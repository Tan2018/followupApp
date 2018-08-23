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
 * 获取指定医生列表
 *
 * @author ：Boven
 * @Description ：
 * @create ： 2018-03-12 11:11
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@XmlRootElement(name = "RESPONSE")
@EqualsAndHashCode(callSuper = false)
public class DesignatedDoctorsRespone implements Serializable {


    private static final long serialVersionUID = 3695552484059708206L;
    /**
    *科室id
    */
    @XmlElement(name="RECORD")
    private List<DesignatedDoctorsResponeRecord> designatedDoctorsResponeRecord;

}
