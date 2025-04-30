package com.metarash.tasktrackerscheduler.kafka.consumer;

import com.metarash.dto.TaskEvent;
import com.metarash.tasktrackerscheduler.service.OverdueNotificationScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskEventListener {

    private final OverdueNotificationScheduler scheduler;

    @KafkaListener(
            topics = "task-events",
            groupId = "scheduler-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void onTaskEvent(TaskEvent event) {
        scheduler.updateDueDateIfEarlier(event.getDueDate());
    }
}
