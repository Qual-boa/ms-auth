package com.qualaboa.msauth.mappers;

import com.qualaboa.msauth.dataContract.dtos.establishment.EstablishmentCreateDTO;
import com.qualaboa.msauth.dataContract.dtos.establishment.EstablishmentResponseDTO;
import com.qualaboa.msauth.dataContract.dtos.establishment.EstablishmentUpdateDTO;
import com.qualaboa.msauth.dataContract.entities.Establishment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EstablishmentMapper implements IMapper<Establishment> {

    @Override
    public Establishment toEntity(Object obj) {
        if(obj == null) return null;
        Establishment entity = new Establishment();
        EstablishmentCreateDTO createDTO = (EstablishmentCreateDTO) obj;
        entity.setFantasyName(createDTO.getFantasyName());
        entity.setAverageOrderValue(createDTO.getAverageOrderValue());
        entity.setCnpj(createDTO.getCnpj());
        return entity;
    }

    public Establishment fromUpdateToEntity(Object obj, Establishment entity) {
        if(obj == null) return null;
        EstablishmentUpdateDTO createDTO = (EstablishmentUpdateDTO) obj;
        entity.setFantasyName(createDTO.getFantasyName());
        entity.setCnpj(entity.getCnpj());
        entity.setAverageOrderValue(entity.getAverageOrderValue());
        entity.setCreatedAt(entity.getCreatedAt());
        return entity;
    }

    @Override
    public Object toDto(Establishment entity) {
        if(entity == null) return null;
        return new EstablishmentResponseDTO(entity.getId(),
                entity.getFantasyName(),
                entity.getCnpj(),
                entity.getAverageOrderValue(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }
    
    public List<EstablishmentResponseDTO> toDto(List<Establishment> entity) {
        if(entity == null) return null;
        List<EstablishmentResponseDTO> dtos = new ArrayList<>();
        for(Establishment e : entity) {
            dtos.add((EstablishmentResponseDTO)toDto(e));
        }
        return dtos;
    }
}