package com.achilles.wild.server.common.aop.listener.event;

import org.springframework.context.ApplicationEvent;

public class ExceptionLogsEvent extends ApplicationEvent {

    public ExceptionLogsEvent(Object source) {
        super(source);
    }
}
