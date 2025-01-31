package com.metarash.emailsender.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.metarash.emailsender.dto.EmailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailKafkaListener {
    private final EmailService emailService;

    @KafkaListener(topics = "EMAIL_SENDING_TASKS", groupId = "task-tracker")
    public void listen(EmailDto emailDto) {
        emailService.sendEmail(emailDto);
    }
}
