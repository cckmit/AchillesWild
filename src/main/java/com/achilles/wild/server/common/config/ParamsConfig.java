package com.achilles.wild.server.common.config;

import com.achilles.wild.server.business.entity.info.Params;
import com.achilles.wild.server.business.manager.account.ParamsManager;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
public class ParamsConfig {

    private final static Logger log = LoggerFactory.getLogger(ParamsConfig.class);

    private volatile Cache<String, String> paramsCache = CacheBuilder.newBuilder().concurrencyLevel(100).maximumSize(1000).expireAfterWrite(24, TimeUnit.HOURS).build();

    @Autowired
    private ParamsManager paramsManager;

    public Cache<String, String> getParamsCache() {
        return paramsCache;
    }

//    private volatile Map<String,String> keyValMap;
//
//    public Map<String, String> getKeyValMap() {
//        return keyValMap;
//    }

    @PostConstruct
    public void initParams(){

        List<Params> paramsList = paramsManager.selectAll();

        log.info("------------initParams  size : "+ paramsList.size());
        if (paramsList.size()==0){
            return;
        }

        //todo redis
        for (Params params:paramsList) {
            paramsCache.put(params.getKey(),params.getVal());
        }

        //keyValMap = paramsList.stream().collect(Collectors.toMap(Params::getKey, Params::getVal, (key1 , key2)-> key2 ));
    }
}
