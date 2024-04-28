package com.qualaboa.msauth.services;

import com.qualaboa.msauth.dto.establishment.EstablishmentCreateDTO;
import com.qualaboa.msauth.dto.establishment.EstablishmentResponseDTO;
import com.qualaboa.msauth.entities.Establishment;
import com.qualaboa.msauth.mappers.EstablishmentMapper;
import com.qualaboa.msauth.repositories.EstablishmentRepository;
import com.qualaboa.msauth.services.interfaces.IServiceSave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class EstablishmentService implements IServiceSave<EstablishmentCreateDTO, EstablishmentResponseDTO> {

    @Autowired
    private EstablishmentRepository repository;

    @Autowired
    private EstablishmentMapper mapper;

    @Override
    @Transactional
    public EstablishmentResponseDTO save(EstablishmentCreateDTO establishmentCreateDTO) {
        Establishment entity = mapper.toEntity(establishmentCreateDTO);
        entity.setCreatedAt(LocalDateTime.now());
        entity = repository.save(entity);
        return (EstablishmentResponseDTO) mapper.toDto(entity);
    }

}
