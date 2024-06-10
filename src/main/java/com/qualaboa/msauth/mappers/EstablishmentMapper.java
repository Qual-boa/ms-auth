package com.qualaboa.msauth.mappers;

import com.qualaboa.msauth.dataContract.dtos.establishment.*;
import com.qualaboa.msauth.dataContract.dtos.information.InformationResponseDTO;
import com.qualaboa.msauth.dataContract.entities.Category;
import com.qualaboa.msauth.dataContract.entities.Establishment;
import com.qualaboa.msauth.dataContract.entities.Relationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EstablishmentMapper implements IMapper<Establishment> {

    @Autowired
    InformationMapper informationMapper;

    @Override
    public Establishment toEntity(Object obj) {
        if(obj == null) return null;
        Establishment entity = new Establishment();
        EstablishmentCreateDTO createDTO = (EstablishmentCreateDTO) obj;
        entity.setFantasyName(createDTO.getFantasyName());
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
        EstablishmentResponseDTO dto = new EstablishmentResponseDTO();
        dto.setFantasyName(entity.getFantasyName());
        dto.setCnpj(entity.getCnpj());
        dto.setAverageOrderValue(entity.getAverageOrderValue());
        dto.setId(entity.getId());
        dto.setCategories(entity.getCategories() == null ? new ArrayList<>() : categoryToDTO(entity.getCategories()));
        dto.setRelationships(entity.getRelationships() == null ? new ArrayList<>() : relationshipToDTO(entity.getRelationships()));
        dto.setInformation(entity.getInformation() == null ? null : (InformationResponseDTO) informationMapper.toDto(entity.getInformation()));
        return dto;
    }
    
    public List<CategoryResponseDTO> categoryToDTO(List<Category> categories) {
        List<CategoryResponseDTO> categoriesDto = new ArrayList<>();
        for(Category category : categories) {
            CategoryResponseDTO categoryDto = new CategoryResponseDTO();
            categoryDto.setCategory(category.getId().getCategory());
            categoryDto.setCategoryType(category.getId().getCategoryType());
            categoryDto.setName(category.getName());
            categoriesDto.add(categoryDto);
        }
        return categoriesDto;
    }
    
    public List<RelationshipResponseDTO> relationshipToDTO(List<Relationship> relationships) {
        List<RelationshipResponseDTO> relationshipsDto = new ArrayList<>();
        for(Relationship relationship : relationships) {
            RelationshipResponseDTO relationshipDto = new RelationshipResponseDTO();
            relationshipDto.setUserId(relationship.getId().getUserId());
            relationshipDto.setInteractionType(relationship.getId().getInteractionType());
            relationshipDto.setRate(relationship.getRate() == null ? 0 : relationship.getRate());
            relationshipDto.setMessage(relationship.getMessage() == null ? "" : relationship.getMessage());
            relationshipsDto.add(relationshipDto);
        }
        return relationshipsDto;
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
