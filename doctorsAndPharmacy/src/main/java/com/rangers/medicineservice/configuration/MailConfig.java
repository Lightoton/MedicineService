package com.rangers.medicineservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * The {@code MailConfig} class provides configuration for the JavaMailSender bean.
 * <p>
 * This class is annotated with {@link Configuration} to indicate that it's a Spring configuration class.
 * It reads email configuration properties from the application properties file and sets up a {@link JavaMailSender} bean.
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * @Autowired
 * private JavaMailSender mailSender;
 * }
 * </pre>
 * </p>
 *
 * @see JavaMailSender
 * @see JavaMailSenderImpl
 * @see Properties
 *
 * @author Volha Zadziarkouskaya
 */
@Configuration
public class MailConfig {

    /**
     * The host address of the mail server.
     */
    @Value("${spring.mail.host}")
    private String host;

    /**
     * The username for the mail server authentication.
     */
    @Value("${spring.mail.username}")
    private String username;

    /**
     * The password for the mail server authentication.
     */
    @Value("${spring.mail.password}")
    private String password;

    /**
     * The port number of the mail server.
     */
    @Value("${spring.mail.port}")
    private int port;

    /**
     * The protocol to be used by the mail sender.
     */
    @Value("${spring.mail.protocol}")
    private String protocol;

    /**
     * Enables STARTTLS for the mail sender.
     */
    @Value("true")
    private String starttlsEnable;

    /**
     * Requires STARTTLS for the mail sender.
     */
    @Value("true")
    private String starttlsRequired;

    /**
     * Enables debug mode for the mail sender.
     */
    @Value("true")
    private String debug;

    /**
     * Configures and returns a {@link JavaMailSender} bean.
     *
     * @return a configured {@link JavaMailSender} instance
     */
    @Bean
    public JavaMailSender getMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        mailSender.setPort(port);

        Properties properties = mailSender.getJavaMailProperties();
        properties.setProperty("mail.transport.protocol", protocol);
        properties.setProperty("mail.debug", debug);
        properties.setProperty("mail.smtp.starttls.enable", starttlsEnable);
        properties.setProperty("mail.smtp.starttls.required", starttlsRequired);
        return mailSender;
    }
}
