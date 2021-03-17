package com.achilles.wild.server.other.queue.disruptor;

import com.achilles.wild.server.business.manager.common.LogTimeInfoManager;
import com.achilles.wild.server.entity.common.LogTimeInfo;
import com.achilles.wild.server.tool.SpringContextUtil;
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
    public void afterPropertiesSet() throws Exception {
        log.info("-----afterPropertiesSet------");
    }

    @Override
    public void onEvent(LogTimeInfo logTimeInfo, long sequence, boolean endOfBatch) {

        log.debug("consumer ----- sequence:"+sequence+",endOfBatch:"+endOfBatch+",event:"+ JsonUtil.toJsonString(logTimeInfo));

        if (logTimeInfo == null) {
            log.error("----------------------------空消息-------------------------sequence:"+sequence);
            return;
        }

        if (threadState==null){
            doIt();
        }

        logTimeInfoList.add(logTimeInfo);
        lastUpdateTime = DateUtil.getCurrentDate();
        if (logTimeInfoList.size() < 5){
            return;
        }
        try {
            synchronized (logTimeInfoList){
                if (logTimeInfoList.size() != 5) {
                    return;
                }
                getLogTimeInfoManager().addLogs(logTimeInfoList);
                logTimeInfoList.clear();
                lastUpdateTime = DateUtil.getCurrentDate();

            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("----------------------------insert into db error-------------------------");
        }
    }

    @Override
    public void destroy() throws Exception {
        log.info("-----destroy------");
    }

    private void doIt(){

        if (lastUpdateTime==null || logTimeInfoList.size() == 0){
            return;
        }

        log.debug("-----Disruptor--consumer   task -start-------");

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor(
                new ThreadFactoryBuilder().setNameFormat("disruptor_consumer_task_%d").build());

        service.scheduleAtFixedRate(()->{

            threadState = Thread.currentThread().getState();
            if (DateUtil.getGapSeconds(lastUpdateTime) <= 10){
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
                    getLogTimeInfoManager().addLogs(logTimeInfoList);
                    logTimeInfoList.clear();
                    lastUpdateTime = DateUtil.getCurrentDate();
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("-----Disruptor--consumer   task--- :"+e.getMessage());
            }

        }, 0, 7, TimeUnit.SECONDS);
    }

}
