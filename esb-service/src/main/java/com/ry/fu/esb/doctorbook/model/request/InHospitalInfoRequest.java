package com.ry.fu.esb.doctorbook.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;

/**
* @Author:Boven
* @Description:检查检验记录查询入口
* @Date: Created in 14:20 2018/4/2
*/
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class InHospitalInfoRequest implements Serializable {

    private static final long serialVersionUID = -2521136659392496880L;
    /**
     *诊疗卡号
     */
    private String patientCardNo;

    /**
     *患者ID
     */
    private String patientId;

    /**
     *住院号
     */
    private String ipSeqNoText;

    /**
     *类型:检查检验为1,影像为2,其他为3
     */
    private String inspectionType = "1" ;

    /**
     * 起始日期（不填默认当天）
     */
    private String startDate= new DateTime().minusMonths(6).toString().substring(0,10) ;
    /**
     * 结束日期（不填默认当天）
     */
    private String endDate=new DateTime().toString().substring(0,10);

    /**
     * 页码,目前默认半年内为第一页,页码为1;
     * 半年以前的为2
     */
    private String pageNo;

}
