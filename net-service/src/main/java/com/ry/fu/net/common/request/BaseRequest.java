package com.ry.fu.net.common.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "COMMONREQUEST")
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseRequest extends Request {

    private static final long serialVersionUID = 526295864811154739L;

    @XmlElement(required = true,name = "ACCESSTOKEN")
    private String accessToken;

    @XmlElement(required = true,name = "SERVICEVERSION")
    private String serviceVersion;

    @XmlElement(required = true,name = "SERVICECODE")
    private String serviceCode;

    @XmlElement(name = "REQUESTDATA")
    @XmlJavaTypeAdapter(value = RequestDataAdapter.class)
    private String requestData;

    private String originData;//原始请求数据

//    private Date currentDate = TimeUtils.getCurrentDate();//当前日期
}
