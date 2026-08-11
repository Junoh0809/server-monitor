package com.example.demo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class ServerRegisterRequest {
    @NotBlank(message = "hostname은 비어있을 수 없습니다")
    private String hostname;

    @NotBlank(message = "ipAddress는 비어있을 수 없습니다")
    private String ipAddress;

    @Min(value = 1, message = "port는 1 이상이어야 합니다")
    @Max(value = 65535, message = "port는 65535 이하여야 합니다")
    private int port;

    public ServerRegisterRequest() {}

    public String getHostname() {return hostname;}
    public void setHostname(String hostname) {this.hostname = hostname;}

    public String getIpAddress() {return ipAddress;}
    public void setIpAddress(String ipAddress) {this.ipAddress = ipAddress;}

    public int getPort() {return port;}
    public void setPort(int port) {this.port = port;}
}