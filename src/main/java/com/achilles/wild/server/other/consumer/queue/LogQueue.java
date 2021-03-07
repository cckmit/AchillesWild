package com.achilles.wild.server.other.consumer.queue;

import com.achilles.wild.server.entity.common.LogBizInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class LogQueue {


    @Bean
    public BlockingQueue<LogBizInfo> logBizInfoQueue(){
        return new LinkedBlockingQueue<>(3);
    }
}