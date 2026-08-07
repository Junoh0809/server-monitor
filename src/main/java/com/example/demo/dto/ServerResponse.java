package com.example.demo.dto;


import com.example.demo.entity.Server;

public class ServerResponse {
    private Long id;
    private String hostname;
    private String ipAddress;

    public ServerResponse(Server server) {
        this.id = server.getId();
        this.hostname = server.getHostname();
        this.ipAddress = server.getIpAddress();
    }

    public Long getId() {
        return id;
    }

    public String getHostname() {
        return hostname;
    }

    public String getIpAddress() {
        return ipAddress;
    }
}
