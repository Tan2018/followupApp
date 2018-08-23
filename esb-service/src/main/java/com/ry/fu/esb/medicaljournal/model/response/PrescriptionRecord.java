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
 * @author ：Boven
 * @Description ：门诊处方
 * @create ： 2018-03-22 18:27
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class PrescriptionRecord  implements Serializable {

    private static final long serialVersionUID = 11425045112335950L;

    /**
     *处方编号
     */
    @XmlElement(name = "RXNO")
    private String rxNo;

    /**
    *药品编码
    */
    @XmlElement(name = "MEDICINENO")
    private String medicineNo;
    /**
     *药品名称
     */
    @XmlElement(name = "MEDICINENAME")
    private String medicineName;
    /**
     *价格
     */
    @XmlElement(name = "AMOUNT")
    private String amount;
    /**
     *规格
     */
    @XmlElement(name = "SPECIFICATION")
    private String specification;
    /**
     *每次量
     */
    @XmlElement(name = "DOSAGEPERTIME")
    private String dosageperTime;
    /**
     *用药途径
     */
    @XmlElement(name = "MEDICINEUSINGMETHOD")
    private String medicineUsingMethod;
    /**
     *用药时间
     */
    @XmlElement(name = "TAKINGMEDICINETIME")
    private String takingMedicineTime;
    /**
     *补充用法
     */
    @XmlElement(name = "SECONDMEDICINEUSINGMETHOD")
    private String secondmMedicineUsingMethod;
    /**
     *描述
     */
    @XmlElement(name = "DESCRIPTION")
    private String description;
    /**
     *天数
     */
    @XmlElement(name = "DAYS")
    private String days;
    /**
     *总量
     */
    @XmlElement(name = "DOSAGE")
    private String dosage;
    /**
     *单位
     */
    @XmlElement(name = "UNIT")
    private String unit;
    /**
     *单位（大类）
     */
    @XmlElement(name = "BIGUNIT")
    private String bigUnit;
    /**
     *执行科室
     */
    @XmlElement(name = "EXECUTDEPARTMENTTEXT")
    private String executDepartmentText;
    /**
     *开嘱医生
     */
    @XmlElement(name = "INPUTDOCTOR")
    private String inputDoctor;
     /**
     *配药员
     */
    @XmlElement(name = "DISPENSER")
    private String dispenser;
    /**
     *发药员
     */
    @XmlElement(name = "DRUGDISPENSER")
    private String drugDispenser;
    /**
     *开嘱时间
     */
    @XmlJavaTypeAdapter(TimeAdapater.class)
    @XmlElement(name = "INPUTDATETIME")
    private String inputDateTime;
    /**
     *0-未打印,1-打印,2-配药,3-挂起,4-发药,5-退药,6-申请发药
     */
    @XmlElement(name = "STATUSFLAG")
    private String statusFlag;
    /**
     *1-西药,2-中成药,3-中草药,4-用药方式,5-材料,6-项目,7-技诊费,8-标本,9-容器,10-手术,11-中草药煎法
     */
    @XmlElement(name = "SOURCETYPEFLAG")
    private String sourceTypeFlag;
    /**
     *0-未付费,1-已付费
     */
    @XmlElement(name = "PAYFLAG")
    private String payFlag;
    /**
     *特管（
     0-普通药物,1-麻醉药,2-毒性药品,3-第一类精神类药物,4-第二类精神类药物,5-未知,6-剧药,7-非限制使用的抗菌药物,8-限制使用的抗菌药物,9-特殊使用的抗菌药物,10-含兴奋剂药品,11-高危药品,12-限制使用的抗菌药物,13-特殊使用的抗菌药物,14-辅助用药,15-营养药）
     */
    @XmlElement(name = "SPECIALCONTROLFLAG")
    private String specialControlFlag;
    /**
     *费别ID
     */
    @XmlElement(name = "FEEKINDID")
    private String feekindId;
    /**
     *费别名称
     */
    @XmlElement(name = "FEEKINDNAME")
    private String feekindName;
    /**
     *医保目录等级（1-甲类（统筹全部支付）,2-乙类（准予部分支付）,3-自费）
     */
    @XmlElement(name = "CATALOGRANK")
    private String catalogRank;
    /**
     *医保目录等级（1-甲类（统筹全部支付）,2-乙类（准予部分支付）,3-自费）
     */
    @XmlElement(name = "PAYPROPORTION")
    private String payProportion;



}
