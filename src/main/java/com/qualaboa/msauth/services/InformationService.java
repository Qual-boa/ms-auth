package com.qualaboa.msauth.services;

import com.qualaboa.msauth.dataContract.dtos.establishment.EstablishmentResponseDTO;
import com.qualaboa.msauth.dataContract.dtos.information.InformationCreateDTO;
import com.qualaboa.msauth.dataContract.dtos.information.InformationResponseDTO;
import com.qualaboa.msauth.dataContract.entities.Establishment;
import com.qualaboa.msauth.dataContract.entities.Information;
import com.qualaboa.msauth.mappers.EstablishmentMapper;
import com.qualaboa.msauth.mappers.InformationMapper;
import com.qualaboa.msauth.repositories.EstablishmentRepository;
import com.qualaboa.msauth.repositories.InformationRepository;
import com.qualaboa.msauth.services.exceptions.ConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class InformationService {

    @Autowired
    private InformationRepository repository;

    @Autowired
    private EstablishmentRepository establishmentRepository;

    @Autowired
    private EstablishmentMapper establishmentMapper;

    @Autowired
    private InformationMapper infoMapper;

    public EstablishmentResponseDTO save(InformationCreateDTO informationCreateDTO, UUID establishmentId) {
        if(existsInfo(establishmentId)) throw new ConflictException("Information already exists");
        Optional<Establishment> establishmentEntity = establishmentRepository.findById(establishmentId);
        if(establishmentEntity.isEmpty()) throw new IllegalArgumentException("Establishment not found");

        LocalTime closeAt = LocalTime.of(informationCreateDTO.getCloseAt().getHour(), informationCreateDTO.getCloseAt().getMinute());
        LocalTime openAt = LocalTime.of(informationCreateDTO.getOpenAt().getHour(), informationCreateDTO.getOpenAt().getMinute());

        Information entity = infoMapper.toEntity(informationCreateDTO);
        establishmentEntity.get().setInformation(repository.save(entity));
        return (EstablishmentResponseDTO) establishmentMapper.toDto(establishmentRepository.save(establishmentEntity.get()));
    }

    private boolean existsInfo(UUID establishmentId){
        return repository.existsByEstablishmentId(establishmentId);
    }
}
