package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

public class SignUpRequest {

    @NotBlank(message = "username은 비어있을 수 없습니다")
    private String username;

    @NotBlank(message = "password는 비어있을 수 없습니다")
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
