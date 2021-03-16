package com.achilles.wild.server.other.queue.disruptor;

import com.achilles.wild.server.entity.common.LogTimeInfo;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DisruptorConfig {

    @Bean
    public RingBuffer<LogTimeInfo> messageModelRingBuffer() {

        //指定事件工厂
        DisruptorEventFactory factory = new DisruptorEventFactory();

        //指定ringbuffer字节大小，必须为2的N次方（能将求模运算转为位运算提高效率），否则将影响效率
        int bufferSize = 8;

        //单线程模式，获取额外的性能
        Disruptor<LogTimeInfo> disruptor = new Disruptor<>(
                factory,
                bufferSize,
                new ThreadFactoryBuilder().setNameFormat("disruptor_%d").build(),
                ProducerType.SINGLE,
                new BlockingWaitStrategy());

        //设置事件业务处理器---消费者
        disruptor.handleEventsWith(new ConsumerEventHandler());

        // 启动disruptor线程
        disruptor.start();

        //获取ringbuffer环，用于接取生产者生产的事件
        RingBuffer<LogTimeInfo> ringBuffer = disruptor.getRingBuffer();

        return ringBuffer;
    }

}
