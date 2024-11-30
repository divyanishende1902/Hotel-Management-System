package com.hms.HMS.services.Impl;

import com.hms.HMS.services.EmailService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class EmailServiceImpl implements EmailService {


    //   @Autowired
//    private JavaMailSender mailSender;


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


//    @Value("${spring.mail.username}")
//    private String fromEmail;
//
//    public EmailServiceImpl(JavaMailSender mailSender) {
//        this.mailSender = mailSender;
//    }
//
//    @Override
//    public void sendEmail(String to, String subject, String body) {
//        try {
//            MimeMessage message = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//            helper.setFrom(fromEmail);
//            helper.setTo(to);
//            helper.setSubject(subject);
//            helper.setText(body, true);  // HTML content enabled
//
//            mailSender.send(message);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//            throw new RuntimeException("Failed to send email");
//        }
//    }


    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;

    @Value("${sendgrid.sender.email}")
    private String sendGridSenderEmail;

    @Override
    public String sendEmail(String to, String subject, String body)  {
        Email from = new Email("shendedivyani37@gmail.com"); // Verified sender email
        Email recipient = new Email(to);
        Content content = new Content("text/plain", body);
        Mail mail = new Mail(from, subject, recipient, content);

        SendGrid sendGrid = new SendGrid(sendGridApiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);

            System.out.println("Email sent successfully: " + response.getStatusCode());
            if (response.getStatusCode() == 200 || response.getStatusCode() == 202) {
                return "Email sent successfully.";
            } else {
                return "Failed to send email. Status Code: " + response.getStatusCode() + ", Response: " + response.getBody();
            }

        } catch (Exception ex) {

            throw new RuntimeException("Error while sending email: " + ex.getMessage());
        }
    }

}






