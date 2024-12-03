package com.example.BabyMonitorLobbyService.controller;

import com.example.BabyMonitorLobbyService.model.Participant;
import com.example.BabyMonitorLobbyService.model.events.ParticipantAction;
import com.example.BabyMonitorLobbyService.service.ParticipantService;
import com.example.BabyMonitorLobbyService.service.RabbitMQSenderService;
import com.example.BabyMonitorLobbyService.service.RsaKeyUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.micrometer.common.lang.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.UUID;

@RestController
@RequestMapping("/participant")
public class ParticipantController {

    @Value("${jwt.secret}")
    private String jwtSecret;

    // Load RSA public key from properties file (or you can use a hardcoded key for testing)
    @Value("${jwt.rs256}")
    private String rsaPublicKeyString;

    private final ParticipantService participantService;
    private final RabbitMQSenderService senderService;

    @Autowired
    public ParticipantController(ParticipantService participantService, @Nullable RabbitMQSenderService senderService) {
        this.participantService = participantService;
        this.senderService = senderService;
    }

    @PostMapping
    @PreAuthorize("hasRole('deelnemer')")
    public Participant addParticipant(@RequestBody Participant participant, HttpServletRequest request) {
        //TEMP!!
        UUID userid = extractSubject(request);
        if (userid != null) {
            //request.
            if (isSameUser(userid, participant.getUserId())) {
                if (!isInLobby(participant.getUserId())) {
                    return participantService.addParticipant(participant);
                }
            }
        }
        return null;
    }

    @PostMapping("/MQ")
    public void mQ(@RequestBody ParticipantAction action) {
        senderService.sendParticipantAction(action);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('deelnemer')")
    public boolean removeParticipant(@PathVariable UUID id) {
        //TEMP!!
        UUID userid = id;
        if (isSameUser(userid, id)) {
            if (isInLobby(id)) {
                participantService.removeParticipant(id);
                return true;
            }
        }
        return false;
    }

    @GetMapping("/current/{id}")
    public Participant geParticipant(@PathVariable UUID id) { return participantService.getParticipant(id); }

    private boolean isSameUser(Object request, UUID userId) {
        return request == userId;
    }

    private boolean isInLobby(UUID userId) {
        return participantService.getParticipant(userId) != null;
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
}