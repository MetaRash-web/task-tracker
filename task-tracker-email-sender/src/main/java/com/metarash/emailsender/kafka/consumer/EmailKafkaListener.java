package com.metarash.emailsender.kafka.consumer;

import com.metarash.dto.EmailDto;
import com.metarash.dto.ReportDto;
import com.metarash.emailsender.mapper.EmailMapper;
import com.metarash.emailsender.service.EmailService;
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

    @KafkaListener(topics = "all-tasks-topic", groupId = "task-tracker")
    public void listenAllTasks(ReportDto reportDto) {
        log.info("Получено сообщение из all-tasks-topic: " + reportDto);
        EmailDto emailDto = EmailMapper.mapAllTasks(reportDto);
        sendEmailReport(emailDto);
    }

    @KafkaListener(topics = "unfinished-tasks-topic", groupId = "task-tracker")
    public void listenUnfinishedTasks(ReportDto reportDto) {
        log.info("Получено сообщение из unfinished-tasks-topic: " + reportDto);
        EmailDto emailDto = EmailMapper.mapUnfinishedTasks(reportDto);
        sendEmailReport(emailDto);
    }

    @KafkaListener(topics = "finished-tasks-topic", groupId = "task-tracker")
    public void listenFinishedTasks(ReportDto reportDto) {
        log.info("Получено сообщение из finished-tasks-topic: " + reportDto);
        EmailDto emailDto = EmailMapper.mapFinishedTasks(reportDto);
        sendEmailReport(emailDto);
    }

    private void sendEmailReport(EmailDto reportDto) {
        try {
            emailService.sendEmail(reportDto);
        } catch (MailException e) {
            log.severe("Ошибка при отправке письма: " + e.getMessage());
        }
    }

    @KafkaListener(topics = "email-sending-tasks", groupId = "task-tracker")
    public void listen(String message) {
        log.info("Получено сообщение из Kafka: " + message);
//        try {
//            EmailDto dto = objectMapper.readValue(message, EmailDto.class);
//            emailService.sendEmail(dto);
//
//        } catch (JsonProcessingException e) {
//            log.severe("Ошибка при парсинге: " + e.getMessage());
//        } catch (MailException e) {
//            log.severe("Ошибка при отправке письма: " + e.getMessage());
//        }
    }
}
