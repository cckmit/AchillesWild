package com.achilles.wild.server.common.listener.event;

import org.springframework.context.ApplicationEvent;

public class LogExceptionInfoEvent extends ApplicationEvent {

    public LogExceptionInfoEvent(Object source) {
        super(source);
    }
}
