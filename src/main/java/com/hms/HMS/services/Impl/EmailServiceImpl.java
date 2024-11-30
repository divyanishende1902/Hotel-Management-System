package com.hms.HMS.services.Impl;

import com.hms.HMS.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {


    @Autowired
    private JavaMailSender mailSender;


//    @Override
//    public void sendEmail(String to, String subject, String body) {
//
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setTo(to);
//            message.setSubject(subject);
//            message.setText(body);
//
//            mailSender.send(message);
//
//    }


    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);  // HTML content enabled

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send email");
        }
    }
}




