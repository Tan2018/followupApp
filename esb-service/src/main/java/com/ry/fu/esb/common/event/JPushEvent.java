package com.ry.fu.esb.common.event;

import org.springframework.context.ApplicationEvent;

import java.util.Map;

public class JPushEvent  extends ApplicationEvent {
    public JPushEvent(Map map) {
        super(map);
    }
}
