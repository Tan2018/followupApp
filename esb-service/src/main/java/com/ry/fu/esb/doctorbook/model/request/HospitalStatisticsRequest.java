package com.ry.fu.esb.doctorbook.model.request;

/**
* @Author:Boven
* @Description:	院情就医人次统计信息
* @Date: Created in 10:20 2018/7/9
*/
import lombok.Data;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @Author: telly
 * @Description: 检查结果查询（影像结果和其他结果）
 * @Date: Create in 16:22 2018/1/11
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "REQUEST")
@Data
public class HospitalStatisticsRequest implements Serializable{

    private static final long serialVersionUID = 7750109753243437661L;
    /**
     * 统计口径分类 1.按院区  2.按科室
     */
    @XmlElement(name = "COLLECTTYPE",required = false)
    private String collectType = "";
    /**
     * 医生Id
     */
    @XmlElement(name = "ACCOUNTID",required = false)
    private String doctorId ;
    /**
     * 统计数据分类 1、门诊人次2、住院人次3、手术台次
     */
    @XmlElement(name = "DATATYPE",required = false)
    private String dataType = "";
    /**
     * 统计数据分类 1、门诊人次2、住院人次3、手术台次
     */

    private String hospitalArea = "";

    /**
     * 起始日期（不填默认当天）
     */
    @XmlElement(name = "STARTDATE",required = false)
   private String startDate = new DateTime().minusDays(1).withMillisOfDay(0).toString().substring(0,10);
    //private String startDate = new DateTime().withMillisOfDay(0).toString().substring(0,10);
    /**
     * 结束日期（不填默认当天）
     */
    @XmlElement(name = "ENDDATE",required = false)
    private String endDate = new DateTime().toString().substring(0,10);
}
