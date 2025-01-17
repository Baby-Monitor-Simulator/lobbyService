package com.example.BabyMonitorLobbyService.controller;

import com.example.BabyMonitorLobbyService.model.ActiveLobby;
import com.example.BabyMonitorLobbyService.model.NewLobbyDTO;
import com.example.BabyMonitorLobbyService.model.NewLobbyResponseDTO;
import com.example.BabyMonitorLobbyService.model.events.ParticipantAction;
import com.example.BabyMonitorLobbyService.service.LobbyService;
import com.example.BabyMonitorLobbyService.service.RabbitMQSenderService;
import io.micrometer.common.lang.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lobbies")
public class LobbyController {

    private final LobbyService lobbyService;
    private final RabbitMQSenderService senderService;
    private final SimpMessagingTemplate template;

    @Autowired
    public LobbyController(LobbyService lobbyService, @Nullable RabbitMQSenderService senderService, SimpMessagingTemplate simpMessagingTemplate) {
        this.lobbyService = lobbyService;
        this.senderService = senderService;
        this.template = simpMessagingTemplate;
    }


    @PostMapping("/NewLobby")
//    @PreAuthorize("hasRole('instructeur')")
    public ResponseEntity<?> newLobby(@RequestBody NewLobbyDTO lobbyDTO, @RequestHeader("Authorization") String authorization) {
        System.out.println("Trying to start new lobby...");

        // Check if lobby is allowed, if so, create it
        ActiveLobby savedLobby = lobbyService.openLobby(authorization, lobbyDTO.getScenarioid());

        if (savedLobby.getId() != -1){
            NewLobbyResponseDTO responseDTO = new NewLobbyResponseDTO(savedLobby.getId());
            return new ResponseEntity<>(responseDTO, HttpStatusCode.valueOf(200));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }


    @PostMapping("/MQ")
    public void mQ(@RequestBody ParticipantAction action) {
        senderService.sendParticipantAction(action);
    }

    @DeleteMapping("/{id}")
    public void closeLobby(@PathVariable int id) {
        lobbyService.closeLobby(id);
        // Add to closed lobby DB
    }

}
