package com.example.BabyMonitorLobbyService.controller;

import com.example.BabyMonitorLobbyService.model.ActiveLobby;
import com.example.BabyMonitorLobbyService.model.Lobby;
import com.example.BabyMonitorLobbyService.model.Participant;
import com.example.BabyMonitorLobbyService.model.events.ParticipantAction;
import com.example.BabyMonitorLobbyService.service.LobbyService;
import com.example.BabyMonitorLobbyService.service.RabbitMQSenderService;
import io.micrometer.common.lang.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lobbies")
public class LobbyController {

    private final LobbyService lobbyService;
    private final RabbitMQSenderService senderService;

    @Autowired
    public LobbyController(LobbyService lobbyService, @Nullable RabbitMQSenderService senderService) {
        this.lobbyService = lobbyService;
        this.senderService = senderService;
    }

    @PostMapping("/NewLobby")
    public ResponseEntity<?> newLobby(@RequestBody ActiveLobby lobbyRequest) {

        ActiveLobby newLobby = new ActiveLobby(lobbyRequest.getId(), lobbyRequest.getOwner(), lobbyRequest.getSimulationid(), lobbyRequest.getActive());

        lobbyService.openLobby(newLobby);

        return new ResponseEntity<>(newLobby, HttpStatusCode.valueOf(200));
    }


    @PostMapping("/MQ")
    public void mQ(@RequestBody ParticipantAction action) {
        senderService.sendParticipantAction(action);
    }

    @DeleteMapping("/{id}")
    public void closeLobby(@PathVariable int id) {
        lobbyService.closeLobby(id);
    }

}
