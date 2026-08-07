package com.example.demo.dto;

public class ServerRegisterRequest {
    private String hostname;
    private String ipAddress;

    public ServerRegisterRequest() {}

    public String getHostname() {return hostname;}
    public void setHostname(String hostname) {this.hostname = hostname;}

    public String getIpAddress() {return ipAddress;}
    public void setIpAddress(String ipAddress) {this.ipAddress = ipAddress;}
}
