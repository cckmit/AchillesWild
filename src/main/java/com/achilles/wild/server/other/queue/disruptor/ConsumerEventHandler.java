package com.achilles.wild.server.other.queue.disruptor;

import com.achilles.wild.server.business.manager.common.LogTimeInfoManager;
import com.achilles.wild.server.entity.common.LogTimeInfo;
import com.achilles.wild.server.tool.SpringContextUtil;
import com.achilles.wild.server.tool.bean.BeanUtil;
import com.achilles.wild.server.tool.date.DateUtil;
import com.achilles.wild.server.tool.json.JsonUtil;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.lmax.disruptor.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 消费者
 */
public class ConsumerEventHandler implements EventHandler<LogTimeInfo>, InitializingBean,DisposableBean {

    private final static Logger log = LoggerFactory.getLogger(ConsumerEventHandler.class);

    private LogTimeInfoManager logTimeInfoManager;

    private Date lastUpdateTime;

    private final Integer batchSize = 500;

    private final Integer rate = 5;

    private final Integer expiredTime = 5;

    private LogTimeInfoManager getLogTimeInfoManager() {
        if (logTimeInfoManager != null){
            return logTimeInfoManager;
        }
        logTimeInfoManager = SpringContextUtil.getBean(LogTimeInfoManager.class);
        return logTimeInfoManager;
    }

    private final List<LogTimeInfo> logTimeInfoList = new ArrayList<>();

    private Thread.State threadState;

    @Override
    public void onEvent(LogTimeInfo logTimeInfo, long sequence, boolean endOfBatch) {

        log.debug("disruptor consumer ----- sequence:"+sequence+",endOfBatch:"+endOfBatch+",event:"+ JsonUtil.toJsonString(logTimeInfo));
        log.debug("disruptor getObjectSize:"+ BeanUtil.getObjectSize(logTimeInfo));

//        try {
//            Thread.sleep(15000L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        if (logTimeInfo == null) {
            log.error("disruptor consumer null sequence : "+sequence);
            return;
        }

        if (threadState==null){
            doIt();
        }
        logTimeInfoList.add(logTimeInfo);
        lastUpdateTime = DateUtil.getCurrentDate();
        if (logTimeInfoList.size() != batchSize){
            return;
        }
        try {
            synchronized (logTimeInfoList){

                if (logTimeInfoList.size() != batchSize) {
                    return;
                }

                log.debug("disruptor consumer event batch insert size : " + logTimeInfoList.size());

                getLogTimeInfoManager().addLogs(logTimeInfoList);
                logTimeInfoList.clear();
                lastUpdateTime = DateUtil.getCurrentDate();
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("-----disruptor consumer event batch insert error :"+e.getMessage());
        }
    }

    private void doIt(){

        if (lastUpdateTime==null || logTimeInfoList.size() == 0){
            return;
        }

        log.debug("-----Disruptor--consumer   task -start-------");

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder().setNameFormat("disruptor_consumer_task_%d").build());

        executor.scheduleAtFixedRate(()->{

            threadState = Thread.currentThread().getState();
            if (DateUtil.getGapSeconds(lastUpdateTime) <= expiredTime){
                return;
            }
            if (logTimeInfoList.size() == 0) {
                return;
            }

            try {
                synchronized (logTimeInfoList){

                    if (logTimeInfoList.size() == 0) {
                        return;
                    }

                    log.debug("disruptor consumer task batch insert size : " + logTimeInfoList.size());

                    getLogTimeInfoManager().addLogs(logTimeInfoList);
                    logTimeInfoList.clear();
                    lastUpdateTime = DateUtil.getCurrentDate();
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("-----disruptor consumer task batch insert error :"+e.getMessage());
            }

        }, 0, rate, TimeUnit.SECONDS);
    }


    @Override
    public void destroy() throws Exception {
        log.info("-----destroy------");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("-----afterPropertiesSet------");
    }
}
