package com.achilles.wild.server.common.aop.listener.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class TaskExecutePool {

    private static final Logger log = LoggerFactory.getLogger(TaskExecutePool.class);

    @Bean("myTaskAsyncPool")
    public Executor myTaskAsyncPool() {

        log.info("--------------- myTaskAsyncPool   start------------");

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(1000);
        executor.setKeepAliveSeconds(30);
        executor.setThreadNamePrefix("AchillesWild-TaskExecutePool-");//设置线程名
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy()); //用于被拒绝任务的处理程序，它将抛出 RejectedExecutionException
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy()); //直接丢弃任务
        executor.initialize();

        return executor;
    }
}
