package com.achilles.wild.server.other.queue.config;

import com.achilles.wild.server.entity.common.LogTimeInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class LogQueue {


    //@Bean
    public Queue<LogTimeInfo> logBizInfoQueue(){
        return new LinkedBlockingQueue<>(10000);
    }

    @Bean
    public Queue<LogTimeInfo> logInfoConcurrentLinkedQueue(){
        return new ConcurrentLinkedQueue<>();
    }
}
