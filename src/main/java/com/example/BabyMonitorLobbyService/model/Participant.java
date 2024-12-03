package com.example.BabyMonitorLobbyService.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "participants")
public class Participant {
    @Id
    @Column(name = "userid", nullable = false)
    private UUID userId;
    @Column(name = "lobbyid", nullable = false)
    private Long lobbyId;

    public Participant() {}

    public Participant(UUID userId, Long lobbyId) {
        this.userId = userId;
        this.lobbyId = lobbyId;
    }

    // Getters and Setters
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Long getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(Long lobbyId) {
        this.lobbyId = lobbyId;
    }
}

