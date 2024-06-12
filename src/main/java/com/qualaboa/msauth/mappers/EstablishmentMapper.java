package com.qualaboa.msauth.mappers;

import com.qualaboa.msauth.dataContract.dtos.establishment.*;
import com.qualaboa.msauth.dataContract.dtos.information.InformationResponseDTO;
import com.qualaboa.msauth.dataContract.dtos.relationship.RelationshipResponseDTO;
import com.qualaboa.msauth.dataContract.entities.Category;
import com.qualaboa.msauth.dataContract.entities.Establishment;
import com.qualaboa.msauth.dataContract.entities.Relationship;
import com.qualaboa.msauth.dataContract.entities.RelationshipEmbeddedId;
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
        entity.setAverageOrderValue(createDTO.getAverageOrderValue());
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
    
    public CategoryResponseDTO categoryToDTO(Category category) {
        if(category == null) return null;
        CategoryResponseDTO categoryDto = new CategoryResponseDTO();
        categoryDto.setCategory(category.getId().getCategory());
        categoryDto.setCategoryType(category.getId().getCategoryType());
        categoryDto.setName(category.getName());
        categoryDto.setSearchesCount(category.getCountSearches());
        return categoryDto;
    }

    public List<CategoryResponseDTO> categoryToDTO(List<Category> categories) {
        if(categories == null || categories.isEmpty()) return null;
        List<CategoryResponseDTO> categoriesDto = new ArrayList<>();
        for(Category category : categories) {
            categoriesDto.add(categoryToDTO(category));
        }
        return categoriesDto;
    }
    
    public RelationshipResponseDTO relationshipToDTO(Relationship relationship) {
        if(relationship == null) return null;
        RelationshipResponseDTO relationshipDto = new RelationshipResponseDTO();
        if (relationship.getMessage() != null) relationshipDto.setMessage(relationship.getMessage());
        if (relationship.getRate() != null) relationshipDto.setRate(relationship.getRate());
        if (relationship.getId().getEstablishmentId() != null) relationshipDto.setEstablishmentId((relationship.getId().getEstablishmentId()));
        if (relationship.getId().getUserId() != null) relationshipDto.setUserId((relationship.getId().getUserId()));
        relationshipDto.setInteractionType(relationship.getId().getInteractionType());
        return relationshipDto;
    }

    public List<RelationshipResponseDTO> relationshipToDTO(List<Relationship> relationships) {
        if(relationships == null || relationships.isEmpty()) return null;
        List<RelationshipResponseDTO> relationshipDto = new ArrayList<>();
        for(Relationship relationship : relationships) {
            relationshipDto.add(relationshipToDTO(relationship));
        }
        return relationshipDto;
    }
    
    public Relationship relationshipToEntity(RelationshipResponseDTO dto) {
        if(dto == null) return null;
        Relationship relationship = new Relationship();
        RelationshipEmbeddedId id = new RelationshipEmbeddedId();
        id.setEstablishmentId(dto.getEstablishmentId());
        id.setUserId(dto.getUserId());
        id.setInteractionType(dto.getInteractionType());
        relationship.setId(id);
        if(dto.getMessage() != null) relationship.setMessage(dto.getMessage());
        if(dto.getRate() != null) relationship.setRate(dto.getRate());
        return relationship;
    }
    
    public List<Relationship> relationshipToEntity(List<RelationshipResponseDTO> dtos){
        if(dtos == null || dtos.isEmpty()) return null;
        List<Relationship> relationships = new ArrayList<>();
        for(RelationshipResponseDTO dto : dtos) {
            relationships.add(relationshipToEntity(dto));
        }
        return relationships;
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
