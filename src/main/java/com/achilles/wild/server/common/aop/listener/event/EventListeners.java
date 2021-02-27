package com.achilles.wild.server.common.aop.listener.event;

import com.achilles.wild.server.common.constans.CommonConstant;
import com.achilles.wild.server.entity.common.LogExceptionInfo;
import com.achilles.wild.server.business.manager.common.LogExceptionInfoManager;
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
    private LogExceptionInfoManager logExceptionInfoManager;

    @Async
    @EventListener
    public void addExceptionLogsEvent(LogExceptionInfoEvent event) {

        if (event.getSource()==null){
            return;
        }

        LogExceptionInfo logExceptionInfo = (LogExceptionInfo) event.getSource();
        logExceptionInfoManager.addLog(logExceptionInfo);

        MDC.put(CommonConstant.TRACE_ID, logExceptionInfo.getTraceId());

        log.debug("--------insert ExceptionLogs into DB------"+ JsonUtil.toJsonString(event.getSource()));

    }
}
