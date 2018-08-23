package com.ry.fu.esb.jpush.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;
/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/5/12
 * @description 危急值
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUESTDATA")
@Data
@EqualsAndHashCode(callSuper = false)
public class SendCriticalValuesRequest implements Serializable{
    /**
     * 检验标本号
     */
    @XmlElement(required = true, name = "LISLABLENO")
    private Long lisLableNo;
    /**
     * 检验申请单ID
     */
    @XmlElement(required = false, name = "EXAMINEREQUESTID")
    private Long examineRequestId;
    /**
     * 患者ID
     */
    @XmlElement(required = false, name = "PATIENTID")
    private String patientId;
    /**
     * 患者姓名
     */
    @XmlElement(required = false, name = "PATIENTNAME")
    private String patientName;
    /**
     *
     */
    @XmlElementWrapper(name="NOTELIST")
    @XmlElement(name="PERSON")
    private List<NoteList> noteList;
    /**
     * 住院患者ID
     */
    @XmlElement(required = false, name = "INPATIENTID")
    private String inPatientId;
    /**
     * 住院号
     */
    @XmlElement(required = false, name = "IPSEQNOTEXT")
    private String ipSeqnoText;
    /**
     * 住院次数
     */
    @XmlElement(required = true, name = "IPTIMES")
    private String ipTimes;
    /**
     *
     */
    @XmlElementWrapper(name="ITEMLIST")
    @XmlElement(name="ITEM")
    private List<ItemList> itemList;
}
