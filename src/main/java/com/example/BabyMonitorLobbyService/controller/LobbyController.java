package com.example.BabyMonitorLobbyService.controller;

import com.example.BabyMonitorLobbyService.model.Lobby;
import com.example.BabyMonitorLobbyService.model.Participant;
import com.example.BabyMonitorLobbyService.model.events.ParticipantAction;
import com.example.BabyMonitorLobbyService.service.LobbyService;
import com.example.BabyMonitorLobbyService.service.RabbitMQSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lobbies")
public class LobbyController {

    private final LobbyService lobbyService;
    private RabbitMQSenderService senderService;

    @Autowired
    public LobbyController(LobbyService lobbyService, RabbitMQSenderService senderService) {
        this.lobbyService = lobbyService;
        this.senderService = senderService;
    }

    @PostMapping
    public Lobby createLobby(@RequestParam String name) {
        System.out.println("yepypepyepeeeppp");
        return lobbyService.createLobby(name);
    }

    @PostMapping("/MQ")
    public void mQ(@RequestBody ParticipantAction action) {
        System.out.println("yepypepyepeeeppp");
        senderService.sendParticipantAction(action);
    }

    @DeleteMapping("/{id}")
    public void closeLobby(@PathVariable Long id) {
        lobbyService.closeLobby(id);
    }

    @GetMapping("/{id}")
    public Lobby getLobby(@PathVariable Long id) {
        return lobbyService.getLobby(id);
    }

    @GetMapping
    public List<Lobby> getAllLobbies() {
        return lobbyService.getAllLobbies();
    }

    @PostMapping("/{id}/participants")
    public Lobby addParticipant(@PathVariable Long id, @RequestBody Participant participant) {
        return lobbyService.addParticipant(id, participant);
    }
}
