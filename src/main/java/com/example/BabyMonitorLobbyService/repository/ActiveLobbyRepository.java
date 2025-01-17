package com.example.BabyMonitorLobbyService.repository;
import com.example.BabyMonitorLobbyService.model.ActiveLobby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ActiveLobbyRepository extends JpaRepository<ActiveLobby, Long>
{
    List<ActiveLobby> findByOwnerid(UUID id);
}