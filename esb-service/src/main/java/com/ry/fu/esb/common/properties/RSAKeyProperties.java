package com.ry.fu.esb.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/4/3 16:56
 * @description RSAKey配置
 */
@Configuration
@PropertySource("classpath:/conf/RSAkey.properties")
@ConfigurationProperties(prefix = "rsa")
public class RSAKeyProperties {

    /**
     * 收银台公钥
     */
    private String casherPublicKey;

    /**
     * 收银台私钥
     */
    private String casherPrivateKey;

    /**
     * 医保公钥(商户)
     */
    private String insurancePublicKey;

    /**
     * 医保私钥(商户)
     */
    private String insurancePrivateKey;



    /**
     * 医保服务方公钥（平台）
     */
    private String insuranceServicePublicKey;

    /**
     * 服务方公钥
     */
    private String insuranceServiceKey;


    public String getCasherPublicKey() {
        return casherPublicKey;
    }

    public void setCasherPublicKey(String casherPublicKey) {
        this.casherPublicKey = casherPublicKey;
    }

    public String getCasherPrivateKey() {
        return casherPrivateKey;
    }

    public void setCasherPrivateKey(String casherPrivateKey) {
        this.casherPrivateKey = casherPrivateKey;
    }

    public String getInsurancePublicKey() {
        return insurancePublicKey;
    }

    public void setInsurancePublicKey(String insurancePublicKey) {
        this.insurancePublicKey = insurancePublicKey;
    }

    public String getInsurancePrivateKey() {
        return insurancePrivateKey;
    }

    public void setInsurancePrivateKey(String insurancePrivateKey) {
        this.insurancePrivateKey = insurancePrivateKey;
    }

    public String getInsuranceServiceKey() {
        return insuranceServiceKey;
    }

    public void setInsuranceServiceKey(String insuranceServiceKey) {
        this.insuranceServiceKey = insuranceServiceKey;
    }

    public String getInsuranceServicePublicKey() {
        return insuranceServicePublicKey;
    }

    public void setInsuranceServicePublicKey(String insuranceServicePublicKey) {
        this.insuranceServicePublicKey = insuranceServicePublicKey;
    }
}
