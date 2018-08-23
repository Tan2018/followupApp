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
* @Author:Jane
* @Description:手术记录
* @Date: Created in 11:20 2018/1/24
*/
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@XmlRootElement(name = "RESPONSE")
@EqualsAndHashCode(callSuper = false)
public class OperationRecordResponse implements Serializable {

    private static final long serialVersionUID = -4656901954482947821L;

    @XmlElement(name="RECORD")
    private List<OperationRecordResponseRecode> operationRecordResponseRecode;

}
