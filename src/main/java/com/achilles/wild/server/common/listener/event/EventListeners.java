package com.achilles.wild.server.common.listener.event;

import com.achilles.wild.server.business.manager.common.LogBizInfoManager;
import com.achilles.wild.server.business.manager.common.LogExceptionInfoManager;
import com.achilles.wild.server.business.manager.common.LogFilterInfoManager;
import com.achilles.wild.server.entity.common.LogBizInfo;
import com.achilles.wild.server.entity.common.LogExceptionInfo;
import com.achilles.wild.server.entity.common.LogFilterInfo;
import com.achilles.wild.server.tool.json.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Async
@Component
public class EventListeners {

    private final static Logger log = LoggerFactory.getLogger(EventListeners.class);

    @Autowired
    private LogExceptionInfoManager logExceptionInfoManager;

    @Autowired
    private LogFilterInfoManager logFilterInfoManager;

    @Autowired
    private LogBizInfoManager logBizInfoManager;


    @EventListener
    public void addLogBizInfoEvent(LogBizInfoEvent event) {

        if (event.getSource()==null){
            return;
        }

        LogBizInfo logBizInfo = (LogBizInfo) event.getSource();
        log.debug("--------insert LogBizInfo into DB------"+ JsonUtil.toJsonString(logBizInfo));
        logBizInfoManager.addLog(logBizInfo);


    }

    @EventListener
    public void addLogFilterEvent(LogFilterInfoEvent event) {

        if (event.getSource()==null){
            return;
        }

        LogFilterInfo logFilterInfo = (LogFilterInfo) event.getSource();
        log.debug("--------insert LogFilterInfo into DB------"+ JsonUtil.toJsonString(logFilterInfo));
        logFilterInfoManager.addLog(logFilterInfo);

    }

    @EventListener
    public void addLogExceptionEvent(LogExceptionInfoEvent event) {

        if (event.getSource()==null){
            return;
        }

        LogExceptionInfo logExceptionInfo = (LogExceptionInfo) event.getSource();
        log.debug("--------insert LogExceptionInfo into DB------"+ JsonUtil.toJsonString(logExceptionInfo));
        logExceptionInfoManager.addLog(logExceptionInfo);
    }
}
