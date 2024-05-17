package com.rangers.medicineservice.utils;

import com.rangers.medicineservice.TestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class SupportMailSenderTest {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SupportMailSender supportMainSender;

    @Test
    void sendTest() {
        String message = "Test message";

        // Выполнение
        supportMainSender.send("", "", message);

        // Проверка
        ArgumentCaptor<SimpleMailMessage> argument = ArgumentCaptor.forClass(SimpleMailMessage.class);
        Mockito.verify(mailSender).send(argument.capture());

        SimpleMailMessage mailMessage = argument.getValue();
        assertEquals("support@example.com", Objects.requireNonNull(mailMessage.getTo())[0]); // Проверяем, что письмо отправлено на адрес поддержки
        assertEquals("support message", mailMessage.getSubject());
        assertEquals(message, mailMessage.getText());
    }
}