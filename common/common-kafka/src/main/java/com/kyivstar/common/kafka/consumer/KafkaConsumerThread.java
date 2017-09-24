package com.kyivstar.common.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Properties;
import java.util.UUID;
import java.util.function.Consumer;

import static org.apache.kafka.clients.CommonClientConfigs.CLIENT_ID_CONFIG;


/**
 * Created by igor on 24.09.17.
 */
public class KafkaConsumerThread implements Runnable, Closeable {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerThread.class);

    private KafkaConsumer<String, byte[]> kafkaConsumer;

    private Consumer<String> consumer;

    private volatile Boolean stop = false;
    public KafkaConsumerThread(Properties props, Consumer<String> consumer) {

    final String consumerClientId = props.get(CLIENT_ID_CONFIG) + "-" + UUID.randomUUID().toString();
        props.put(CLIENT_ID_CONFIG, consumerClientId);
        this.kafkaConsumer = new KafkaConsumer<>(props);
        this.consumer = consumer;
    }


    public void run() {
        while (!Thread.currentThread().isInterrupted() && !stop) {
            try {
                ConsumerRecords<String, byte[]> records = kafkaConsumer.poll(1000);
                if (!((records == null) || records.isEmpty())) {

                    processMessages(records);
                    kafkaConsumer.commitSync();

                }
            } catch (Exception ex) {
                LOGGER.error("Kafka consumer exception: " + ex);
            }
        }
        LOGGER.info("Kafka consumer thread " + Thread.currentThread().getId() + " interrupted");
        kafkaConsumer.close();
    }
    public void subscribe(Collection<String> topics) {
        this.kafkaConsumer.subscribe(topics);
    }

    private void processMessages(ConsumerRecords<String, byte[]> records) {
        records.forEach(r -> {
            try {
                consumer.accept(new String(r.value(), StandardCharsets.UTF_8));
            } catch (Exception e) {
                LOGGER.error("Error processing message: {}", e);
            }
        });
    }

    @Override
    public void close() throws IOException {
        stop = true;
    }
}
