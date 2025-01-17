package com.example.BabyMonitorLobbyService.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "active_lobbies")
public class ActiveLobby {

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
    //private List<Participant> participants;

    public ActiveLobby(int id, UUID _ownerid, String _scenarioid, boolean _active) {
        this.id = id;
        this.ownerid = _ownerid;
        this.scenarioid = _scenarioid;
        this.active = _active;  // Lobbies are active by default when created
    }


    public ActiveLobby(UUID _ownerid, String _scenarioid) {
        this.ownerid = _ownerid;
        this.scenarioid = _scenarioid;
        this.active = false;
    }


    public ActiveLobby() {

    }


    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UUID getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(UUID ownerid) {
        this.ownerid = ownerid;
    }

    public String getScenarioid(){ return scenarioid;}

    public void setScenarioid(String scenarioid){ this.scenarioid = scenarioid; }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}