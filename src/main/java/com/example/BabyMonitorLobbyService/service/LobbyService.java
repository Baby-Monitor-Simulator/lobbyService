package com.example.BabyMonitorLobbyService.service;

import com.example.BabyMonitorLobbyService.model.Lobby;
import com.example.BabyMonitorLobbyService.model.Participant;

import java.util.List;

public interface LobbyService {
    Lobby createLobby(String name);
    void closeLobby(Long id);
    Lobby getLobby(Long id);
    List<Lobby> getAllLobbies();
    Lobby addParticipant(Long lobbyId, Participant participant);
}

