package com.hms.HMS.services;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}
