package com.kyivstar.ws.frontend.service;

import org.springframework.web.socket.*;

/**
 * Created by igor on 24.09.17.
 */
public class ClientWebSocketHandler implements org.springframework.web.socket.WebSocketHandler {
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {

    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {

    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
