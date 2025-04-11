package com.example.BabyMonitorLobbyService.service;

import com.example.BabyMonitorLobbyService.model.ActiveLobby;
import com.example.BabyMonitorLobbyService.model.Participant;
import com.example.BabyMonitorLobbyService.repository.ParticipantRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import java.security.interfaces.RSAPublicKey;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ParticipantServiceImpl implements ParticipantService {
    @Value("${jwt_rsa256}")
    private String rsaPublicKeyString;

    private final ParticipantRepository repository;
    private final LobbyService lobbyService;

    @Autowired
    public ParticipantServiceImpl(ParticipantRepository repository, LobbyService lobbyService) throws SQLException {
        this.repository = repository;
        this.lobbyService = lobbyService;
    }
    private final List<Participant> participants = new ArrayList<>();

    @Override
    public ResponseEntity<Object> addParticipant(Participant participant, HttpServletRequest request) {
        //Extract subject from the JWT, subject is the UserID
        UUID userid = getCurrentUserId();

        //Get the lobby the user would like to join
        ActiveLobby lobby = lobbyService.getLobby(participant.getLobbyId());

        //Check if the lobby is active
        if (lobby == null || lobby.getActive()) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    //.body(String.format("Lobby with ID %s cannot be found or is inactive", participant.getLobbyId()));
                    .body("Lobby cannot be found or is active");
        }

        //Is the subject null?
        if (userid == null) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body("No userID could be found in JWT token");
        }

        if (!participant.getUserId().equals(userid)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Given userIDs were not the same");
        }

        if (lobby.getOwnerid().equals(userid)) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Owner cannot join lobby");
        }

        if (!isInLobby(participant.getUserId())) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(repository.save(participant));
        }
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("User already in a lobby");
    }

    @Override
    public ResponseEntity<Object> removeParticipant(UUID id, HttpServletRequest request) {
        UUID userid = getCurrentUserId();
        if (userid == null) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body("No userID could be found in token");
        }

        if (!id.equals(userid)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Given userIDs were not the same");
        }

        if (isInLobby(id)) {
            repository.deleteById(id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("User removed from lobby");
        }
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("User is not in a lobby");
    }

    @Override
    public ResponseEntity<Object> getParticipant(UUID id) {
        System.out.println("Getting participant with id: " + id);
        Participant participant = repository.findById(id).orElse(null);
        if (participant != null)
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(participant);
        System.out.println("Participant not found");
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("User is not in a lobby");
    }


    @Override
    public List<Participant> getAllLobbyParticipants(Integer lobbyId){
        return repository.findAllByLobbyId(lobbyId);
    }

    private boolean isInLobby(UUID userId) {
        return repository.findById(userId).isPresent();
    }

    private UUID getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            String sub = jwt.getSubject();
            if (sub != null) {
                try {
                    return UUID.fromString(sub);
                } catch (IllegalArgumentException e) {
                    System.err.println("Error parsing subject claim as UUID: " + sub);
                }
            }
        }
        System.err.println("Could not get authenticated user ID from Security Context.");
        return null; // Or throw exception
    }
}

