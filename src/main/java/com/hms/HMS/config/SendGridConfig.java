package com.hms.HMS.config;

import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendGridConfig {

    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;

    @Bean
    public SendGrid getSendGridBean() {
        return new SendGrid(sendGridApiKey);
    }
}

