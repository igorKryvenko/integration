package com.kyivstar.ws.frontend.configuration;

import com.kyivstar.ws.frontend.service.ClientWebSocketHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {
  private static final String WS_PATH = "/ws-api";

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry wshr) {
    wshr.addHandler(requestHandler(), WS_PATH).setAllowedOrigins("*");
  }

  @Bean
  public ServletServerContainerFactoryBean createWebSocketContainer() {
    final ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
    container.setMaxTextMessageBufferSize(8192);
    container.setMaxBinaryMessageBufferSize(8192);
    return container;
  }

  @Bean
  public WebSocketHandler requestHandler() {
    return new ClientWebSocketHandler();
  }
}
