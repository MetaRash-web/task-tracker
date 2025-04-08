package com.metarash.tasktrackerscheduler.producer;

import com.metarash.tasktrackerscheduler.dto.ReportDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Producer {

    private final KafkaTemplate<String, ReportDto> kafkaTemplate;

    public void sendAllTasks(ReportDto dayReport) {
        kafkaTemplate.send("all-tasks-topic", dayReport);
    }

    public void sendUnfinishedTasks(ReportDto dayReport) {
        kafkaTemplate.send("unfinished-tasks-topic", dayReport);
    }

    public void sendFinishedTasks(ReportDto dayReport) {
        kafkaTemplate.send("finished-tasks-topic", dayReport);
    }
}
