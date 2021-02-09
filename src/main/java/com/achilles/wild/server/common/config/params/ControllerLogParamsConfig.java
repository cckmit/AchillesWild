package com.achilles.wild.server.common.config.params;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ControllerLogParamsConfig {

    @Autowired
    private ParamsConfig paramsConfig;

    @Autowired
    private Environment environment;

    public Boolean getIfOpenLog() {
        String key = "controller.log.time.open";
        String val = paramsConfig.getParamsCache().getIfPresent(key);
        Boolean ifOpenLog;
        if(val!=null){
            ifOpenLog = Boolean.valueOf(val);
        }else{
            ifOpenLog = Boolean.valueOf(environment.getProperty(key));
        }
        return ifOpenLog;
    }

    public Boolean getIfTimeLogInsertDb() {
        String key = "controller.log.time.insert.db";
        String val = paramsConfig.getParamsCache().getIfPresent(key);
        Boolean ifTimeLogInsertDb;
        if(val!=null){
            ifTimeLogInsertDb = Boolean.valueOf(val);
        }else {
            ifTimeLogInsertDb = Boolean.valueOf(environment.getProperty(key));
        }
        return ifTimeLogInsertDb;
    }

    public Integer getTimeLimit() {
        String key = "controller.log.time.of.time.consuming.limit";
        String val = paramsConfig.getParamsCache().getIfPresent(key);
        Integer timeLimit;
        if(val!=null){
            timeLimit = Integer.valueOf(val);
        }else {
            timeLimit = Integer.valueOf(environment.getProperty(key));
        }
        return timeLimit;
    }

    public Integer getCountOfInsertDBInTime() {
        String key = "controller.log.time.of.count.limit.in.time";
        String val = paramsConfig.getParamsCache().getIfPresent(key);
        Integer countOfInsertDBInTime;
        if(val!=null){
            countOfInsertDBInTime = Integer.valueOf(val);
        }else{
            countOfInsertDBInTime = Integer.valueOf(environment.getProperty(key));
        }
        return countOfInsertDBInTime;
    }

    public Double getRateOfInsertDBPerSecond() {
        String key = "controller.log.time.of.insert.db.rate.per.second";
        String val = paramsConfig.getParamsCache().getIfPresent(key);
        Double rateOfInsertDBPerSecond;
        if(val!=null){
            rateOfInsertDBPerSecond = Double.valueOf(val);
        }else{
            rateOfInsertDBPerSecond = Double.valueOf(environment.getProperty(key));
        }
        return rateOfInsertDBPerSecond;
    }

    public Boolean getIfExceptionLogInsertDb() {
        String key = "controller.log.exception.insert.db";
        String val = paramsConfig.getParamsCache().getIfPresent(key);
        Boolean ifExceptionLogInsertDb;
        if(val!=null){
            ifExceptionLogInsertDb = Boolean.valueOf(val);
        }else{
            ifExceptionLogInsertDb = Boolean.valueOf(environment.getProperty(key));
        }
        return ifExceptionLogInsertDb;
    }
}
