package com.rangers.medicineservice.utils;

import com.rangers.medicineservice.util.MailSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SupportMailSender extends MailSender {

    private final String supportEmailTo;
    private static final String SUPPORT_SUBJECT = "support message";

    public SupportMailSender(@Value("${spring.mail.username}") String supportEmailTo) {
        this.supportEmailTo = supportEmailTo;
    }

    @Override
    public void send(String emailTo, String subject, String message) {
        super.send(supportEmailTo, SUPPORT_SUBJECT, message);
    }
}
