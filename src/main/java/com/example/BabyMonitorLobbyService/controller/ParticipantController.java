package com.example.BabyMonitorLobbyService.controller;

import com.example.BabyMonitorLobbyService.model.Participant;
import com.example.BabyMonitorLobbyService.model.events.ParticipantAction;
import com.example.BabyMonitorLobbyService.service.JwtAuthConverter;
import com.example.BabyMonitorLobbyService.service.ParticipantService;
import com.example.BabyMonitorLobbyService.service.RabbitMQSenderService;
import io.micrometer.common.lang.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/participant")
public class ParticipantController {
    @Autowired
    private JwtAuthConverter tokenProvider;

    private final ParticipantService participantService;
    private final RabbitMQSenderService senderService;

    @Autowired
    public ParticipantController(ParticipantService participantService, @Nullable RabbitMQSenderService senderService) {
        this.participantService = participantService;
        this.senderService = senderService;
    }

    @PostMapping
    @PreAuthorize("hasRole('deelnemer')")
    public ResponseEntity<Object> addParticipant(@RequestBody Participant participant, HttpServletRequest request) {
        return participantService.addParticipant(participant, request);
    }

    @PostMapping("/MQ")
    public void mQ(@RequestBody ParticipantAction action) {
        senderService.sendParticipantAction(action);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('deelnemer')")
    public ResponseEntity<Object> removeParticipant(@PathVariable UUID id, HttpServletRequest request) {
        return participantService.removeParticipant(id, request);
    }

    @GetMapping("/current/{id}")
    public ResponseEntity<Object> geParticipant(@PathVariable UUID id, HttpServletRequest request) {
        return participantService.getParticipant(id, request);
    }
}