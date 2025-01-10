package com.example.BabyMonitorLobbyService.repository;
import com.example.BabyMonitorLobbyService.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, UUID>
{
    List<Participant> findByUserId(UUID participantId);
}