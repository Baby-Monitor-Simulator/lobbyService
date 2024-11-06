package com.example.BabyMonitorLobbyService.service;

import com.example.BabyMonitorLobbyService.config.RabbitMQConfig;
import com.example.BabyMonitorLobbyService.model.events.SimulationUpdate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQListenerService {

    @RabbitListener(queues = RabbitMQConfig.LOBBY_QUEUE)
    public void receiveSimulationUpdate(SimulationUpdate update) {
        System.out.println("Received Simulation Update: Lobby ID = " + update.getLobbyId() + ", Status = " + update.getStatus());
        // Process the update as needed
    }
}
