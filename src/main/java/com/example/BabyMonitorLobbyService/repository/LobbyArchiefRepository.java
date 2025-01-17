package com.example.BabyMonitorLobbyService.repository;

import com.example.BabyMonitorLobbyService.model.LobbyArchief;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LobbyArchiefRepository extends JpaRepository<LobbyArchief, Integer> {

}
