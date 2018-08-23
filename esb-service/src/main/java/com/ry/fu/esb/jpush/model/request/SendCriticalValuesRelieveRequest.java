package com.ry.fu.esb.jpush.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/5/12
 * @description 危急值
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
@Data
@EqualsAndHashCode(callSuper = false)
public class SendCriticalValuesRelieveRequest implements Serializable{
    /**
     * 检验申请单ID
     */
    @XmlElement(required = true, name = "EXAMINEREQUESTID")
    private Long examineRequestId;
    /**
     * 状态描述
     */
    @XmlElement(required = true, name = "STATREMARK")
    private String statremark;
    /**
     * 操作时间
     */
    @XmlElement(required = true, name = "OPTM")
    private String optm;
    /**
     * 检验编码
     */
    @XmlElement(name="RESULTS")
    private Results results;
}
