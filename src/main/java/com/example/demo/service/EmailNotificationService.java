package com.example.demo.service;

import org.springframework.stereotype.Component;

@Component
public class EmailNotificationService implements NotificationService {

    @Override
    public void send(String message) {
        System.out.println("이메일 발송: " + message);
    }
}
