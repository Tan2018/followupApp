package com.ry.fu.esb.doctorbook.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.io.Serializable;
import java.util.List;

/**
 *
 *
 * @author ：Boven
 * @Description ：影像响应实体
 * @create ： 2018-03-12 11:09
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class GetImageRecordSeris implements Serializable{


    private static final long serialVersionUID = 2071115265290481353L;
    /**
    *申请单号
    */
    @XmlElement(name = "SERIES_NO")
    private String seriesNo;
    /**
    *分组描述
    */
    @XmlElement(name = "SERIES_DESC")
    private String seriesDesc;
    @XmlElementWrapper(name = "IMAGELIS")
    @XmlElement(name = "IMAGE")
    private List<GetImageRecordImage> imageLis;





}
