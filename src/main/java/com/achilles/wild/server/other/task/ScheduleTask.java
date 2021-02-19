package com.achilles.wild.server.other.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

//@Configuration
//@EnableScheduling
public class ScheduleTask {

    private final static Logger log = LoggerFactory.getLogger(ScheduleTask.class);

    //3.添加定时任务
    @Scheduled(cron = "0/5 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void configureTasks() {
        log.info("------task-----: " + LocalDateTime.now());
    }
}
