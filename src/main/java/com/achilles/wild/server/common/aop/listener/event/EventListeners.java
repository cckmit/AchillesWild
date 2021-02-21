package com.achilles.wild.server.common.aop.listener.event;

import com.achilles.wild.server.entity.ExceptionLogs;
import com.achilles.wild.server.business.manager.common.ExceptionLogsManager;
import com.achilles.wild.server.tool.json.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EventListeners {

    private final static Logger log = LoggerFactory.getLogger(EventListeners.class);

    @Autowired
    private ExceptionLogsManager exceptionLogsManager;

    @Async
    @EventListener
    public void addExceptionLogsEvent(ExceptionLogsEvent event) {

        if (event.getSource()==null){
            return;
        }

        ExceptionLogs exceptionLogs = (ExceptionLogs) event.getSource();
        exceptionLogsManager.addLog(exceptionLogs);

        log.debug("--------insert ExceptionLogs into DB------"+ JsonUtil.toJsonString(event.getSource()));

    }
}
