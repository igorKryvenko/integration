package com.kyivstar.common.kafka.consumer;

/**
 * Created by igor on 24.09.17.
 */
public interface BusConsumer {
    void shutdown();
    void start();
}
