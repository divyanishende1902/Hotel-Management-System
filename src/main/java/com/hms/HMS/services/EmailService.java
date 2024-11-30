package com.hms.HMS.services;

public interface EmailService {
    String sendEmail(String to, String subject, String body);
}
