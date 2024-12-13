package com.example.BabyMonitorLobbyService.repository;
import com.example.BabyMonitorLobbyService.model.ActiveLobby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActiveLobbyRepository extends JpaRepository<ActiveLobby, Long>
{
    // Custom query methods (optional)
}