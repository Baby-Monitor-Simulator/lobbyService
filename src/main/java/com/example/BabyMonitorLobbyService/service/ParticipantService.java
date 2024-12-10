package com.example.BabyMonitorLobbyService.service;

import com.example.BabyMonitorLobbyService.model.Participant;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface ParticipantService {
    ResponseEntity<Object> removeParticipant(UUID id, HttpServletRequest request);
    ResponseEntity<Object> getParticipant(UUID id, HttpServletRequest request);
    //List<Participant> getParticipants(Long id);
    ResponseEntity<Object> addParticipant(Participant participant, HttpServletRequest request);
}

