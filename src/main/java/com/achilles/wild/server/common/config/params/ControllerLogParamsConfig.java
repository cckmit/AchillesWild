package com.achilles.wild.server.common.config.params;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ControllerLogParamsConfig {

    @Value("${controller.log.time.open}")
    private Boolean ifOpenLog;

    @Value("${controller.log.time.insert.db}")
    private Boolean ifTimeLogInsertDb;

    @Value("${controller.log.time.of.time.consuming.limit}")
    private Integer timeLimit;

    @Value("${controller.log.time.of.count.limit.in.time}")
    private Integer countOfInsertDBInTime;

    @Value("${controller.log.time.of.insert.db.rate.per.second}")
    private Double rateOfInsertDBPerSecond;

    @Value("${controller.log.exception.insert.db}")
    private Boolean ifExceptionLogInsertDb;

    public Boolean getIfOpenLog() {
        return ifOpenLog;
    }

    public void setIfOpenLog(Boolean ifOpenLog) {
        this.ifOpenLog = ifOpenLog;
    }

    public Boolean getIfTimeLogInsertDb() {
        return ifTimeLogInsertDb;
    }

    public void setIfTimeLogInsertDb(Boolean ifTimeLogInsertDb) {
        this.ifTimeLogInsertDb = ifTimeLogInsertDb;
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    public Integer getCountOfInsertDBInTime() {
        return countOfInsertDBInTime;
    }

    public void setCountOfInsertDBInTime(Integer countOfInsertDBInTime) {
        this.countOfInsertDBInTime = countOfInsertDBInTime;
    }

    public Double getRateOfInsertDBPerSecond() {
        return rateOfInsertDBPerSecond;
    }

    public void setRateOfInsertDBPerSecond(Double rateOfInsertDBPerSecond) {
        this.rateOfInsertDBPerSecond = rateOfInsertDBPerSecond;
    }

    public Boolean getIfExceptionLogInsertDb() {
        return ifExceptionLogInsertDb;
    }

    public void setIfExceptionLogInsertDb(Boolean ifExceptionLogInsertDb) {
        this.ifExceptionLogInsertDb = ifExceptionLogInsertDb;
    }
}
