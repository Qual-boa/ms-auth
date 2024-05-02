package com.qualaboa.msauth.mappers;

import com.qualaboa.msauth.dataContract.dtos.establishment.EstablishmentCreateDTO;
import com.qualaboa.msauth.dataContract.dtos.establishment.EstablishmentResponseDTO;
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

    public Establishment toEntity(EstablishmentResponseDTO obj) {
        if(obj == null) return null;
        Establishment entity = new Establishment();
        entity.setId(obj.id());
        entity.setCreatedAt(obj.createdAt());
        entity.setCnpj(obj.cnpj());
        entity.setFantasyName(obj.fantasyName());
        entity.setAverageOrderValue(obj.averageOrderValue());
        entity.setUpdatedAt(obj.updatedAt());
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
