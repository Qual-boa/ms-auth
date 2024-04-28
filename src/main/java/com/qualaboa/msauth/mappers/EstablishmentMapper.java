package com.qualaboa.msauth.mappers;

import com.qualaboa.msauth.dto.establishment.EstablishmentCreateDTO;
import com.qualaboa.msauth.dto.establishment.EstablishmentResponseDTO;
import com.qualaboa.msauth.entities.Establishment;
import org.springframework.stereotype.Component;

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

    @Override
    public Object toDto(Establishment entity) {
        if(entity == null) return null;
        EstablishmentResponseDTO responseDTO = new EstablishmentResponseDTO(entity.getId(),
                entity.getFantasyName(),
                entity.getCnpj(),
                entity.getAverageOrderValue(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
        return responseDTO;
    }
}
