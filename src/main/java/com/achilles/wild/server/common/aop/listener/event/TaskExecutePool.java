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
        executor.setCorePoolSize(5);//配置核心线程数
        executor.setMaxPoolSize(10);//配置核心线程数
        executor.setQueueCapacity(1000);//配置队列容量
        executor.setKeepAliveSeconds(30);//设置线程活跃时间
        executor.setThreadNamePrefix("AchillesWild-TaskExecutePool-");//设置线程名
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); //拒绝策略:由调用方线程运行
        executor.initialize();

        return executor;
    }
}
