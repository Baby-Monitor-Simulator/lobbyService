package com.example.BabyMonitorLobbyService.service;

import com.example.BabyMonitorLobbyService.model.ActiveLobby;
import com.example.BabyMonitorLobbyService.model.Participant;

import java.util.List;

public interface LobbyService {
    void closeLobby(int id);
    void openLobby(ActiveLobby _lobby);
}

