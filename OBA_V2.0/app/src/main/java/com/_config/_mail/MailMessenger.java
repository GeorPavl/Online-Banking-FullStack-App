package com._config._mail;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class MailMessenger {

    public static final String emailFrom = "georpavloglou@gmail.com";
    public static final String emailTitle = "Account Verification";

    public static void htmlEmailMessenger(
            String from, String toMail, String subject, String body) throws MessagingException{
        // Get Mail Config
        JavaMailSender sender = MailConfig.getMailConfig();
        // Set Mime Message
        MimeMessage message = sender.createMimeMessage();
        // Set Mime Message Helper
        MimeMessageHelper htmlMessage = new MimeMessageHelper(message, true);

        // Set Mail Attributes / Properties
        htmlMessage.setFrom(from);
        htmlMessage.setTo(toMail);
        htmlMessage.setSubject(subject);
        htmlMessage.setText(body, true);

        // Send Message
        sender.send(message);
    }
}
