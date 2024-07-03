package com.rangers.medicineservice.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MailSenderTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private MailSender mailSenderService;

    @Test
    void sendTest() {
        String emailTo = "recipient@example.com";
        String subject = "Test Subject";
        String message = "Test Message";

        mailSenderService.send(emailTo, subject, message);

        verify(mailSender).send(createMailMessage(emailTo, subject, message));
    }

    private SimpleMailMessage createMailMessage(String emailTo, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(null);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        return mailMessage;
    }
}