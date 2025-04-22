package com.example.BabyMonitorLobbyService.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "lobby_archief")
public class LobbyArchief {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lobbyid")
    private Integer id;

    @Column(name = "ownerid", nullable = false)
    private UUID ownerid;

    @Column(name = "scenarioid", nullable = false)
    private String scenarioid;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "date", nullable = false)
    private Date date;

    public LobbyArchief() {
    }

    public LobbyArchief(int id, UUID ownerid, String scenarioid, boolean active, Date date) {
        this.id = id;
        this.ownerid = ownerid;
        this.scenarioid = scenarioid;
        this.active = active;
        this.date = date;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UUID getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(UUID ownerid) {
        this.ownerid = ownerid;
    }

    public String getScenarioid() {
        return scenarioid;
    }

    public void setScenarioid(String scenarioid) {
        this.scenarioid = scenarioid;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
