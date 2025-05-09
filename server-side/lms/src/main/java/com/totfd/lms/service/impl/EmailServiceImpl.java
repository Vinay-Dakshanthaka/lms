package com.totfd.lms.service.impl;

import com.totfd.lms.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private static Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendVerificationEmail(String toEmail, String token) {
        try {
            String subject = "Verify your email";
            String link = "http://localhost:8080/api/user/verify?token=" + token;
            String body = "Click the link to verify your account:\n" + link;

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("vinayd@totfd.com");
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);
//            System.out.println("Mail sent successfully to " + toEmail);
            logger.info("Mail Sent Successfully to {}", toEmail);
        } catch (Exception e) {
//            System.err.println("Failed to send email: " + e.getMessage());
            logger.error("Failed to send email to {}: {}", toEmail, e.getMessage(), e);
//            e.printStackTrace();
        }
    }
}
