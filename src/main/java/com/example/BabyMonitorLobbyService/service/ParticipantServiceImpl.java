package com.example.BabyMonitorLobbyService.service;

import com.example.BabyMonitorLobbyService.model.ActiveLobby;
import com.example.BabyMonitorLobbyService.model.Participant;
import com.example.BabyMonitorLobbyService.repository.ActiveLobbyRepository;
import com.example.BabyMonitorLobbyService.repository.ParticipantRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.security.interfaces.RSAPublicKey;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ParticipantServiceImpl implements ParticipantService {
    @Value("${jwt.rs256}")
    private String rsaPublicKeyString;

    private final ParticipantRepository repository;

    @Autowired
    public ParticipantServiceImpl(ParticipantRepository repository) throws SQLException {
        this.repository = repository;
    }
    private final List<Participant> participants = new ArrayList<>();

    @Override
    public ResponseEntity<Object> addParticipant(Participant participant, HttpServletRequest request) {
        UUID userid = extractSubject(request);
        if (userid != null) {
            if (participant.getUserId().equals(userid)) {
                if (!isInLobby(participant.getUserId())) {
                    return ResponseEntity
                            .status(HttpStatus.OK)
                            .body(repository.save(participant));
                }
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body("User already in a lobby");
            }
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Given userIDs were not the same");
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("No userID could be found in token");
    }

    @Override
    public ResponseEntity<Object> removeParticipant(UUID id, HttpServletRequest request) {
        UUID userid = extractSubject(request);
        if (userid != null) {
            if (id.equals(userid)) {
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
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Given userIDs were not the same");
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("No userID could be found in token");
    }

    @Override
    public ResponseEntity<Object> getParticipant(UUID id, HttpServletRequest request) {
        Participant participant = repository.findById(id).orElse(null);
        if (participant != null)
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(participant);

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("User is not in a lobby");
    }


    private UUID extractSubject(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        System.out.println("Authorization Header: " + bearerToken);

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            // Remove "Bearer " prefix
            String token = bearerToken.substring(7);

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
        }
        return null;
    }

    private boolean isInLobby(UUID userId) {
        return repository.findById(userId).isPresent();
    }
}

