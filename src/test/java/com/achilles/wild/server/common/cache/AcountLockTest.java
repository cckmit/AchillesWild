package com.achilles.wild.server.common.cache;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.achilles.wild.server.tool.generate.unique.GenerateUniqueUtil;
public class AcountLockTest {

    private final static Logger LOG = LoggerFactory.getLogger(AcountLockTest.class);

    private Cache<String,String> accountCache = CacheBuilder.newBuilder().concurrencyLevel(10).maximumSize(20).expireAfterWrite(12, TimeUnit.SECONDS).build();


    @Test
    public void theVeryIncome(){

        for(Integer i=1;i<=100;i++) {

            String key = GenerateUniqueUtil.getUuId();
            accountCache.put(key,"989");
            LOG.info("线程:"+Thread.currentThread().getId()+" ~~~~~~~~size:"+accountCache.size()+"~~~~~~~~~~  "+ accountCache.getIfPresent(key));
        }

    }

    @Test
    public void lock() throws Exception{

        for(Integer i=1;i<=5000;i++) {
            new Thread() {
                public void run() {

                    String key = "achilles";
                    AccountLock.lock(key);
                    LOG.info("线程:"+Thread.currentThread().getId()+"***************************************************** ");
                    AccountLock.unLock(key);

                }

                ;
            }.start();
        }
        Thread.sleep(10000l);
    }
}
