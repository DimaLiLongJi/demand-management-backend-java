package com.demand.management.impl;

import com.demand.management.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    JavaMailSender javaMailSender;

    @Value("${self-config.base-content-url}")
    private String baseContentUrl;

    @Override
    public void sendEmail(String toUserEmail, String fromUserName, String demandId) {
        String contentUrl = baseContentUrl + demandId;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("小伙子，你被安排了");
        message.setFrom("cm_hl_email@163.com");
        message.setTo(toUserEmail);
        message.setSentDate(new Date());
        message.setText("<p>" + fromUserName + "给你安排了一个需求，请打开<a href=\"" + contentUrl +"\">" + "</a></p>");
        javaMailSender.send(message);
    }
}
