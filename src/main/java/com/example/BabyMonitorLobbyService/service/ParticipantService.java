package com.example.BabyMonitorLobbyService.service;

import com.example.BabyMonitorLobbyService.model.Participant;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface ParticipantService {
    ResponseEntity<Object> removeParticipant(UUID id);
    ResponseEntity<Object> getParticipant(UUID id);
    ResponseEntity<Object> addParticipant(Participant participant);
    List<Participant> getAllLobbyParticipants(Integer lobbyId);
}

