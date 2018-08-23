package com.ry.fu.esb.doctorbook.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author ：Boven
 * @Description ：患者入院记录信息响应参数
 * @create ： 2018-01-15 16:54
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RESPONSE")
@Data
@EqualsAndHashCode(callSuper = false)
public class HospitalizationRecordResponse implements Serializable {


    private static final long serialVersionUID = -5717285057797868413L;
    @XmlElement(name="RECORD")
    private List<HospitalizationRecordResponseRecode> hospitalizationRecordDetailResponse;

}
