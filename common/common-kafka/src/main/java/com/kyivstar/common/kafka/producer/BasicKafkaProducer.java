package com.kyivstar.common.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * Created by igor on 24.09.17.
 */
public class BasicKafkaProducer {
    protected Producer<String, byte[]> producer;

    public Future<Void> send(String topic, String message) {
        ProducerRecord<String, byte[]> record = new ProducerRecord<>(topic, "1", message.getBytes(StandardCharsets.UTF_8));

        final CompletableFuture<Void> result = new CompletableFuture<>();
        producer.send(record, (RecordMetadata recordMetadata, Exception e) -> {
            if (e != null) {
                result.completeExceptionally(e);
            } else {
                result.complete(null);
            }
        });
        return result;
    }

    public BasicKafkaProducer(Properties kafkaProps) {
        producer = new KafkaProducer<>(kafkaProps);
    }

    public BasicKafkaProducer() {}
}
