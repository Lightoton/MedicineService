package com.rangers.medicineservice.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
/**
 * The {@code MailSender} class provides functionality to send simple email messages.
 * It uses {@link JavaMailSender} to send the emails.
 * <p>
 * This class is annotated with {@link Service} to indicate that it's a service component
 * in the Spring context.
 * </p>
 * <p>
 * The email sender's username is injected from the application properties using the {@code @Value} annotation.
 * </p>
 * <p>
 * Usage:
 * <pre>
 * {@code
 * @Autowired
 * private MailSender mailSender;
 *
 * mailSender.send("recipient@example.com", "Subject", "Message text");
 * }
 * </pre>
 * </p>
 *
 * @see JavaMailSender
 * @see SimpleMailMessage
 *
 * @author Volha Zadziarkouskaya
 */
@Service
public class MailSender {
    /**
     * The {@code JavaMailSender} instance used to send emails.
     */
    @Autowired
    private JavaMailSender mailSender;
    /**
     * The email address from which the emails are sent.
     * This value is injected from the application properties.
     */
    @Value("${spring.mail.username}")
    private String username;
    /**
     * Sends an email with the specified recipient, subject, and message text.
     *
     * @param emailTo the recipient's email address
     * @param subject the subject of the email
     * @param message the text of the email
     */
    public void send(String emailTo, String subject, String message){
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }
}
