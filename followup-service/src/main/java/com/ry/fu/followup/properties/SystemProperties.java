package com.ry.fu.followup.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/2/11 9:58
 * @description appKey，位于application.properties，读取system.app.*开头的键值对
 */
@ConfigurationProperties(prefix = "system.app")
public class SystemProperties {

    private String appKey;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }
}
