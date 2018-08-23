package com.ry.fu.esb.medicaljournal.model.response;




import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;
import java.util.Date;

/**
 * 用户待缴费记录支付
 * @author ：Joker
 * @Description ：
 * @create ： 2018-4-12
 **/
@XmlAccessorType(XmlAccessType.NONE)
@Data
@EqualsAndHashCode(callSuper = false)
public class PayTheFeesResponeRecord implements Serializable{


    private static final long serialVersionUID = -6800126617880619951L;

    /**
     * 用户证件号码
     */
    @XmlElement(name = "RESULTCODE")
    private String resultCode;

    /**
     * 需处理的窗口窗口位置；
     */
    @XmlElement(name = "RESULTDESC")
    private String resultDesc;

    /**
     * 用户姓名
     */
    @XmlElement(name = "USERNAME")
    private String userName;

    /**
     * HIS就诊登记号
     */
    @XmlElement(name = "INFOSEQ")
    private String infoSeq;

    /**
     * 未缴费总金额(单位“分”)
     */
    @XmlElement(name = "PAYAMOUT")
    private String payAmout;

    /**
     * 个人自负金额(单位“分”)
     */
    @XmlElement(name = "SELFPAYAMOUT")
    private String selfPayAmout;

    /**
     * 医保记账金额(单位“分”)
     */
    @XmlElement(name = "ACCOUNTAMOUNT")
    private String accountAmount;


    /**
     * 医生名字
     */
    private String doctorName;


    /**
     * 就诊日期
     */
    private String visitTime;


    /**
     * 就诊时段
     */
    private String visitDate;

    /**
     * 医生科室
     */
    private String orgName;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 订单号；
     *
     */
    @XmlElement(required = true,name = "ORDERID")
    private String orderId;

    /**
     * 缴费订单明细集合
     */
    @XmlElement(name = "ORDERDETAILINFO")
    private List<PayTheFeesResponeRecordOrderDetailInfo> orderDetailInfo;

}
