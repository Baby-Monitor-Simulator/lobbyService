package com.example.BabyMonitorLobbyService.model;

public class NewLobbyResponseDTO {
    private Integer lobbyid;

    public NewLobbyResponseDTO(Integer lobbyid) {
        this.lobbyid = lobbyid;
    }

    public Integer getLobbyid() {
        return lobbyid;
    }

    public void setLobbyid(Integer lobbyid) {
        this.lobbyid = lobbyid;
    }
}

