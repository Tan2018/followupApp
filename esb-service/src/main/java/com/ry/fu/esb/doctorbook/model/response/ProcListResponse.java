package com.ry.fu.esb.doctorbook.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

/**
 * @Author jane
 * @Date 2018/7/9
 */
@XmlAccessorType(XmlAccessType.NONE)
@Data
@EqualsAndHashCode(callSuper = false)
public class ProcListResponse implements Serializable{
    private static final long serialVersionUID = -8517777784259113145L;

    /**
     * 操作类型
     */
    @XmlElement(required = true, name = "PROCREC")
    private List<ProCrecRecord> prorec;
}
