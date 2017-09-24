package com.kyivstar.common.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;


public class KafkaTopicConsumer implements BusConsumer{
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaTopicConsumer.class);


    private ExecutorService executor;

    private Properties consumerProperties;

    private Consumer<String> consumer;

    private Collection<String> topics;

    private Collection<KafkaConsumerThread> threads;

    private int poolSize;

    public KafkaTopicConsumer(Properties props, Consumer<String> consumer, Collection<String> topics, int poolSize) {
        this.consumerProperties = props;
        this.consumer = consumer;
        this.poolSize = poolSize;
        this.topics = topics;
        this.threads = new ArrayList<>();
    }


    @Override
    public void start() {

        if(executor == null) {
            executor = Executors.newFixedThreadPool(poolSize);

            for (int i = 0; i < poolSize; i++) {
                KafkaConsumerThread consumerThread = new KafkaConsumerThread(consumerProperties,consumer);
                consumerThread.subscribe(topics);
                threads.add(consumerThread);
                executor.submit(consumerThread);
            }

        }
    }

    @Override
    public void shutdown() {
        LOGGER.info("Shutting down executor");
        if (this.executor != null) {
            threads.forEach(t -> {
                try{
                    t.close();
                } catch (IOException e) {
                    LOGGER.error("Exception: {}", e);
                }
            });
            this.executor.shutdown();
            try {
                LOGGER.info("Await termination");
                this.executor.awaitTermination(5000L, TimeUnit.MILLISECONDS);
            } catch (InterruptedException ignore) {
                this.executor.shutdownNow();
            }
        }
    }
}
