package com.ry.fu.net.jpush.service.impl;

import com.ry.fu.net.jpush.mapper.JpushHistoricalRecordsMapper;
import com.ry.fu.net.jpush.model.JpushHistoricalRecords;
import com.ry.fu.net.jpush.service.JpushHistoricalRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/6/4
 * @description 极光推送serviceImpl
 */
@Service
public class JpushHistoricalRecordsServiceImpl implements JpushHistoricalRecordsService{

    @Autowired
    private JpushHistoricalRecordsMapper jpushHistoricalRecordsMapper;

    public void saveHistoricalRecordsService(JpushHistoricalRecords jpushHistoricalRecords){
        jpushHistoricalRecordsMapper.insert(jpushHistoricalRecords);
    }
}
