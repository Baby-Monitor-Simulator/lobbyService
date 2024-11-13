package com.example.BabyMonitorLobbyService.service;


import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    // Method to get a connection to the active_lobbies database
    public static Connection getActiveLobbiesConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5420/active_lobbies";
        String user = "user";
        String password = "password";

        return DriverManager.getConnection(url, user, password);
    }
}