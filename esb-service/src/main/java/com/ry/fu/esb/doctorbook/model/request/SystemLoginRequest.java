package com.ry.fu.esb.doctorbook.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author ：Boven
 * @Description ：随访系统登录认证
 * @create ： 2018-02-05 17:03
 **/
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "REQUEST")
@EqualsAndHashCode(callSuper = false)
public class SystemLoginRequest implements Serializable {

    private static final long serialVersionUID = 6277798639334779343L;
    /**
     * 平台医疗服务人员账户（工号）
     */
    @XmlElement(name = "USER_NAME")
    private String userName;
    /**
     * 平台医疗服务附属人员登陆名(为空表示只登陆主账号)
     */
    @XmlElement(name = "SUB_USER_NAME")
    private String subUserName;
    /**
     * 平台医疗服务人员登录密码
     */
    @XmlElement(name = "USER_PWD")
    private String userPwd;
    /**
     * 时间戳
     */
    private long timeStamp=System.currentTimeMillis();
    /**
     * 登陆平台
     */
    private String appId;
    /**
     * 医生状态：0.正式医生、1.附属账号、2未知、3其他
     */
    private String doctorFlag="0";



}
