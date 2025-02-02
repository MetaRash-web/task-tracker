package com.metarash.backend.controller;

import com.metarash.backend.dto.EmailDto;
import com.metarash.backend.producer.EmailProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/send-email")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:80")
public class SendEmailController {
    private final EmailProducer emailProducer;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailDto emailDto) {
        emailProducer.sendEmailMessage(emailDto);
        return ResponseEntity.ok("Message sent to Kafka");
    }

}
