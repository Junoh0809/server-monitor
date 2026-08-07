package com.example.demo.service;

import com.example.demo.dto.ServerRegisterRequest;
import com.example.demo.dto.ServerResponse;
import com.example.demo.entity.Server;
import com.example.demo.repository.ServerRepository;
import org.springframework.stereotype.Service;

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
                .orElseThrow(() -> new RuntimeException("해당 id의 서버가 없습니다:: " + id));
        server.setHostname(request.getHostname());
        server.setIpAddress(request.getIpAddress());
        serverRepository.save(server);
        return id + "번 서버 수정 완료";
    }

    public String delete(Long id) {
        serverRepository.deleteById(id);
        return id + "번 서버 삭제 완료";
    }
}
