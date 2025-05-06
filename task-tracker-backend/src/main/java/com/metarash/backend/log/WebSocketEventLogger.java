package com.metarash.backend.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Slf4j
@Component
public class WebSocketEventLogger {

    @EventListener
    public void handleSessionConnected(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        Object user = accessor.getUser();
        log.info("[WS-EVENT] CONNECTED: sessionId={}, principal={}", sessionId, user);
    }

    @EventListener
    public void handleSessionDisconnected(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        Object user = accessor.getUser();
        log.info("[WS-EVENT] DISCONNECTED: sessionId={}, principal={}", sessionId, user);
        log.info("[WS-EVENT] DISCONNECT REASON: {}", event.getCloseStatus());
    }
}