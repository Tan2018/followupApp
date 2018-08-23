package com.ry.fu.esb.jpush.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/5/12
 * @description 危急值
 */
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@XmlRootElement(name = "RESPONSE")
@EqualsAndHashCode(callSuper = false)
public class PushBarNumberResponse implements Serializable{
    /**
     * 危急值通知
     */
    private Integer criticalNotice;
    /**
     * 交班通知
     */
    private Integer shiftsNotice;
    /**
     * 项目随访通知
     */
    private Integer  projectNotice;
    /**
     * 会诊通知
     */
    private Integer consultationNotice;
}
