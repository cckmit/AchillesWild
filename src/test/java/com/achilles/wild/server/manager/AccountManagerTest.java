package com.achilles.wild.server.manager;

import com.achilles.wild.server.SpringbootApplicationTests;
import com.achilles.wild.server.manager.account.AccountManager;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

public class AccountManagerTest extends SpringbootApplicationTests {

    private final static Logger LOG = LoggerFactory.getLogger(AccountManagerTest.class);

    @Resource
    private AccountManager accountManager;

    @Test
    public void reduce() throws Exception{

        for(Integer i=1;i<=1000;i++) {
            new Thread() {
                public void run() {
                    //boolean result = accountManager.reduceBalance(32L,1L);
                    //LOG.info("线程:"+Thread.currentThread().getId()+" ~~~~~~~~~~~~~~~~~~~~~  "+ result);
                }
            }.start();
        }
        Thread.sleep(12000l);
    }

}
