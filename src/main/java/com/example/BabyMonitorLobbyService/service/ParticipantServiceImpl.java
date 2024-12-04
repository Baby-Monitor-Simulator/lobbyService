package com.example.BabyMonitorLobbyService.service;

import com.example.BabyMonitorLobbyService.model.ActiveLobby;
import com.example.BabyMonitorLobbyService.model.Participant;
import com.example.BabyMonitorLobbyService.repository.ActiveLobbyRepository;
import com.example.BabyMonitorLobbyService.repository.ParticipantRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ParticipantServiceImpl implements ParticipantService {

    private final ParticipantRepository repository;

    @Autowired
    public ParticipantServiceImpl(ParticipantRepository repository) throws SQLException {
        this.repository = repository;
    }
    private final List<Participant> participants = new ArrayList<>();

    @Override
    public Participant addParticipant(Participant participant) {
        repository.save(participant);
        //participants.add(participant);
        return participant;
    }

    @Override
    public void removeParticipant(UUID id) {
        /*int index = participants.indexOf(participants.stream()
                .filter(participant -> Objects.equals(participant.getId(), id))
                .findFirst()
                .orElse(null));
        participants.remove(index);*/

        repository.deleteById(id);
    }

    @Override
    public Participant getParticipant(UUID id) {
//        return participants.stream()
//                .filter(participant -> Objects.equals(participant.getUserId(), id))
//                .findFirst()
//                .orElse(null);
        return repository.findById(id).orElse(null);
    }

//    @Override
//    public List<Participant> getParticipants(Long id) {
//        //return participants;
//        return repository.findBy(id);
//    }
}

