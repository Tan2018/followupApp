package com.ry.fu.esb.common.event;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JPushEventService implements ApplicationContextAware {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void saveAndPush(List<String> openId, String title, String notification_title, Integer pushType, String patientId, String orderId){
        Map map = new HashMap(8);
        map.put("openId",openId);
        map.put("title",title);
        map.put("notification_title",notification_title);
        map.put("pushType",pushType);
        map.put("patientId",patientId);
        map.put("orderId",orderId);
        applicationEventPublisher.publishEvent(new JPushEvent(map));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}
