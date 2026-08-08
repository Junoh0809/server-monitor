package com.example.demo.service;

import com.example.demo.dto.ServerRegisterRequest;
import com.example.demo.dto.ServerResponse;
import com.example.demo.entity.Server;
import com.example.demo.exception.ServerNotFoundException;
import com.example.demo.repository.ServerRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServerService {
    private final ServerRepository serverRepository;

    public ServerService(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    public String register(ServerRegisterRequest request) {
        System.out.println("서버 등록: " + request.getHostname() + " (" + request.getIpAddress() + ")");
        Server server = new Server(request.getHostname(), request.getIpAddress());
        serverRepository.save(server);
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
        server.setHostname(request.getHostname());
        server.setIpAddress(request.getIpAddress());
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

        for (Server server : servers) {
            boolean isReachable = pingServer(server.getIpAddress());
            String status = isReachable ? "정상" : "다운";
            System.out.println(server.getHostname() + " (" + server.getIpAddress() + "): " + status);
        }
    }

    private boolean pingServer(String ipAddress) {
        try (Socket socket = new Socket()) {
            socket.connect(new java.net.InetSocketAddress(ipAddress, 22), 2000);
            // TCP socket 방식, 특정 포트만 열어서 서버 상태 확인 가능
            return true;

            /*
            // ICMP(ping) 방식
            InetAddress address = InetAddress.getByName(ipAddress);
            return address.isReachable(5000); // timeout
             */
        } catch (IOException e) {
            return false;
        }
    }
}
