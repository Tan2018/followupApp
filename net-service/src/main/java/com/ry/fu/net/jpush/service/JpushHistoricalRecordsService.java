package com.ry.fu.net.jpush.service;

import com.ry.fu.net.jpush.model.JpushHistoricalRecords;
/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/6/4
 * @description 极光推送service
 */
public interface JpushHistoricalRecordsService{
    /**
     * 保存推送记录
     * @param jpushHistoricalRecords
     */
    public void saveHistoricalRecordsService(JpushHistoricalRecords jpushHistoricalRecords);
}
