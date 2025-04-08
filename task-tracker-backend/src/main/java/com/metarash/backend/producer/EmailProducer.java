package com.metarash.backend.producer;

import com.metarash.backend.dto.EmailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

//@Service
//@RequiredArgsConstructor
//public class EmailProducer {
//    private final KafkaTemplate<String, EmailDto> kafkaTemplate;
//
//    public void sendEmailMessage(EmailDto emailDto) {
//        kafkaTemplate.sendDefault(emailDto);
//    }
//}
