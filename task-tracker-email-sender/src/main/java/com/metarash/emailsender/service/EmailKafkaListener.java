package com.metarash.emailsender.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.metarash.emailsender.dto.EmailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
@EnableKafka
public class EmailKafkaListener {
    private final Logger log = Logger.getLogger(EmailKafkaListener.class.getName());
    private final EmailService emailService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "EMAIL_SENDING_TASKS", groupId = "task-tracker")
    public void listen(String message) {
        log.info("Получено сообщение из Kafka: " + message);
        try {
            EmailDto dto = objectMapper.readValue(message, EmailDto.class);
            log.info("Отправка письма на " + dto.recipient());
            emailService.sendEmail(dto);
            log.info("Письмо отправлено!");
        } catch (JsonProcessingException e) {
            log.severe("Ошибка при парсинге: " + e.getMessage());
        } catch (MailException e) {
            log.severe("Ошибка при отправке письма: " + e.getMessage());
        }
    }
}
