package com.metarash.backend.interceptor;

import com.metarash.backend.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketAuthChannelInterceptor implements ChannelInterceptor {

    private final JwtService jwtService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        log.info("[WS-INTERCEPTOR] Incoming command: {}", accessor.getCommand());
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String authHeader = accessor.getFirstNativeHeader("Authorization");
            log.info("[WS-INTERCEPTOR] Authorization header: {}", authHeader);
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                log.info("[WS-INTERCEPTOR] Extracted token: {}", token);
                String username = jwtService.getEmailFromToken(token);
                log.info("[WS-INTERCEPTOR] Parsed username/email from token: {}", username);
                boolean valid = jwtService.validateJwtToken(token);
                log.info("[WS-INTERCEPTOR] Token valid: {}", valid);
                if (username != null && valid) {
                    Authentication auth = new UsernamePasswordAuthenticationToken(username, null, /* authorities */ null);
                    accessor.setUser(auth);
                    log.info("[WS-INTERCEPTOR] Principal set: {}", username);
                } else {
                    log.info("[WS-INTERCEPTOR] JWT invalid or username not found");
                }
            } else {
                log.info("[WS-INTERCEPTOR] No valid Authorization header found");
            }
        }
        return message;
    }
}
