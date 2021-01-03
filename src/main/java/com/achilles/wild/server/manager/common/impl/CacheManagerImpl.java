package com.achilles.wild.server.manager.common.impl;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.achilles.wild.server.manager.common.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class CacheManagerImpl implements CacheManager {


    private Cache<String,String> accountCache = CacheBuilder.newBuilder().concurrencyLevel(1000).maximumSize(20000).expireAfterWrite(2, TimeUnit.SECONDS).build();

    @Override
    public boolean lock(String key,int seconds) {

        accountCache.put(key,"1");
        return true;
    }

    @Override
    public boolean unLock(String key) {
        return true;
    }
}
