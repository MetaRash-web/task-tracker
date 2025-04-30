package com.metarash.backend.kafka.producer;

import com.metarash.dto.EmailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendEmailMessage(EmailDto emailDto) {
        kafkaTemplate.send("email-sending-tasks", emailDto);
    }
}
