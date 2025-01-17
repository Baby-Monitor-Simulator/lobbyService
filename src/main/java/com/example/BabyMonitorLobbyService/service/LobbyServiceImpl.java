package com.example.BabyMonitorLobbyService.service;

import com.example.BabyMonitorLobbyService.model.ActiveLobby;
import com.example.BabyMonitorLobbyService.model.LobbyArchief;
import com.example.BabyMonitorLobbyService.model.Participant;
import com.example.BabyMonitorLobbyService.repository.ActiveLobbyRepository;
import com.example.BabyMonitorLobbyService.repository.LobbyArchiefRepository;
import com.example.BabyMonitorLobbyService.repository.ParticipantRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPublicKey;
import java.util.*;

import java.sql.SQLException;

@Service
public class LobbyServiceImpl implements LobbyService {
    @Value("${jwt_rsa256}")
    private String rsaPublicKeyString;
    private final ActiveLobbyRepository repository;
    private final ParticipantRepository participantRepository;
    private final LobbyArchiefRepository lobbyArchiefRepository;
    private final List<ActiveLobby> lobbies = new ArrayList<>();
    private Long nextLobbyId = 1L;

    @Autowired
    public LobbyServiceImpl(ActiveLobbyRepository repository, ParticipantRepository participantRepository, LobbyArchiefRepository lobbyArchiefRepository) throws SQLException {
        this.repository = repository;
        this.participantRepository = participantRepository;
        this.lobbyArchiefRepository = lobbyArchiefRepository;
    }

    public ActiveLobby openLobby(String authHeader, String scenarioId)
    {
        UUID UUID = extractSubject(authHeader);
        ActiveLobby savedLobby = new ActiveLobby();
        savedLobby.setId(-1);
        boolean isAllowed = true;
        // Check whether the user already has an open lobby
        List<ActiveLobby> ownedLobbies =  repository.findByOwnerid(UUID);

        if (!ownedLobbies.isEmpty()){
            isAllowed = false;
        }

        // Check whether the user is currently part of a lobby
        List<Participant> participants = participantRepository.findByUserId(UUID);
        if (!participants.isEmpty()){
            Long lobbyId = participants.get(0).getLobbyId();
            Optional<ActiveLobby> participatedLobbies = repository.findById(lobbyId);
            if (participatedLobbies.isPresent()){
                isAllowed = false;
            }
        }

        if (isAllowed){
            ActiveLobby lobby = new ActiveLobby(UUID, scenarioId);

            savedLobby = repository.save(lobby);
        }

        return savedLobby;
    }

    public boolean activateLobby(Long lobbyId) {
        // Zoek de lobby op in de database
        Optional<ActiveLobby> optionalLobby = repository.findById(lobbyId);

        if (optionalLobby.isPresent()) {
            ActiveLobby lobby = optionalLobby.get();
            // Zet de lobby actief
            lobby.setActive(true);
            repository.save(lobby); // Sla de wijziging op
            return true;
        }

        // Lobby niet gevonden
        return false;
    }

    public boolean deactivateLobby(Long lobbyId) {
        // Zoek de lobby op in de database
        Optional<ActiveLobby> optionalLobby = repository.findById(lobbyId);

        if (optionalLobby.isPresent()) {
            ActiveLobby lobby = optionalLobby.get();
            // Zet de lobby inactief
            lobby.setActive(false);
            repository.save(lobby); // Sla de wijziging op
            return true;
        }

        // Lobby niet gevonden
        return false;
    }

    public void closeLobby(long id) {
        Optional<ActiveLobby> optionalLobby = repository.findById(id);

        if (optionalLobby.isPresent()) {
            ActiveLobby activeLobby = optionalLobby.get();

            // Verplaats gegevens naar LobbyArchief
            LobbyArchief lobbyArchief = new LobbyArchief(
                    activeLobby.getId(),
                    activeLobby.getOwnerid(),
                    activeLobby.getScenarioid(),
                    activeLobby.getActive(),
                    new Date() // Archiveringsdatum
            );
            lobbyArchiefRepository.save(lobbyArchief);

            // Verwijder de actieve lobby
            repository.deleteById(id);
        }
    }





    public ActiveLobby getLobby(Long lobbyId) {
        return repository.findById(lobbyId).orElse(null);
    }




    private UUID extractSubject(String authHeader) {
        String token = authHeader.substring(7);

        try {
            // Load the RSA public key
            RSAPublicKey publicKey = RsaKeyUtil.getPublicKey(rsaPublicKeyString);

            // Parse the JWT and extract the claims
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(publicKey)  // Use RSA public key here
                    .build()
                    .parseClaimsJws(token)   // Use the stripped token
                    .getBody();

            // Extract and return the "sub" claim as UUID
            return UUID.fromString(claims.getSubject());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

