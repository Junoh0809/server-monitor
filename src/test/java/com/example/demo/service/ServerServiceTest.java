package com.example.demo.service;

import com.example.demo.dto.ServerRegisterRequest;
import com.example.demo.entity.Server;
import com.example.demo.exception.ServerNotFoundException;
import com.example.demo.repository.ServerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;
import static org.assertj.core.api.InstanceOfAssertFactories.OPTIONAL;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ServerServiceTest {

    @Mock
    private ServerRepository serverRepository;

    @InjectMocks
    private  ServerService serverService;

    @Test
    void server_register_success() {
        ServerRegisterRequest request = new ServerRegisterRequest();
        request.setHostname("test-server");
        request.setIpAddress("1.1.1.1");
        request.setPort(22);

        String result = serverService.register(request);

        assertThat(result).isEqualTo("test-server 등록 완료");
        verify(serverRepository).save(any(Server.class));
    }

    @Test
    void server_update_success() {
        Long id = 1L;
        Server existingServer = new Server("old-name", "1.1.1.1", 22);

        ServerRegisterRequest request = new ServerRegisterRequest();
        request.setHostname("new-name");
        request.setIpAddress("2.2.2.2");
        request.setPort(80);

        given(serverRepository.findById(id)).willReturn(Optional.of(existingServer));

        String result = serverService.update(id, request);

        assertThat(result).isEqualTo(id + "번 서버 수정 완료");
        assertThat(existingServer.getHostname()).isEqualTo("new-name");
        verify(serverRepository).save(existingServer);
    }

    @Test
    void server_update_failed_nonexistent_id() {
        Long id = 999L;
        ServerRegisterRequest request = new ServerRegisterRequest();

        given(serverRepository.findById(id)).willReturn(Optional.empty());

        assertThrows(ServerNotFoundException.class, () -> {
            serverService.update(id, request);
        });
    }

    @Test
    void server_delete_success() {
        Long id = 1L;

        String result = serverService.delete(id);

        assertThat(result).isEqualTo(id + "번 서버 삭제 완료");
        verify(serverRepository).deleteById(id);
    }
}
