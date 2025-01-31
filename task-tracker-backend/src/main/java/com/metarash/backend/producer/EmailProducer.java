package com.metarash.backend.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendEmailMessage(String message) {
        kafkaTemplate.send("EMAIL_SENDING_TASKS", message);
    }
}
