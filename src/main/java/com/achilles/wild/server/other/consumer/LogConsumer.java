package com.achilles.wild.server.other.consumer;

import com.achilles.wild.server.entity.common.LogBizInfo;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class LogConsumer {

    private final static Logger log = LoggerFactory.getLogger(LogConsumer.class);

    @Autowired
    private BlockingQueue<LogBizInfo> logBizInfoQueue;

    @PostConstruct
    public void execute(){

        log.debug("-----logBizInfoQueue---before logQueue.poll size :"+logBizInfoQueue.size());

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor(
                new ThreadFactoryBuilder().setNameFormat("single_pool_worker_%d").build());

        service.scheduleAtFixedRate(()->{

            try {
                LogBizInfo logBizInfo = logBizInfoQueue.poll();
                log.debug("--------logQueue.poll logBizInfo:"+logBizInfo);

                //todo

                log.debug("-----logBizInfoQueue---after logQueue.poll size :"+logBizInfoQueue.size());
            } catch (Exception e) {
                System.out.println("发生异常");
            }

        }, 5, 6, TimeUnit.SECONDS);
    }

    @PreDestroy
    public void destroy(){

        log.debug("-----logBizInfoQueue---destroy :"+logBizInfoQueue.size());

    }
}
