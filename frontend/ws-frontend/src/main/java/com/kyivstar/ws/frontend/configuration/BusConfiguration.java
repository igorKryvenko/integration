package com.kyivstar.ws.frontend.configuration;

import com.kyivstar.common.kafka.consumer.BusConsumer;
import com.kyivstar.front.base.configuration.AbstractKafkaConfiguration;
import com.kyivstar.ws.frontend.service.WebSocketSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Created by igor on 24.09.17.
 */
@Configuration
public class BusConfiguration extends AbstractKafkaConfiguration {
    @Autowired
    private WebSocketSender webSocketSender;

    @Bean
    public BusConsumer busConsumer(){
        return createBusConsumer(webSocketSender::proceedMessage);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent) {
            busConsumer().start();
        } else if (event instanceof ContextClosedEvent) {
            busConsumer().shutdown();
        }
    }
}
