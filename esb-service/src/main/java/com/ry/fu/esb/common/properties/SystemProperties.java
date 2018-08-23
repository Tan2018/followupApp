package com.ry.fu.esb.common.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SystemProperties {

    @Value("${fua.SYSTEM_CODE}")
    private String systemCode;
    @Value("${fua.SYSTEM_PASSWORD}")
    private Integer systemPassword;
    @Value("${fua.SYSTEM_IP}")
    private String systemIp;
    @Value("${fua.SRV_VERSION}")
    private String serviceVision;

    //与App交互的appKey，用于校验拦截appToken所需要前缀加密字符串
    @Value("${fua.appKey}")
    private String appKey;

    @Value("${fua.cacherAppId}")
    private String cacherAppId;

    //阳光康众支付的收银台域名
    @Value("${fua.paymentHost}")
    private String paymentHost;

    //医保支付平台域名
    @Value("${fua.medPaymentHost}")
    private String medPaymentHost;

    //商户号
    @Value("${fua.paymentMarchantNo}")
    private String paymentMarchantNo;

    //医保商户号
    @Value("${fua.paymentMedMarchantNo}")
    private String paymentMedMarchantNo;

    //本机公网IP  或  本机域名
    @Value("${fua.localHost}")
    private String localHost;

    public String getSystemCode() {
        return systemCode;
    }

    public String getPaymentMedMarchantNo() {
        return paymentMedMarchantNo;
    }

    public void setPaymentMedMarchantNo(String paymentMedMarchantNo) {
        this.paymentMedMarchantNo = paymentMedMarchantNo;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public Integer getSystemPassword() {
        return systemPassword;
    }

    public void setSystemPassword(Integer systemPassword) {
        this.systemPassword = systemPassword;
    }

    public String getSystemIp() {
        return systemIp;
    }

    public void setSystemIp(String systemIp) {
        this.systemIp = systemIp;
    }

    public String getServiceVision() {
        return serviceVision;
    }

    public void setServiceVision(String serviceVision) {
        this.serviceVision = serviceVision;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getPaymentHost() {
        return paymentHost;
    }

    public void setPaymentHost(String paymentHost) {
        this.paymentHost = paymentHost;
    }

    public String getPaymentMarchantNo() {
        return paymentMarchantNo;
    }

    public void setPaymentMarchantNo(String paymentMarchantNo) {
        this.paymentMarchantNo = paymentMarchantNo;
    }

    public String getLocalHost() {
        return localHost;
    }

    public void setLocalHost(String localHost) {
        this.localHost = localHost;
    }

    public String getMedPaymentHost() {
        return medPaymentHost;
    }

    public void setMedPaymentHost(String medPaymentHost) {
        this.medPaymentHost = medPaymentHost;
    }

    public String getCacherAppId() {
        return cacherAppId;
    }

    public void setCacherAppId(String cacherAppId) {
        this.cacherAppId = cacherAppId;
    }
}
