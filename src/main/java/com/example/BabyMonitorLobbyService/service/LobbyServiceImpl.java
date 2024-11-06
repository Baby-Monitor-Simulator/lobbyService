package com.example.BabyMonitorLobbyService.service;

import com.example.BabyMonitorLobbyService.model.Lobby;
import com.example.BabyMonitorLobbyService.model.Participant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LobbyServiceImpl implements LobbyService {

    private final List<Lobby> lobbies = new ArrayList<>();
    private Long nextLobbyId = 1L;

    @Override
    public Lobby createLobby(String name) {
        Lobby lobby = new Lobby(nextLobbyId++, name);
        lobbies.add(lobby);
        return lobby;
    }

    @Override
    public void closeLobby(Long id) {
        lobbies.stream()
                .filter(lobby -> lobby.getId().equals(id))
                .findFirst()
                .ifPresent(lobby -> lobby.setActive(false));
    }

    @Override
    public Lobby getLobby(Long id) {
        return lobbies.stream()
                .filter(lobby -> lobby.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Lobby> getAllLobbies() {
        return lobbies;
    }

    @Override
    public Lobby addParticipant(Long lobbyId, Participant participant) {
        Lobby lobby = getLobby(lobbyId);
        if (lobby != null && lobby.isActive()) {
            lobby.addParticipant(participant);
        }
        return lobby;
    }
}

