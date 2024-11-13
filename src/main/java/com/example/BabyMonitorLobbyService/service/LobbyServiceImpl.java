package com.example.BabyMonitorLobbyService.service;

import com.example.BabyMonitorLobbyService.model.Lobby;
import com.example.BabyMonitorLobbyService.model.ActiveLobby;
import com.example.BabyMonitorLobbyService.repository.ActiveLobbyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import java.sql.SQLException;



@Service
public class LobbyServiceImpl implements LobbyService {

    private final ActiveLobbyRepository repository;
    private final List<Lobby> lobbies = new ArrayList<>();
    private Long nextLobbyId = 1L;

    @Autowired
    public LobbyServiceImpl(ActiveLobbyRepository repository) throws SQLException {
        this.repository = repository;
    }

    public void openLobby(ActiveLobby _lobby)
    {
        repository.save(_lobby);
        /*
        try
        {

            Connection con = DatabaseConnector.getActiveLobbiesConnection();
            System.out.println("Connected to active_lobbies database!");
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM active_lobbies");
            con.close();


        }
        catch ()
        {
            System.err.println("Connection failed: " + e.getMessage());
        }
        */

    }

    @Override
    public void closeLobby(int id) {

    }


}

