package com.ry.fu.esb.medicaljournal.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 *
 *
 * @author ：Boven
 * @Description ：挂号详情实体
 * @create ： 2018-04-11 17:49
 **/
@XmlAccessorType(XmlAccessType.NONE)
@Data
@EqualsAndHashCode(callSuper = false)
public class RegistrationDetailRecord implements Serializable {


    private static final long serialVersionUID = 5339735758508640404L;
    /**
    *系统生成的订单号
    */
    @XmlElement(name = "ORDERIDHIS")
    private String orderIdHis;
    /**
    *患者标志（需要根据订单中的患者信息如身份证件号码判断是否存在的标志，在取号环节，如果该字段是0，则外部预约系统提示患者去挂号窗口登记注册）
     0-初次就诊患者
     1-复诊患者
     */
    @XmlElement(name = "USERFLAG")
    private String userFlag;
    /**
    *系统生成的候诊时间
    */
    @XmlElement(name = "WAITTIME")
    private String waitTime;
    /**
    *系统生成的就诊诊室名称
    */
    @XmlElement(name = "DIAGNOSEROOMNAME")
    private String diagnoseRoomName;
    /**
    *状态标志：0-已挂号 1-已取消 2-已支付3 -已取号 4-已退费
     */
    @XmlElement(name = "STATUS")
    private String orderStatus;

}
