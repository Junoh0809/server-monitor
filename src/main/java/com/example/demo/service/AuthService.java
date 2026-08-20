package com.example.demo.service;

import com.example.demo.dto.SignUpRequest;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String signUp(SignUpRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword()); // 암호화
        User user = new User(request.getUsername(), request.getPassword());
        userRepository.save(user);
        return request.getUsername() + "회원가입 완료";
    }
}
