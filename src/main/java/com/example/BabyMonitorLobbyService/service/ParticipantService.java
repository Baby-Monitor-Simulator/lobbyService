package com.example.BabyMonitorLobbyService.service;

import com.example.BabyMonitorLobbyService.model.Participant;

import java.util.List;
import java.util.UUID;

public interface ParticipantService {
    void removeParticipant(UUID id);
    Participant getParticipant(UUID id);
    //List<Participant> getParticipants(Long id);
    Participant addParticipant(Participant participant);
}

