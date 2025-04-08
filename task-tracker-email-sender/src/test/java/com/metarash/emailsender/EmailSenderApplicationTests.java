package com.metarash.emailsender;

import com.metarash.emailsender.dto.EmailDto;
import com.metarash.emailsender.service.EmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class EmailSenderApplicationTests {

    @Mock
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @InjectMocks
    private EmailService emailService;

    @Test
    void testSendEmail() {
        // Arrange
        EmailDto emailDto = new EmailDto("recipient@example.com", "Test Subject", "Test Text");

        // Act
        emailService.sendEmail(emailDto);

        // Assert
        SimpleMailMessage expectedMessage = new SimpleMailMessage();
        expectedMessage.setFrom(fromEmail);
        expectedMessage.setTo(emailDto.recipient());
        expectedMessage.setSubject(emailDto.subject());
        expectedMessage.setText(emailDto.text());

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(javaMailSender, times(1)).send(captor.capture());

        SimpleMailMessage capturedMessage = captor.getValue();
        assertEquals(fromEmail, capturedMessage.getFrom());
        assertEquals(emailDto.recipient(), capturedMessage.getTo()[0]);
        assertEquals(emailDto.subject(), capturedMessage.getSubject());
        assertEquals(emailDto.text(), capturedMessage.getText());
    }
}
