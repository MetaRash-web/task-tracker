package com.metarash.tasktrackerscheduler.kafka.producer;

import com.metarash.dto.ReportDto;
import com.metarash.dto.NotificationMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendAllTasks(ReportDto dayReport) {
        kafkaTemplate.send("all-tasks-topic", dayReport);
    }

    public void sendUnfinishedTasks(ReportDto dayReport) {
        kafkaTemplate.send("unfinished-tasks-topic", dayReport);
    }

    public void sendFinishedTasks(ReportDto dayReport) {
        kafkaTemplate.send("finished-tasks-topic", dayReport);
    }

    public void sendOverdueTasks(NotificationMessage notificationMessage) {
        kafkaTemplate.send("task-overdue-notifications", notificationMessage);
    }
}
