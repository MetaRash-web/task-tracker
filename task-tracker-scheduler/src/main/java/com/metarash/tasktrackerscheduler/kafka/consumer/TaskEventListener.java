package com.metarash.tasktrackerscheduler.kafka.consumer;

import com.metarash.dto.TaskEvent;
import com.metarash.tasktrackerscheduler.service.NotificationScheduler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskEventListener {

    private final NotificationScheduler notificationScheduler;

    @KafkaListener(
            topics = "task-events",
            groupId = "scheduler-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void onTaskEvent(TaskEvent event) {
        log.info("Received task event: {}", event);
        notificationScheduler.scheduleNotification(event);
    }
}
