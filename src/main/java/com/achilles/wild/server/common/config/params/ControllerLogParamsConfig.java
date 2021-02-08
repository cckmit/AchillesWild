package com.achilles.wild.server.common.config.params;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ControllerLogParamsConfig {

    @Autowired
    private ParamsConfig paramsConfig;

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
        String val = paramsConfig.getParamsCache().getIfPresent("controller.log.time.open");
        if(val!=null){
            ifOpenLog = Boolean.valueOf(val);
        }
        return ifOpenLog;
    }

    public Boolean getIfTimeLogInsertDb() {
        String val = paramsConfig.getParamsCache().getIfPresent("controller.log.time.insert.db");
        if(val!=null){
            ifTimeLogInsertDb = Boolean.valueOf(val);
        }
        return ifTimeLogInsertDb;
    }

    public Integer getTimeLimit() {
        String val = paramsConfig.getParamsCache().getIfPresent("controller.log.time.of.time.consuming.limit");
        if(val!=null){
            timeLimit = Integer.valueOf(val);
        }
        return timeLimit;
    }

    public Integer getCountOfInsertDBInTime() {
        String val = paramsConfig.getParamsCache().getIfPresent("controller.log.time.of.count.limit.in.time");
        if(val!=null){
            countOfInsertDBInTime = Integer.valueOf(val);
        }
        return countOfInsertDBInTime;
    }

    public Double getRateOfInsertDBPerSecond() {
        String val = paramsConfig.getParamsCache().getIfPresent("controller.log.time.of.insert.db.rate.per.second");
        if(val!=null){
            rateOfInsertDBPerSecond = Double.valueOf(val);
        }
        return rateOfInsertDBPerSecond;
    }

    public Boolean getIfExceptionLogInsertDb() {
        String val = paramsConfig.getParamsCache().getIfPresent("controller.log.exception.insert.db");
        if(val!=null){
            ifExceptionLogInsertDb = Boolean.valueOf(val);
        }
        return ifExceptionLogInsertDb;
    }
}
