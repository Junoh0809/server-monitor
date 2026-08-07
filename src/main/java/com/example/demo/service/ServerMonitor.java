package com.example.demo.service;

import org.springframework.stereotype.Component;

@Component
public class ServerMonitor {
    private final NotificationService notificationService;

    public ServerMonitor(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void checkServer(boolean isDown) {
        if (isDown) {
            notificationService.send("server is DOWN!");
        }
    }
}
