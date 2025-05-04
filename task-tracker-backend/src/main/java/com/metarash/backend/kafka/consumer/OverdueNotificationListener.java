package com.metarash.backend.kafka.consumer;

import com.metarash.dto.NotificationMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OverdueNotificationListener {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @KafkaListener(
            topics = "task-overdue-notifications",
            groupId = "backend-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void onOverdueNotification(NotificationMessage msg) {
        log.info("getting overdue notification: {}", msg);
        try {
            log.info("Sending overdue tasks notification to user: {}", msg.getUserEmail());
            simpMessagingTemplate.convertAndSendToUser(
                    msg.getUserEmail(),
                    "/topic/notifications",
                    msg
            );
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
