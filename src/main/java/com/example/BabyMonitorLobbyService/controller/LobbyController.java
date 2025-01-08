package com.example.BabyMonitorLobbyService.controller;

import com.example.BabyMonitorLobbyService.model.ActiveLobby;
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
    //@PreAuthorize("hasRole('Instructeur')")
    public ResponseEntity<?> newLobby(@RequestBody ActiveLobby lobbyRequest) {
//        System.out.println("id:" + lobbyRequest.getId());
//        System.out.println("owner:"+ lobbyRequest.getOwnerid());
//        System.out.println("simulation:" + lobbyRequest.getSimulationid());
//        System.out.println("activity:" + lobbyRequest.getActive());
        ActiveLobby newLobby = new ActiveLobby(lobbyRequest.getId(), lobbyRequest.getOwnerid(), lobbyRequest.getSimulationid(), lobbyRequest.getActive());

        lobbyService.openLobby(newLobby);

        //websocket
        template.convertAndSend("/lobbies/" + newLobby.getId(), String.valueOf(newLobby.getId()));
        return new ResponseEntity<>(newLobby, HttpStatusCode.valueOf(200));
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
