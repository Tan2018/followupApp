package com.ry.fu.esb.medicaljournal.model.response;

import com.ry.fu.esb.common.adapter.TimeAdapater;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;


/**
 * @author ：Joker
 * @Description ：取药详情
 * @create ： 2018-05-8 11:03
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class PharmacyRecord implements Serializable {


    private static final long serialVersionUID = -2093282820396561330L;

    /**
     * 药品名称
     */
    private String medicineName;

    /**
     * 价格
     */
    private String amount;

    /**
     * 执行科室
     */
    private String executDepartmentText;
    /**
     * 开嘱医生
     */
    private String inputDoctor;

    /**
     * 开嘱时间
     */
    private String inputDateTime;
    /**
     * 0-未打印,1-打印,2-配药,3-挂起,4-发药,5-退药,6-申请发药
     */
 /*   @XmlElement(name = "STATUSFLAG")
    private String statusFlag;*/

    /**
     * 1-西药,2-中成药,3-中草药,4-用药方式,5-材料,6-项目,7-技诊费,8-标本,9-容器,10-手术,11-中草药煎法
     */
    private String sourceTypeFlag;
    /**
     * 0-未付费,1-已付费
     */
    /*@XmlElement(name = "PAYFLAG")
    private String payFlag;*/


    /**
     * 取药状态  0-未取药
     */
 /*   private String pharmacyFlag;*/

    /**
     * 取药地址
     */
    private String pharmacyAddress;

}
