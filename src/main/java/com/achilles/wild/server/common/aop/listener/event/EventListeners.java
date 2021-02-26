package com.achilles.wild.server.common.aop.listener.event;

import com.achilles.wild.server.common.constans.CommonConstant;
import com.achilles.wild.server.entity.common.LogException;
import com.achilles.wild.server.business.manager.common.LogExceptionManager;
import com.achilles.wild.server.tool.json.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EventListeners {

    private final static Logger log = LoggerFactory.getLogger(EventListeners.class);

    @Autowired
    private LogExceptionManager logExceptionManager;

    @Async
    @EventListener
    public void addExceptionLogsEvent(ExceptionLogsEvent event) {

        if (event.getSource()==null){
            return;
        }

        LogException logException = (LogException) event.getSource();
        logExceptionManager.addLog(logException);

        MDC.put(CommonConstant.TRACE_ID, logException.getTraceId());

        log.debug("--------insert ExceptionLogs into DB------"+ JsonUtil.toJsonString(event.getSource()));

    }
}
