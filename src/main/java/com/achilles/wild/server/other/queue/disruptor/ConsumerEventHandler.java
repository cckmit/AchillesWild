package com.achilles.wild.server.other.queue.disruptor;

import com.achilles.wild.server.business.manager.common.LogTimeInfoManager;
import com.achilles.wild.server.entity.common.LogTimeInfo;
import com.achilles.wild.server.tool.SpringContextUtil;
import com.achilles.wild.server.tool.json.JsonUtil;
import com.lmax.disruptor.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 消费者
 */
public class ConsumerEventHandler implements EventHandler<LogTimeInfo> {

    private final static Logger log = LoggerFactory.getLogger(ConsumerEventHandler.class);


    private LogTimeInfoManager logTimeInfoManager;

    private LogTimeInfoManager getLogTimeInfoManager() {
        if (logTimeInfoManager != null){
            return logTimeInfoManager;
        }
        logTimeInfoManager = SpringContextUtil.getBean(LogTimeInfoManager.class);
        return logTimeInfoManager;
    }

    private final List<LogTimeInfo> logTimeInfoList = new ArrayList<>();

    @Override
    public void onEvent(LogTimeInfo logTimeInfo, long sequence, boolean endOfBatch) {

        log.debug("consumer ----- sequence:"+sequence+",endOfBatch:"+endOfBatch+",event:"+ JsonUtil.toJsonString(logTimeInfo));

        if (logTimeInfo == null) {
            log.error("----------------------------空消息-------------------------sequence:"+sequence);
            return;
        }

        logTimeInfoList.add(logTimeInfo);
        try {

            if (logTimeInfoList.size() == 1){
                getLogTimeInfoManager().addLogs(logTimeInfoList);
                logTimeInfoList.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("----------------------------insert into db error-------------------------");
        }

    }

}
