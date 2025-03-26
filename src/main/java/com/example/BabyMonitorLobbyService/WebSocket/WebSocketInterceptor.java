package com.example.BabyMonitorLobbyService.WebSocket;

import com.example.BabyMonitorLobbyService.model.ActiveLobby;
import com.example.BabyMonitorLobbyService.model.Participant;
import com.example.BabyMonitorLobbyService.repository.ActiveLobbyRepository;
import com.example.BabyMonitorLobbyService.service.LobbyService;
import com.example.BabyMonitorLobbyService.service.ParticipantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class WebSocketInterceptor implements ChannelInterceptor {
    private final Logger logger = LoggerFactory.getLogger(WebSocketInterceptor.class);
    private final ParticipantService participantService;

    private final LobbyService lobbyService;

    @Autowired
    public WebSocketInterceptor(ParticipantService participantService, LobbyService lobbyService) {
        this.participantService = participantService;
        this.lobbyService = lobbyService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        logger.debug("WebSocket message intercepted, command: {}", accessor.getCommand());

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String userId = accessor.getFirstNativeHeader("userId");
            String sessionId = accessor.getSessionId();

            logger.debug("Connection attempt with userId: {}, sessionId: {}", userId, sessionId);

            if (userId != null && sessionId != null) {
                logger.info("User connected: ID = {}, Session = {}", userId, sessionId);
                WebSocketSessionManager.addSession(sessionId, userId);
            } else {
                logger.warn("Connection attempt missing userId or sessionId");
            }
        }
        return message;
    }
}