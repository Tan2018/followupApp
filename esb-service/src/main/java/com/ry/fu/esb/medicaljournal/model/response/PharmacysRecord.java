package com.ry.fu.esb.medicaljournal.model.response;

import com.ry.fu.esb.medicaljournal.model.OrderDetail;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * @author ：Joker
 * @Description ：取药详情
 * @create ： 2018-06-04 15:33
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class PharmacysRecord implements Serializable {


    private static final long serialVersionUID = -7989625809025904003L;
    /**
     * 科室
     */
    private String dept;

    /**
     * 医生名字
     */
    private String doctorName;

    /**
     * 取药地址
     */
    private String pharmacyAddress;

    /**
     * 订单ID；
     */
    private String orderId;

    /**
     * 创建时间；
     */
    private Date createTime;

    /**
     * 药品详情
     */
    private List<OrderDetail> orderDetail;
}
