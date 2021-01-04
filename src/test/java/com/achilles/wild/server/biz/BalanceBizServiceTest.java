package com.achilles.wild.server.biz;

import com.achilles.wild.server.model.request.account.BalanceRequest;
import com.achilles.wild.server.model.response.DataResult;
import com.achilles.wild.server.tool.ThreadUtil;
import com.achilles.wild.server.tool.date.DateUtil;
import com.achilles.wild.server.tool.generate.unique.GenerateUniqueUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.achilles.wild.server.tool.BaseSpringJUnitTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class BalanceBizServiceTest extends BaseSpringJUnitTest {

    private final static Logger LOG = LoggerFactory.getLogger(BalanceBizServiceTest.class);

    @Resource
    private BalanceBiz balanceBiz;

    private static ExecutorService poolExecutor = ThreadUtil.getExecutor(2,9,10);

    private Cache<String,String> accountCache = CacheBuilder.newBuilder().concurrencyLevel(10).maximumSize(20).expireAfterWrite(12, TimeUnit.SECONDS).build();


    @Test
    public void add(){

        long start = DateUtil.getCurrentDate().getTime();
        for(Integer i=1;i<=100;i++) {
            BalanceRequest request = new BalanceRequest();
            request.setUserId("ACHILLES_1590254077133");
            request.setKey(GenerateUniqueUtil.getUuId());
            request.setAmount(1l);
            request.setTradeDate(new Date());
            DataResult result = balanceBiz.add(request);
            System.out.println(result);
        }
        long end = DateUtil.getCurrentDate().getTime();
        System.out.println("******************************************      "+(end-start)/1000);
    }


    @Test
    public void add2() throws Exception{

        long start = DateUtil.getCurrentDate().getTime();
        for(Integer i=1;i<=100;i++) {
            new Thread() {
                public void run() {
                    BalanceRequest request = new BalanceRequest();
                    request.setUserId("ACHILLES_1590254077133");
                    request.setKey(GenerateUniqueUtil.getUuId());
                    request.setAmount(1L);
                    request.setTradeDate(new Date());
                    DataResult result = balanceBiz.add(request);
                    LOG.info("匿名内部类创建线程方式1..."+Thread.currentThread().getName());
                }

                ;
            }.start();
        }
        long end = DateUtil.getCurrentDate().getTime();
        Thread.sleep(10000l);
        LOG.info("******************************************      "+(end-start)/1000);
    }
}
