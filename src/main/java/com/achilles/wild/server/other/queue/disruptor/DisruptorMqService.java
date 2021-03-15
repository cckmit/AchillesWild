package com.achilles.wild.server.other.queue.disruptor;

public interface DisruptorMqService {

    /**
     * 消息
     * @param message
     */
    void sayHelloMq(String message);
}
