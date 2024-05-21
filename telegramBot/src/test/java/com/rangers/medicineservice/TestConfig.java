package com.rangers.medicineservice;

import com.rangers.medicineservice.utils.SupportMailSender;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class TestConfig {
    @Bean
    public JavaMailSender javaMailSender() {
        return Mockito.mock(JavaMailSender.class);
    }
    @Bean
    public SupportMailSender supportMainSender() {
        return new SupportMailSender("support@example.com");
    }
}
