package com.achilles.wild.server.other.consumer;

import com.achilles.wild.server.business.manager.common.LogBizInfoManager;
import com.achilles.wild.server.entity.common.LogBizInfo;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @PostConstruct
    public void execute(){

        log.debug("-----logBizInfoQueue---start-------");

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor(
                new ThreadFactoryBuilder().setNameFormat("single_pool_worker_%d").build());

        service.scheduleAtFixedRate(()->{
            log.debug("-----logBizInfoQueue---before logQueue.poll size :"+logBizInfoQueue.size());
            List<LogBizInfo> logBizInfoList = new ArrayList<>();
            try {
                for (int i = 0; i < 500; i++) {
                    LogBizInfo logBizInfo = logBizInfoQueue.poll();
                    if (logBizInfo==null){
                        break;
                    }
                    logBizInfoList.add(logBizInfo);
                    log.debug("--------logQueue.poll logBizInfo:"+logBizInfo);
                    //todo
                }
                if (logBizInfoList.size()!=0){
                    //logBizInfoManager.addLog();
                }


                log.debug("-----logBizInfoQueue---after logQueue.poll size :"+logBizInfoQueue.size());
            } catch (Exception e) {
                System.out.println("发生异常");
            }

            log.debug("-----logBizInfoQueue---end-------");

        }, 5, 10, TimeUnit.SECONDS);
    }

    @PreDestroy
    public void destroy(){

        log.debug("-----logBizInfoQueue---destroy :"+logBizInfoQueue.size());

    }
}
