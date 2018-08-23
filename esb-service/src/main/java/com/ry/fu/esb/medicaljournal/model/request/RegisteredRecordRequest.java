package com.ry.fu.esb.medicaljournal.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 挂号记录请求报文
 */
/*@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "REQUEST")*/
@Data
@EqualsAndHashCode(callSuper = false)
public class RegisteredRecordRequest implements Serializable {
    private static final long serialVersionUID = -3804978379162543699L;

    /*@XmlElement(name = "PATIENTIDS")
    private String patientIds;*/

    /**
     * 用户ID
     */
    private Long accountId;

    /**
     * 页码
     */
    //@XmlElement(name = "PAGENUM")
    private Integer pageNum;

    /**
     * 页面大小
     */
    //@XmlElement(name = "PAGESIZE")
    private Integer pageSize;
//    @XmlElement(name = "STARTDATE")
//    private String startDate=new DateTime().minusMonths(3).toString().substring(0,10);
//
//    @XmlElement(name = "ENDDATE")
//    private String endDate=new DateTime().toString().substring(0,10);


}
