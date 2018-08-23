package com.ry.fu.esb.doctorbook.model.request;

import com.ry.fu.esb.doctorbook.model.response.DeprecListRecord;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/7/23 10:05
 **/
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "REQUEST")
@Data
public class EntrustDoctorList {

    /**
     * 操作类型
     */
    @XmlElement(required = true, name = "DEPREC")
    private List<EntrustDoctorRecord> depRec;
}
