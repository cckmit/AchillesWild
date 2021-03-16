package com.achilles.wild.server.other.queue.disruptor;

import com.achilles.wild.server.business.manager.common.LogTimeInfoManager;
import com.achilles.wild.server.entity.common.LogTimeInfo;
import com.achilles.wild.server.tool.SpringContextUtil;
import com.achilles.wild.server.tool.json.JsonUtil;
import com.lmax.disruptor.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;

import java.util.ArrayList;
import java.util.List;

/**
 * 消费者
 */
public class ConsumerEventHandler implements EventHandler<LogTimeInfo> {

    private final static Logger log = LoggerFactory.getLogger(ConsumerEventHandler.class);

//    @Autowired
//    LogTimeInfoManager logTimeInfoManager;

    private final List<LogTimeInfo> logTimeInfoList = new ArrayList<>();

    @Override
    public void onEvent(LogTimeInfo logTimeInfo, long sequence, boolean endOfBatch) {

        log.info("消费者消费的信息是：sequence:"+sequence+",endOfBatch:"+endOfBatch+",event:"+ JsonUtil.toJsonString(logTimeInfo));
        //这里停止1000ms是为了确定消费消息是异步的
//            Thread.sleep(1000);
        log.info("消费者处理消息开始");
        if (logTimeInfo == null) {
            log.error("----------------------------空消息-------------------------sequence:"+sequence);
            return;
        }

        logTimeInfoList.add(logTimeInfo);
        try {
            if (logTimeInfoList.size()==1){
                LogTimeInfoManager logTimeInfoManager = (LogTimeInfoManager) SpringContextUtil.getBean(LogTimeInfoManager.class);
                logTimeInfoManager.addLogs(logTimeInfoList);

                logTimeInfoList.clear();
            }
        } catch (BeansException e) {
            e.printStackTrace();
        }


        log.info("消费者处理消息结束");
    }

}
