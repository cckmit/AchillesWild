package com.achilles.wild.server.common.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class AccountLock {

    private final static Logger LOG = LoggerFactory.getLogger(AccountLock.class);


    private static Cache<String,String> accountCache = CacheBuilder.newBuilder().concurrencyLevel(1000).maximumSize(20000).expireAfterWrite(2, TimeUnit.SECONDS).build();


    public static synchronized boolean lock(String key) {

        LOG.info("1111111111----lock-----------start-start-----------"+Thread.currentThread().getId()+"------------     value:"+accountCache.getIfPresent(key));

        if(accountCache.getIfPresent(key)!=null){
            return false;
        }

        accountCache.put(key,"1");

        LOG.info("222222222-------lock-----------end--end----------"+Thread.currentThread().getId()+"------------     value:"+accountCache.getIfPresent(key));

        return true;
    }


    public static boolean unLock(String key) {

        LOG.info("3333333--------unLock--unLock----------start--start-------"+Thread.currentThread().getId()+"---------------     value:"+accountCache.getIfPresent(key));

        accountCache.invalidate(key);

        if(accountCache.getIfPresent(key)!=null){
            return false;
        }

        LOG.info("44444444--------unLock-unLock----------end--end---------"+Thread.currentThread().getId()+"-------------     value:"+accountCache.getIfPresent(key));
        return true;
    }
}
