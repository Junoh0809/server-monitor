package com.example.demo.service;

import com.example.demo.dto.ServerRegisterRequest;
import com.example.demo.entity.Server;
import com.example.demo.repository.ServerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ServerServiceTest {

    @Mock
    private ServerRepository serverRepository;

    @InjectMocks
    private  ServerService sserverService;

    @Test
    void server_register_success() {
        ServerRegisterRequest request = new ServerRegisterRequest();
        request.setHostname("test-server");
        request.setIpAddress("1.1.1.1");
        request.setPort(22);

        String result = sserverService.register(request);

        assertThat(result).isEqualTo("test-server 등록 완료");
        verify(serverRepository).save(any(Server.class));
    }
}
