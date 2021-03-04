package com.achilles.wild.server.common.listener.event;

import org.springframework.context.ApplicationEvent;

public class LogBizInfoEvent extends ApplicationEvent {

    public LogBizInfoEvent(Object source) {
        super(source);
    }
}
