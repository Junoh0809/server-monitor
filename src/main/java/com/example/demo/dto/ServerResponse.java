package com.example.demo.dto;


import com.example.demo.entity.Server;

public class ServerResponse {
    private Long id;
    private String hostname;
    private String ipAddress;
    private int port;

    public ServerResponse(Server server) {
        this.id = server.getId();
        this.hostname = server.getHostname();
        this.ipAddress = server.getIpAddress();
        this.port = server.getPort();
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

    public int getPort() {
        return port;
    }
}
