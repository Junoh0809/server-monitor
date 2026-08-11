package com.example.demo.service;

import com.example.demo.dto.ServerRegisterRequest;
import com.example.demo.dto.ServerResponse;
import com.example.demo.entity.Server;
import com.example.demo.exception.ServerNotFoundException;
import com.example.demo.repository.ServerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServerService {
    private static final Logger log = LoggerFactory.getLogger(ServerService.class);
    private final ServerRepository serverRepository;

    public ServerService(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    public String register(ServerRegisterRequest request) {
        log.info("서버 등록 요청: hostname={}, ip={}, port={}", request.getHostname(), request.getIpAddress(), request.getPort());

        Server server = new Server(request.getHostname(), request.getIpAddress(), request.getPort());
        serverRepository.save(server);
        log.info("서버 등록 완료: id={}", server.getId());
        return request.getHostname() + " 등록 완료";
    }

    public List<ServerResponse> getAll() {
        List<Server> servers = serverRepository.findAll();
        return servers.stream()
                .map(server -> new ServerResponse(server))
                .collect(Collectors.toList());
    }

    public String update(Long id, ServerRegisterRequest request) {
        Server server = serverRepository.findById(id)
                .orElseThrow(() -> new ServerNotFoundException(id));

        log.info("서버 수정: id={}, {} -> {}", id, server.getHostname(), request.getHostname());

        server.setHostname(request.getHostname());
        server.setIpAddress(request.getIpAddress());
        server.setPort(request.getPort());
        serverRepository.save(server);

        return id + "번 서버 수정 완료";
    }

    public String delete(Long id) {
        serverRepository.deleteById(id);
        return id + "번 서버 삭제 완료";
    }

    @Scheduled(fixedRate = 10000)  // fixedRate 단위: ms
    public void checkAllServer() {
        List<Server> servers = serverRepository.findAll();

        if (servers.isEmpty()) {
            log.debug("등록된 서버가 업어 헬스체크를 건너뜁니다.");
        }

        for (Server server : servers) {
            boolean isReachable = pingServer(server.getIpAddress(), server.getPort());
            if (isReachable) {
                log.info("{} ({}:{}) - 정상", server.getHostname(), server.getIpAddress(), server.getPort());
            } else {
                log.info("{} ({}:{}) - 다운 감지!", server.getHostname(), server.getIpAddress(), server.getPort());
            }
        }
    }

    private boolean pingServer(String ipAddress, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new java.net.InetSocketAddress(ipAddress, port), 2000);
            // TCP socket 방식, 특정 포트만 열어서 서버 상태 확인 가능
            return true;
        } catch (IOException e) {
            log.debug("연결 실패: {}:{} - {}", ipAddress, port, e.getMessage());
            return false;
        }
    }
}
