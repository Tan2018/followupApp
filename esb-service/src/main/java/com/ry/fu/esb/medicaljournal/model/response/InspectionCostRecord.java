package com.ry.fu.esb.medicaljournal.model.response;

import com.ry.fu.esb.common.adapter.DateAdapater;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;

/**
 * @author ：Boven
 * @Description ：
 * @create ： 2018-04-03 11:13
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class InspectionCostRecord  implements Serializable{

    private static final long serialVersionUID = -8106431131087118230L;


    /**
    *4-检查，5-检验
    */
    @XmlElement(name="SOURCETYPE")
    private String sourceType;
    /**
    *项目ID
    */
    @XmlElement(name="ITEMID")
    private String itemId;
    /**
    *项目名称
    */
    @XmlElement(name="ITEMNAME")
    private String itemName;
    /**
    *价格
    */
    @XmlElement(name="AMOUNT")
    private String amount;
    /**
    *技诊(检验)科室编码
    */
    @XmlElement(name="EXAMINEDEPARTMENTNO")
    private String examineDepartMentNo;
    /**
    *技诊(检验)科室名称
    */
    @XmlElement(name="EXAMINEDEPARTMENTNAME")
    private String examineDepartMentName;
    /**
    *数量
    */
    @XmlElement(name="QUANTITY")
    private Long quantity;
    /**
    *执行时间
    */
    @XmlJavaTypeAdapter(DateAdapater.class)
    @XmlElement(name="EXECDATETIME")
    private String execDateTime;
    /**
    *执行状态：0-未执行,1-执行
    */
    @XmlElement(name="EXECSTATUS")
    private String execStatus;
    /**
    *描述
    */
    @XmlElement(name="DESCRIPTION")
    private String description;
    /**
    *是否急诊：	0-非急诊,1-急诊
    */
    @XmlElement(name="EMERGENCYFLAG")
    private String emergencyFlag;

    /**
    *申请医生
    */
    @XmlElement(name="REQUESTEMPLOYEENAME")
    private String requestEmployeeName;
    /**
    *申请时间
    */
    @XmlJavaTypeAdapter(DateAdapater.class)
    @XmlElement(name="REQUESTDATETIME")
    private String requestdateTime;
    /**
    *状态：0-录入,1-提交,2-登记,3-确认,4-记帐确认
    */
    @XmlElement(name="STATUS")
    private String status;
    /**
    *1-西药,2-中成药,3-中草药,4-用药方式,5-材料,6-项目,7-技诊费,8-标本,9-容器,10-手术,11-中草药煎法
    */
    @XmlElement(name="SOURCETYPEFLAG")
    private String sourceTypeFlag;

    private String sourceFlag;
    /**
     *0-未付费,1-已付费
     */
    @XmlElement(name="PAYFLAG")
    private String payFlag;

    /**
     *检查 检验类型
     */
    @XmlElement(name="FUNC_TYPE")
    private String funcType;
    /**
     *检查类型名称
     */
    @XmlElement(name="FUNC_NAME")
    private String funcName;


    /**
     * 就诊科室名称
     */
    @XmlElement(name="EXECUTDEPARTMENTTEXT")
    private String executDepartmentText;


}
