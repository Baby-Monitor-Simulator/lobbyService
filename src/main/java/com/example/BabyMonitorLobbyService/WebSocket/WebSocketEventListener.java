package com.example.BabyMonitorLobbyService.WebSocket;

import com.example.BabyMonitorLobbyService.model.ActiveLobby;
import com.example.BabyMonitorLobbyService.model.Participant;
import com.example.BabyMonitorLobbyService.repository.ActiveLobbyRepository;
import com.example.BabyMonitorLobbyService.service.LobbyService;
import com.example.BabyMonitorLobbyService.service.ParticipantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class WebSocketEventListener {
    private final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    private final LobbyService lobbyService;
    private final ActiveLobbyRepository repository;
    private final ParticipantService participantService;

    @Autowired
    public WebSocketEventListener(LobbyService lobbyService, ActiveLobbyRepository repository, ParticipantService participantService) {
        this.lobbyService = lobbyService;
        this.repository = repository;
        this.participantService = participantService;
        logger.info("WebSocketEventListener initialized");
    }



    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
        String userId = WebSocketSessionManager.getUserId(sessionId);
        if (userId != null) {
            List<ActiveLobby> ownedlobbies = repository.findByOwnerid(UUID.fromString(userId));
            if (!ownedlobbies.isEmpty()){
                long lobbyId = ownedlobbies.get(0).getId();
                lobbyService.closeLobby(lobbyId);
            }
        }
    }


}