package com.example.BabyMonitorLobbyService.repository;
import com.example.BabyMonitorLobbyService.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, UUID>
{
    // Custom query methods (optional)
}