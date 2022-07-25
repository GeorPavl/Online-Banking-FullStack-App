package com.demobank._config;

import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class MailConfig {

    public static JavaMailSenderImpl getMailConfig() {
        JavaMailSenderImpl emailConfig = new JavaMailSenderImpl();

        // Set Properties:
        Properties props = emailConfig.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        // Set Mail Credentials:
        emailConfig.setHost("smtp.gmail.com");
        emailConfig.setPort(587);
        emailConfig.setUsername("georpavloglou@gmail.com");
        emailConfig.setPassword("mhnriidazfaruatr");

        return emailConfig;
    }
}
