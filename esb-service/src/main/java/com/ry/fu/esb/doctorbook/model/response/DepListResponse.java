package com.ry.fu.esb.doctorbook.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/7/3 15:12
 **/
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "REQUEST")
@Data
public class DepListResponse {
    /**
     * 操作类型
     */
    @XmlElement(required = true, name = "DEPREC")
    private List<DeprecListRecord> depRec;

}
