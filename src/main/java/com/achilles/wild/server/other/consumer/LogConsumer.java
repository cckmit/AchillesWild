package com.achilles.wild.server.other.consumer;

import com.achilles.wild.server.business.manager.common.LogBizInfoManager;
import com.achilles.wild.server.common.constans.CommonConstant;
import com.achilles.wild.server.entity.common.LogBizInfo;
import com.achilles.wild.server.tool.generate.unique.GenerateUniqueUtil;
import com.achilles.wild.server.tool.page.PageUtil;
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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class LogConsumer {

    private final static Logger log = LoggerFactory.getLogger(LogConsumer.class);

    @Autowired
    private BlockingQueue<LogBizInfo> logBizInfoQueue;

    @Autowired
    private LogBizInfoManager logBizInfoManager;

    private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor(
            new ThreadFactoryBuilder().setNameFormat("single_pool_%d").build());

    @PostConstruct
    public void execute(){

        log.debug("-----logBizInfoQueue---start-------");

        service.scheduleAtFixedRate(()->{

            String traceId = GenerateUniqueUtil.getTraceId("log");
            MDC.put(CommonConstant.TRACE_ID,traceId);

            try {
                addLogs();
            } catch (Exception e) {
                e.printStackTrace();
                log.error("-----logBizInfoQueue--- :"+e.getMessage());
            }

        }, 5, 2, TimeUnit.SECONDS);
    }

    @PreDestroy
    public void destroy(){
        log.debug("-----logBizInfoQueue---destroy  size:"+logBizInfoQueue.size());
        addLogs();
    }

    private void addLogs(){
        int size = logBizInfoQueue.size();
        if (size==0){
            return;
        }
        log.debug("-----logBizInfoQueue---  size:"+size);

        List<LogBizInfo> logBizInfoList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            LogBizInfo logBizInfo = logBizInfoQueue.poll();
            if (logBizInfo==null){
                break;
            }
            logBizInfoList.add(logBizInfo);
        }
        if (logBizInfoList.size()==0){
            return;
        }
        List<List> pageList = PageUtil.getPageDataList(logBizInfoList,500);
        for(List list:pageList){
            logBizInfoManager.addLogs(list);
        }
    }
}
