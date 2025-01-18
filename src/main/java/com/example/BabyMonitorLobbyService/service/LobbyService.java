package com.example.BabyMonitorLobbyService.service;

import com.example.BabyMonitorLobbyService.model.ActiveLobby;
import com.example.BabyMonitorLobbyService.model.Participant;

import java.util.List;

public interface LobbyService {
    ActiveLobby getLobby(Long lobbyId);
    ActiveLobby openLobby(String authHeader, String scenarioId);
    boolean activateLobby(Long lobbyId);
    boolean deactivateLobby(Long lobbyId);
    void closeLobby(long id);
}

