package com.example.BabyMonitorLobbyService.service;

import com.example.BabyMonitorLobbyService.config.RabbitMQConfig;
import com.example.BabyMonitorLobbyService.model.events.ParticipantAction;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSenderService {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMQSenderService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendParticipantAction(ParticipantAction action) {
        System.out.println("Sending participant action: " + action);
        rabbitTemplate.convertAndSend(RabbitMQConfig.MATLAB_QUEUE, action);
    }
}
