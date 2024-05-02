package com.qualaboa.msauth.services;

import com.qualaboa.msauth.dataContract.dtos.establishment.EstablishmentResponseDTO;
import com.qualaboa.msauth.dataContract.dtos.information.InformationCreateDTO;
import com.qualaboa.msauth.dataContract.dtos.information.InformationResponseDTO;
import com.qualaboa.msauth.dataContract.entities.Establishment;
import com.qualaboa.msauth.dataContract.entities.Information;
import com.qualaboa.msauth.mappers.EstablishmentMapper;
import com.qualaboa.msauth.mappers.InformationMapper;
import com.qualaboa.msauth.repositories.InformationRepository;
import com.qualaboa.msauth.services.exceptions.ConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Service
public class InformationService {

    @Autowired
    private InformationRepository repository;

    @Autowired
    private EstablishmentService establishmentService;

    @Autowired
    private EstablishmentMapper establishmentMapper;

    @Autowired
    private InformationMapper infoMapper;

    public InformationResponseDTO save(InformationCreateDTO informationCreateDTO, UUID establishmentId) {
        if(existsInfo(establishmentId)) throw new ConflictException("Information already exists");
        EstablishmentResponseDTO establishmentResponseDTO = establishmentService.findById(establishmentId);
        Establishment establishmentEntity = establishmentMapper.toEntity(establishmentResponseDTO);

        LocalTime closeAt = LocalTime.of(informationCreateDTO.getCloseAt().getHour(), informationCreateDTO.getCloseAt().getMinute());
        LocalTime openAt = LocalTime.of(informationCreateDTO.getOpenAt().getHour(), informationCreateDTO.getOpenAt().getMinute());

        Information entity = infoMapper.toEntity(establishmentEntity, informationCreateDTO);
        entity.setOpenAt(openAt);
        entity.setCloseAt(closeAt);
        entity.setCreatedAt(LocalDateTime.now());
        entity = repository.save(entity);
        return (InformationResponseDTO) infoMapper.toDto(entity);
    }

    private boolean existsInfo(UUID establishmentId){
        return repository.existsByOwnerId(establishmentId);
    }
}
