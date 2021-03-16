package com.achilles.wild.server.other.queue.disruptor;

import com.achilles.wild.server.entity.common.LogTimeInfo;
import com.lmax.disruptor.EventFactory;

public class DisruptorEventFactory implements EventFactory<LogTimeInfo> {

    @Override
    public LogTimeInfo newInstance() {
        return new LogTimeInfo();
    }
}
