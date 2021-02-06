package com.achilles.wild.server.common.listener.event;

import org.springframework.context.ApplicationEvent;

public class EventListenerConfigEvent extends ApplicationEvent {

    public EventListenerConfigEvent(Object source) {
        super(source);
    }
}
