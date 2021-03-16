package com.achilles.wild.server.other.queue.consumer;

import com.achilles.wild.server.business.manager.common.LogTimeInfoManager;
import com.achilles.wild.server.common.constans.CommonConstant;
import com.achilles.wild.server.entity.common.LogTimeInfo;
import com.achilles.wild.server.tool.generate.unique.GenerateUniqueUtil;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class LogConsumer {

    private final static Logger log = LoggerFactory.getLogger(LogConsumer.class);

    @Autowired
    private Queue<LogTimeInfo> logInfoConcurrentLinkedQueue;

    @Autowired
    private LogTimeInfoManager logTimeInfoManager;

    private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor(
            new ThreadFactoryBuilder().setNameFormat("single_pool_log_%d").build());

    @PostConstruct
    public void doIt(){

        log.debug("-----logInfoConcurrentLinkedQueue---start-------");

        service.scheduleAtFixedRate(()->{

            String traceId = GenerateUniqueUtil.getTraceId("log");
            MDC.put(CommonConstant.TRACE_ID,traceId);

            try {
                addLogs();
            } catch (Exception e) {
                e.printStackTrace();
                log.error("-----logInfoConcurrentLinkedQueue--- :"+e.getMessage());
            }

        }, 5, 2, TimeUnit.SECONDS);
    }

    @PreDestroy
    public void destroy(){
        log.debug("-----logInfoConcurrentLinkedQueue---destroy  size:"+ logInfoConcurrentLinkedQueue.size());
        addLogs();
    }

    private void addLogs(){

        int consumeCount = 0;

        List<LogTimeInfo> logTimeInfoList = new ArrayList<>();
        while (!logInfoConcurrentLinkedQueue.isEmpty()){
            LogTimeInfo logTimeInfo = logInfoConcurrentLinkedQueue.poll();
            if (logTimeInfo == null){
                break;
            }
            consumeCount++;
            logTimeInfoList.add(logTimeInfo);
            if (logTimeInfoList.size()<500){
                continue;
            }
            logTimeInfoManager.addLogs(logTimeInfoList);
            logTimeInfoList.clear();
        }

        if (consumeCount!=0){
            log.debug("-----logInfoConcurrentLinkedQueue--- this time consume count : "+consumeCount);
        }

        if (logTimeInfoList.size()==0){
            return;
        }
        logTimeInfoManager.addLogs(logTimeInfoList);
    }
}
