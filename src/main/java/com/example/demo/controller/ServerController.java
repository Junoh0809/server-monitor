package com.example.demo.controller;

import com.example.demo.dto.ServerRegisterRequest;
import com.example.demo.dto.ServerResponse;
import com.example.demo.service.ServerMonitor;
import com.example.demo.service.ServerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ServerController {
    private final ServerMonitor serverMonitor;
    private final ServerService serverService;

    public ServerController(ServerMonitor serverMonitor, ServerService serverService) {
        this.serverMonitor = serverMonitor;
        this.serverService = serverService;
    }

    @GetMapping("/check")
    public String checkServer(@RequestParam boolean isDown) {
        serverMonitor.checkServer(isDown);
        return isDown ? "다운 감지, 알림 발송됨" : "서버 정상";
    }

    @PostMapping("/servers")
    public String registerServer(@RequestBody ServerRegisterRequest request) {
        return serverService.register(request);
    }

    @GetMapping("/servers")
    public List<ServerResponse> getServers() {
        return serverService.getAll();
    }

    @DeleteMapping("/servers/{id}")
    public String deleteServer(@PathVariable Long id) {
        return serverService.delete(id);
    }

    @PutMapping("/servers/{id}")
    public String updateServer(@PathVariable Long id, @RequestBody ServerRegisterRequest request) {
        return serverService.update(id, request);
    }
}
