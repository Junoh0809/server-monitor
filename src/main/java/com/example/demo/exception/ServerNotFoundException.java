package com.example.demo.exception;

public class ServerNotFoundException extends RuntimeException {
    public ServerNotFoundException(Long id) {
        super("해당 id의 서버가 없습니다 : " + id);
    }
}
