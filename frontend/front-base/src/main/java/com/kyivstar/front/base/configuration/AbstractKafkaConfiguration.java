package com.kyivstar.front.base.configuration;

import com.kyivstar.common.dao.repository.UserSessionRepository;
import com.kyivstar.common.kafka.consumer.BusConsumer;
import com.kyivstar.common.kafka.consumer.KafkaTopicConsumer;
import com.kyivstar.common.kafka.producer.BasicKafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;

import java.util.Collections;
import java.util.Properties;
import java.util.function.Consumer;

import static org.apache.kafka.clients.CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.CommonClientConfigs.CLIENT_ID_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.*;
import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.*;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;

/**
 * Created by igor on 24.09.17.
 */
public abstract class AbstractKafkaConfiguration implements ApplicationListener<ApplicationEvent> {
    @Autowired
    private UserSessionRepository userSession;

    @Autowired
    KafkaProperties kafkaProperties;

    @Bean
    public BasicKafkaProducer topicProducer() {
        Properties props = loadKafkaProducerProperties(kafkaProperties);

        return new BasicKafkaProducer(props);
    }

    public BusConsumer createBusConsumer(Consumer<String> consumer) {
        Properties consumerProps = loadKafkaConsumerProperties(kafkaProperties);
        return new KafkaTopicConsumer(consumerProps, consumer,
                Collections.singletonList("task"),
                4);
    }
    public static Properties loadKafkaConsumerProperties(KafkaProperties consumerProperties) {
        Properties consumerProps = new Properties();
        KafkaProperties.Consumer consumer = consumerProperties.getConsumer();

        consumerProps.put(BOOTSTRAP_SERVERS_CONFIG,  consumer.getBootstrapServers());
        consumerProps.put(GROUP_ID_CONFIG,  consumer.getGroupId());

        consumerProps.put(CLIENT_ID_CONFIG,  consumer.getClientId());
        consumerProps.put(EXCLUDE_INTERNAL_TOPICS_CONFIG, false);
        consumerProps.put(MAX_POLL_RECORDS_CONFIG,  consumer.getMaxPollRecords());
        consumerProps.put(AUTO_OFFSET_RESET_CONFIG, "latest");
        consumerProps.put(ENABLE_AUTO_COMMIT_CONFIG, "false");

        consumerProps.put(KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProps.put(VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        return consumerProps;
    }

    public static Properties loadKafkaProducerProperties(KafkaProperties holder) {
        Properties producerProps = new Properties();
        KafkaProperties.Producer producer = holder.getProducer();

        producerProps.put(BOOTSTRAP_SERVERS_CONFIG, producer.getBootstrapServers());
        producerProps.put(ACKS_CONFIG,  producer.getAcks());


        producerProps.put(CLIENT_ID_CONFIG, "producer");
        producerProps.put(RETRIES_CONFIG,  producer.getRetries());

        producerProps.put(BATCH_SIZE_CONFIG,  producer.getBatchSize());
        producerProps.put(BUFFER_MEMORY_CONFIG,  producer.getBufferMemory());
        producerProps.put(KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        producerProps.put(VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArraySerializer");
        return producerProps;
    }

}