package com.metarash.backend.controller;

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
    public ResponseEntity<String> sendEmail(@RequestBody String message) {
        System.out.println("post request: " + message);
        emailProducer.sendEmailMessage(message);
        return ResponseEntity.ok("Message sent to Kafka");
    }

}
