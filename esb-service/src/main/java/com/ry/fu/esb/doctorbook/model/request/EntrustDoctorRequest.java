package com.ry.fu.esb.doctorbook.model.request;

import com.ry.fu.esb.doctorbook.model.response.DepListResponse;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/7/23 9:59
 **/
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "REQUEST")
@Data
public class EntrustDoctorRequest {

    /**
     * 会诊id
     */
    @XmlElement(required = true, name = "CONSULTATION_ID")
    private String consultationId;

    /**
     * 会诊医生集合
     */
    @XmlElement(required = true, name = "DEPLIST")
    private List<EntrustDoctorList> depList;

    /**
     * 会诊医生集合
     */
    @XmlElement(required = true, name = "DOCTORLIST")
    private List<EntrustDoctorList> doctorList;
}
