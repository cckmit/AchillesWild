package com.achilles.wild.server.common.listener.event;

import org.springframework.context.ApplicationEvent;

public class MyApplicationEvent2 extends ApplicationEvent {

    public MyApplicationEvent2(Object source) {
        super(source);
    }
}
