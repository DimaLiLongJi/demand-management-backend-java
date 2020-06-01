package com.demand.management.service;

public interface EmailService {
    void sendEmail(String toUserEmail, String fromUserName, String demandId);
}
