package com.example.BabyMonitorLobbyService.model;

import java.util.ArrayList;
import java.util.List;

public class Lobby {
    private Long id;
    private String name;
    private boolean isActive;
    private List<Participant> participants;

    public Lobby() {
        this.participants = new ArrayList<>();
    }

    public Lobby(Long id, String name) {
        this.id = id;
        this.name = name;
        this.isActive = true;  // Lobbies are active by default when created
        this.participants = new ArrayList<>();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    public void addParticipant(Participant participant) {
        this.participants.add(participant);
    }
}