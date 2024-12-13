package com.example.BabyMonitorLobbyService.model;

import jakarta.persistence.*;

import java.util.UUID;


@Table(name = "active_lobbies")
public class ActiveLobby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lobbyid")
    private Integer id;
    @Column(name = "ownerid", nullable = false)
    private UUID ownerid;
    @Column(name = "simulationid", nullable = false)
    private Integer simulationid;
    @Column(name = "active", nullable = false)
    private boolean active;
    //private List<Participant> participants;

    public ActiveLobby(int id, UUID _ownerid, int _simulationid, boolean _active) {
        this.id = id;
        this.ownerid = _ownerid;
        this.simulationid = _simulationid;
        this.active = _active;  // Lobbies are active by default when created
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

    public int getSimulationid(){ return simulationid;}

    public void setSimulationid(int simulationid){ this.simulationid = simulationid; }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}